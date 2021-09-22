package ru.solomka.business.commands.utils.methods.message;

import org.bukkit.ChatColor;
import ru.solomka.business.config.ConfigManager;

import static org.bukkit.ChatColor.*;

public class Messages {

    private static final ConfigManager config = new ConfigManager();

    public String buyBusinessMessage() {
        return translateAlternateColorCodes('&', config.getString("Message.Business.BuyBusiness"));
    }

    public String sellBusinessMessage(int id, int value) {
        String sell = translateAlternateColorCodes('&', config.getString("Message.Business.SellBusiness")).replace("%business_id", String.valueOf(id));
        return sell.replace("%cost%", String.valueOf(value));
    }

    public String isAlreadyHaveBusiness(int id) {
        return translateAlternateColorCodes('&', config.getString("Message.Business.AlreadyHaveBusiness")).replace("%business_id%", String.valueOf(id));
    }

    public String salaryReceived(int value) {
        return translateAlternateColorCodes('&', config.getString("Message.Business.SalaryReceived")).replace("%salary%", String.valueOf(value));
    }

    public String paymentTaxMessage(int value) {
        return translateAlternateColorCodes('&', config.getString("Message.Business.SellBusiness")).replace("%payment_tax%", String.valueOf(value));
    }

    public String notHavePerm(String perm) {
        return translateAlternateColorCodes('&', config.getString("Message.NotHavePerm")).replace("%permission%", perm);
    }

    public String error(String description) {
        return translateAlternateColorCodes('&', "&cПроизошла ошибка! \n&4&l[!] &fОписание: &f" + description);
    }
}