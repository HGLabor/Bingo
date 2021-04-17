package de.hglabor.listener;

import de.hglabor.utils.BingoManager;
import de.hglabor.Bingo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Locale;

public class ClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            if(event.getCurrentItem() != null) {
                Player player = (Player) event.getWhoClicked();
                Material material = event.getCurrentItem().getType();
                if(event.getClickedInventory() != null) {
                    if(player.getLocale().toLowerCase().contains("de")) {
                        if(!event.getView().getTitle().equalsIgnoreCase(Bingo.getLocalization().getFieldName(Locale.GERMAN))) {
                            if(BingoManager.getNeededMaterials().contains(material)) {
                                if(!BingoManager.hasChecked(player, material)) {
                                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 0);
                                    player.sendTitle("§b§L" + BingoManager.getItemName(material), "§7checked");
                                    BingoManager.setChecked(player, material, true);
                                    Bukkit.getOnlinePlayers().forEach(all -> {
                                        if(all.getLocale().toLowerCase().contains("de")) {
                                            all.sendMessage(Bingo.getLocalization().getRegisteringMessage(Locale.GERMAN)
                                                    .replaceAll("%player%", player.getName())
                                                    .replaceAll("%item%", BingoManager.getItemName(material))
                                                    .replaceAll("%checked%", BingoManager.getCheckedItems(player).size() + "")
                                                    .replaceAll("%needed%", BingoManager.getNeededMaterials().size() + ""));

                                        } else {
                                            all.sendMessage(Bingo.getLocalization().getRegisteringMessage(Locale.ENGLISH)
                                                    .replaceAll("%player%", player.getName())
                                                    .replaceAll("%item%", BingoManager.getItemName(material))
                                                    .replaceAll("%checked%", BingoManager.getCheckedItems(player).size() + "")
                                                    .replaceAll("%needed%", BingoManager.getNeededMaterials().size() + ""));
                                        }
                                    });
                                    if(BingoManager.getCheckedItems(player).size() == BingoManager.getNeededMaterials().size()) {
                                        Bukkit.getOnlinePlayers().forEach(all -> {
                                            if(all.getLocale().toLowerCase().contains("de")) {
                                                all.sendMessage(Bingo.getLocalization().getRegisteringMessage(Locale.GERMAN)
                                                        .replaceAll("%player%", player.getName())
                                                        .replaceAll("%item%", BingoManager.getItemName(material))
                                                        .replaceAll("%checked%", BingoManager.getCheckedItems(player).size() + "")
                                                        .replaceAll("%needed%", BingoManager.getNeededMaterials().size() + ""));

                                            } else {
                                                all.sendMessage(Bingo.getLocalization().getRegisteringMessage(Locale.ENGLISH)
                                                        .replaceAll("%player%", player.getName())
                                                        .replaceAll("%item%", BingoManager.getItemName(material))
                                                        .replaceAll("%checked%", BingoManager.getCheckedItems(player).size() + "")
                                                        .replaceAll("%needed%", BingoManager.getNeededMaterials().size() + ""));
                                            }
                                        });
                                        Bingo.getManager().endGame();
                                    }
                                }
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    } else {
                        if(!event.getView().getTitle().equalsIgnoreCase(Bingo.getLocalization().getFieldName(Locale.ENGLISH))) {

                            if(BingoManager.getNeededMaterials().contains(material)) {
                                if(!BingoManager.hasChecked(player, material)) {
                                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 0);
                                    player.sendTitle("§b§L" + BingoManager.getItemName(material), "§7checked");
                                    BingoManager.setChecked(player, material, true);
                                    Bukkit.getOnlinePlayers().forEach(all -> {
                                        if(all.getLocale().toLowerCase().contains("de")) {
                                            all.sendMessage(Bingo.getLocalization().getRegisteringMessage(Locale.GERMAN)
                                                    .replaceAll("%player%", player.getName())
                                                    .replaceAll("%item%", BingoManager.getItemName(material))
                                                    .replaceAll("%checked%", BingoManager.getCheckedItems(player).size() + "")
                                                    .replaceAll("%needed%", BingoManager.getNeededMaterials().size() + ""));

                                        } else {
                                            all.sendMessage(Bingo.getLocalization().getRegisteringMessage(Locale.ENGLISH)
                                                    .replaceAll("%player%", player.getName())
                                                    .replaceAll("%item%", BingoManager.getItemName(material))
                                                    .replaceAll("%checked%", BingoManager.getCheckedItems(player).size() + "")
                                                    .replaceAll("%needed%", BingoManager.getNeededMaterials().size() + ""));
                                        }
                                    });
                                    if(BingoManager.getCheckedItems(player).size() == BingoManager.getNeededMaterials().size()) {
                                        Bukkit.getOnlinePlayers().forEach(all -> {
                                            if(all.getLocale().toLowerCase().contains("de")) {
                                                all.sendMessage(Bingo.getLocalization().getWinMessage(Locale.GERMAN)
                                                        .replaceAll("%player%", player.getName())
                                                        .replaceAll("%item%", BingoManager.getItemName(material))
                                                        .replaceAll("%checked%", BingoManager.getCheckedItems(player).size() + "")
                                                        .replaceAll("%needed%", BingoManager.getNeededMaterials().size() + ""));

                                            } else {
                                                all.sendMessage(Bingo.getLocalization().getWinMessage(Locale.ENGLISH)
                                                        .replaceAll("%player%", player.getName())
                                                        .replaceAll("%item%", BingoManager.getItemName(material))
                                                        .replaceAll("%checked%", BingoManager.getCheckedItems(player).size() + "")
                                                        .replaceAll("%needed%", BingoManager.getNeededMaterials().size() + ""));
                                            }
                                        });
                                        Bingo.getManager().endGame();
                                    }
                                }
                            }
                        } else {
                            event.setCancelled(true);
                        }
                    }

                }
            }
        }
    }
}
