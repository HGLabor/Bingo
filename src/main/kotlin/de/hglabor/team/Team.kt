package de.hglabor.team

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import java.util.*

data class Team(var players: MutableList<UUID>, var items: MutableSet<Material>, val id: Int, val color: ChatColor, val inventory: Inventory)
