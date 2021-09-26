package ru.solomka.business.commands;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.message.Messages;
import ru.solomka.business.menu.Menu;
import ru.solomka.business.menu.enums.InventoryType;

public class OpenShopMenu implements CommandExecutor {

    @Getter
    private final Main plugin;

    private final Menu menu = new Menu();
    private final Messages msg = new Messages();

    public OpenShopMenu(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) return true;

        Player p = (Player) sender;

        if(!p.hasPermission("Business.Shop.Open")) {
            p.sendMessage(msg.notHavePerm("Business.Shop.Open"));
            return true;
        }
        menu.create(p,36, InventoryType.MAIN);
        return true;
    }
}
