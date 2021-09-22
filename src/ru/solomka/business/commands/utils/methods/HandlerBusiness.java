package ru.solomka.business.commands.utils.methods;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.enums.BusinessClassType;
import ru.solomka.business.commands.utils.methods.interfaces.IBusiness;
import ru.solomka.business.config.utils.Data;
import ru.solomka.business.vault.VaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.solomka.business.config.ConfigManager.*;
import static ru.solomka.business.config.utils.Data.*;
import static ru.solomka.business.config.utils.Data.getDataString;
import static ru.solomka.business.config.utils.Data.setValue;

public class HandlerBusiness implements IBusiness {

    @Getter private final Main plugin;

   private final List<Integer> business = new ArrayList<>();


    private int sizeClassBiz = 0;
    private final VaultHandler vault = new VaultHandler();

    public HandlerBusiness(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setSalary(UUID uuid, int bizId, int salary) throws IOException, InvalidConfigurationException {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        setValue(cfg, file,"players." + uuid + "." + bizId + ".Owner", salary);
    }

    @Override
    public void sellBiz(Player p, int id, String[] strings) throws IOException, InvalidConfigurationException {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        for(String str : strings) {
            setValue(cfg, file, str, null);
        }
        vault.giveSalary(p, getInt("Business.Id." + id + ".CostSale"));
    }

    @Override
    public int getBizId(String name) {
        for(int i : getIntegerList("Business.RegistrationId")) {
            if(getString("Business.Id." + i + ".Name").equals(ChatColor.translateAlternateColorCodes('&', name))) return i;
        }
        plugin.getLogger().warning("Couldn't find a registered business ID! Check section 'RegistrationId'");
        return -1;
    }

    public boolean isSizeMoreLimit(@NotNull BusinessClassType classType) {
        switch (classType) {
            case LOW:
            case MEDIUM:
            case HIGH: {
                for (int i : getIntegerList("Business.RegistrationId")) {
                    if (getString("Business.Id." + i + ".Class").equals(classType.getType())) sizeClassBiz++;
                }
                if (sizeClassBiz <= 20) return true;
                plugin.getLogger().warning("You have exceeded the number limit in the class <" + classType.getType() + ">.\nSize: " + sizeClassBiz + ". Limit: 20" );
                return false;
            }
        }
        return false;
    }

    public List<Integer> getHaveBusinessId(Player p) {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        for(int i : getIntegerList("Business.RegistrationId")) {
            if(getDataString(cfg,"players." + p.getUniqueId() + "." + i) == null) continue;
            business.add(i);
        }
        return business;
    }
}