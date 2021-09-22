package ru.solomka.business.config;

import ru.solomka.business.Main;

import java.io.File;
import java.util.List;

public class ConfigManager {

    public void createConfig() {
        if (new File(Main.getInstance().getDataFolder(), "config.yml").exists())
            return;
        Main.getInstance().getLogger().info("Creating new configuration file...");
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveDefaultConfig();
        Main.getInstance().getLogger().info("Configuration file success created!");
    }

    public static String getString(String string) { return Main.getInstance().getConfig().getString(string); }

    public static List<String> getStringList(String string) { return Main.getInstance().getConfig().getStringList(string); }

    public static int getInt(String string) { return Main.getInstance().getConfig().getInt(string); }

    public static boolean getBoolean(String string) { return Main.getInstance().getConfig().getBoolean(string); }

    public static List<Integer> getIntegerList(String string) { return Main.getInstance().getConfig().getIntegerList(string); }

    public static void reload() { Main.getInstance().reloadConfig(); }
}
