package de.hglabor.rendering

import de.hglabor.core.mechanics.MaterialManager
import de.hglabor.loot.LootSet
import de.hglabor.utils.hasChecked
import de.hglabor.utils.user
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.broadcast
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import org.bukkit.map.MinecraftFont
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object LaborMapRenderer : MapRenderer() {
    private val cachedImages = mutableMapOf<Material, BufferedImage>()
    private val cross: BufferedImage = ImageIO.read(javaClass.getResourceAsStream("/textures/extra/cross.png"))

    init {
        for (material in MaterialManager.materials) {
            var texture = "/textures/"
            val isNether = LootSet.NETHER.materials.keys.contains(material)
            val isTurtle = LootSet.TURTLE.materials.keys.contains(material)
            val isWater = LootSet.WATER.materials.keys.contains(material)
            if (isNether) {
                texture += "nether/"
            }
            var customPath = if (isNether) {
                LootSet.NETHER.materials[material]
            } else {
                LootSet.OVERWORLD.materials[material]
            }
            if (isTurtle) {
                customPath = LootSet.TURTLE.materials[material]
            }
            if (isWater) {
                customPath = LootSet.WATER.materials[material]
            }
            if (customPath?.isEmpty() == true) {
                customPath = material.name.toLowerCase()
            }
            val bufferedImage = javaClass.getResourceAsStream("$texture$customPath.png").let {
                ImageIO.read(it)
            }
            if (bufferedImage == null) {
                broadcast("${KColors.TOMATO}${material.name}")
            } else {
                cachedImages[material] = bufferedImage
            }
        }

    }

    val renderCount = hashMapOf<Player, Int>()

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
        if (player.inventory.itemInOffHand.hasItemMeta()) {
            if (player.inventory.itemInOffHand.itemMeta!!.hasDisplayName()) {
                if (player.inventory.itemInOffHand.itemMeta!!.displayName.contains("Bingo")) {
                    /*
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
                     */
                    canvas.drawText(35, 4, MinecraftFont.Font, "§20;HGLABOR.DE")
                    var x = 8
                    var y = 16
                    var drawn = 0
                    player.user.bingoField?.forEach { col ->
                        col.forEach { rowItem ->
                            kotlin.runCatching {
                                if (player.hasChecked(rowItem)) {
                                    canvas.drawImage(x, y, cross)
                                } else {
                                    cachedImages[rowItem]?.let { canvas.drawImage(x, y, it) }
                                }
                            }.onFailure {
                                broadcast("${KColors.TOMATO}${rowItem.name}")
                            }
                            x += 16
                        }
                        x = 8
                        y += 16
                    }
                    /* for (material in GamePhaseManager.materials) {
                         kotlin.runCatching {
                             cachedImages[material]?.let { canvas.drawImage(x,y, it) }
                         }.onFailure {
                             broadcast("${KColors.TOMATO}${material.name}")
                         }
                         x+=16
                         drawn+=1
                         //TODO if(drawn % 7 == 0 && drawn in 7..49)
                         if(drawn == 7 || drawn == 14 || drawn == 21 || drawn == 28 || drawn == 35 || drawn == 42 || drawn == 49) {
                             x=8
                             y+=16
                         }
                     } */
                }
            }
        }
    }
}
