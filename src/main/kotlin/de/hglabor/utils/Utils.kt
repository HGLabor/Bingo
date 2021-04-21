package de.hglabor.utils

import net.axay.kspigot.chat.KColors
import org.bukkit.Material
import org.bukkit.entity.Player

val checkedItems = hashMapOf<Player, ArrayList<Material>>()

private val diedPlayers = arrayListOf<Player>()


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
    if (checkedItems.containsKey(player)) {
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