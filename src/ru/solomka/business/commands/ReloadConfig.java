package ru.solomka.business.commands;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;

import static ru.solomka.business.config.ConfigManager.reload;

public class ReloadConfig implements CommandExecutor {

    @Getter private final Main plugin;

    public ReloadConfig(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, String str, String[] args) {
        if(!sender.isOp()) return true;
        reload();
        return true;
    }
}