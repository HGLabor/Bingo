package de.hglabor.core.mechanics

import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.localization.Localization
import de.hglabor.utils.canLogin
import net.kyori.adventure.text.Component
import org.bukkit.event.player.PlayerLoginEvent

object ConnectionHandler {
    fun handlePlayerLoginEvent(event: PlayerLoginEvent) {
        if(!event.player.canLogin) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text(Localization.getUnprefixedMessage("bingo.died", event.player.locale().displayLanguage)))
        } else if(GamePhaseManager.phase is InGamePhase) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text(Localization.getUnprefixedMessage("bingo.gameAlreadyStarted", event.player.locale().displayLanguage)))
        }
    }
}
