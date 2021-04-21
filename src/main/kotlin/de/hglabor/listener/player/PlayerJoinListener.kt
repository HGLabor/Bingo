package de.hglabor.listener.player

import com.google.common.collect.ImmutableMap
import de.hglabor.core.GameManager
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.utils.die
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.kill
import net.axay.kspigot.runnables.task
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerJoinListener {

    init {
        listen<PlayerJoinEvent> {
            it.joinMessage = null
            Localization.broadcastMessage("bingo.playerJoined", ImmutableMap.of("player", it.player.name))
            task(
                delay = 3
            ) { runnable ->
                val y = Bukkit.getWorld("lobby")?.getHighestBlockYAt(0,0)?.plus(2)?.toDouble()!!
                it.player.teleport(Location(Bukkit.getWorld("lobby")!!, 0.0, y, 0.0))
            }
        }
        listen<PlayerQuitEvent> {
            it.quitMessage = null
            Localization.broadcastMessage("bingo.playerLeft", ImmutableMap.of("player", it.player.name))
            if(GameManager.isStarted) {
                it.player.kill()
                if(!Settings.kickOnDeath) {
                    it.player.die()
                }
            }
        }
    }

}