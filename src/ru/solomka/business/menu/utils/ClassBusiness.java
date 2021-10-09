package ru.solomka.business.menu.utils;

import org.jetbrains.annotations.NotNull;
import ru.solomka.business.core.enums.BusinessClassType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.solomka.business.config.ConfigManager.getIntegerList;
import static ru.solomka.business.config.ConfigManager.getString;

public class ClassBusiness {

    private final List<Integer> list = new ArrayList<>();

    public List<Integer> getListBizId(@NotNull BusinessClassType classType) {
        switch (classType) {
            case LOW:
            case MEDIUM:
            case HIGH: {
                for (int biz : getIntegerList("Business.RegistrationId")) {
                    if(!isValuableClassType(biz)) continue;
                    if (getString("Business.Id." + biz + ".Class").equals(classType.name())) list.add(biz);
                }
                return list;
            }
        }
        return null;
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