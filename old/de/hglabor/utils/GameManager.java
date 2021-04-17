package de.hglabor.utils;

import de.hglabor.Bingo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class GameManager {

    public GameManager(File configFile) {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private static Bingo plugin;
    private static YamlConfiguration config;
    private static GamePhase gamePhase = GamePhase.WAITING;
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static ArrayList<Player> spectators = new ArrayList<Player>();

    public GameManager(Bingo bingo) {
        plugin = bingo;
    }

    public void setGamePhase(GamePhase phase) {
        gamePhase = phase;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public Collection<Player> getSpectators() {
        return spectators;
    }

    public Boolean isSpectator(Player player) {
        return spectators.contains(player);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addSpectator(Player player) {
        spectators.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void removeSpectator(Player player) {
        spectators.remove(player);
    }

    public Integer getSpectatorCount() {
        return spectators.size();
    }

    public Integer getPlayerCount() {
        return players.size();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void startGame() {
        Bingo.getManager().setGamePhase(GamePhase.STARTING);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all.getLocale().toLowerCase().contains("de")) {
                    all.sendMessage(Bingo.getLocalization().getStartingMessage(Locale.GERMAN).replaceAll("%value%", "20"));
                } else {
                    all.sendMessage(Bingo.getLocalization().getStartingMessage(Locale.ENGLISH).replaceAll("%value%", "20"));
                }
            });
        }, 10*20L);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all.getLocale().toLowerCase().contains("de")) {
                    all.sendMessage(Bingo.getLocalization().getStartingMessage(Locale.GERMAN).replaceAll("%value%", "10"));
                } else {
                    all.sendMessage(Bingo.getLocalization().getStartingMessage(Locale.ENGLISH).replaceAll("%value%", "10"));
                }
            });
        }, 20*20L);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all.getLocale().toLowerCase().contains("de")) {
                    all.sendMessage(Bingo.getLocalization().getStartingMessage(Locale.GERMAN).replaceAll("%value%", "5"));
                } else {
                    all.sendMessage(Bingo.getLocalization().getStartingMessage(Locale.ENGLISH).replaceAll("%value%", "5"));
                }
            });
        }, 25*20L);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            if(!config.getBoolean("event")) {
                Bingo.getManager().setGamePhase(GamePhase.IN_GAME);
                Bukkit.getOnlinePlayers().forEach(all -> {
                    all.teleport(new Location(Bukkit.getWorld("world"), 0, 257, 0));
                    all.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30*20, 10, false, false));
                    all.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 10, false, false));
                    ItemStack stack = new ItemStack(Material.FILLED_MAP);
                    MapMeta meta = (MapMeta) stack.getItemMeta();
                    meta.setDisplayName("§9Items");
                    stack.setItemMeta(meta);
                    all.getInventory().setItemInOffHand(stack);
                });
            } else {
                ItemStack stack = new ItemStack(Material.FILLED_MAP);
                MapMeta meta = (MapMeta) stack.getItemMeta();
                meta.setDisplayName("§9Items");
                stack.setItemMeta(meta);
                Bukkit.getOnlinePlayers().forEach(all -> {
                    all.getInventory().setItemInOffHand(stack);
                    all.sendTitle("§bPreparing Grid...", "§7Please wait a few seconds", 10, 70, 20);
                    all.sendMessage("§8§m                                       §8[§bHG§fLabor §8— §9Bingo§8]§8§m                                       ");
                    all.sendMessage("");
                    all.sendMessage("§9§l§nFirst de.hglabor.Bingo Event");
                    all.sendMessage("§7PvP is enabled");
                    all.sendMessage("§7Damage is always 0");
                    all.sendMessage("");
                    all.sendMessage("§9§l§nSpecial Thanks");
                    all.sendMessage("§bNoRiskk §7— §9Hosting the server");
                    all.sendMessage("§bMooZiii §7— §9Plugin coding");
                    all.sendMessage("§bSyriuus §7— §9Message Translation");
                    all.sendMessage("");
                    all.sendMessage("§8§m                                       §8[§bHG§fLabor §8— §9Bingo§8]§8§m                                       ");
                    Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                        all.teleport(new Location(Bukkit.getWorld("world"), 0, 257, 0));
                        all.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30*20, 10, false, false));
                        all.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 10, false, false));
                    }, 8*20L);
                });

            }
        }, 30*20L);
    }

    public void endGame() {
        Bingo.getManager().setGamePhase(GamePhase.ENDING);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all.getLocale().toLowerCase().contains("de")) {
                    all.sendMessage(Bingo.getLocalization().getRestartingMessage(Locale.GERMAN).replaceAll("%value%", "20"));
                } else {
                    all.sendMessage(Bingo.getLocalization().getRestartingMessage(Locale.ENGLISH).replaceAll("%value%", "20"));
                }
            });
        }, 10*20L);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all.getLocale().toLowerCase().contains("de")) {
                    all.sendMessage(Bingo.getLocalization().getRestartingMessage(Locale.GERMAN).replaceAll("%value%", "10"));
                } else {
                    all.sendMessage(Bingo.getLocalization().getRestartingMessage(Locale.ENGLISH).replaceAll("%value%", "10"));
                }
            });
        }, 20*20L);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all.getLocale().toLowerCase().contains("de")) {
                    all.sendMessage(Bingo.getLocalization().getRestartingMessage(Locale.GERMAN).replaceAll("%value%", "5"));
                } else {
                    all.sendMessage(Bingo.getLocalization().getRestartingMessage(Locale.ENGLISH).replaceAll("%value%", "5"));
                }
            });
        }, 25*20L);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(all -> {
                all.kickPlayer(Bingo.getLocalization().getPrefix(Locale.GERMAN) + "§cServer is restarting");
            });
            Bukkit.getServer().spigot().restart();
        }, 30*20L);
    }

}
