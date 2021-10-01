package ru.solomka.business.vault.utils;

import lombok.SneakyThrows;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.HandlerBusiness;
import ru.solomka.business.commands.utils.methods.message.Messages;
import ru.solomka.business.vault.VaultHandler;

import java.io.File;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;
import static ru.solomka.business.commands.utils.methods.message.utils.ListBuilder.buildList;
import static ru.solomka.business.config.ConfigManager.getInt;
import static ru.solomka.business.config.ConfigManager.getStringList;

public class Salary {

    private int salary = 0;

    private final HandlerBusiness business = new HandlerBusiness();

    public int salary(Player p) {
        for(int i : business.getAllHaveBiz(loadConfiguration(new File(Main.getInstance().getDataFolder() + "/data.yml")),p)) {
            salary += getInt("Business.Id." + i + ".Salary");
        }
        return salary;
    }

    public void give(Player p) {
        if(p == null) return;
        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                if(p.getStatistic(Statistic.PLAY_ONE_TICK) < 72000) {
                    Messages.playTime.replace("{need_time}", "1 час").send(p);
                    return;
                }
                new VaultHandler().giveSalary(p, salary(p));
                buildList(getStringList("Messages.Money.Payday"), p);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0,3600 * 20L);
    }
}