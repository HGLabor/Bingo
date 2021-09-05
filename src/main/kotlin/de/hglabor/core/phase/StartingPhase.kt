package de.hglabor.core.phase

import de.hglabor.Bingo
import de.hglabor.core.GamePhase
import de.hglabor.core.mechanics.ConnectionHandler
import de.hglabor.core.mechanics.MaterialManager
import de.hglabor.core.mechanics.PlayerScattering
import de.hglabor.listener.player.UserState
import de.hglabor.settings.Settings
import de.hglabor.utils.*
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.utils.hasMark
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.*

class StartingPhase : GamePhase() {
    private val world = Bukkit.getWorld("world")!!
    private val spawnRadius = 30
    private var playerScattering: PlayerScattering? = null

    init {
        initTeams(onlinePlayers.toList())
        listeners += listen<EntityDamageEvent> { it.isCancelled = true }
        listeners += listen<PlayerInteractEvent> {
            if (it.hasItem() && it.item?.hasMark("locked") == true) it.isCancelled = true
        }
        listeners += listen<PlayerQuitEvent> {
            it.quitMessage = null
            broadcast("${it.player.name} hat das Spiel verlassen")
        }
        listeners += listen<PlayerDropItemEvent> { it.isCancelled = true }
        listeners += listen<PlayerLoginEvent> { ConnectionHandler.handlePlayerLoginEvent(it) }
        listeners += listen<PlayerSwapHandItemsEvent> { if (Settings.usingMap) it.isCancelled = true }
        listeners += listen<PlayerArmorStandManipulateEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<BlockBreakEvent> { it.isCancelled = true }
        listeners += listen<PlayerInteractEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractAtEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
        MaterialManager.enable()
        worlds.forEach {
            it.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
            world.time = 0
        }
        playerScattering = PlayerScattering(onlinePlayers.map { it.user }.toMutableList(), 6, spawnRadius, world)
        playerScattering?.runTaskTimer(Bingo.plugin, 0, 1 * 20)
    }

    override fun nextPhase(): GamePhase = InGamePhase()

    override fun tick(tick: Int) {
        if (playerScattering?.isCancelled == true) {
            broadcast("Das Spiel startet!")
            startNextPhase()
        }
    }

    private fun initTeams(players: List<Player>) {
        players.forEach {
            if (Settings.teams) {
                if (!it.isInTeam && it.user.state != UserState.SPECTATOR) {
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
