package ru.solomka.business.menu.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.menu.Menu;
import ru.solomka.business.menu.enums.InventoryType;

public class MainMenu implements Listener {

    @Getter private final Main plugin;

    public MainMenu(Main plugin) {
        this.plugin = plugin;
    }

    private final Menu menu = new Menu();

    @EventHandler
    public void click(@NotNull InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (p.getOpenInventory().getTitle().contains("Главное меню")) {
            if (click == null) return;
            if (slot <= 36) e.setCancelled(true);
            switch (slot) {
                case 11 : {
                    menu.create(p, 36, InventoryType.CHANGE_CLASS);
                    return;
                }
                case 15 : {
                    menu.create(p, 36, InventoryType.UPDATE);
                    return;
                }
                case 22 : {
                    menu.create(p, 36, InventoryType.DEVELOP_BIZ);
                }
            }
        }
    }
}
