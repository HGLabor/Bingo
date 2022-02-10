package de.hglabor.commands

import de.hglabor.Bingo
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.mechanics.MaterialManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.utils.hasChecked
import de.hglabor.utils.sendMsg
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.utils.mark
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

object BingoCommand : CommandExecutor {

    init {
        Bingo.bingo.getCommand("bingo")?.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (GamePhaseManager.phase !is InGamePhase) {
            sender.sendMsg("Diese Command kannst du jetzt nicht ausführen")
            return true
        }
        val inventory = Bukkit.createInventory(
            null,
            5 * 9,
            Component.text("${KColors.CORNFLOWERBLUE}Bingo")
        )
        MaterialManager.materials.forEach {
            val itemStack = itemStack(it) {
                meta {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    name = "${KColors.CORNFLOWERBLUE}${it.name.lowercase().replace("_", " ")}"
                    //TODO onClick -> show item recipe / if item is craftable (configurable)
                    if (sender.hasChecked(it)) {
                        addEnchant(Enchantment.PROTECTION_FALL, 1, true)
                    }
                }
            }
            itemStack.mark("locked")
            inventory.addItem(itemStack)
        }
        sender.openInventory(inventory)
        return true
    }
}

