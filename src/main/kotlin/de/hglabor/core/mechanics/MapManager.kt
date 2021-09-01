package de.hglabor.core.mechanics

import de.hglabor.rendering.LaborMapRenderer
import de.hglabor.settings.Settings
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.utils.mark
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.map.MapView

object MapManager {
    fun createBingoField(items: List<Material>): List<List<Material>> {
        var counter = 0
        var list = mutableListOf<Material>()
        val field = mutableListOf<List<Material>>()
        items.forEachIndexed { _, material ->
            list.add(material)
            counter++
            if (counter >= Settings.itemCount) {
                field.add(list)
                list = mutableListOf()
                counter = 0
            }
        }
        return field;
    }

    fun Player.giveBingoMap() {
        val stack = itemStack(Material.FILLED_MAP) {
            meta<MapMeta> {
                name = "${KColors.CORNFLOWERBLUE}Bingo"
                val view = Bukkit.createMap(world)
                view.scale = MapView.Scale.FARTHEST
                view.isUnlimitedTracking = true
                view.renderers.clear()
                view.addRenderer(LaborMapRenderer)
                view.isLocked = true
                mapView = view
            }
        }
        stack.mark("locked")
        inventory.setItemInOffHand(stack)
    }
}
