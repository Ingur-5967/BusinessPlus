package ru.solomka.business.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.config.ConfigManager;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.HandlerBusiness;
import ru.solomka.business.core.enums.BusinessClassType;
import ru.solomka.business.core.message.utils.ListHandler;
import ru.solomka.business.menu.enums.InventoryType;
import ru.solomka.business.menu.enums.ItemType;
import ru.solomka.business.menu.utils.ClassBusiness;
import ru.solomka.business.menu.utils.Items;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.business.config.ConfigManager.*;
import static ru.solomka.business.core.message.utils.ListHandler.*;
import static ru.solomka.business.core.message.utils.ListHandler.replace;

public class Menu {

    private Inventory mainMenu;
    private Inventory changeClassBiz;
    private Inventory updateBiz;
    private Inventory changeUpdate;
    private Inventory lowClassBiz;
    private Inventory mediumClassBiz;
    private Inventory highClassBiz;

    private final ClassBusiness biz = new ClassBusiness();
    private final HandlerBusiness handlerBusiness = new HandlerBusiness();
    private final Items items = new Items();

    private final CustomConfig custom = new CustomConfig("menu");
    private final CustomConfig data = new CustomConfig("data");

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

            case CHANGE_UPDATE: {
                if (changeUpdate == null) changeUpdate = Bukkit.createInventory(null, slots, "Выберите улучшение...");
                setItems(p, changeUpdate, type, ItemType.DEFAULT_ITEM);
                p.openInventory(changeUpdate);
                return;
            }

            case UPDATE: {
                if (updateBiz == null) updateBiz = Bukkit.createInventory(null, slots, "Выберите бизнес...");
                setItems(p, updateBiz, type, ItemType.DEFAULT_ITEM);
                p.openInventory(updateBiz);
                return;
            }

            case MAIN : {
                if (mainMenu == null) mainMenu = Bukkit.createInventory(null, slots, "Главное меню");
                setItems(p, mainMenu, type, ItemType.DEFAULT_ITEM, ItemType.BUY_BIZ, ItemType.UPDATE_BIZ);
                p.openInventory(mainMenu);
            }
        }
    }

    public void setItems(Player p, Inventory inventory, @NotNull InventoryType type, ItemType... item) {

        switch (type) {
            case CHANGE_CLASS : {
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);

                inventory.setItem(15, items.setItemMeta(item[1].getItemStack(), custom.getList("Menu.ChangeClassBiz.HighClass.Lore"),
                        custom.getString("Menu.ChangeClassBiz.HighClass.Name")));
                inventory.setItem(22, items.setItemMeta(item[2].getItemStack(), custom.getList("Menu.ChangeClassBiz.MediumClass.Lore"),
                        custom.getString("Menu.ChangeClassBiz.MediumClass.Name")));
                inventory.setItem(11, items.setItemMeta(item[3].getItemStack(), custom.getList("Menu.ChangeClassBiz.LowClass.Lore"),
                        custom.getString("Menu.ChangeClassBiz.LowClass.Name")));
                return;

            }

            case CHANGE_UPDATE: {

                int bizId = data.getInt("players." + p.getUniqueId() + ".OtherInfo");

                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);

                inventory.setItem(13, items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + bizId + ".Item"))), replace("{status}", biz.status(p, bizId)).list(getStringList("Business.Id." + bizId + ".Lore")),
                        getString("Business.Id." + bizId + ".Name")));

                inventory.setItem(21, items.setItemMeta(new ItemStack(Material.getMaterial(custom.getString("Menu.ChangeUpdate.HalfTax.Item"))),
                        replace("{cost}", getString("Business.GlobalSettings.Update.Biz.REDUCTION_TAX.Cost"))
                        .list(custom.getList("Menu.ChangeUpdate.HalfTax.Lore")), custom.getString("Menu.ChangeUpdate.HalfTax.Name")));

                inventory.setItem(22, items.setItemMeta(new ItemStack(Material.getMaterial(custom.getString("Menu.ChangeUpdate.Staff.Item"))),
                        replace("{cost}", getString("Business.GlobalSettings.Update.Biz.STAFF.Cost"))
                        .list(custom.getList("Menu.ChangeUpdate.Staff.Lore")), custom.getString("Menu.ChangeUpdate.Staff.Name")));

                if(data.getBoolean("players." + p.getUniqueId() + ".isPremium")) {
                    inventory.setItem(23, items.setItemMeta(new ItemStack(Material.getMaterial(custom.getString("Menu.ChangeUpdate.X2.Item"))),

                            replace("{cost}", getString("Business.GlobalSettings.Update.Biz.DOUBLE_MONEY.Cost"))
                            .list(custom.getList("Menu.ChangeUpdate.X2.Lore")), custom.getString("Menu.ChangeUpdate.X2.Name")));
                } else {
                    inventory.setItem(23, items.setItemMeta(ItemType.DENY_SLOT.getItemStack(),
                            replace("{cost}", getString("Business.GlobalSettings.Update.Biz.DOUBLE_MONEY.Cost"))
                            .list(custom.getList("Menu.ChangeUpdate.X2.DenyLore")), custom.getString("Menu.ChangeUpdate.X2.Name")));
                }
                return;
            }

            case MAIN : {
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);

                inventory.setItem(11, items.setItemMeta(item[1].getItemStack(), custom.getConfig().getStringList("Menu.Main.BuySlot.Lore"),
                        custom.getConfig().getString("Menu.Main.BuySlot.Name")));
                inventory.setItem(15, items.setItemMeta(item[2].getItemStack(), custom.getConfig().getStringList("Menu.Main.UpdateSlot.Lore"),
                        custom.getConfig().getString("Menu.Main.UpdateSlot.Name")));
                return;
            }

            case UPDATE: {
                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);

                for(int i : getIntegerList("Business.RegistrationId")) {
                    if(!handlerBusiness.isHaveBusiness(p, i)) continue;
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"), items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))),
                            replace("{status}", translateAlternateColorCodes('&', biz.status(p, i))).list(getStringList("Business.Id." + i + ".Lore")), getString("Business.Id." + i + ".Name")));
                }
                return;
            }

            case LOW_CLASS : {
                if(!handlerBusiness.isSizeMoreLimit(BusinessClassType.LOW)) return;

                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                for(int i : biz.getListBizId(BusinessClassType.LOW)) {
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"),
                            items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))), replace("{status}", translateAlternateColorCodes('&', biz.status(p, i))).list(getStringList("Business.Id." + i + ".Lore")),
                                    getString("Business.Id." + i + ".Name")));
                }
                return;
            }

            case MEDIUM_CLASS : {
                if(!handlerBusiness.isSizeMoreLimit(BusinessClassType.MEDIUM)) return;

                items.setItemInv(item[0].getItemStack(), inventory, 0, 36);
                for(int i : biz.getListBizId(BusinessClassType.MEDIUM)) {
                    inventory.setItem(getInt("Business.Id." + i + ".Slot"),
                            items.setItemMeta(new ItemStack(Material.getMaterial(getString("Business.Id." + i + ".Item"))), replace("{status}", translateAlternateColorCodes('&', biz.status(p, i))).list(getStringList("Business.Id." + i + ".Lore")),
                                    getString("Business.Id." + i + ".Name")));
                }
                return;
            }

            case HIGH_CLASS : {
                if(!handlerBusiness.isSizeMoreLimit(BusinessClassType.HIGH)) return;

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