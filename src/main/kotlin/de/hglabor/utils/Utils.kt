package de.hglabor.utils

import com.google.common.collect.ImmutableMap
import de.hglabor.Bingo
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.listener.player.User
import de.hglabor.listener.player.UserState
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

val users = mutableMapOf<UUID, User>()
val worlds: Collection<World> get() = Bukkit.getWorlds()


fun command(commandLine: String) = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandLine)
fun Location.toEasy(): String = "$x, $y, $z"
fun broadcast(msg: String) = Bukkit.broadcastMessage(Prefix + msg)
val Prefix = "${KColors.WHITE}[${KColors.GREEN}Bingo${KColors.WHITE}]${KColors.RESET} " //TODO other colors?

val Player.isInTeam: Boolean get() = getTeam() != null
fun Player.sendMsg(message: String) = sendMessage(Prefix + message)
fun Player.isLobby(): Boolean = world.name.equals("lobby", ignoreCase = true)
fun Player.getTeam(): Team? = Bingo.teams.firstOrNull { it.players.contains(uniqueId) }
fun Player.quit() = users.remove(uniqueId)

fun Player.leaveTeam(id: Int) {
    if (isInTeam) {
        val team = Bingo.teams[id]
        val players = team.players
        players.remove(uniqueId)
        team.players = players
        setPlayerListName("${KColors.GRAY}${player?.name}")
        players.forEach { Bukkit.getPlayer(it)?.sendMsg("$name hat das Team verlassen") }
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
    if (isInTeam) {
        leaveTeam(getTeam()!!.id)
    }
    val team = Bingo.teams[id]
    val players = team.players
    if (players.size < Settings.teamCap) {
        players.add(uniqueId)
        setPlayerListName("${team.color}#${team.id}  ${player?.name}")
        players.forEach { Bukkit.getPlayer(it)?.sendMsg("$name ist dem Team beigetreten") }
    } else {
        sendMsg("Das Team ist voll")
    }
}

fun isTeamFull(team: Team): Boolean {
    return team.players.size >= 4
}

private fun Player.check(material: Material) {
    if (!Settings.teams) {
        user.checkedItems.add(material)
    } else {
        val team = Bingo.teams.find { it.players.contains(uniqueId) }
        if (team?.players?.contains(uniqueId) == true) {
            team.items.add(material)
            team.players.map { Bukkit.getPlayer(it) }.forEach { it?.sendMessage("$name hat $material aufgesammelt") }
        }
    }
}

val Player.user: User get() = users.computeIfAbsent(uniqueId) { User(uniqueId) }

fun Player.die() {
    user.state = UserState.DEAD
}

val Player.canLogin: Boolean get() = user.state == UserState.ALIVE || user.state == UserState.SPECTATOR
val Player.checkedItems: Set<Material>
    get() = if (!Settings.teams) user.checkedItems else getTeam()?.items ?: mutableSetOf()


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

fun Player.hasChecked(material: Material): Boolean = checkedItems.contains(material)

fun Player.checkItem(material: Material) {
    if (!hasChecked(material)) {
        check(material)
        broadcast("$name hat $material gefunden")
        //broadcast("$name checkrows: ${checkedRows()}/${Settings.rowsToComplete}")
        playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 10.0f)
        title(Localization.getUnprefixedMessage("bingo.checkedItem",
            ImmutableMap.of("item", material.name.toLowerCase().replace("_", " ")),
            locale),
            "${KColors.CORNFLOWERBLUE}${checkedItems.size} ${KColors.GRAY}of ${KColors.CORNFLOWERBLUE}${Settings.itemCount}")
        if (checkedRows() >= Settings.rowsToComplete) {
            playSound(location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 10.0f)
            title(Localization.getUnprefixedMessage("bingo.finished", locale),
                "${KColors.LIME}gg")
            for (others in onlinePlayers) {
                others.playSound(others.location, Sound.BLOCK_BEACON_ACTIVATE, 10.0f, 1.0f)
                others.title("${KColors.CORNFLOWERBLUE}${name}",
                    Localization.getUnprefixedMessage("bingo.word.wins", others.locale))
            }
            if (GamePhaseManager.phase is InGamePhase) {
                (GamePhaseManager.phase as InGamePhase).end(this)
            }
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

