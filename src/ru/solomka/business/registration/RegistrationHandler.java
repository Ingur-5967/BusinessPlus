package ru.solomka.business.registration;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import ru.solomka.business.Main;
import ru.solomka.business.config.ConfigManager;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.message.Messages;
import ru.solomka.business.registration.utils.Checker;
import ru.zmmxasl.dl.DefenderLibrary;

import java.io.IOException;

public class RegistrationHandler {

    @Getter private final Main plugin;

    private final Checker checker = new Checker();

    public RegistrationHandler(Main plugin) {
        new DefenderLibrary().init(Main.getInstance());
        this.plugin = plugin;
    }

    public void cfgInitialization(boolean checkLic, String... files) throws IOException {
        if (checkLic) checker.ip(checker.getIp());
        new ConfigManager().createConfig();
        for(String name : files) {
            if(name.equals("data")) {
                new CustomConfig(name, false);
                continue;
            }
            new CustomConfig(name, true);
        }
        Messages.logger("Files loaded: " + files.length);
    }

    public void registrationEvents(Listener... listeners) {
        for(Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
        Messages.logger("Registered listeners: " + listeners.length);
    }
}