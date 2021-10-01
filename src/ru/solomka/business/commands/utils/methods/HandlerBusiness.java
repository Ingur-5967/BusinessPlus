package ru.solomka.business.commands.utils.methods;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.enums.BusinessClassType;
import ru.solomka.business.commands.utils.methods.message.Messages;
import ru.solomka.business.menu.enums.ItemType;
import ru.solomka.business.vault.VaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.*;
import static org.bukkit.configuration.file.YamlConfiguration.*;
import static ru.solomka.business.config.ConfigManager.*;
import static ru.solomka.business.config.utils.Data.getDataString;
import static ru.solomka.business.config.utils.Data.setValue;

public class HandlerBusiness  {

    private final List<Integer> business = new ArrayList<>();
    private final VaultHandler vault = new VaultHandler();

    private int sizeClassBiz = 0;

    public void sellBiz(File file, Player p, int id, String[] strings) throws IOException {
        for(String str : strings) {
            setValue(loadConfiguration(file), file, str, null);
        }
        vault.giveSalary(p, getInt("Business.Id." + id + ".CostSale"));
    }

    public boolean isSizeMoreLimit(@NotNull BusinessClassType classType) {
        switch (classType) {
            case LOW:
            case MEDIUM:
            case HIGH: {
                for (int i : getIntegerList("Business.RegistrationId")) {
                    if (getString("Business.Id." + i + ".Class").equals(classType.name())) sizeClassBiz++;
                }
                try {
                    if (sizeClassBiz <= 20) return true;
                } catch (IndexOutOfBoundsException exception) {
                    throw new IndexOutOfBoundsException("You have exceeded the number limit in the class <" + classType.name() + ">. Size: " + sizeClassBiz + ". Limit: 20");
                }
            }
        }
        return false;
    }

    public List<Integer> getAllHaveBiz(FileConfiguration cfg, Player p) {
        for(int i : getIntegerList("Business.RegistrationId")) {
            if(getDataString(cfg,"players." + p.getUniqueId() + ".Business." + i) == null) continue;
            business.add(i);
        }
        return business;
    }

    public int getBizId(String name) {
        for(int i : getIntegerList("Business.RegistrationId")) {
            try {
                if(getString("Business.Id." + i + ".Name").equals(translateAlternateColorCodes('&', name))) return i;
            } catch (NullPointerException exception) {
                throw new NullPointerException("Couldn't find a business with the name '" + name + "'! Maybe you didn't write it in 'RegistrationId'");
            }
        }
        return -1;
    }

    public boolean onBusiness(File file, @NotNull Inventory inventory, Player p, int slot) throws IOException {
        if(inventory.getItem(slot) == null || inventory.getItem(slot) == ItemType.DEFAULT_ITEM.getItemStack()) return false;

        ItemStack item = inventory.getItem(slot);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return false;

        int id = getBizId(meta.getDisplayName());

        if(!vault.takeMoney(p,getInt("Business.Id." + id + ".Cost"))) {
            Messages.noMoney.replace("{need_money}", String.valueOf(getInt("Business.Id." + id + ".Cost"))).send(p);
            return true;
        }

        setValue(loadConfiguration(file), file, "players." + p.getUniqueId() + ".Business." + id + ".Updates", null);
        vault.takeMoney(p, getInt("Business.Id." + id + ".Cost"));
        return true;
    }
}