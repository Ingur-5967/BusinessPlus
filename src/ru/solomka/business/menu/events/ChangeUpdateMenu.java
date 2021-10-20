package ru.solomka.business.menu.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;

public class ChangeUpdateMenu {

    @Getter private final Main plugin;

    public ChangeUpdateMenu(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void changeUpdate(@NotNull InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (p.getOpenInventory().getTitle().contains("Выберите улучшение...")) {
            if (click == null) return;
            if (slot <= 36) e.setCancelled(true);
            switch (slot) {
                case 16: {
                    return;
                }
                case 18 : {
                    return;
                }
                case 19 : {
                    return;
                }
            }
        }
    }
}
