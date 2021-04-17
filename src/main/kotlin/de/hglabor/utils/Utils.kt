package de.hglabor.utils

import org.bukkit.Material
import org.bukkit.entity.Player

private val checkedItems = hashMapOf<Player, ArrayList<Material>>()

private val diedPlayers = arrayListOf<Player>()

fun Player.check(material: Material) {
    if(checkedItems.containsKey(player)) {
        val list = checkedItems[player]!!
        list.add(material)
        checkedItems[player!!] = list
    } else {
        checkedItems[player!!] = arrayListOf(material)
    }
}

fun Player.die() {
    diedPlayers.add(player!!)
}

val Player.canLogin get() = !diedPlayers.contains(player!!)

fun Player.checkedItems(): ArrayList<Material> {
    return if(checkedItems.containsKey(player)) {
        checkedItems[player]!!
    } else {
        arrayListOf()
    }
}

fun Player.hasChecked(material: Material): Boolean {
    return player?.checkedItems()!!.contains(material)
}

fun translateGuiScale(itemCount: Int): Int {
    return if(itemCount < 10) {
        9
    } else if(itemCount in 10..18) {
        18
    } else if(itemCount in 18..27) {
        27
    } else if(itemCount in 27..36) {
        36
    } else if(itemCount in 36..45) {
        45
    } else {
        54
    }
}

fun translateGuiScale(itemCount: Long): Int {
    return if(itemCount < 10) {
        9
    } else if(itemCount in 10..18) {
        18
    } else if(itemCount in 18..27) {
        27
    } else if(itemCount in 27..36) {
        36
    } else if(itemCount in 36..45) {
        45
    } else {
        54
    }
}