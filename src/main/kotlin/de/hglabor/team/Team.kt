package de.hglabor.team

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

data class Team(var players: ArrayList<Player>, var items: ArrayList<Material>, val id: Int, val color: ChatColor)
