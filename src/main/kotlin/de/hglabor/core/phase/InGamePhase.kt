package de.hglabor.core.phase

import de.hglabor.core.mechanics.Broadcaster
import de.hglabor.core.mechanics.ConnectionHandler
import de.hglabor.core.mechanics.ItemCollectorManager
import de.hglabor.settings.Settings
import de.hglabor.utils.isLobby
import de.hglabor.core.GamePhase
import net.axay.kspigot.event.listen
import net.axay.kspigot.utils.hasMark
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*

class InGamePhase : GamePhase() {
    var actionBarMessages = Broadcaster.startActionBarMessage()

    init {
        listeners += listen<EntityDamageByEntityEvent> {
            if (it.entity !is Player) return@listen
            if (it.damager is Player) {
                it.isCancelled = !Settings.pvp
            } else {
                it.isCancelled = !Settings.mobdamage
            }
        }
        listeners += listen<EntityDamageEvent> {
            if (it.entity !is Player) return@listen
            if (it.cause == EntityDamageEvent.DamageCause.FALL) it.isCancelled = !Settings.falldamage
            if (!Settings.allDamage) it.isCancelled = true
        }
        listeners += listen<PlayerDropItemEvent> { if (it.itemDrop.itemStack.hasMark("locked")) it.isCancelled = true }
        listeners += listen<PlayerInteractEvent> {
            if (it.hasItem() && it.item?.hasMark("locked") == true) it.isCancelled = true
        }
        listeners += listen<EntityPickupItemEvent> { ItemCollectorManager.onEntityPickupItem(it) }
        listeners += listen<InventoryClickEvent> { ItemCollectorManager.onPlayerClicksItem(it) }
        listeners += listen<PlayerLoginEvent> { ConnectionHandler.handlePlayerLoginEvent(it) }
        listeners += listen<PlayerSwapHandItemsEvent> { if (Settings.usingMap) it.isCancelled = true }
        listeners += listen<PlayerArmorStandManipulateEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractAtEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
    }

    override fun nextPhase(): GamePhase {
        TODO("Not yet implemented")
    }

    override fun tick(tick: Int) {

    }
}
