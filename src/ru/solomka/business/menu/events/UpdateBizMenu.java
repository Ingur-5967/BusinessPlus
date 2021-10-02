package ru.solomka.business.menu.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class UpdateBizMenu implements Listener {

    // TODO 01/10/21

    @EventHandler
    public void click(@NotNull InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (p.getOpenInventory().getTitle().contains("Улучшение бизнеса")) {
            if (click == null) return;
            if (slot <= 36) e.setCancelled(true);
        }
    }
}