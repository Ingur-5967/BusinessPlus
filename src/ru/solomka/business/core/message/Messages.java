package ru.solomka.business.core.message;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public enum Messages {

    notHavePerm,
    sellBiz,
    alreadyHaveBiz,
    paymentTaxMessage,
    noMoney,
    notHaveBiz,
    buyBusiness,
    buyUpdate,
    reloadCfg,
    onlyForPlayers,
    playTime;

    private List<String> msg;

    private static final Main plugin = Main.getInstance();

    public static void load(FileConfiguration c) {
        for(Messages message : Messages.values()) {
            Object obj = c.get("Messages." + message.name().replace("_", "."));
            if(obj instanceof List) {
                message.msg = (((List<String>) obj)).stream().map(m -> translateAlternateColorCodes('&', m)).collect(Collectors.toList());
            }
            else {
                message.msg = Lists.newArrayList(obj == null ? "" : translateAlternateColorCodes('&', obj.toString()));
            }
        }
    }

    public static void logger(String... info) {
        for(String string : info) {
            plugin.getLogger().info(string);
        }
    }

    public Sender replace(String from, String to) {
        Sender sender = new Sender();
        return sender.replace(from, to);
    }

    public void send(Player player) {
        new Sender().send(player);
    }

    public class Sender {
        private final Map<String, String> placeholders = new HashMap<>();

        public void send(Player player) {
            for (String message : Messages.this.msg) {
                sendMessage(player, replacePlaceholders(message));
            }
        }

        public Sender replace(String from, String to) {
            placeholders.put(from, to);
            return this;
        }

        private void sendMessage(Player player, @NotNull String message) {
            if (message.startsWith("json:")) {
                player.sendMessage(String.valueOf(new TextComponent(ComponentSerializer.parse(message.substring(5)))));
            } else {
                player.sendMessage(message);
            }
        }

        private String replacePlaceholders(@NotNull String message) {
            if (!message.contains("{")) return message;
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
            return message;
        }
    }
}