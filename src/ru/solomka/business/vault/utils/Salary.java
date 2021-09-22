package ru.solomka.business.vault.utils;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.HandlerBusiness;
import ru.solomka.business.commands.utils.methods.message.Messages;
import ru.solomka.business.vault.VaultHandler;

import java.util.ArrayList;
import java.util.List;

import static ru.solomka.business.config.ConfigManager.getInt;

public class Salary {

    @Getter private final Player p;

    @Getter private int salary = 0;

    public Salary(Player p) {
        this.p = p;
    }

    private final HandlerBusiness business = new HandlerBusiness(Main.getInstance());
    private final List<Integer> list = new ArrayList<>();
    private final VaultHandler vault = new VaultHandler();
    private final Messages messages = new Messages();

    private int salary() {
        list.addAll(business.getHaveBusinessId(p));
        for(int i : list) {
            salary += getInt("Business.Id." + i + ".Salary");
        }
        return salary;
    }

    public void give() {
        new BukkitRunnable() {
            @Override
            public void run() {
                vault.giveSalary(p, salary());
                p.sendMessage(messages.salaryReceived(salary()));
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0,3600 * 20L);
    }
}