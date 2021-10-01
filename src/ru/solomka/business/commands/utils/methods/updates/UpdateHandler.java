package ru.solomka.business.commands.utils.methods.updates;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.enums.UpdateCategoryType;
import ru.solomka.business.commands.utils.methods.enums.UpdateType;

import java.io.File;

import static ru.solomka.business.config.utils.Data.getDataList;
import static ru.solomka.business.config.utils.Data.getDataString;

public class UpdateHandler {

    public boolean isHaveUpdate(Player p, int bizId, @NotNull UpdateCategoryType type) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder() + "/data.yml"));
        switch (type) {
            case HAVE_TWO_BUSINESS :
            case HAVE_FOUR_BUSINESS:
            case HAVE_SIX_BUSINESS: {
                return getDataString(cfg,"players." + p.getUniqueId() + ".Updates." + type.getStr()) != null;
            }
        }
        switch (type) {
            case DOUBLE_MONEY:
            case REDUCTION_TAX:
            case SELL_STARTING_PRICE: {
                return getDataString(cfg,"players." + p.getUniqueId() + ".Business." + bizId + ".Updates." + type.getStr()) != null;
            }
        }
        return false;
    }


    public void setUpdate(Player p, int bizId, @NotNull UpdateType type, UpdateCategoryType categoryType) {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        switch (type) {
            case PLAYER: {
                getDataList(cfg, "players." + p.getUniqueId() + ".Updates").add(categoryType.getStr());
                return;
            }
            case BUSINESS: {
                getDataList(cfg, "players." + p.getUniqueId() + ".Business." + bizId + ".Updates").add(categoryType.getStr());
            }
        }

    }
}