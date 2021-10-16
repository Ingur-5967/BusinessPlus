package ru.solomka.business.menu.utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.enums.BusinessClassType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static ru.solomka.business.config.ConfigManager.getIntegerList;
import static ru.solomka.business.config.ConfigManager.getString;

public class ClassBusiness {

    private final List<Integer> listLow = new ArrayList<>();
    private final List<Integer> listMedium = new ArrayList<>();
    private final List<Integer> listHigh = new ArrayList<>();

    public List<Integer> getListBizId(@NotNull BusinessClassType classType) {
        switch (classType) {
            case LOW: {
                for (int biz : getIntegerList("Business.RegistrationId")) {
                    if(!isValuableClassType(biz) || !getString("Business.Id." + biz + ".Class").contains(classType.name())) continue;
                    listLow.add(biz);
                }
                return listLow;
            }
            case MEDIUM: {
                for (int biz : getIntegerList("Business.RegistrationId")) {
                    if(!isValuableClassType(biz) || !getString("Business.Id." + biz + ".Class").contains(classType.name())) continue;
                    listMedium.add(biz);
                }
                return listMedium;
            }
            case HIGH: {
                for (int biz : getIntegerList("Business.RegistrationId")) {
                    if(!isValuableClassType(biz) || !getString("Business.Id." + biz + ".Class").contains(classType.name())) continue;
                    listHigh.add(biz);
                }
                return listHigh;
            }
        }
        return null;
    }

    public boolean isHaveBusiness(Player p, int id) {
        CustomConfig data = new CustomConfig("data");
        return data.getString("players." + p.getUniqueId() + ".Business." + id) != null;
    }

    public String status(Player p, int id) {
        if(!isHaveBusiness(p, id)) return "&cне имеется";
        return "&aимеется";
    }

    public boolean isValuableClassType(int id) {
        for(BusinessClassType type : BusinessClassType.values()) {
            try {
                if (Objects.equals(getString("Business.Id." + id + ".Class"), type.name())) return true;
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("It is impossible to determine the business class '" + id + "'");
            }
        }
        return false;
    }
}