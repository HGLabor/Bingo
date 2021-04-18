package de.hglabor.rendering

import de.hglabor.core.GameManager
import de.hglabor.loot.LootSet
import de.hglabor.settings.Settings
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.event.listen
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.server.MapInitializeEvent
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import org.bukkit.map.MinecraftFont
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object MapListener {

    init {
        listen<MapInitializeEvent> {
            if(Settings.usingMap) {
                val mapView: MapView = it.map
                mapView.scale = MapView.Scale.FARTHEST
                mapView.isUnlimitedTracking = true
                mapView.renderers.clear()
                mapView.addRenderer(LaborMapRenderer())
                mapView.isLocked = true
            }
        }
    }
}

class LaborMapRenderer : MapRenderer() {

    val cachedImages = hashMapOf<Material, BufferedImage>()

    init {
        for (material in GameManager.materials) {
            var texture = "/textures/"
            val isNether = LootSet.NETHER.materials.keys.contains(material)
            if(isNether) {
                texture+="nether/"
            }
            var customPath = if(isNether) {
                LootSet.NETHER.materials[material]
            } else {
                LootSet.OVERWORLD.materials[material]
            }
            if(customPath?.isEmpty() == true) {
                customPath = material.name.toLowerCase()
            }
            val bufferedImage = ImageIO.read(javaClass.getResourceAsStream("$texture$customPath.png"))
            cachedImages[material] = bufferedImage
        }
    }

    val renderCount = hashMapOf<Player,Int>()

    override fun render(map: MapView, canvas: MapCanvas, player: Player) {
        /*
        if(!renderCount.containsKey(player)) {
            renderCount[player] = 1
            renderMap(canvas, player)
        } else {
            renderCount[player] = renderCount[player]!!.plus(1)
            if(renderCount[player]!! < Settings.itemCount) {
                renderMap(canvas, player)
            }
        }
         */
        if(player.inventory.itemInOffHand.hasItemMeta()) {
            if(player.inventory.itemInOffHand.itemMeta!!.hasDisplayName()) {
                if(player.inventory.itemInOffHand.itemMeta!!.displayName.contains("Bingo")) {
                    for (material in GameManager.materials) {
                        var texture = "/textures/"
                        val isNether = LootSet.NETHER.materials.keys.contains(material)
                        if(isNether) {
                            texture+="nether/"
                        }
                        var customPath = if(isNether) {
                            LootSet.NETHER.materials[material]
                        } else {
                            LootSet.OVERWORLD.materials[material]
                        }
                        if(customPath?.isEmpty() == true) {
                            customPath = material.name.toLowerCase()
                        }
                        val bufferedImage = ImageIO.read(javaClass.getResourceAsStream("$texture$customPath.png"))
                        cachedImages[material] = bufferedImage
                    }
                    canvas.drawText(35, 4, MinecraftFont.Font, "§20;HGLABOR.DE")
                    var x = 8
                    var y = 16
                    var drawn = 0
                    for (material in GameManager.materials) {
                        kotlin.runCatching {
                            cachedImages[material]?.let { canvas.drawImage(x,y, it) }
                        }
                        x+=16
                        drawn+=1
                        if(drawn == 7 || drawn == 14 || drawn == 21 || drawn == 28 || drawn == 35 || drawn == 42 || drawn == 49) {
                            x=8
                            y+=16
                        }
                    }
                    renderMap(canvas,player)
                }
            }
        }
    }

    private fun renderMap(canvas: MapCanvas, player: Player) {

    }
}