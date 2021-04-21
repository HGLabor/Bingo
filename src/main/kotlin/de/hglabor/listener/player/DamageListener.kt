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
            if (!GameManager.isStarted) {
                it.isCancelled = true
            } else {
                if (it.entity is Player) {
                    if (it.cause == EntityDamageEvent.DamageCause.FALL) {
                        it.isCancelled = !Settings.falldamage
                    }
                    if(!Settings.allDamage) {
                        it.isCancelled = true
                    }
                }
            }
        }
        listen<EntityDamageByEntityEvent> {
            if (it.entity is Player) {
                if (GameManager.isStarted) {
                    it.isCancelled = true
                } else {
                    if (it.damager is Player) {
                        it.isCancelled = !Settings.pvp
                    } else {
                        it.isCancelled = !Settings.mobdamage
                    }
                }
            }
        }
    }
}