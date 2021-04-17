package de.hglabor

import de.hglabor.commands.BingoCommand
import de.hglabor.commands.StartCommand
import de.hglabor.listener.inventory.InventoryClickListener
import de.hglabor.listener.player.*
import de.hglabor.localization.Localization
import de.hglabor.rendering.MapListener
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.extensions.pluginManager
import net.axay.kspigot.main.KSpigot
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.WorldCreator
import org.bukkit.permissions.Permission
import org.bukkit.plugin.Plugin

class Bingo : KSpigot() {

    companion object {
        lateinit var plugin: Plugin
        lateinit var bingo: Bingo
    }

    override fun load() {
        broadcast("${KColors.GREENYELLOW}ENABLING PLUGIN")
        for (player in onlinePlayers) {
            player.playSound(player.location, Sound.BLOCK_BEACON_ACTIVATE, 10.0f, 1.0f)
            val y = Bukkit.getWorld("lobby")?.getHighestBlockYAt(0,0)?.plus(2)?.toDouble()!!
            player.teleport(Location(Bukkit.getWorld("lobby")!!, 0.0, y, 0.0))
        }
    }

    override fun startup() {
        plugin = this
        bingo = this
        WorldCreator("lobby").createWorld()
        Localization.load()
        MapListener
        InventoryClickListener
        PlayerPickupListener
        DamageListener
        PlayerMapManipulateListener
        PlayerDeathListener
        PlayerLoginListener
        PlayerJoinListener
        StartCommand
        BingoCommand
        pluginManager.addPermission(Permission("hglabor.bingo.startgame"))
        pluginManager.addPermission(Permission("hglabor.bingo.settings"))
    }

    override fun shutdown() {
        broadcast("${KColors.TOMATO}DISABLING PLUGIN")
        for (player in onlinePlayers) player.playSound(player.location, Sound.BLOCK_BEACON_DEACTIVATE, 10.0f, 1.0f)
    }

}