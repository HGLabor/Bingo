package de.hglabor.utils

import com.google.common.collect.ImmutableMap
import de.hglabor.Bingo
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.team.Team
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.ipaddress.ipAddressData
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import java.awt.Color

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
        val team = Bingo.teams[id]
        val players = team.players
        players.remove(player!!)
        team.players = players
        player!!.setPlayerListName("${KColors.GRAY}${player?.name}")
        for(member in players) {
            member.sendMessage(Localization.getMessage("bingo.playerLeftTeam", ImmutableMap.of("player", player?.name), member.locale))
        }
    }
}

fun teamColors(): ArrayList<ChatColor> {
    return arrayListOf(KColors.HOTPINK, KColors.SADDLEBROWN, KColors.LIGHTSTEELBLUE, KColors.MEDIUMSPRINGGREEN, KColors.GOLDENROD, KColors.LIGHTGOLDENRODYELLOW, KColors.DARKGREEN, KColors.PAPAYAWHIP, KColors.NAVY, KColors.PALEVIOLETRED, KColors.WHEAT, KColors.WHITESMOKE, KColors.DARKORANGE, KColors.DARKCYAN, KColors.DARKKHAKI)
}

fun Player.joinTeam(id: Int) {
    if(player!!.isInTeam()) {
        player!!.leaveTeam(id)
    }
    val team = Bingo.teams[id]
    val players = team.players
    players.add(player!!)
    team.players = players
    player!!.setPlayerListName("${team.color}#${team.id}  ${player?.name}")
    for(member in players) {
        member.sendMessage(Localization.getMessage("bingo.playerJoinedTeam", ImmutableMap.of("player", player?.name), member.locale))
    }
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
                for (member in team.players) {
                    member.sendMessage(Localization.getMessage("bingo.playerFromTeamCheckedItem", ImmutableMap.of("player", player?.name, "item", material.name.toLowerCase().replace("_", " ")), member.locale))
                }
            }
        }
    }
}

fun Player.die() {
    diedPlayers.add(player!!)
}

val Player.canLogin get() = !diedPlayers.contains(player!!)

fun Player.checkedItems(): ArrayList<Material> {
    return if(!Settings.teams) {
        if (checkedItems.containsKey(player)) {
            checkedItems[player]!!
        } else {
            arrayListOf()
        }
    } else {
        player!!.getTeam()!!.items
    }
}

fun Player.hasChecked(material: Material): Boolean {
    return if(!Settings.teams) {
        player!!.checkedItems().contains(material)
    } else {
        player!!.getTeam()!!.items.contains(material)
    }
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