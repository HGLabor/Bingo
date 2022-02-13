package de.hglabor.commands

import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.localization.Localization
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import org.bukkit.Bukkit
import org.bukkit.Location

object TopCommand {
    init {
        command("top") {
            runs {
                if (GamePhaseManager.phase is InGamePhase) {
                    if (player.world.name.contains("nether")) {
                        player.teleport(calculateOverworldCoords(player.location))
                    } else {
                        val location = player.location
                        location.y = location.world?.getHighestBlockAt(location)?.y?.plus(2)?.toDouble()!!
                        player.teleport(location)
                    }
                } else {
                    player.sendMessage(Localization.getMessage("bingo.gameNotStarted", player.locale().displayLanguage))
                }
            }
        }
    }
}

private fun calculateOverworldCoords(netherLocation: Location): Location {
    val x = netherLocation.x * 8
    val z = netherLocation.z * 8
    val world = Bukkit.getWorld("world")!!
    val location = Location(world, x, 100.0, z)
    location.y = world.getHighestBlockAt(location).y.plus(2).toDouble()
    return location
}
