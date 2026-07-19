package net.bestemor.villagermarket.command.subcommand;

import net.bestemor.core.command.ISubCommand;
import net.bestemor.core.config.ConfigManager;
import net.bestemor.villagermarket.VMPlugin;
import net.bestemor.villagermarket.shop.ShopMenu;
import net.bestemor.villagermarket.shop.VillagerShop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EditCommand implements ISubCommand {

    private final VMPlugin plugin;

    public EditCommand(VMPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getCompletion(String[] args) {
        if (args.length == 2) {
            return plugin.getShopManager().getShops().stream()
                    .map(VillagerShop::getEntityUUID)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ConfigManager.getMessage("messages.player_only"));
            return;
        }

        if (args.length != 2) {
            player.sendMessage(ConfigManager.getMessage("messages.edit_usage"));
            return;
        }

        UUID shopUUID;
        try {
            shopUUID = UUID.fromString(args[1]);
        } catch (IllegalArgumentException e) {
            player.sendMessage(ConfigManager.getMessage("messages.villager_shop_not_found"));
            return;
        }

        VillagerShop shop = plugin.getShopManager().getShop(shopUUID);
        if (shop == null) {
            player.sendMessage(ConfigManager.getMessage("messages.villager_shop_not_found"));
            return;
        }

        shop.updateMenu(ShopMenu.EDIT_SHOP);
        shop.openInventory(player, ShopMenu.EDIT_SHOP);
    }

    @Override
    public String getDescription() {
        return "Open edit menu for a villager shop.";
    }

    @Override
    public String getUsage() {
        return "<uuid>";
    }

    @Override
    public boolean requirePermission() {
        return true;
    }
}
