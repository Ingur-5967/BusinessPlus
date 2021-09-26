package ru.solomka.business.commands.utils.methods.message.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.HandlerBusiness;
import ru.solomka.business.commands.utils.methods.enums.BusinessClassType;
import ru.solomka.business.vault.utils.Salary;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.business.vault.utils.Salary.*;

public class ListBuilder {

    private static final HandlerBusiness business = new HandlerBusiness(Main.getInstance());

    public static void buildList(List<String> list, Player p){
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string).append("\n");
        }
        String result = sb.toString().replace("%salary%", String.valueOf(getSalary())).replace("%count_biz%", String.valueOf(business.getAllHaveBiz(p).size()));
        p.sendMessage(translateAlternateColorCodes('&', result));
    }
}
