package de.hglabor.listener;

import de.hglabor.Bingo;
import de.hglabor.utils.GamePhase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!Bingo.getManager().getGamePhase().equals(GamePhase.IN_GAME)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent event) {
        if(!Bingo.getManager().getGamePhase().equals(GamePhase.IN_GAME)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if(!Bingo.getManager().getGamePhase().equals(GamePhase.IN_GAME)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!Bingo.getManager().getGamePhase().equals(GamePhase.IN_GAME)) {
            event.setCancelled(true);
        }
    }

}
