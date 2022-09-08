package de.hglabor.team

import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import java.util.*

data class Team(var players: MutableList<UUID>, var items: MutableSet<Material>, val id: Int, val color: TextColor, val inventory: Inventory)
