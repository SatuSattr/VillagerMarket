package net.bestemor.villagermarket.command.subcommand;

import net.bestemor.core.command.ISubCommand;
import net.bestemor.core.config.ConfigManager;
import net.bestemor.villagermarket.VMPlugin;
import net.bestemor.villagermarket.shop.VillagerShop;
import net.bestemor.villagermarket.utils.VMUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveHereCommand implements ISubCommand {

    private final VMPlugin plugin;

    public MoveHereCommand(VMPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getCompletion(String[] args) {
        if (args.length == 2) {
            List<String> completions = new ArrayList<>();

            for (VillagerShop shop : plugin.getShopManager().getShops()) {
                String shortUuid = shop.getEntityUUID().toString().substring(0, 8);
                completions.add(shortUuid);
            }

            String input = args[1].toLowerCase();
            if (!input.isEmpty()) {
                completions.removeIf(completion -> !completion.toLowerCase().startsWith(input));
            }

            return completions;
        }
        return new ArrayList<>();
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ConfigManager.getMessage("messages.player_only"));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(ConfigManager.getMessage("messages.movehere_usage"));
            return;
        }

        String identifier = args[1];
        VillagerShop targetShop = null;

        for (VillagerShop shop : plugin.getShopManager().getShops()) {
            String uuid = shop.getEntityUUID().toString();
            if (uuid.toLowerCase().startsWith(identifier.toLowerCase()) || uuid.equalsIgnoreCase(identifier)) {
                targetShop = shop;
                break;
            }
        }

        if (targetShop == null) {
            player.sendMessage(ConfigManager.getMessage("messages.villager_shop_not_found"));
            return;
        }

        String shopName = targetShop.getShopName() != null ? targetShop.getShopName() : "Villager Shop";

        Entity entity = VMUtils.getEntity(targetShop.getEntityUUID());
        if (entity == null) {
            // entity missing (e.g. shop imported from another server) — spawn a new villager at player's location
            Entity newEntity = plugin.getShopManager().spawnShop(player.getLocation(), "player");
            if (newEntity == null) {
                player.sendMessage(ConfigManager.getMessage("messages.movehere_entity_not_found"));
                return;
            }
            targetShop.setUUID(newEntity.getUniqueId());
            targetShop.setShopName(shopName);
            player.sendMessage(ConfigManager.getMessage("messages.movehere_success").replace("%shop%", shopName));
            return;
        }

        entity.teleport(player.getLocation());
        player.sendMessage(ConfigManager.getMessage("messages.movehere_success").replace("%shop%", shopName));
    }

    @Override
    public String getDescription() {
        return "Move shop to your location";
    }

    @Override
    public String getUsage() {
        return "<id>";
    }

    @Override
    public boolean requirePermission() {
        return true;
    }
}
