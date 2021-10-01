package ru.solomka.business.menu.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Items {
    public ItemStack setItemMeta(@NotNull ItemStack item, @NotNull List<String> lore, String name) {
        ItemMeta meta = item.getItemMeta();
        lore.replaceAll(s -> translateAlternateColorCodes('&', s));
        meta.setLore(lore);
        meta.setDisplayName(translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return item;
    }
    public void setItemInv(ItemStack item, Inventory inventory,
                           int startSlot, int finalSlot) {
        if(inventory == null) return;
        for (int i = startSlot; i < finalSlot; i++) {
            if (inventory.getItem(i) != null) {
                i++;
            }
            inventory.setItem(i, item);
        }
    }

}
