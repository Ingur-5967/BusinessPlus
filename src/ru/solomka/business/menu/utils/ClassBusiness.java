package ru.solomka.business.menu.utils;

import ru.solomka.business.commands.utils.methods.enums.BusinessClassType;
import ru.solomka.business.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassBusiness {

    private final ConfigManager config = new ConfigManager();
    private final List<Integer> list = new ArrayList<>();

    private final String[] classes = new String[] {"LOW","MEDIUM","HIGH"};

    public List<Integer> getListBizId(BusinessClassType classType) {
        switch (classType) {
            case LOW:
            case MEDIUM:
            case HIGH: {
                for (int biz : config.getIntegerList("Business.RegistrationId")) {
                    if (config.getString("Business.Id").equals(classType.getType())) list.add(biz);
                }
                return list;
            }
        }
        return null;
    }

    public boolean isValuableClass(int id) {
        for(String type : classes) {
            if (Objects.equals(config.getString("Business.Id." + id), type)) return true;
            new Exception("It is impossible to determine the business class '" + id + "'").printStackTrace();
            return false;
        }
        return false;
    }
}