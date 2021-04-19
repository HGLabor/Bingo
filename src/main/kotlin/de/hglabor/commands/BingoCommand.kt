package de.hglabor.commands

import de.hglabor.Bingo
import de.hglabor.core.GameManager
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.utils.hasChecked
import de.hglabor.utils.translateGuiScale
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.utils.mark
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

object BingoCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if(GameManager.isStarted) {
                val inventory = Bukkit.createInventory(
                    null,
                    translateGuiScale(Settings.itemCount),
                    "${KColors.CORNFLOWERBLUE}Bingo"
                )
                var i = -1
                for (material in GameManager.materials) {
                    i++
                    val itemStack = itemStack(material) {
                        meta {
                            addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                            addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                            addItemFlags(ItemFlag.HIDE_ENCHANTS)
                            name = "${KColors.CORNFLOWERBLUE}${material.name.toLowerCase().replace("_", " ")}"
                            //TODO onClick -> show item recipe / if item is craftable (configurable)
                            if(sender.hasChecked(material)) {
                                addEnchant(Enchantment.PROTECTION_FALL, 1, true)
                            }
                        }
                    }

                    itemStack.mark("locked")
                    inventory.setItem(i, itemStack)
                }
                sender.openInventory(inventory)
            } else {
                sender.sendMessage(Localization.getMessage("bingo.gameNotStarted", sender.locale))
            }
        }
        return false
    }

    init {
        Bingo.bingo.getCommand("bingo")?.setExecutor(this)
    }


}