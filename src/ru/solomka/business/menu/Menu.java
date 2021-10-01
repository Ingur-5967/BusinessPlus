package ru.solomka.business.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.HandlerBusiness;
import ru.solomka.business.commands.utils.methods.enums.BusinessClassType;
import ru.solomka.business.menu.enums.InventoryType;
import ru.solomka.business.menu.enums.ItemType;
import ru.solomka.business.menu.utils.ClassBusiness;
import ru.solomka.business.menu.utils.Items;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static org.bukkit.configuration.file.YamlConfiguration.*;
import static ru.solomka.business.config.ConfigManager.*;
import static ru.solomka.business.config.utils.Data.*;

public class Menu {

    private Inventory mainMenu;
    private Inventory changeClassBiz;
    private Inventory updateBiz;
    private Inventory lowClassBiz;
    private Inventory mediumClassBiz;
    private Inventory highClassBiz;

    private final ClassBusiness biz = new ClassBusiness();
    private final HandlerBusiness handlerBusiness = new HandlerBusiness();
    private final Items items = new Items();

    public void create(Player p, int slots, @NotNull InventoryType type) {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        switch (type) {
            case CHANGE_CLASS : {
                if (changeClassBiz == null) changeClassBiz = Bukkit.createInventory(null, slots, "Выбор класса");
                setItems(file, p, changeClassBiz, type, ItemType.DEFAULT_ITEM, ItemType.HIGH_CLASS, ItemType.MEDIUM_CLASS, ItemType.LOW_CLASS);
                p.openInventory(changeClassBiz);
                return;
            }

            case LOW_CLASS : {
                if (lowClassBiz == null) lowClassBiz = Bukkit.createInventory(null, slots, "Низкий класс");
                setItems(file, p, lowClassBiz, type, ItemType.DEFAULT_ITEM);
                p.openInventory(lowClassBiz);
                return;
            }

            case MEDIUM_CLASS : {
                if (mediumClassBiz == null) mediumClassBiz = Bukkit.createInventory(null, slots, "Элитный класс");
                setItems(file, p, mediumClassBiz, type, ItemType.DEFAULT_ITEM);
                p.openInventory(mediumClassBiz);
                return;
            }

            case HIGH_CLASS : {
                if (highClassBiz == null) highClassBiz = Bukkit.createInventory(null, slots, "Бизнес класс");
                setItems(file, p, highClassBiz, type, ItemType.DEFAULT_ITEM);
                p.openInventory(highClassBiz);
                return;
            }

            case UPDATE : {
                if (updateBiz == null) updateBiz = Bukkit.createInventory(null, slots, "Улучшение");
                setItems(file, p, updateBiz, type, ItemType.DEFAULT_ITEM);
                p.openInventory(updateBiz);
                return;
            }

            case MAIN : {
                if (mainMenu == null) mainMenu = Bukkit.createInventory(null, slots, "Главное меню");
                setItems(file, p, mainMenu, type, ItemType.DEFAULT_ITEM, ItemType.BUY_BIZ, ItemType.UPDATE_BIZ, ItemType.DEV_BIZ);
                p.openInventory(mainMenu);
            }
        }
    }

    public void setItems(File file, Player p, Inventory inventory, @NotNull InventoryType type, ItemType... item) {
        FileConfiguration config = loadConfiguration(file);
        switch (type) {
            case CHANGE_CLASS : {
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);

                inventory.setItem(15, items.setItemMeta(item[1].getItemStack(), getStringList("Menu.ChangeClassBiz.HighClass.Lore"),
                        getString("Menu.ChangeClassBiz.HighClass.Name")));
                inventory.setItem(22, items.setItemMeta(item[2].getItemStack(), getStringList("Menu.ChangeClassBiz.MediumClass.Lore"),
                        getString("Menu.ChangeClassBiz.MediumClass.Name")));
                inventory.setItem(11, items.setItemMeta(item[3].getItemStack(), getStringList("Menu.ChangeClassBiz.LowClass.Lore"),
                        getString("Menu.ChangeClassBiz.LowClass.Name")));
                return;
            }

            case MAIN : {
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);

                inventory.setItem(11, items.setItemMeta(item[1].getItemStack(), getStringList("Menu.Main.BuySlot.Lore"),
                        getString("Menu.Main.BuySlot.Name")));
                inventory.setItem(15, items.setItemMeta(item[2].getItemStack(), getStringList("Menu.Main.UpdateSlot.Lore"),
                        getString("Menu.Main.UpdateSlot.Name")));
                inventory.setItem(22, items.setItemMeta(item[3].getItemStack(), getStringList("Menu.Main.DevelopBusiness.Lore"),
                        getString("Menu.Main.DevelopBusiness.Name")));
                return;
            }

            case UPDATE: {
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);

                for(int i : getIntegerList("Business.RegistrationId")) {
                    if(getDataString(config,"players." + p.getUniqueId() + "." + i) == null) continue;
                    for (int slots = 0; slots < inventory.getSize(); slots++) {
                        if(inventory.getItem(slots) != null) {
                            if(Objects.equals(inventory.getItem(slots), new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7))) continue;
                            slots++;
                        }
                        inventory.setItem(slots, items.setItemMeta(config.getItemStack("Business.Id." + i + ".Item"),
                                getStringList("Business.Id." + i + ".Lore"), getString("Business.Id." + i + ".Name")));
                    }
                    return;
                }
            }

            case LOW_CLASS : {
                if(handlerBusiness.isSizeMoreLimit(BusinessClassType.LOW)) return;

                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                for(int i : biz.getListBizId(BusinessClassType.LOW)) {
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"),
                            items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))), getStringList("Business.Id." + i + ".Lore"),
                                    getString("Business.Id." + i + ".Name")));
                }
            }

            case MEDIUM_CLASS : {
                if(handlerBusiness.isSizeMoreLimit(BusinessClassType.MEDIUM)) return;

                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                for(int i : biz.getListBizId(BusinessClassType.MEDIUM)) {
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"),
                            items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))), getStringList("Business.Id." + i + ".Lore"),
                                    getString("Business.Id." + i + ".Name")));
                }
                return;
            }

            case HIGH_CLASS : {
                if(handlerBusiness.isSizeMoreLimit(BusinessClassType.HIGH)) return;

                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                for(int i : biz.getListBizId(BusinessClassType.HIGH)) {
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"),
                            items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))), getStringList("Business.Id." + i + ".Lore"),
                                    getString("Business.Id." + i + ".Name")));
                }
                return;
            }

            case DEVELOP_BIZ : {
                for(int i : handlerBusiness.getAllHaveBiz(config, p)) {
                    for(int slots = 0; slots < inventory.getSize(); i++) {
                        if(inventory.getItem(slots) != null) {
                            i++;
                            continue;
                        }
                        inventory.setItem(slots, items.setItemMeta(config.getItemStack("Business.Id." + i + ".Item"),
                                getStringList("Business.Id." + i + ".Lore"), getString("Business.Id." + i + ".Name")));

                    }
                }
            }
        }
    }
}