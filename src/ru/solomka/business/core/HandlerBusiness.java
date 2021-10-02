package ru.solomka.business.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.core.enums.BusinessClassType;
import ru.solomka.business.core.message.Messages;
import ru.solomka.business.vault.VaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;
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

    public int getBizId(int slot) {
        for(int i : getIntegerList("Business.RegistrationId")) {
            if(getInt("Business.Id." + i + ".Slot") != slot) continue;
            return i;
        }
        return -1;
    }

    public boolean isHaveBusiness(File file, Player p, int id) {
        if(getDataString(loadConfiguration(file),"players." + p.getUniqueId() + ".Business." + id) == null) return false;
        return true;
    }

    public void onBusiness(File file, Player p, int slot) throws IOException {
        int id = getBizId(slot);

        if(isHaveBusiness(file, p, getBizId(slot))) {
            Messages.alreadyHaveBiz.replace("{id}", String.valueOf(id)).send(p);
            return;
        }

        if(!vault.takeMoney(p, getInt("Business.Id." + id + ".Cost"))) {
            Messages.noMoney.replace("{need_money}", String.valueOf(getInt("Business.Id." + getBizId(slot) + ".Cost"))).send(p);
            return;
        }
        setValue(loadConfiguration(file), file, "players." + p.getUniqueId() + ".Business." + getBizId(slot) + ".Updates", "");
        Messages.buyBusiness.replace("{id}", String.valueOf(id)).replace("{cost}", String.valueOf(getInt("Business.Id." + id + ".Cost"))).send(p);
        p.closeInventory();
    }
}