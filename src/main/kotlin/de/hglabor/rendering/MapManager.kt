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

    override fun render(map: MapView, canvas: MapCanvas, player: Player) {
        val itemInOffHand = player.inventory.itemInOffHand
        if (!player.user.shouldUpdateMap) return
        if (!itemInOffHand.hasItemMeta()) return
        if (!itemInOffHand.itemMeta!!.hasDisplayName()) return
        if (!itemInOffHand.itemMeta!!.displayName.contains("Bingo")) return
        canvas.drawText(35, 4, MinecraftFont.Font, "§20;HGLABOR.DE")
        var x = 8
        var y = 16
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
        player.user.shouldUpdateMap = false
    }
}
