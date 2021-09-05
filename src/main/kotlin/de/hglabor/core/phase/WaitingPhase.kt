package de.hglabor.core.phase

import de.hglabor.config.Config
import de.hglabor.core.GamePhase
import de.hglabor.localization.Localization
import de.hglabor.rendering.ChunkGenerator
import de.hglabor.settings.Settings
import de.hglabor.utils.broadcast
import de.hglabor.utils.isLobby
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.feedSaturate
import net.axay.kspigot.extensions.bukkit.heal
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.utils.hasMark
import net.axay.kspigot.utils.mark
import org.bukkit.*
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.*

class WaitingPhase : GamePhase() {
    val worldGenerator = ChunkGenerator(Bukkit.getWorld("world"), checkToStartGame = true)
    val netherGenerator = ChunkGenerator(Bukkit.getWorld("world_nether"), useWorldBorderSize = true)

    init {
        worldGenerator.pregenerate()
        netherGenerator.pregenerate()
        Bukkit.getWorld("lobby")?.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        Bukkit.getWorld("lobby")?.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        Bukkit.getWorld("lobby")?.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        Bukkit.getWorld("lobby")?.time = 6000
        listeners += listen<FoodLevelChangeEvent> { it.isCancelled = true }
        listeners += listen<PlayerJoinEvent> {
            it.joinMessage = null
            it.player.teleport(Location(Bukkit.getWorld("lobby"), 0.0, 8.0, 0.0))
            it.player.performCommand("sideboard")
            it.player.heal()
            it.player.gameMode = GameMode.ADVENTURE
            it.player.feedSaturate()
            if (it.player.hasPermission("hglabor.bingo.settings")) {
                val stack = itemStack(Material.TURTLE_EGG) {
                    meta {
                        name = "${KColors.CORNFLOWERBLUE}${
                            Localization.getUnprefixedMessage(
                                "bingo.word.settings",
                                it.player.locale
                            )
                        }"
                    }
                }
                stack.mark("locked")
                stack.mark("settings")
                it.player.inventory.setItem(4, stack)
            }
            it.player.inventory.setItem(8, itemStack(Material.COMPARATOR) {
                meta {
                    name = "${KColors.RED}Settings"
                }
                mark("player-settings")
                mark("locked")
            })
            if (Settings.teams) {
                val stack = itemStack(Material.LIGHT_BLUE_BED) {
                    meta {
                        name = "${KColors.CORNFLOWERBLUE}Teams"
                    }
                }
                stack.mark("locked")
                stack.mark("teams")
                it.player.inventory.setItem(1, stack)
            }

            broadcast("${it.player.name} ist beigetreten")
        }
        listeners += listen<PlayerInteractEvent> {
            it.isCancelled = true
            if (it.hasItem() && it.item?.hasMark("settings")!!) it.player.performCommand("settings")
            if (it.hasItem() && it.item?.hasMark("teams")!!) it.player.performCommand("teams")
            if (it.hasItem() && it.item?.hasMark("player-settings")!!) it.player.performCommand("sideboard")
        }
        listeners += listen<PlayerQuitEvent> {
            it.quitMessage = null
            broadcast("${it.player.name} hat das Spiel verlassen")
        }
        listeners += listen<EntityDamageEvent> { it.isCancelled = true }
        listeners += listen<PlayerDropItemEvent> { it.isCancelled = true }
        listeners += listen<PlayerArmorStandManipulateEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
        listeners += listen<PlayerInteractAtEntityEvent> { if (it.player.isLobby()) it.isCancelled = true }
    }

    override fun tick(tick: Int) {
        if (!worldGenerator.hasFinished && !netherGenerator.hasFinished) {
            return
        }

        if (Settings.teams) {
            if (onlinePlayers.size >= Config.playerCountToStart * 2) {
                //  GamePhaseManager.startGame(120)
            }
        } else {
            if (onlinePlayers.size >= Config.playerCountToStart) {
                //    GamePhaseManager.startGame(60)
            }
        }
    }

    override fun nextPhase(): GamePhase = StartingPhase()
}
