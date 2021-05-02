package de.hglabor.listener.player

import de.hglabor.core.GameManager
import de.hglabor.settings.Settings
import net.axay.kspigot.event.listen
import net.axay.kspigot.utils.hasMark
import org.bukkit.event.player.*

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
                if(it.item?.hasMark("settings")!!) {
                    it.player.performCommand("settings")
                    it.isCancelled = true
                }
                if(it.item?.hasMark("teams")!!) {
                    it.player.performCommand("teams")
                    it.isCancelled = true
                }
            }
        }
        listen<PlayerArmorStandManipulateEvent> {
            if(it.player.world.name == "lobby") {
                it.isCancelled = true
            }
        }
        listen<PlayerInteractEntityEvent> {
            if(it.player.world.name == "lobby") {
                it.isCancelled = true
            }
        }
        listen<PlayerInteractAtEntityEvent> {
            if(it.player.world.name == "lobby") {
                it.isCancelled = true
            }
        }
        listen<PlayerDropItemEvent> {
            if(it.itemDrop.itemStack.hasMark("locked")) {
                it.isCancelled = true
            }
            if(it.itemDrop.itemStack.hasMark("settings")) {
                it.player.performCommand("settings")
                it.isCancelled = true
            }
        }
    }

}