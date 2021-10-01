package ru.solomka.business.commands.utils.methods.message;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
    buyBusiness,
    buyUpdate,
    playTime,
    error;

    private List<String> msg;

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

 /*   public String sellBusinessMessage(int id, int value) {
        String sell = translateAlternateColorCodes('&', getString("Message.Business.SellBusiness")).replace("%business_id", String.valueOf(id));
        return sell.replace("%cost%", String.valueOf(value));
    }

    public String isAlreadyHaveBusiness(int id) {
        return translateAlternateColorCodes('&', getString("Message.Business.AlreadyHaveBusiness")).replace("%business_id%", String.valueOf(id));
    }

    public String salaryReceived(int value) {
        return translateAlternateColorCodes('&', getString("Message.Money.SalaryReceived")).replace("%salary%", String.valueOf(value));
    }

    public String paymentTaxMessage(int value) {
        return translateAlternateColorCodes('&', getString("Message.Money.SellBusiness")).replace("%payment_tax%", String.valueOf(value));
    }

    public String notHavePerm(String perm) {
        return translateAlternateColorCodes('&', getString("Message.NotHavePerm")).replace("%permission%", perm);
    }

    public String notHaveMoney(String moneyNeed) {
        return translateAlternateColorCodes('&', getString("Message.BuyBusiness.NotHaveMoney")).replace("%cost%", moneyNeed);
    }

    public String buyBusiness(String id) {
        return translateAlternateColorCodes('&', getString("Message.BuyBusiness.SuccessBuyBusiness")).replace("%id%", id);
    }

    public String buyUpdate(String update) {
        return translateAlternateColorCodes('&', getString("Message.Update.BuyUpdate")).replace("%update_type%", update);
    }

    public String playTime(String time) {
        return translateAlternateColorCodes('&', getString("Message.Time.PlayTime")).replace("%need_time%", time);
    }

    public String error(String description) {
        return translateAlternateColorCodes('&', "&cПроизошла ошибка! \n&4&l[!] &fОписание: &f" + description);
    }

  */
}