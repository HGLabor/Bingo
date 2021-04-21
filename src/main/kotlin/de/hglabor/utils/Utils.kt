package de.hglabor.utils

import de.hglabor.Bingo
import de.hglabor.settings.Settings
import de.hglabor.team.Team
import org.bukkit.Material
import org.bukkit.entity.Player

val checkedItems = hashMapOf<Player, ArrayList<Material>>()

private val diedPlayers = arrayListOf<Player>()

fun Player.getTeam(): Team? {
    for(team in Bingo.teams) {
        if(team.players.contains(player!!)) {
            return team
        }
    }
    return null
}

fun Player.isInTeam(): Boolean {
    return player!!.getTeam() != null
}

fun Player.leaveTeam(id: Int) {
    if(player!!.isInTeam()) {

    }
}

fun Player.joinTeam(id: Int) {
    if(player!!.isInTeam()) {
        player!!.leaveTeam(id)
    }
    val team = Bingo.teams[id]
    
 }

fun Player.check(material: Material) {
    if(!Settings.teams) {
        if (checkedItems.containsKey(player)) {
            val list = checkedItems[player]!!
            list.add(material)
            checkedItems[player!!] = list
        } else {
            checkedItems[player!!] = arrayListOf(material)
        }
    } else {
        for(team in Bingo.teams) {
            if(team.players.contains(player!!)) {
                val checkedItemsFromTeam = team.items
                checkedItemsFromTeam.add(material)
                team.items = checkedItemsFromTeam
            }
        }
    }
}

fun Player.die() {
    diedPlayers.add(player!!)
}

val Player.canLogin get() = !diedPlayers.contains(player!!)

fun Player.checkedItems(): ArrayList<Material> {
    return if (checkedItems.containsKey(player)) {
        checkedItems[player]!!
    } else {
        arrayListOf()
    }
}

fun Player.hasChecked(material: Material): Boolean {
    return player?.checkedItems()!!.contains(material)
}

fun translateGuiScale(itemCount: Int): Int {
    return if (itemCount < 10) {
        9
    } else if (itemCount in 10..18) {
        18
    } else if (itemCount in 18..27) {
        27
    } else if (itemCount in 27..36) {
        36
    } else if (itemCount in 36..45) {
        45
    } else {
        54
    }
}

fun translateGuiScale(itemCount: Long): Int {
    return if (itemCount < 10) {
        9
    } else if (itemCount in 10..18) {
        18
    } else if (itemCount in 18..27) {
        27
    } else if (itemCount in 27..36) {
        36
    } else if (itemCount in 36..45) {
        45
    } else {
        54
    }
}