package de.hglabor.listener.player

import de.hglabor.utils.noriskutils.scoreboard.KBoard
import org.bukkit.Material
import java.util.*

enum class UserState {
    ALIVE, DEAD, SPECTATOR, LOBBY
}

class User(val uuid: UUID) {
    var bingoField: List<List<Material>>? = null
    var state: UserState = UserState.LOBBY
    val checkedItems = mutableSetOf<Material>()
    var kBoard: KBoard? = null
    val checkFields = mutableListOf<String>()
    var shouldUpdateMap: Boolean = true
}
