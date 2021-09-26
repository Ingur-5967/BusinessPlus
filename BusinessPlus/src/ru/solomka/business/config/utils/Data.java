package ru.solomka.business.config.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class Data {

    public static void setValue(@NotNull FileConfiguration cfg, File file, String str, Object obj) throws IOException {
        cfg.set(str, obj);
        cfg.save(file);
    }

    public static String getDataString(@NotNull FileConfiguration cfg, String path) {
        return cfg.getString(path);
    }

    public static int getDataInt(@NotNull FileConfiguration cfg, File file, String path) {
        return cfg.getInt(path);
    }

    public static boolean getDataBoolean(@NotNull FileConfiguration cfg, String path) {
        return cfg.getBoolean(path);
    }

}
