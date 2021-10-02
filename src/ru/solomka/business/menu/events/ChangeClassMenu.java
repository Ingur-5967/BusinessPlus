package ru.solomka.business.menu.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.business.menu.Menu;
import ru.solomka.business.menu.enums.InventoryType;

public class ChangeClassMenu implements Listener {

    private final  Menu menu = new Menu();

    @EventHandler
    public void click(InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (p.getOpenInventory().getTitle().contains("Выбор класса")) {
            if (click == null) return;
            if (slot <= 36) e.setCancelled(true);
            switch (slot) {
                case 11 : {
                    if(click.getTitle().equals("Главное меню")) return;
                    menu.create(p, 36, InventoryType.LOW_CLASS);
                    return;
                }
                case 15 : {
                    menu.create(p, 36, InventoryType.HIGH_CLASS);
                    return;
                }
                case 22 : {
                    menu.create(p, 36, InventoryType.MEDIUM_CLASS);
                }
            }
        }
    }
}
