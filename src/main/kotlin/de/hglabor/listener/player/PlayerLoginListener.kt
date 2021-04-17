package de.hglabor.listener.player

import de.hglabor.core.GameManager
import de.hglabor.localization.Localization
import de.hglabor.utils.canLogin
import net.axay.kspigot.event.listen
import org.bukkit.event.player.PlayerLoginEvent

object PlayerLoginListener {

    init {
        listen<PlayerLoginEvent> {
            if(!it.player.canLogin) {
                it.disallow(PlayerLoginEvent.Result.KICK_OTHER, Localization.getUnprefixedMessage("bingo.died", it.player.locale))
            } else if(GameManager.isStarted) {
                it.disallow(PlayerLoginEvent.Result.KICK_OTHER, Localization.getUnprefixedMessage("bingo.gameAlreadyStarted", it.player.locale))
            }
        }
    }

}