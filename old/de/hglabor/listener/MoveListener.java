package de.hglabor.listener;

import de.hglabor.utils.GamePhase;
import de.hglabor.Bingo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(!Bingo.getManager().getGamePhase().equals(GamePhase.IN_GAME)) {
            event.setCancelled(true);
        }
    }

}
