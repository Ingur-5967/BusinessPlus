package ru.solomka.business.menu.enums;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static ru.solomka.business.config.ConfigManager.getString;

public enum ItemType {
    
    BUY_BIZ(new ItemStack(Material.getMaterial(getString("Menu.Main.BuySlot.Item")))),
    DEV_BIZ(new ItemStack(Material.getMaterial(getString("Menu.Main.DevelopBusiness.Item")))),
    UPDATE_BIZ(new ItemStack(Material.getMaterial(getString("Menu.Main.UpdateSlot.Item")))),
    DEFAULT_ITEM(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7)),
    LOW_CLASS(new ItemStack(Material.getMaterial(getString("Menu.ChangeClassBiz.LowClass.Item")))),
    MEDIUM_CLASS(new ItemStack(Material.getMaterial(getString("Menu.ChangeClassBiz.MediumClass.Item")))),
    HIGH_CLASS(new ItemStack(Material.getMaterial(getString("Menu.ChangeClassBiz.HighClass.Item"))));

    @Getter private final ItemStack itemStack;

    ItemType(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
