package ru.solomka.business.core.updates;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.config.utils.CustomConfig;
import ru.solomka.business.core.enums.UpdateCategoryType;
import ru.solomka.business.core.enums.UpdateType;

public class UpdateHandler {

    private final CustomConfig data = new CustomConfig("data");

    public boolean isHaveUpdate(Player p, int bizId, @NotNull UpdateCategoryType type) {
        switch (type) {
            case HAVE_TWO_BUSINESS :
            case HAVE_FOUR_BUSINESS:
            case HAVE_SIX_BUSINESS: {
                return data.getString("players." + p.getUniqueId() + ".Updates." + type.getUpdate()) != null;
            }
        }

        switch (type) {
            case DOUBLE_MONEY:
            case REDUCTION_TAX:
            case SELL_STARTING_PRICE: {
                return data.getString("players." + p.getUniqueId() + ".Business." + bizId + ".Updates." + type.getUpdate()) != null;
            }
        }
        return false;
    }

    public void setUpdate(Player p, int bizId, @NotNull UpdateType type, UpdateCategoryType categoryType) {
        switch (type) {
            case PLAYER: {
                data.getList("players." + p.getUniqueId() + ".Updates").add(categoryType.getUpdate());
                return;
            }
            case BUSINESS: {
                data.getList("players." + p.getUniqueId() + ".Business." + bizId + ".Updates").add(categoryType.getUpdate());
            }
        }
    }
}