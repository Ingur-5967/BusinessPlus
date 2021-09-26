package ru.solomka.business.commands.utils.methods.updates;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.commands.utils.methods.enums.UpdateBusinessType;
import ru.solomka.business.commands.utils.methods.enums.UpdatePlayerType;

public class UpdateHandler {

    // TODO 21.09.21

    @Getter private final Player p;

    public UpdateHandler(Player p) {
        this.p = p;
    }

    public boolean isHavePlayerUpdate(@NotNull UpdatePlayerType type) {
        switch (type) {
            case HAVE_TWO_BUSINESS: {}
            case HAVE_FOUR_BUSINESS: {}
            case HAVE_SIX_BUSINESS: {}
        }
        return false;
    }

    public boolean isHaveBusinessUpdate(@NotNull UpdateBusinessType type) {
        switch (type) {
            case DOUBLE_MONEY:  {}
            case REDUCTION_TAX: {}
            case SELL_STARTING_PRICE: {}
        }
        return false;
    }

    public void setUpdate(int bizId, int level) {}
}
