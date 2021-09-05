package de.hglabor.core.mechanics

import de.hglabor.loot.LootSet
import de.hglabor.settings.Settings
import org.bukkit.Material

object MaterialManager {
    private val materialPool = mutableListOf<Material>()
    val materials = mutableListOf<Material>()

    fun enable() {
        if (materialPool.isNotEmpty()) return
        LootSet.values()
            .filter { it.isEnabled }
            .forEach { addToMaterialPool(it) }
        generateRandomMaterials(Settings.itemCount * Settings.itemCount)
    }

    fun random(): Material = materialPool.random()
    fun Material.isBingoItem(): Boolean = materials.contains(this)
    private fun addToMaterialPool(lootSet: LootSet) = lootSet.materials.keys.forEach { materialPool.add(it) }
    private fun removeFromMaterialPool(lootSet: LootSet) = lootSet.materials.keys.forEach { materialPool.remove(it) }
    private fun generateRandomMaterials(count: Int) {
        repeat(count) {
            var randomMaterial = materialPool.random()
            while (materials.contains(randomMaterial)) {
                randomMaterial = materialPool.random()
            }
            materials.add(randomMaterial)
        }
    }
}
