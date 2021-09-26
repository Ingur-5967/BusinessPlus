package ru.solomka.business.menu.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.business.Main;
import ru.solomka.business.menu.enums.InventoryType;

public class BuyMenu implements Listener {

    @Getter private final Main plugin;

    public BuyMenu(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void click(InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (p.getOpenInventory().getTitle().contains("Главное меню")) {
            if (click == null) return;
            if (slot <= 36) e.setCancelled(true);
            switch (slot) {
                case 11 : {

                    return;
                }
                case 15 : {

                    return;
                }
                case 22 : {

                }
            }
        }
    }
}
