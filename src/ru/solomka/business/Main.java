package ru.solomka.business;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.business.commands.OpenShopMenu;
import ru.solomka.business.commands.ReloadConfig;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.message.Messages;
import ru.solomka.business.menu.events.*;
import ru.solomka.business.registration.RegistrationHandler;
import ru.solomka.business.vault.VaultHandler;
import ru.solomka.business.vault.utils.Salary;

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    private final RegistrationHandler registration = new RegistrationHandler(this);


    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        VaultHandler.init();

        for(Player p : Bukkit.getOnlinePlayers()) {
            new Salary().give(p);
        }

        registration.cfgInitialization("messages", "menu", "data");

        Messages.load(new CustomConfig("messages").getConfig());

        registration.registrationEvents(
                new MainMenu(),
                new ChangeClassMenu(),
                new BuyMenu()
        );

        Bukkit.getPluginCommand("business").setExecutor(new OpenShopMenu());
        Bukkit.getPluginCommand("bsreload").setExecutor(new ReloadConfig());
    }

    @Override public void onDisable() {
        Messages.logger("Disable!");
    }

}