package ru.solomka.business.menu.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.HandlerBusiness;

import java.io.File;
import java.io.IOException;

public class BuyMenu implements Listener {

    private final HandlerBusiness handlerBusiness = new HandlerBusiness();

    @EventHandler
    public void click(InventoryClickEvent e) throws IOException {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (p.getOpenInventory().getTitle().contains("Покупка бизнеса")) {
            if (click == null) return;
            if (slot <= 36) e.setCancelled(true);
            switch (slot) {
                case 1 : case 2 : case 3 : case 4 : case 5 : case 6 : case 7 :
                case 8 : case 9 : case 10 : case 11 : case 12 : case 13 : case 14 :
                case 15 : case 16 : case 17 : case 18 : case 19 : case 20 : case 21 :
                case 22 : case 23 : case 24 : case 25 : case 26 : case 27 : case 28 :
                case 29 : case 30 : case 31 : case 32 : {
                    handlerBusiness.onBusiness(new File(Main.getInstance().getDataFolder() + "/data.yml"), click, p, slot);
                }
            }
        }
    }
}