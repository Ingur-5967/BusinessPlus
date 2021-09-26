package ru.solomka.business.commands.utils.methods.interfaces;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.commands.utils.methods.enums.BusinessClassType;

import java.io.IOException;
import java.util.UUID;

public interface IBusiness {

    void sellBiz(Player p, int id, String[] strings) throws IOException, InvalidConfigurationException;

    void setSalary(UUID uuid, int bizId, int salary) throws IOException, InvalidConfigurationException;

    int getBizId(String name);

    boolean isSizeMoreLimit(@NotNull BusinessClassType classType);
}


