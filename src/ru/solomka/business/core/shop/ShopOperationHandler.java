package ru.solomka.business.core.shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.HandlerBusiness;
import ru.solomka.business.core.enums.UpdateCategoryType;
import ru.solomka.business.core.message.Messages;
import ru.solomka.business.menu.enums.ItemType;
import ru.solomka.business.vault.VaultHandler;

import static ru.solomka.business.config.ConfigManager.getInt;

public class ShopOperationHandler {

    private final HandlerBusiness biz = new HandlerBusiness();
    private final VaultHandler vault = new VaultHandler();
    private final CustomConfig data = new CustomConfig("data");

    public void onBuyBusiness(@NotNull Inventory inventory, int slot, @NotNull Player p) {

        if(inventory.getItem(slot) == ItemType.DEFAULT_ITEM.getItemStack()) return;

        int bizId = biz.getBizId(slot);

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

    public void redirectingToMenu(int slot, @NotNull Player p) {
        data.setValue("players." + p.getUniqueId() + ".OtherInfo", biz.getBizId(slot));
    }

    public void onUpdateBusiness(int slot, Player p, UpdateCategoryType update) {
        int bizId = biz.getBizId(slot);

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