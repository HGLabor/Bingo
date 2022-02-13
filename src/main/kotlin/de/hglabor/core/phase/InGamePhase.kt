package de.hglabor.core.phase

import de.hglabor.core.GamePhase
import de.hglabor.core.mechanics.Broadcaster
import de.hglabor.core.mechanics.ConnectionHandler
import de.hglabor.core.mechanics.ItemCollectorManager
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.utils.*
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.taskRunLater
import net.axay.kspigot.utils.hasMark
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.entity.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*
import org.bukkit.inventory.EquipmentSlot

class InGamePhase : GamePhase() {
    var actionBarMessages = Broadcaster.startActionBarMessage()
    private var endPhase: EndPhase? = null

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
            if (it.hand == EquipmentSlot.OFF_HAND && it.hasItem() && it.item?.hasMark("locked") == true) it.isCancelled =
                true
        }
        listeners += listen<FoodLevelChangeEvent> { it.isCancelled = !Settings.loseHunger }
        listeners += listen<EntityPickupItemEvent> { ItemCollectorManager.onEntityPickupItem(it) }
        listeners += listen<InventoryClickEvent> { ItemCollectorManager.onPlayerClicksItem(it) }
        listeners += listen<PlayerLoginEvent> { ConnectionHandler.handlePlayerLoginEvent(it) }
        listeners += listen<PlayerSwapHandItemsEvent> { if (Settings.usingMap) it.isCancelled = true }
        listeners += listen<PlayerArmorStandManipulateEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractAtEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerQuitEvent> {
            it.quitMessage(null)
            broadcast("${it.player.name} hat das Spiel verlassen")
            if (Settings.teams && it.player.isInTeam) {
                it.player.leaveTeam(it.player.getTeam()!!.id)
            }
            if (Settings.kickOnDeath) {
                it.player.die()
                it.player.kick(Component.text("Du bist gestorben"))
            }
        }
        listeners += listen<PlayerDeathEvent> {
            if (!Settings.kickOnDeath) return@listen
            it.entity.die()
            taskRunLater(3) { it.entity.kick(Component.text(Localization.getUnprefixedMessage("bingo.died", it.entity.locale().displayLanguage))) }
            taskRunLater(5) {
                when {
                    onlinePlayers.size == 1 -> end(onlinePlayers.firstOrNull())
                    onlinePlayers.isEmpty() -> end(null)
                }
            }
        }
    }

    fun end(player: Player?) {
        endPhase = EndPhase(player)
        startNextPhase()
    }

    override fun nextPhase(): GamePhase = endPhase!!

    override fun tick(tick: Int) {

    }
}
