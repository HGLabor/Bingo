package de.hglabor.listener;

import de.hglabor.utils.GamePhase;
import de.hglabor.Bingo;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class JoinListener implements Listener {

    private static YamlConfiguration config;

    public JoinListener(File configFile) {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(Bingo.getManager().getGamePhase() == GamePhase.WAITING) {
            Bingo.getManager().addPlayer(player);
            event.setJoinMessage(null);
            if(!config.getBoolean("event")) {
                if(Bingo.getManager().getPlayerCount() > 1) {
                    Bingo.getManager().startGame();
                }
                player.setPlayerListHeaderFooter("§a\n§bHG§fLabor§3.de §7— §eDie Zukunft\n", "\n§6§l#1 minecraft server \n§a");
            } else {
                player.setPlayerListHeaderFooter("§a\n§bHG§fLabor§3.de §7— §eDie Zukunft\n", "\n§9§lFirst de.hglabor.Bingo Event\n§a");
            }
        } else {
            Bingo.getManager().addSpectator(player);
            event.setJoinMessage(null);
            player.setPlayerListHeaderFooter("§a\n§bHG§fLabor§3.de §7— §eDie Zukunft\n", "\n§7§o#1 minecraft server \n§a");
        }
    }

}
