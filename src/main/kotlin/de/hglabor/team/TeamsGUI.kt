package de.hglabor.team

import com.mojang.brigadier.arguments.StringArgumentType
import de.hglabor.Bingo
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.utils.getTeam
import de.hglabor.utils.joinTeam
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import net.axay.kspigot.gui.*
import net.axay.kspigot.gui.elements.GUICompoundElement
import net.axay.kspigot.gui.elements.GUIRectSpaceCompound
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TeamsGUI {

    val gui = kSpigotGUI(GUIType.FOUR_BY_NINE) {

        title = Component.text("${KColors.LIGHTSALMON}Teams")
        defaultPage = 0
        //General Settings
        page(0) {
            placeholder(Slots.Border, itemStack(Material.CYAN_STAINED_GLASS_PANE) { meta { name = null } })
            compound = createSimpleRectCompound(Slots.RowTwoSlotTwo, Slots.RowThreeSlotEight)
            for (i in 0..16) {
                addContent(TeamEntry(
                    team = Bingo.teams[i],
                    onClick = {
                        (it.bukkitEvent.whoClicked as Player).joinTeam(i)
                        (it.bukkitEvent.whoClicked as Player).playSound(it.bukkitEvent.whoClicked.location,
                            Sound.ITEM_ARMOR_EQUIP_ELYTRA,
                            1f,
                            1f)
                        it.bukkitEvent.whoClicked.closeInventory()
                    }
                ))
            }
        }
    }

    object TeamsCommand {
        init {
            command("teams") {
                runs {
                    if (sender is Player) {
                        if (Settings.teams && GamePhaseManager.phase !is InGamePhase) {
                            player.openGUI(TeamsGUI().gui)
                        } else {
                            player.sendMessage(Localization.getMessage("bingo.teams.NotEnabled", player.locale().language))
                        }
                    }
                }
            }
        }
    }

    private var compound: GUIRectSpaceCompound<ForInventoryFourByNine, GUICompoundElement<ForInventoryFourByNine>>? =
        null

    private fun addContent(element: GUICompoundElement<ForInventoryFourByNine>) {
        compound?.addContent(element)
    }

    class TeamEntry(
        team: Team,
        onClick: ((GUIClickEvent<ForInventoryFourByNine>) -> Unit)? = null,
    ) : GUICompoundElement<ForInventoryFourByNine>(
        teamItem(team),
        onClick
    )

}

fun teamItem(team: Team): ItemStack {
    return itemStack(Material.WHITE_BED) {
        meta {
            lore()?.clear()
            name = Component.text("${team.color}#${team.id}")
            val loreList = arrayListOf("${KColors.LIGHTGOLDENRODYELLOW}Member:", " ")
            team.players.map { Bukkit.getPlayer(it) }
                .forEach { loreList.add("${KColors.SADDLEBROWN}- ${KColors.LIGHTSALMON}${it?.name}") }
            lore = loreList
        }
    }
}

object BackpackCommand {
    init {
        fun backpack(player: Player) {
            if (Settings.teams && GamePhaseManager.phase is InGamePhase) {
                player.openInventory(player.getTeam()!!.inventory)
            } else {
                player.sendMessage(Localization.getMessage("bingo.teams.NotEnabled", player.locale().language))
            }
        }

        command("backpack") {
            runs {
                backpack(player)
            }
        }
        command("bp") {
            runs {
                backpack(player)
            }
        }
    }
}

object TeamChatCommand {
    init {
        fun teamchat(player: Player, message: String) {
            if (Settings.teams && GamePhaseManager.phase is InGamePhase && message.isNotEmpty()) {
                player.getTeam()?.players?.map { Bukkit.getPlayer(it) }?.forEach {
                    it?.sendMessage("${player.getTeam()!!.color}${player.name}${KColors.DARKGRAY}: ${KColors.WHITE}${message}")
                }
            } else {
                player.sendMessage(Localization.getMessage("bingo.teams.NotEnabled", player.locale().language))
            }
        }

        command("teamchat") {
            argument("message", StringArgumentType.string()) {
                runs {
                    teamchat(player, getArgument<String>("message"))
                }
            }
        }
        command("tc") {
            argument("message", StringArgumentType.string()) {
                runs {
                    teamchat(player, getArgument<String>("message"))
                }
            }
        }
    }
}
