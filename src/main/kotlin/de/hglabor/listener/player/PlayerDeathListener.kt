package de.hglabor.listener.player

import de.hglabor.core.GamePhaseManager
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.utils.die
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.task
import org.bukkit.event.entity.PlayerDeathEvent

object PlayerDeathListener {

    init {
        listen<PlayerDeathEvent> {
            if(Settings.kickOnDeath) {
                it.entity.die()
                task(
                    delay = 3
                ) { runnable ->
                    it.entity.kickPlayer(Localization.getUnprefixedMessage("bingo.died", it.entity.locale))
                }
                task(
                    delay = 5
                ) { runnable ->
                    if(onlinePlayers.size == 1) {
                        GamePhaseManager.endGame(onlinePlayers.toList()[0])
                    } else if(onlinePlayers.isEmpty()) {
                        GamePhaseManager.endGame(it.entity)
                    }
                }
            }
        }
    }

}
