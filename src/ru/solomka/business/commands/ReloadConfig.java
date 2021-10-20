package ru.solomka.business.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.core.message.Messages;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.business.config.ConfigManager.reload;

public class ReloadConfig implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(translateAlternateColorCodes('&',"&cError! This command is for players only"));
            return true;
        }

        Player p = (Player) sender;

        if(!p.isOp() || !p.hasPermission("Business.admin")) {
            Messages.notHavePerm.replace("{permission}", "OP/Business.admin");
            return true;
        }
        reload();
        Messages.reloadCfg.send(p);
        return true;
    }
}