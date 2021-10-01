package ru.solomka.business;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.business.commands.OpenShopMenu;
import ru.solomka.business.commands.ReloadConfig;
import ru.solomka.business.commands.utils.methods.message.Messages;
import ru.solomka.business.config.ConfigManager;
import ru.solomka.business.config.utils.Data;
import ru.solomka.business.menu.events.BuyMenu;
import ru.solomka.business.menu.events.ChangeClassMenu;
import ru.solomka.business.menu.events.DevelopBizMenu;
import ru.solomka.business.menu.events.MainMenu;
import ru.solomka.business.registration.RegistrationHandler;
import ru.solomka.business.vault.VaultHandler;
import ru.solomka.business.vault.utils.Salary;

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        new ConfigManager().createConfig();
        VaultHandler.init();
        registrationCmd();
        Data.init();
        Messages.load(getConfig());

        new RegistrationHandler().registrationEvents(
                new DevelopBizMenu(),
                new MainMenu(),
                new ChangeClassMenu(),
                new BuyMenu()
        );

        for(Player p : Bukkit.getOnlinePlayers()) {
            new Salary().give(p);
        }
    }

    public void registrationCmd() {
        Bukkit.getPluginCommand("business").setExecutor(new OpenShopMenu());
        Bukkit.getPluginCommand("bsreload").setExecutor(new ReloadConfig());
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling...");
    }
}