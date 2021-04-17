package de.hglabor.listener.player

import de.hglabor.core.GameManager
import de.hglabor.settings.Settings
import net.axay.kspigot.event.listen
import net.axay.kspigot.utils.hasMark
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

object PlayerMapManipulateListener {

    init {
        listen<PlayerInteractEvent> {
            if(!GameManager.isStarted) {
                it.isCancelled = true
            }
        }
        listen<PlayerSwapHandItemsEvent> {
            if(Settings.usingMap) {
                it.isCancelled = true
            }
        }
        listen<PlayerInteractEvent> {
            if(it.hasItem()) {
                if(it.item?.hasMark("locked")!!) {
                    it.isCancelled = true
                }
            }
        }
    }

}