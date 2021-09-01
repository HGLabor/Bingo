package de.hglabor.utils

import com.google.common.collect.ImmutableMap
import de.hglabor.Bingo
import de.hglabor.core.GamePhaseManager
import de.hglabor.listener.player.User
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.team.Team
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.extensions.onlinePlayers
import net.md_5.bungee.api.ChatColor
import org.bukkit.*
import org.bukkit.entity.Player
import java.util.*

val checkedItems = hashMapOf<Player, ArrayList<Material>>()

val users = mutableMapOf<UUID, User>()

private val diedPlayers = arrayListOf<Player>()

fun Player.getTeam(): Team? {
    for (team in Bingo.teams) {
        if (team.players.contains(player!!)) {
            return team
        }
    }
    return null
}

val worlds: Collection<World> get() = Bukkit.getWorlds()
fun command(commandLine: String) = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandLine)
fun Location.toEasy(): String = "$x, $y, $z"
fun broadcast(msg: String) = Bukkit.broadcastMessage(Prefix + msg)

val Prefix = "${KColors.WHITE}[${KColors.GREEN}Bingo${KColors.WHITE}]${KColors.RESET} " //TODO other colors?

fun Player.isInTeam(): Boolean {
    return player!!.getTeam() != null
}

fun Player.isLobby(): Boolean = world.name.equals("lobby", ignoreCase = true)

fun Player.leaveTeam(id: Int) {
    if (player!!.isInTeam()) {
        val team = Bingo.teams[id]
        val players = team.players
        players.remove(player!!)
        team.players = players
        player!!.setPlayerListName("${KColors.GRAY}${player?.name}")
        for (member in players) {
            member.sendMessage(Localization.getMessage("bingo.playerLeftTeam",
                ImmutableMap.of("player", player?.name),
                member.locale))
        }
    }
}

fun teamColors(): ArrayList<ChatColor> {
    return arrayListOf(KColors.HOTPINK,
        KColors.SADDLEBROWN,
        KColors.LIGHTSTEELBLUE,
        KColors.MEDIUMSPRINGGREEN,
        KColors.GOLDENROD,
        KColors.LIGHTGOLDENRODYELLOW,
        KColors.DARKGREEN,
        KColors.PAPAYAWHIP,
        KColors.NAVY,
        KColors.PALEVIOLETRED,
        KColors.WHEAT,
        KColors.WHITESMOKE,
        KColors.DARKORANGE,
        KColors.DARKCYAN,
        KColors.DARKKHAKI)
}

fun Player.joinTeam(id: Int) {
    if (player!!.isInTeam()) {
        player!!.leaveTeam(player?.getTeam()!!.id)
    }
    val team = Bingo.teams[id]
    val players = team.players
    if (players.size < Settings.teamCap) {
        players.add(player!!)
        team.players = players
        player!!.setPlayerListName("${team.color}#${team.id}  ${player?.name}")
        for (member in players) {
            member.sendMessage(Localization.getMessage("bingo.playerJoinedTeam",
                ImmutableMap.of("player", player?.name),
                member.locale))
        }
    } else {
        player!!.sendMessage(Localization.getMessage("bingo.teamIsFull", player!!.locale))
    }
}

fun isTeamFull(team: Team): Boolean {
    return team.players.size >= 4
}

private fun Player.check(material: Material) {
    if (!Settings.teams) {
        if (checkedItems.containsKey(player)) {
            val list = checkedItems[player]!!
            list.add(material)
            checkedItems[player!!] = list
        } else {
            checkedItems[player!!] = arrayListOf(material)
        }
    } else {
        for (team in Bingo.teams) {
            if (team.players.contains(player!!)) {
                val checkedItemsFromTeam = team.items
                checkedItemsFromTeam.add(material)
                team.items = checkedItemsFromTeam
                for (member in team.players) {
                    member.sendMessage(Localization.getMessage("bingo.playerFromTeamCheckedItem",
                        ImmutableMap.of("player", player!!.name, "item", material.name.toLowerCase().replace("_", " ")),
                        member.locale).replace("$player", player!!.name))
                }
            }
        }
    }
}

val Player.user: User get() = users.computeIfAbsent(uniqueId) { User(uniqueId) }

fun Player.die() {
    diedPlayers.add(player!!)
}

val Player.canLogin get() = !diedPlayers.contains(player!!)

fun Player.checkedItems(): ArrayList<Material> {
    return if (!Settings.teams) {
        if (checkedItems.containsKey(player)) {
            checkedItems[player]!!
        } else {
            arrayListOf()
        }
    } else {
        return if (player != null && player?.getTeam() != null) {
            player!!.getTeam()!!.items
        } else {
            arrayListOf()
        }
    }
}

fun Player.checkedRows(): Int {
    var checkedRows = 0;
    if (user.bingoField == null) return 0
    user.bingoField!!.forEach { col ->
        var rowFlag = false
        //REIHEN
        for (rowItem in col) {
            if (hasChecked(rowItem)) {
                rowFlag = true
            } else {
                rowFlag = false
                break
            }
        }
        if (rowFlag) checkedRows++
    }
    repeat(Settings.itemCount) { col ->
        var colFlag = false;
        //SPALTEN
        for (row in 0 until Settings.itemCount) {
            if (hasChecked(user.bingoField!![row][col])) {
                colFlag = true
            } else {
                colFlag = false
                break
            }
        }
        if (colFlag) checkedRows++
    }
    //OBEN LINKS NACH UNTEN RECHTS
    var leftTopBottomRight = false;
    for (index in 0 until Settings.itemCount) {
        if (hasChecked(user.bingoField!![index][index])) {
            leftTopBottomRight = true
        } else {
            leftTopBottomRight = false
            break
        }
    }
    if (leftTopBottomRight) checkedRows++

    //OBEN RECHTS NACH UNTEN LINKS
    var rightTopToBottomLeft = false;
    for (index in 0 until Settings.itemCount) {
        if (hasChecked(user.bingoField!![index][Settings.itemCount - (index + 1)])) {
            rightTopToBottomLeft = true
        } else {
            rightTopToBottomLeft = false
            break
        }
    }
    if (rightTopToBottomLeft) checkedRows++


    return checkedRows
}

fun Player.hasChecked(material: Material): Boolean {
    return if (!Settings.teams) {
        player!!.checkedItems().contains(material)
    } else {
        player!!.getTeam()!!.items.contains(material)
    }
}

fun Player.checkItem(material: Material) {
    if (!hasChecked(material)) {
        check(material)
        broadcast("$name hat $material gefunden")
        //broadcast("$name checkrows: ${checkedRows()}/${Settings.rowsToComplete}")
        playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 10.0f)
        title(Localization.getUnprefixedMessage("bingo.checkedItem",
            ImmutableMap.of("item", material.name.toLowerCase().replace("_", " ")),
            locale),
            "${KColors.CORNFLOWERBLUE}${checkedItems().size} ${KColors.GRAY}of ${KColors.CORNFLOWERBLUE}${Settings.itemCount}")
        if (checkedRows() >= Settings.rowsToComplete) {
            playSound(location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 10.0f)
            title(Localization.getUnprefixedMessage("bingo.finished", locale),
                "${KColors.LIME}gg")
            for (others in onlinePlayers) {
                others.playSound(others.location, Sound.BLOCK_BEACON_ACTIVATE, 10.0f, 1.0f)
                others.title("${KColors.CORNFLOWERBLUE}${name}",
                    Localization.getUnprefixedMessage("bingo.word.wins", others.locale))
            }
            GamePhaseManager.endGame(this)
        }
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
