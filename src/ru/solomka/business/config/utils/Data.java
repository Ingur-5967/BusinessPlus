package ru.solomka.business.config.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Data {

    public static void init() throws IOException, InvalidConfigurationException {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        YamlConfiguration.loadConfiguration(file);
    }

    public static void setValue(@NotNull FileConfiguration cfg, File file, String str, Object obj) throws IOException {
        cfg.set(str, obj);
        cfg.save(file);
    }

    public static String getDataString(@NotNull FileConfiguration cfg, String path) {
        return cfg.getString(path);
    }

    public static int getDataInt(@NotNull FileConfiguration cfg, String path) {
        return cfg.getInt(path);
    }

    public static boolean getDataBoolean(@NotNull FileConfiguration cfg, String path) {
        return cfg.getBoolean(path);
    }

    public static List<String> getDataList(@NotNull FileConfiguration cfg, String str) { return cfg.getStringList(str); }

}
