package ru.solomka.business.core.shop;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.HandlerBusiness;
import ru.solomka.business.core.enums.UpdateCategoryType;
import ru.solomka.business.core.message.Messages;
import ru.solomka.business.vault.VaultHandler;

import static ru.solomka.business.config.ConfigManager.getInt;

public class ShopOperationHandler {

    @Getter private int slot;

    public ShopOperationHandler(int slot) {
        this.slot = slot;
    }

    private final HandlerBusiness biz = new HandlerBusiness();
    private final VaultHandler vault = new VaultHandler();

    private final CustomConfig data = new CustomConfig("data");
    private final int bizId = biz.getBizId(slot);

    public void onBuyBusiness(Player p) {

        if(biz.isHaveBusiness(p, bizId)) {
            Messages.alreadyHaveBiz.replace("{id}", String.valueOf(bizId)).send(p);
            return;
        }

        if(!vault.takeMoney(p, getInt("Business.Id." + bizId + ".Cost"))) {
            Messages.noMoney.replace("{need_money}", String.valueOf(getInt("Business.Id." + bizId + ".Cost"))).send(p);
            return;
        }
        data.setValue("players." + p.getUniqueId() + ".Business." + bizId + ".Updates", "");
        Messages.buyBusiness.replace("{id}", String.valueOf(bizId)).replace("{cost}", String.valueOf(getInt("Business.Id." + bizId + ".Cost"))).send(p);
        p.closeInventory();
    }

    public void redirectingToMenu(@NotNull Player p) {
        data.setValue("players." + p.getUniqueId() + ".OtherInfo", bizId);
    }

    public void onUpdateBusiness(Player p, UpdateCategoryType update) {
        if(!biz.isHaveBusiness(p, bizId)) {
            Messages.notHaveBiz.send(p);
            return;
        }

        if(!vault.takeMoney(p, getInt("Business.GlobalSettings.Update.Biz." + update.getUpdate() + ".Cost"))) {
            Messages.noMoney.replace("{need_money}", String.valueOf(getInt("Business.GlobalSettings.Update.Biz." + update.getUpdate() + ".Cost"))).send(p);
            return;
        }
        data.getList("players." + p.getUniqueId() + ".Business." + bizId + ".Updates").add(update.getUpdate());
        Messages.buyUpdate.replace("{id}", String.valueOf(bizId)).replace("{cost}", String.valueOf(getInt("Business.Id." + bizId + ".Cost"))).send(p);
        p.closeInventory();
    }
}