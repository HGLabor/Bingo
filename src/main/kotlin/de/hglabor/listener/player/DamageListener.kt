package de.hglabor.listener.player

import de.hglabor.core.GameManager
import de.hglabor.settings.Settings
import net.axay.kspigot.event.listen
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

object DamageListener {

    init {
        listen<EntityDamageEvent> {
            if(!GameManager.isStarted) {
                it.isCancelled = true
            } else {
                if(it.entity is Player) {
                    if(!Settings.damage) {
                        it.isCancelled = true
                    }
                }
            }
        }
        listen<EntityDamageByEntityEvent> {
            if(!Settings.pvp) {
                if(it.entity is Player && it.damager is Player) {
                    it.isCancelled = true
                }
            }
        }
    }

}