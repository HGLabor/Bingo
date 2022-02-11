package de.hglabor.commands

import de.hglabor.core.GamePhaseManager
import de.hglabor.core.mechanics.MaterialManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.utils.hasChecked
import de.hglabor.utils.sendMsg
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.utils.mark
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag

object BingoCommand {
    init {
        command("bingo") {
            runs {
                if (GamePhaseManager.phase !is InGamePhase) {
                    player.sendMsg("Diese Command kannst du jetzt nicht ausführen")
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
                            if (player.hasChecked(it)) {
                                addEnchant(Enchantment.PROTECTION_FALL, 1, true)
                            }
                        }
                    }
                    itemStack.mark("locked")
                    inventory.addItem(itemStack)
                }
                player.openInventory(inventory)
            }
        }
    }
}

