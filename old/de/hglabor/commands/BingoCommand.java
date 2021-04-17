package de.hglabor.commands;

import de.hglabor.Bingo;
import de.hglabor.utils.BingoManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

public class BingoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§9KEIN SPIELER");
        } else {
            Player player = (Player) sender;
            Inventory inventory;
            if(player.getLocale().toLowerCase().contains("de")) {
                inventory = Bukkit.createInventory(null, 18, Bingo.getLocalization().getFieldName(Locale.GERMAN));
            } else {
                inventory = Bukkit.createInventory(null, 18, Bingo.getLocalization().getFieldName(Locale.ENGLISH));
            }
            int slot = -1;
            for(Material material : BingoManager.getNeededMaterials()) {
                slot+=1;
                ItemStack stack = new ItemStack(material);
                ItemMeta meta = stack.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                if(BingoManager.hasChecked(player, material)) {
                    meta.addEnchant(Enchantment.KNOCKBACK, 10, true);
                }
                stack.setItemMeta(meta);
                inventory.setItem(slot, stack);
            }
            player.openInventory(inventory);
        }
        return false;
    }
}
