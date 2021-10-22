package ru.solomka.business.vault.utils;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.business.Main;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.HandlerBusiness;
import ru.solomka.business.core.message.Messages;

import static ru.solomka.business.config.ConfigManager.getInt;

public class Salary {

    private int salary;

    private final HandlerBusiness business = new HandlerBusiness();

    public int salary(Player p) {
        for(int i : business.getAllHaveBiz(p)) {
            salary += getInt("Business.Id." + i + ".Salary");
        }
        return salary;
    }

    public void give(Player p) {
        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                if(salary(p) == 0) return;

                for(Player p : Bukkit.getOnlinePlayers()) {
                    new CustomConfig("data").setValue("players." + p.getUniqueId() + ".Salary", salary(p) - percent(p));
                }
                Messages.salary.send(p);
                Messages.paymentTaxMessage.replace("{tax}", String.valueOf(percent(p))).send(p);
            }
        }.runTaskTimer(Main.getInstance(), 0,20L);
    }

    public int percent(Player p) {
        int money = salary(p);
        int tallage = 15;
        return (money / 100) * tallage;
    }
}
