package de.hglabor.commands

import de.hglabor.Bingo
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.localization.Localization
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object TopCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if(GamePhaseManager.phase is InGamePhase) {
                if(sender.world.name.contains("nether")) {
                    sender.teleport(calculateOverworldCoords(sender.location))
                } else {
                    val location = sender.location
                    location.y = location.world?.getHighestBlockAt(location)?.y?.plus(2)?.toDouble()!!
                    sender.teleport(location)
                }
            } else {
                sender.sendMessage(Localization.getMessage("bingo.gameNotStarted", sender.locale().displayLanguage))
            }
        }
        return false
    }

    init {
        Bingo.bingo.getCommand("top")?.setExecutor(this)
    }

    private fun calculateOverworldCoords(netherLocation: Location): Location {
        val x = netherLocation.x*8
        val z = netherLocation.z*8
        val world = Bukkit.getWorld("world")!!
        val location = Location(world, x,100.0,z)
        location.y = world.getHighestBlockAt(location).y.plus(2).toDouble()
        return location
    }
}
