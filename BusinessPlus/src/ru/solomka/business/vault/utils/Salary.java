package ru.solomka.business.vault.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.HandlerBusiness;
import ru.solomka.business.commands.utils.methods.message.Messages;
import ru.solomka.business.commands.utils.methods.message.utils.ListBuilder;
import ru.solomka.business.config.utils.Data;
import ru.solomka.business.vault.VaultHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static ru.solomka.business.commands.utils.methods.message.utils.ListBuilder.*;
import static ru.solomka.business.config.ConfigManager.getInt;
import static ru.solomka.business.config.ConfigManager.getStringList;
import static ru.solomka.business.config.utils.Data.*;

public class Salary {

    @Getter private final Player p;

    @Getter public static int salary = 0;

    public Salary(Player p) {
        this.p = p;
    }

    private final HandlerBusiness business = new HandlerBusiness(Main.getInstance());
    private final Messages messages = new Messages();

    private int salary() {
        for(int i : business.getAllHaveBiz(p)) {
            salary += getInt("Business.Id." + i + ".Salary");
        }
        return salary;
    }

    public void give() {
        if(p == null) return;
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                if(p.getStatistic(Statistic.PLAY_ONE_TICK) < 15000) {
                    p.sendMessage(messages.playTime("15 минут"));
                    cancel();
                    return;
                }
                for(int i : business.getAllHaveBiz(p)) {
                    setValue(cfg, file, "players." + p.getUniqueId() + "." + i + ".Salary",
                            getDataInt(cfg, file, "players." + p.getUniqueId() + "." + i + ".Salary") + getInt("Business.Id." + i + ".Salary"));
                    buildList(getStringList("Message.Money.Payday"), p);
                }
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0,3600 * 20L);
    }
}
