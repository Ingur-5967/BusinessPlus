package ru.solomka.business.commands.utils.methods;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ru.solomka.business.Main;
import ru.solomka.business.commands.utils.methods.enums.BusinessClassType;
import ru.solomka.business.commands.utils.methods.enums.HaveBizType;
import ru.solomka.business.commands.utils.methods.interfaces.IBusiness;
import ru.solomka.business.commands.utils.methods.message.Messages;
import ru.solomka.business.menu.enums.ItemType;
import ru.solomka.business.vault.VaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.solomka.business.config.ConfigManager.*;
import static ru.solomka.business.config.utils.Data.getDataString;
import static ru.solomka.business.config.utils.Data.setValue;

public class HandlerBusiness implements IBusiness {

    @Getter private final Main plugin;

    private final List<Integer> business = new ArrayList<>();

    private int sizeClassBiz = 0;

    private final Messages msg = new Messages();
    private final VaultHandler vault = new VaultHandler();

    public HandlerBusiness(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setSalary(UUID uuid, int bizId, int salary) throws IOException {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        setValue(cfg, file,"players." + uuid + "." + bizId + ".Salary", salary);
    }

    @Override
    public void sellBiz(Player p, int id, String @NotNull [] strings) throws IOException {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        for(String str : strings) {
            setValue(cfg, file, str, null);
        }
        vault.giveSalary(p, getInt("Business.Id." + id + ".CostSale"));
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
                Main.getInstance().getLogger().warning("You have exceeded the number limit in the class <" + classType.getType() + ">.\nSize: " + sizeClassBiz + ". Limit: 20" );
                return false;
            }
        }
        return false;
    }

    public List<Integer> getAllHaveBiz(Player p) {
        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        for(int i : getIntegerList("Business.RegistrationId")) {
            if(getDataString(cfg,"players." + p.getUniqueId() + ".Business." + i) == null) continue;
            business.add(i);
        }
        return business;
    }

    @Override
    public int getBizId(String name) {
        for(int i : getIntegerList("Business.RegistrationId")) {
            if(getString("Business.Id." + i + ".Name").equals(ChatColor.translateAlternateColorCodes('&', name))) return i;
        }
        plugin.getLogger().warning("Couldn't find a business with the name '" + name + "'! Maybe you didn't write it in 'RegistrationId'");
        return -1;
    }

    public boolean onBusiness(@NotNull Inventory inventory, Player p, int slot) throws IOException {
        if(inventory.getItem(slot) == null || inventory.getItem(slot) == ItemType.DEFAULT_ITEM.getItemStack()) return false;

        ItemStack item = inventory.getItem(slot);
        ItemMeta meta = item.getItemMeta();

        if(meta == null) return false;

        int id = getBizId(meta.getDisplayName());

        if(!vault.takeMoney(p,getInt("Business.Id." + id + ".Cost"))) {
            p.sendMessage(msg.notHaveMoney(getString("Business.Id." + id + ".Cost")));
            return true;
        }

        File file = new File(Main.getInstance().getDataFolder() + "/data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        setValue(cfg, file, "players." + p.getUniqueId() + ".Business." + id + ".Updates", null);
        vault.takeMoney(p, getInt("Business.Id." + id + ".Cost"));
        return true;
    }
}
