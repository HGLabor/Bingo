package de.hglabor.core.mechanics

import de.hglabor.core.mechanics.MaterialManager.isBingoItem
import de.hglabor.utils.checkItem
import net.axay.kspigot.utils.hasMark
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent

object ItemCollectorManager {
    fun onEntityPickupItem(event: EntityPickupItemEvent) {
        if (event.entity !is Player) return
        val material = event.item.itemStack.type
        if (!material.isBingoItem()) return
        (event.entity as Player).checkItem(material)
    }

    fun onPlayerClicksItem(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) return
        if (event.currentItem == null) return
        val material = event.currentItem!!.type

        if (event.currentItem?.hasMark("locked")!!) {
            event.isCancelled = true
            return
        }
        if (!material.isBingoItem()) return

        (event.whoClicked as Player).checkItem(material)
    }
}
