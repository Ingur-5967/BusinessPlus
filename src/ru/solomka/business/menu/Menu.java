package ru.solomka.business.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.HandlerBusiness;
import ru.solomka.business.core.enums.BusinessClassType;
import ru.solomka.business.menu.enums.InventoryType;
import ru.solomka.business.menu.enums.ItemType;
import ru.solomka.business.menu.utils.ClassBusiness;
import ru.solomka.business.menu.utils.Items;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.business.config.ConfigManager.*;
import static ru.solomka.business.core.message.utils.ListHandler.replace;

public class Menu {

    private Inventory mainMenu;
    private Inventory changeClassBiz;
    private Inventory lowClassBiz;
    private Inventory mediumClassBiz;
    private Inventory highClassBiz;

    private final ClassBusiness biz = new ClassBusiness();
    private final Items items = new Items();

    private final CustomConfig menu = new CustomConfig("menu");

    public void create(Player p, int slots, @NotNull InventoryType type) {
        switch (type) {
            case CHANGE_CLASS : {
                if (changeClassBiz == null) changeClassBiz = Bukkit.createInventory(null, slots, "Выбор класса");
                setItems(p, changeClassBiz, type, ItemType.DEFAULT_ITEM, ItemType.HIGH_CLASS, ItemType.MEDIUM_CLASS, ItemType.LOW_CLASS);
                p.openInventory(changeClassBiz);
                return;
            }

            case LOW_CLASS : {
                if (lowClassBiz == null) lowClassBiz = Bukkit.createInventory(null, slots, "Покупка бизнеса");
                setItems(p, lowClassBiz, type, ItemType.DEFAULT_ITEM);
                p.openInventory(lowClassBiz);
                return;
            }

            case MEDIUM_CLASS : {
                if (mediumClassBiz == null) mediumClassBiz = Bukkit.createInventory(null, slots, "Покупка бизнеса");
                setItems(p, mediumClassBiz, type, ItemType.DEFAULT_ITEM);
                p.openInventory(mediumClassBiz);
                return;
            }

            case HIGH_CLASS : {
                if (highClassBiz == null) highClassBiz = Bukkit.createInventory(null, slots, "Покупка бизнеса");
                setItems(p, highClassBiz, type, ItemType.DEFAULT_ITEM);
                p.openInventory(highClassBiz);
                return;
            }

            case MAIN : {
                if (mainMenu == null) mainMenu = Bukkit.createInventory(null, slots, "Главное меню");
                setItems(p, mainMenu, type, ItemType.DEFAULT_ITEM, ItemType.BUY_BIZ);
                p.openInventory(mainMenu);
            }
        }
    }

    public void setItems(Player p, Inventory inventory, @NotNull InventoryType type, ItemType... item) {
        switch (type) {
            case CHANGE_CLASS : {
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);

                inventory.setItem(15, items.setItemMeta(item[1].getItemStack(), menu.getList("Menu.ChangeClassBiz.HighClass.Lore"),
                        menu.getString("Menu.ChangeClassBiz.HighClass.Name")));
                inventory.setItem(22, items.setItemMeta(item[2].getItemStack(), menu.getList("Menu.ChangeClassBiz.MediumClass.Lore"),
                        menu.getString("Menu.ChangeClassBiz.MediumClass.Name")));
                inventory.setItem(11, items.setItemMeta(item[3].getItemStack(), menu.getList("Menu.ChangeClassBiz.LowClass.Lore"),
                        menu.getString("Menu.ChangeClassBiz.LowClass.Name")));
                return;

            }

            case MAIN : {
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                inventory.setItem(13, items.setItemMeta(item[1].getItemStack(), menu.getConfig().getStringList("Menu.Main.BuySlot.Lore"),
                        menu.getConfig().getString("Menu.Main.BuySlot.Name")));
                return;
            }

            case LOW_CLASS : {
                if(!new HandlerBusiness().isSizeMoreLimit(BusinessClassType.LOW)) return;

                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                for(int i : biz.getListBizId(BusinessClassType.LOW)) {
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"),
                            items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))), replace("{status}", translateAlternateColorCodes('&', biz.status(p, i))).list(getStringList("Business.Id." + i + ".Lore")),
                                    getString("Business.Id." + i + ".Name")));
                }
                return;
            }

            case MEDIUM_CLASS : {
                if(!new HandlerBusiness().isSizeMoreLimit(BusinessClassType.MEDIUM)) return;

                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                for(int i : biz.getListBizId(BusinessClassType.MEDIUM)) {
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"),
                            items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))), replace("{status}", translateAlternateColorCodes('&', biz.status(p, i))).list(getStringList("Business.Id." + i + ".Lore")),
                                    getString("Business.Id." + i + ".Name")));
                }
                return;
            }

            case HIGH_CLASS : {
                if(!new HandlerBusiness().isSizeMoreLimit(BusinessClassType.HIGH)) return;
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                for(int i : biz.getListBizId(BusinessClassType.HIGH)) {
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"),
                            items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))), replace("{status}", translateAlternateColorCodes('&', biz.status(p, i))).list(getStringList("Business.Id." + i + ".Lore")),
                                    getString("Business.Id." + i + ".Name")));
                }
            }
        }
    }
}