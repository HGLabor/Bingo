package de.hglabor.core.phase

import de.hglabor.Bingo
import de.hglabor.core.GamePhase
import de.hglabor.core.mechanics.ConnectionHandler
import de.hglabor.core.mechanics.MapManager
import de.hglabor.core.mechanics.MapManager.giveBingoMap
import de.hglabor.core.mechanics.MaterialManager
import de.hglabor.listener.player.User
import de.hglabor.listener.player.UserState
import de.hglabor.settings.Settings
import de.hglabor.utils.*
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.utils.hasMark
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.*
import kotlin.random.Random

class StartingPhase : GamePhase() {
    private val world = Bukkit.getWorld("world")!!
    private val spawnRadius = 30;

    init {
        listeners += listen<EntityDamageEvent> { it.isCancelled = true }
        listeners += listen<PlayerDropItemEvent> { if (it.itemDrop.itemStack.hasMark("locked")) it.isCancelled = true }
        listeners += listen<PlayerInteractEvent> {
            if (it.hasItem() && it.item?.hasMark("locked") == true) it.isCancelled = true
        }
        listeners += listen<PlayerLoginEvent> { ConnectionHandler.handlePlayerLoginEvent(it) }
        listeners += listen<PlayerSwapHandItemsEvent> { if (Settings.usingMap) it.isCancelled = true }
        listeners += listen<PlayerArmorStandManipulateEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractAtEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
        MaterialManager.enable()
        worlds.forEach {
            it.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
            world.time = 0
        }
        teleportPlayers(onlinePlayers.toList())
    }

    override fun nextPhase(): GamePhase = InGamePhase()

    override fun tick(tick: Int) {
        broadcast("Das Spiel startet!")
        startNextPhase()
    }

    private fun teleportPlayers(players: List<Player>) {
        players.forEach {
            val x = Random.nextInt(-spawnRadius, spawnRadius)
            val z = Random.nextInt(-spawnRadius, spawnRadius)
            val y = world.getHighestBlockYAt(x, z) + 2
            it.teleport(Location(world, x.toDouble(), y.toDouble(), z.toDouble()))
            it.inventory.clear()
            it.title("Bingo", "gl & hf")

            it.user.state = UserState.ALIVE
            it.user.bingoField = MapManager.createBingoField(MaterialManager.materials)

            if (!Settings.hitCooldown) it.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.baseValue = 100.0
            if (Settings.usingMap) it.giveBingoMap()
            if (Settings.teams) {
                if (!it.isInTeam) {
                    var randomTeam = Bingo.teams.random()
                    var triedTeams = 0
                    while (isTeamFull(randomTeam)) {
                        randomTeam = Bingo.teams.random()
                        triedTeams++
                        if (triedTeams > 10) {
                            it.kickPlayer("No team found for you.")
                            break
                        }
                    }
                    it.joinTeam(randomTeam.id)
                }
            }
        }
    }
}
