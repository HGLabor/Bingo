package de.hglabor.listener.player

import com.google.common.collect.ImmutableMap
import de.hglabor.config.Config
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.PhaseType
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.utils.die
import de.hglabor.utils.getTeam
import de.hglabor.utils.isInTeam
import de.hglabor.utils.leaveTeam
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.kill
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.task
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerJoinListener {

    init {
        listen<PlayerQuitEvent> {
            it.quitMessage = null
            if(Settings.teams) {
                if(it.player.isInTeam()) {
                    it.player.leaveTeam(it.player.getTeam()!!.id)
                }
            }
            Localization.broadcastMessage("bingo.playerLeft", ImmutableMap.of("player", it.player.name))
            if(GamePhaseManager.isStarted) {
                it.player.kill()
                if(!Settings.kickOnDeath) {
                    it.player.die()
                }
            }
        }
    }
}
