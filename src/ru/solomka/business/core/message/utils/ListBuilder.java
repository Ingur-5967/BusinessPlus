package ru.solomka.business.core.message.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.core.HandlerBusiness;
import ru.solomka.business.vault.utils.Salary;

import java.io.File;
import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

public class ListBuilder {

    private static final HandlerBusiness business = new HandlerBusiness();

    public static void buildList(@NotNull List<String> list, Player p) {
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string).append("\n");
        }
        String result = sb.toString().replace("%salary%", String.valueOf(new Salary().salary(p)))
                .replace("%count_biz%", String.valueOf(business.getAllHaveBiz(loadConfiguration(new File(Main.getInstance().getDataFolder(), "data.yml")), p).size()));
        p.sendMessage(translateAlternateColorCodes('&', result));
    }
}
