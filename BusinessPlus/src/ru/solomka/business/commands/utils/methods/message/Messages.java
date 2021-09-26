package ru.solomka.business.commands.utils.methods.message;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.business.config.ConfigManager.getString;

public class Messages {

    public String sellBusinessMessage(int id, int value) {
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
}