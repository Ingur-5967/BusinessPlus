package ru.solomka.business;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.business.commands.OpenShopMenu;
import ru.solomka.business.commands.ReloadConfig;
import ru.solomka.business.config.ConfigManager;
import ru.solomka.business.registration.RegistrationHandler;
import ru.solomka.business.vault.VaultHandler;

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    private final RegistrationHandler registration = new RegistrationHandler(this);

    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdir();
        new ConfigManager().createConfig();
        VaultHandler.init();
        registrationCmd();
        registration.registrationEvents();
    }

    public void registrationCmd() {
        Bukkit.getPluginCommand("business").setExecutor(new OpenShopMenu(this));
        Bukkit.getPluginCommand("bsreload").setExecutor(new ReloadConfig(this));
    }

    @Override
    public void onDisable() {}
}