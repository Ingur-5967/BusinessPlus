package ru.solomka.business;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.business.commands.OpenShopMenu;
import ru.solomka.business.commands.ReloadConfig;
import ru.solomka.business.config.ConfigManager;
import ru.solomka.business.registration.RegistrationHandler;
import ru.solomka.business.vault.VaultHandler;
import ru.solomka.business.vault.utils.Salary;

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    private final RegistrationHandler registration = new RegistrationHandler(this);

    @Override
    public void onEnable() {
        instance = this;
        new ConfigManager().createConfig();
        VaultHandler.init();
        registrationCmd();
        registration.registrationEvents();
        for(Player p : Bukkit.getOnlinePlayers()) {
            new Salary(p).give();
        }
    }

    public void registrationCmd() {
        Bukkit.getPluginCommand("business").setExecutor(new OpenShopMenu(this));
        Bukkit.getPluginCommand("bsreload").setExecutor(new ReloadConfig(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling...");
    }
}