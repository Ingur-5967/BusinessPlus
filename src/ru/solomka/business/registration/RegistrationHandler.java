package ru.solomka.business.registration;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import ru.solomka.business.Main;

public class RegistrationHandler {
    public void registrationEvents(Listener... listeners) {
        for(Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
        }
        Main.getInstance().getLogger().info("Registered listeners: " + listeners.length);
    }
}