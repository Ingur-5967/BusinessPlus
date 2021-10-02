package ru.solomka.business.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static ru.solomka.business.config.ConfigManager.reload;

public class ReloadConfig implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, String str, String[] args) {
        if(!sender.isOp()) return true;
        reload();
        return true;
    }
}