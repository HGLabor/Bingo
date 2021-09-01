package de.hglabor

import de.hglabor.commands.BingoCommand
import de.hglabor.commands.SettingsCommand
import de.hglabor.commands.StartCommand
import de.hglabor.commands.TopCommand
import de.hglabor.config.Config
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.mechanics.ConnectionHandler
import de.hglabor.core.phase.WaitingPhase
import de.hglabor.listener.player.PlayerDeathListener
import de.hglabor.listener.player.PlayerJoinListener
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.team.BackpackCommand
import de.hglabor.team.Team
import de.hglabor.team.TeamChatCommand
import de.hglabor.team.TeamsGUI
import de.hglabor.utils.teamColors
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.bukkit.feedSaturate
import net.axay.kspigot.extensions.bukkit.heal
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.pluginManager
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.main.KSpigot
import net.axay.kspigot.runnables.task
import net.axay.kspigot.utils.mark
import org.bukkit.*
import org.bukkit.permissions.Permission
import org.bukkit.plugin.Plugin

class Bingo : KSpigot() {

    companion object {
        lateinit var plugin: Plugin
        lateinit var bingo: Bingo
        var teams: ArrayList<Team> = arrayListOf()
    }

    override fun load() {
        broadcast("${KColors.GREENYELLOW}ENABLING PLUGIN")
        for (player in onlinePlayers) {
            player.playSound(player.location, Sound.BLOCK_BEACON_ACTIVATE, 10.0f, 1.0f)
            val y = Bukkit.getWorld("lobby")?.getHighestBlockYAt(0, 0)?.plus(2)?.toDouble()!!
            player.teleport(Location(Bukkit.getWorld("lobby")!!, 0.0, y, 0.0))
        }
    }

    override fun startup() {
        plugin = this
        bingo = this
        //GamePhaseManager.worldGenerator.pregenerate()
       // GamePhaseManager.netherGenerator.pregenerate()
        WorldCreator("lobby").type(WorldType.FLAT).createWorld()
        Bukkit.getWorld("world_the_nether")?.worldBorder?.size = 1000.0 //TODO eig config
        Localization.load()
        Config
        var i = 0
        task(
            howOften = 17,
            period = 1
        ) {
            i++
            val color = teamColors().random()
            val team = Team(
                arrayListOf(),
                arrayListOf(),
                i - 1,
                color,
                Bukkit.createInventory(null, 27, "${KColors.GRAY}Team ${color}#${i - 1}")
            )
            teams.add(team)
        }
        PlayerDeathListener
        ConnectionHandler
        PlayerJoinListener
        StartCommand
        BingoCommand
        SettingsCommand
        TopCommand
        TeamsGUI.TeamsCommand
        BackpackCommand
        TeamChatCommand
        pluginManager.addPermission(Permission("hglabor.bingo.startgame"))
        pluginManager.addPermission(Permission("hglabor.bingo.settings"))

        GamePhaseManager.run()
    }

    override fun shutdown() {
        broadcast("${KColors.TOMATO}DISABLING PLUGIN")
        for (player in onlinePlayers) player.playSound(player.location, Sound.BLOCK_BEACON_DEACTIVATE, 10.0f, 1.0f)
    }

}
