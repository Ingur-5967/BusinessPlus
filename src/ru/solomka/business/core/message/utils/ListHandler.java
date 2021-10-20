package ru.solomka.business.core.message.utils;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.core.HandlerBusiness;
import ru.solomka.business.core.message.Messages;
import ru.solomka.business.vault.utils.Salary;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class ListHandler {

    private static final HandlerBusiness business = new HandlerBusiness();

    public static void buildList(@NotNull List<String> list, Player p) {
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string).append("\n");
        }
       List<String> result = ListHandler.replace("%salary%", String.valueOf(new Salary().salary(p)))
               .replace("%count_biz%", String.valueOf(business.getAllHaveBiz(p).size())).list(Collections.singletonList(sb.toString()));
        for(String str : result) {
            p.sendMessage(translateAlternateColorCodes('&', str));
        }
    }


    public static Placeholders replace(String from, String to) {
        Placeholders sender = new Placeholders();
        return sender.replace(from, to);
    }

   public static class Placeholders {
       private final Map<String, String> placeholders = new HashMap<>();

       public Placeholders replace(String from, String to) {
           placeholders.put(from, to);
           return this;
       }

       public List<String> list(List<String> list) {
           return replaceList(list);
       }

       private List<String> replaceList(List<String> list) {
           for (Map.Entry<String, String> entry : placeholders.entrySet()) {
               list.replaceAll(s -> s.replace(entry.getKey(), entry.getValue()));
           }
           return list;
       }
   }
}