package de.hglabor.core.mechanics

import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.localization.Localization
import de.hglabor.utils.canLogin
import org.bukkit.event.player.PlayerLoginEvent

object ConnectionHandler {
    fun handlePlayerLoginEvent(event: PlayerLoginEvent) {
        if (event.player.canLogin) return
        if(!event.player.canLogin) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Localization.getUnprefixedMessage("bingo.died", event.player.locale))
        } else if(GamePhaseManager.phase is InGamePhase) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Localization.getUnprefixedMessage("bingo.gameAlreadyStarted", event.player.locale))
        }
    }
}
