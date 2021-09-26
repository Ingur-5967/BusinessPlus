package ru.solomka.business.menu.events;

import lombok.Getter;
import org.bukkit.event.Listener;
import ru.solomka.business.Main;

public class DevelopBizMenu implements Listener {

    @Getter private final Main plugin;

    public DevelopBizMenu(Main plugin) {
        this.plugin = plugin;
    }
}
