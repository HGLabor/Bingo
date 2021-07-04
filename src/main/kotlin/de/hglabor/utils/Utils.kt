package de.hglabor.utils

import com.google.common.collect.ImmutableMap
import de.hglabor.Bingo
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.team.Team
import net.axay.kspigot.chat.KColors
import net.md_5.bungee.api.ChatColor
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

fun teamColors(): ArrayList<ChatColor> { return arrayListOf(KColors.HOTPINK, KColors.SADDLEBROWN, KColors.LIGHTSTEELBLUE, KColors.MEDIUMSPRINGGREEN, KColors.GOLDENROD, KColors.LIGHTGOLDENRODYELLOW, KColors.DARKGREEN, KColors.PAPAYAWHIP, KColors.NAVY, KColors.PALEVIOLETRED, KColors.WHEAT, KColors.WHITESMOKE, KColors.DARKORANGE, KColors.DARKCYAN, KColors.DARKKHAKI) }

fun Player.joinTeam(id: Int) {
    if(player!!.isInTeam()) {
        player!!.leaveTeam(player?.getTeam()!!.id)
    }
    val team = Bingo.teams[id]
    val players = team.players
    if(players.size < Settings.teamCap) {
        players.add(player!!)
        team.players = players
        player!!.setPlayerListName("${team.color}#${team.id}  ${player?.name}")
        for(member in players) {
            member.sendMessage(Localization.getMessage("bingo.playerJoinedTeam", ImmutableMap.of("player", player?.name), member.locale))
        }
    } else {
        player!!.sendMessage(Localization.getMessage("bingo.teamIsFull", player!!.locale))
    }
 }

fun isTeamFull(team: Team): Boolean {
    return team.players.size >= 4
}

val allColors = arrayListOf(
    KColors.DARKBLUE,
    KColors.DARKGREEN,
    KColors.DARKAQUA,
    KColors.DARKRED,
    KColors.DARKPURPLE,
    KColors.GOLD,
    KColors.GRAY,
    KColors.DARKGRAY,
    KColors.BLUE,
    KColors.GREEN,
    KColors.AQUA,
    KColors.RED,
    KColors.LIGHTPURPLE,
    KColors.YELLOW,
    KColors.WHITE,
    KColors.ALICEBLUE,
    KColors.ANTIQUEWHITE,
    KColors.AQUAMARINE,
    KColors.AZURE,
    KColors.BEIGE,
    KColors.BISQUE,
    KColors.BLACK,
    KColors.BLANCHEDALMOND,
    KColors.BLUEVIOLET,
    KColors.BROWN,
    KColors.BURLYWOOD,
    KColors.CADETBLUE,
    KColors.CHARTREUSE,
    KColors.CHOCOLATE,
    KColors.CORAL,
    KColors.CORNFLOWERBLUE,
    KColors.CORNSILK,
    KColors.CRIMSON,
    KColors.CYAN,
    KColors.DARKCYAN,
    KColors.DARKGOLDENROD,
    KColors.DARKKHAKI,
    KColors.DARKMAGENTA,
    KColors.DARKOLIVEGREEN,
    KColors.DARKORANGE,
    KColors.DARKORCHID,
    KColors.DARKSALMON,
    KColors.DARKSEAGREEN,
    KColors.DARKSLATEBLUE,
    KColors.DARKSLATEGRAY,
    KColors.DARKTURQUOISE,
    KColors.DARKVIOLET,
    KColors.DEEPPINK,
    KColors.DEEPSKYBLUE,
    KColors.DIMGRAY,
    KColors.DODGERBLUE,
    KColors.FIREBRICK,
    KColors.FLORALWHITE,
    KColors.FORESTGREEN,
    KColors.FUCHSIA,
    KColors.GAINSBORO,
    KColors.GHOSTWHITE,
    KColors.GOLDENROD,
    KColors.GREENYELLOW,
    KColors.HONEYDEW,
    KColors.INDIANRED,
    KColors.INDIGO,
    KColors.IVORY,
    KColors.KHAKI,
    KColors.LAVENDER,
    KColors.LAVENDERBLUSH,
    KColors.LAWNGREEN,
    KColors.LEMONCHIFFON,
    KColors.LIGHTBLUE,
    KColors.LIGHTCORAL,
    KColors.LIGHTCYAN,
    KColors.LIGHTGOLDENRODYELLOW,
    KColors.LIGHTGRAY,
    KColors.LIGHTGREEN,
    KColors.LIGHTPINK,
    KColors.LIGHTSALMON,
    KColors.LIGHTSEAGREEN,
    KColors.LIGHTSKYBLUE,
    KColors.LIGHTSLATEGRAY,
    KColors.LIGHTSTEELBLUE,
    KColors.LIGHTYELLOW,
    KColors.LIME,
    KColors.LIMEGREEN,
    KColors.LINEN,
    KColors.MAGENTA,
    KColors.MAROON,
    KColors.MEDIUMAQUAMARINE,
    KColors.MEDIUMBLUE,
    KColors.MEDIUMORCHID,
    KColors.MEDIUMPURPLE,
    KColors.MEDIUMSEAGREEN,
    KColors.MEDIUMSLATEBLUE,
    KColors.MEDIUMSPRINGGREEN,
    KColors.MEDIUMTURQUOISE,
    KColors.MEDIUMVIOLETRED,
    KColors.MIDNIGHTBLUE,
    KColors.MINTCREAM,
    KColors.MISTYROSE,
    KColors.MOCCASIN,
    KColors.NAVAJOWHITE,
    KColors.NAVY,
    KColors.OLDLACE,
    KColors.OLIVE,
    KColors.OLIVEDRAB,
    KColors.ORANGE,
    KColors.ORANGERED,
    KColors.ORCHID,
    KColors.PALEGOLDENROD,
    KColors.PALEGREEN,
    KColors.PALETURQUOISE,
    KColors.PALEVIOLETRED,
    KColors.PAPAYAWHIP,
    KColors.PEACHPUFF,
    KColors.PERU,
    KColors.PINK,
    KColors.PLUM,
    KColors.POWDERBLUE,
    KColors.PURPLE,
    KColors.ROSYBROWN,
    KColors.ROYALBLUE,
    KColors.SADDLEBROWN,
    KColors.SALMON,
    KColors.SANDYBROWN,
    KColors.SEAGREEN,
    KColors.SEASHELL,
    KColors.SIENNA,
    KColors.SILVER,
    KColors.SKYBLUE,
    KColors.SLATEBLUE,
    KColors.SLATEGRAY,
    KColors.SNOW,
    KColors.SPRINGGREEN,
    KColors.STEELBLUE,
    KColors.TAN,
    KColors.TEAL,
    KColors.THISTLE,
    KColors.TOMATO,
    KColors.TURQUOISE,
    KColors.VIOLET,
    KColors.WHEAT,
    KColors.WHITESMOKE,
    KColors.YELLOWGREEN,
)

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
                    member.sendMessage(Localization.getMessage("bingo.playerFromTeamCheckedItem", ImmutableMap.of("player", player!!.name, "item", material.name.toLowerCase().replace("_", " ")), member.locale).replace("$player", player!!.name))
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
        return if(player != null && player?.getTeam() != null) {
            player!!.getTeam()!!.items
        } else {
            arrayListOf()
        }
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