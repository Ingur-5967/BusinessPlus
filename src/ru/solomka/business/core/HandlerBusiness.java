package ru.solomka.business.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.enums.BusinessClassType;

import java.util.ArrayList;
import java.util.List;

import static ru.solomka.business.config.ConfigManager.*;

public class HandlerBusiness  {

    private final List<Integer> business = new ArrayList<>();
    private final CustomConfig data = new CustomConfig("data");

    private int sizeClassBiz = 0;

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

    public List<Integer> getAllHaveBiz(Player p) {
        for(int i : getIntegerList("Business.RegistrationId")) {
            if(data.getString("players." + p.getUniqueId() + ".Business." + i) == null) continue;
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

    public boolean isHaveBusiness(@NotNull Player p, int id) {
        return data.getString("players." + p.getUniqueId() + ".Business." + id) != null;
    }
}