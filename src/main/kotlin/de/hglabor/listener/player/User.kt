package de.hglabor.listener.player

import org.bukkit.Material
import java.util.*

enum class UserState {
    ALIVE, DEAD, SPECTATOR, LOBBY
}

class User(val uuid: UUID) {
    var bingoField: List<List<Material>>? = null
    var state: UserState = UserState.LOBBY
    val checkedItems = mutableSetOf<Material>()
}
