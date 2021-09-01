package de.hglabor.core

import com.google.common.collect.ImmutableMap
import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper
import de.hglabor.Bingo
import de.hglabor.localization.Localization
import de.hglabor.loot.LootSet
import de.hglabor.rendering.ChunkGenerator
import de.hglabor.rendering.LaborMapRenderer
import de.hglabor.settings.Settings
import de.hglabor.utils.*
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.bukkit.actionBar
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.extensions.console
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.runnables.task
import net.axay.kspigot.utils.mark
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.map.MapView
import java.util.*

object GameManager {

    var currentGamePhase = GamePhase.WAITING
    var isStarted = false
    private val materialPool: ArrayList<Material> = arrayListOf()
    val materials: ArrayList<Material> = arrayListOf()
    val worldGenerator = ChunkGenerator(Bukkit.getWorld("world"), checkToStartGame = true)
    val netherGenerator = ChunkGenerator(Bukkit.getWorld("world_nether"), useWorldBorderSize = true)

    fun addToMaterialPool(lootSet: LootSet) {
        for (material in lootSet.materials.keys) {
            materialPool.add(material)
        }
    }

    fun removeFromMaterialPool(lootSet: LootSet) {
        for (material in lootSet.materials.keys) {
            materialPool.remove(material)
        }
    }

    fun endGame(player: Player) {
        currentGamePhase = GamePhase.END
        isStarted = false
        var i = 0
        task(
            period = 20
        ) {
            i++
            if (i > 19) {
                Localization.broadcastMessage("bingo.serverIsRestarting")
                Bukkit.dispatchCommand(console, "restart")
            } else {
                if (Settings.teams) {
                    Localization.broadcastMessage("bingo.teamWins",
                        ImmutableMap.of("team", "${player.getTeam()?.color}#${player.getTeam()?.id}"))
                } else {
                    Localization.broadcastMessage("bingo.playerWins", ImmutableMap.of("player", player.name))
                }
            }
        }
    }

    fun startGame(startDelay: Int, isForceStart: Boolean = false) {
        if (!worldGenerator.hasFinished) {
            if (isForceStart) {
                broadcast("Spiel konnte nicht gestartet werden.")
                broadcast("Map wird noch geladen...")
            }
            return
        }
        if (materialPool.isEmpty()) {
            for (lootSet in LootSet.values()) {
                if (lootSet.isEnabled) {
                    addToMaterialPool(lootSet)
                }
            }
        }
        currentGamePhase = GamePhase.STARTING
        BukkitCloudNetHelper.setApiMotd("Starting...")
        BukkitCloudNetHelper.setState("Starting")
        var seconds = startDelay
        task(
            howOften = seconds.toLong(),
            period = 20,
            delay = 10
        ) {
            seconds -= 1
            if (seconds == 0) {
                task(
                    howOften = Settings.itemCount,
                    delay = 0,
                    period = 1
                ) {
                    var randomMaterial = materialPool.random()
                    while (materials.contains(randomMaterial)) {
                        randomMaterial = materialPool.random()
                    }
                    materials.add(randomMaterial)
                }
                for (player in onlinePlayers) {
                    player.inventory.clear()
                    player.title("Bingo", "gl & hf")
                    if (Settings.usingMap) {
                        task(delay = 15) {
                            giveMap(player)
                        }
                    }
                    if (Settings.teams) {
                        if (!player.isInTeam()) {
                            var randomTeam = Bingo.teams.random()
                            var triedTeams = 0
                            while (isTeamFull(randomTeam)) {
                                randomTeam = Bingo.teams.random()
                                triedTeams++
                                if (triedTeams > 10) {
                                    player.kickPlayer("No team found for you.")
                                    break
                                }
                            }
                            player.joinTeam(randomTeam.id)
                        }
                    }
                    task(
                        howOften = 20,
                        period = 10
                    ) {
                        player.inventory.remove(Material.TURTLE_EGG)
                        player.inventory.remove(Material.LIGHT_BLUE_BED)
                    }
                    val world = Bukkit.getWorld("world")!!
                    val x = Random().nextInt(30) - Random().nextInt(30)
                    val z = Random().nextInt(30) - Random().nextInt(30)
                    val y = world.getHighestBlockYAt(x, z) + 2
                    world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
                    Bukkit.getWorld("world_nether")?.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
                    player.teleport(Location(world, x.toDouble(), y.toDouble(), z.toDouble()))
                    if (!Settings.hitCooldown) {
                        val attackSpeedAttribute: AttributeInstance? =
                            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)
                        if (attackSpeedAttribute != null) {
                            attackSpeedAttribute.baseValue = 100.0
                        }
                    }
                    manageActionBar(player)
                }
                currentGamePhase = GamePhase.IN_GAME
                isStarted = true
                Localization.broadcastMessage("bingo.gameStarted")
                BukkitCloudNetHelper.changeToIngame()
            } else {
                Localization.broadcastMessage("bingo.gameStartingIn", ImmutableMap.of("seconds", "$seconds"))
            }
        }
    }

    private fun manageActionBar(player: Player) {
        task(
            period = 40
        ) {
            val list = listOf(
                "${KColors.BLUE}/bingo ${KColors.DARKGRAY}| ${KColors.BLUE}/top ${if (Settings.teams) "${KColors.DARKGRAY}| ${KColors.BLUE}/backpack ${KColors.DARKGRAY}| ${KColors.BLUE}/tc <message>" else ""}",
                if (Settings.teams) "${KColors.GRAY}Team ${player.getTeam()?.color}#${player.getTeam()?.id}" else "${KColors.CORNFLOWERBLUE}HGlabor.de Bingo ${KColors.DARKGRAY}| ${KColors.BLUE}1.16.5",
                "${KColors.BLUE}${player.checkedItems().size} ${KColors.DARKGRAY}/ ${KColors.BLUE}${Settings.itemCount}",
                "${KColors.BLUE}PvP${KColors.DARKGRAY}: ${if (Settings.pvp) "§ayes" else "§cno"} ${KColors.DARKGRAY}| ${KColors.BLUE}Hardcore${KColors.DARKGRAY}: ${if (Settings.kickOnDeath) "§ayes" else "§cno"}",
                "${KColors.BLUE}Position${KColors.DARKGRAY}: ${
                    when (posInRanking(player)) {
                        1 -> "${KColors.GOLDENROD}${KColors.UNDERLINE}#1"; 2 -> "${KColors.LIGHTSTEELBLUE}${KColors.UNDERLINE}#2"; 3 -> "${KColors.SADDLEBROWN}${KColors.UNDERLINE}#3"; else -> "${KColors.CORNFLOWERBLUE}${KColors.UNDERLINE}#${
                        posInRankingString(player)
                    }"
                    }
                }"
            )
            player.actionBar(list.random())
        }
    }

    private fun giveMap(player: Player) {
        val stack = itemStack(Material.FILLED_MAP) {
            meta<MapMeta> {
                name = "${KColors.CORNFLOWERBLUE}Bingo"
                val view = Bukkit.createMap(Bukkit.getWorld("world")!!)
                view.scale = MapView.Scale.FARTHEST
                view.isUnlimitedTracking = true
                view.renderers.clear()
                view.addRenderer(LaborMapRenderer)
                view.isLocked = true
                mapView = view
            }
        }
        stack.mark("locked")
        player.inventory.setItemInOffHand(stack)
    }


    private fun posInRankingString(player: Player): String {
        val position = posInRanking(player)
        return if (position < 0) "?" else (position + 1).toString()
    }

    private fun posInRanking(player: Player): Int {
        data class TempBingo(val uuid: UUID, val found: Int)

        val allPlayers = arrayListOf<TempBingo>()
        for (i in checkedItems) allPlayers.add(TempBingo(i.key.uniqueId, i.value.size))
        val sorted = allPlayers.sortedBy { it.found }
        val me = sorted.find { it.uuid == player.uniqueId }
        return sorted.indexOf(me)
    }

}
