package ru.solomka.business.menu.enums;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.config.utils.CustomConfig;

import static ru.solomka.business.config.ConfigManager.getInt;

public enum ItemType {
    
    BUY_BIZ(new ItemStack(Material.getMaterial(new CustomConfig("menu").getString("Menu.Main.BuySlot.Item")))),
    UPDATE_BIZ(new ItemStack(Material.getMaterial(new CustomConfig("menu").getString("Menu.Main.UpdateSlot.Item")))),
    DEFAULT_ITEM(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) getInt("Business.GlobalSettings.ColorGlassPane"))),
    LOW_CLASS(new ItemStack(Material.getMaterial(new CustomConfig("menu").getString("Menu.ChangeClassBiz.LowClass.Item")))),
    MEDIUM_CLASS(new ItemStack(Material.getMaterial(new CustomConfig("menu").getString("Menu.ChangeClassBiz.MediumClass.Item")))),
    DENY_SLOT(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14)),
    HIGH_CLASS(new ItemStack(Material.getMaterial(new CustomConfig("menu").getString("Menu.ChangeClassBiz.HighClass.Item"))));

    @Getter private final ItemStack itemStack;

    ItemType(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
