package ru.solomka.business.registration;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import ru.solomka.business.Main;
import ru.solomka.business.menu.events.MainMenu;

public class RegistrationHandler {

    @Getter private final Main plugin;

    public RegistrationHandler(Main plugin) {
        this.plugin = plugin;
    }

    private final Listener[] events = new Listener[] { new MainMenu(Main.getInstance()) }; // TODO 22.09.21

    public void registrationEvents() {
        for(Listener listener : events) {
            Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
        }
        plugin.getLogger().info("Registered listeners: " + events.length);
    }
}
