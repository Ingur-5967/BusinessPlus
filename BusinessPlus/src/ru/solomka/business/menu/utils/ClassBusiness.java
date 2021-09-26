package ru.solomka.business.menu.utils;

import ru.solomka.business.commands.utils.methods.enums.BusinessClassType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.solomka.business.config.ConfigManager.getIntegerList;
import static ru.solomka.business.config.ConfigManager.getString;

public class ClassBusiness {

    private final List<Integer> list = new ArrayList<>();

    private final String[] classes = new String[] {
            "LOW",
            "MEDIUM",
            "HIGH"
    };

    public List<Integer> getListBizId(BusinessClassType classType) {
        switch (classType) {
            case LOW:
            case MEDIUM:
            case HIGH: {
                for (int biz : getIntegerList("Business.RegistrationId")) {
                    if(!isValuableClass(biz)) continue;
                    if (getString("Business.Id." + biz + ".Class").equals(classType.getType())) list.add(biz);
                }
                return list;
            }
        }
        return null;
    }

    public boolean isValuableClass(int id) {
        for(String type : classes) {
            if (Objects.equals(getString("Business.Id." + id + ".Class"), type)) return true;
            new Exception("It is impossible to determine the business class '" + id + "'").printStackTrace();
            return false;
        }
        return false;
    }
}