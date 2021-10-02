package ru.solomka.business.core.updates;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.core.enums.UpdateCategoryType;
import ru.solomka.business.core.enums.UpdateType;

import java.io.File;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;
import static ru.solomka.business.config.utils.Data.getDataList;
import static ru.solomka.business.config.utils.Data.getDataString;

public class UpdateHandler {

    public boolean isHaveUpdate(Player p, int bizId, @NotNull UpdateCategoryType type) {
        switch (type) {
            case HAVE_TWO_BUSINESS :
            case HAVE_FOUR_BUSINESS:
            case HAVE_SIX_BUSINESS: {
                return getDataString(loadConfiguration(new File(Main.getInstance().getDataFolder(), "data.yml")),
                        "players." + p.getUniqueId() + ".Updates." + type.getStr()) != null;
            }
        }

        switch (type) {
            case DOUBLE_MONEY:
            case REDUCTION_TAX:
            case SELL_STARTING_PRICE: {
                return getDataString(loadConfiguration(new File(Main.getInstance().getDataFolder(), "data.yml")),
                        "players." + p.getUniqueId() + ".Business." + bizId + ".Updates." + type.getStr()) != null;
            }
        }
        return false;
    }

    public void setUpdate(Player p, int bizId, @NotNull UpdateType type, UpdateCategoryType categoryType) {
        switch (type) {
            case PLAYER: {
                getDataList(loadConfiguration(new File(Main.getInstance().getDataFolder(),"data.yml")),
                        "players." + p.getUniqueId() + ".Updates").add(categoryType.getStr());
                return;
            }
            case BUSINESS: {
                getDataList(loadConfiguration(new File(Main.getInstance().getDataFolder(),"data.yml")),
                        "players." + p.getUniqueId() + ".Business." + bizId + ".Updates").add(categoryType.getStr());
            }
        }
    }
}