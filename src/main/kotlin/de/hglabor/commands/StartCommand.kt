package de.hglabor.commands

import de.hglabor.Bingo
import de.hglabor.core.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object StartCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("hglabor.bingo.startgame")) {
            GameManager.startGame(10)
        }
        return false
    }

    init {
        Bingo.bingo.getCommand("start")?.setExecutor(this)
    }


}