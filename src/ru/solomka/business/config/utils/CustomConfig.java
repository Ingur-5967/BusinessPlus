package ru.solomka.business.config.utils;

import org.bukkit.configuration.file.FileConfiguration;
import ru.solomka.business.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

public class CustomConfig {

    FileConfiguration config;
    File file;

    public CustomConfig(String name, boolean mode) {
        if (!new File(Main.getInstance().getDataFolder(), name + ".yml").exists()) {
            this.file = new File(Main.getInstance().getDataFolder(), name + ".yml");
            this.config = loadConfiguration(this.file);
            this.config.options().copyDefaults(mode);
            if(mode) Main.getInstance().saveResource(name + ".yml", false);
        }
    }

    public CustomConfig(String name) {
        this.file = new File(Main.getInstance().getDataFolder(), name + ".yml");
        this.config = loadConfiguration(this.file);
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public List<String> getList(String str) { return this.config.getStringList(str); }

    public void setValue(String str, Object obj) {
        this.config.set(str, obj);
        save();
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}