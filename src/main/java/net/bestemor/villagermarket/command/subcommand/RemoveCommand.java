package net.bestemor.villagermarket.command.subcommand;

import net.bestemor.core.command.ISubCommand;
import net.bestemor.core.config.ConfigManager;
import net.bestemor.villagermarket.VMPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.bestemor.villagermarket.utils.VMUtils;

import java.util.ArrayList;
import java.util.List;

public class RemoveCommand implements ISubCommand {

    private final VMPlugin plugin;

    public RemoveCommand(VMPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getCompletion(String[] args) {
        return new ArrayList<>();
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return;
        }


        player.sendMessage(ConfigManager.getMessage("messages.remove_villager"));
        plugin.getPlayerListener().addClickListener(player.getUniqueId(), shop -> {
            player.sendMessage(ConfigManager.getMessage("messages.villager_removed"));
            VMUtils.playSound(player, "sounds.remove_villager", 0.5f, 1);
            plugin.getShopManager().removeShop(shop.getEntityUUID());
        });
    }

    @Override
    public String getDescription() {
        return "Remove shop";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public boolean requirePermission() {
        return true;
    }
}
