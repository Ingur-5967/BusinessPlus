package ru.solomka.business.core.shop;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.HandlerBusiness;
import ru.solomka.business.core.message.Messages;
import ru.solomka.business.vault.VaultHandler;

import static ru.solomka.business.config.ConfigManager.getInt;

public class ShopOperationHandler {

    private final VaultHandler vault = new VaultHandler();

    public void onBuyBusiness(int slot, @NotNull Player p) {
        int bizId = new HandlerBusiness().getBizId(slot);

        if(bizId == -1) return;

        if(new HandlerBusiness().isHaveBusiness(p, bizId)) {
            Messages.alreadyHaveBiz.replace("{id}", String.valueOf(bizId)).send(p);
            return;
        }

        if(!vault.takeMoney(p, getInt("Business.Id." + bizId + ".Cost"))) {
            Messages.noMoney.replace("{need_money}", String.valueOf(getInt("Business.Id." + bizId + ".Cost"))).send(p);
            return;
        }

        new CustomConfig("data").setValue("players." + p.getUniqueId() + ".Business." + bizId, "");
        Messages.buyBusiness.replace("{id}", String.valueOf(bizId)).replace("{cost}", String.valueOf(getInt("Business.Id." + bizId + ".Cost"))).send(p);
        p.closeInventory();
    }
}
