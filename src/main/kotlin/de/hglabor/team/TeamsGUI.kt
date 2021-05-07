package de.hglabor.team

import de.hglabor.Bingo
import de.hglabor.core.GameManager
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.utils.getTeam
import de.hglabor.utils.joinTeam
import de.hglabor.utils.teamColors
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.gui.*
import net.axay.kspigot.gui.elements.GUICompoundElement
import net.axay.kspigot.gui.elements.GUIRectSpaceCompound
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import net.axay.kspigot.runnables.task
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class TeamsGUI {

    val gui = kSpigotGUI(GUIType.FOUR_BY_NINE) {

        title = "${KColors.LIGHTSALMON}Teams"
        defaultPage = 0
        //General Settings
        page(0) {
            placeholder(Slots.Border, itemStack(Material.CYAN_STAINED_GLASS_PANE) { meta { name = null } })
            compound = createSimpleRectCompound(Slots.RowTwoSlotTwo, Slots.RowThreeSlotEight)
            for(i in 0..16){
                addContent(TeamEntry(
                    team = Bingo.teams[i],
                    onClick = {
                        (it.bukkitEvent.whoClicked as Player).joinTeam(i)
                        (it.bukkitEvent.whoClicked as Player).playSound(it.bukkitEvent.whoClicked.location, Sound.ITEM_ARMOR_EQUIP_ELYTRA, 1f, 1f)
                        it.bukkitEvent.whoClicked.closeInventory()
                    }
                ))
            }
        }
    }

    companion object TeamsCommand : CommandExecutor {
        init {
            Bingo.bingo.getCommand("teams")?.setExecutor(this)
        }

        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
            if(sender is Player) {
                if(Settings.teams && !GameManager.isStarted) {
                    sender.openGUI(TeamsGUI().gui)
                } else {
                    sender.sendMessage(Localization.getMessage("bingo.teams.NotEnabled", sender.locale))
                }
            }
            return false
        }
    }

    private var compound: GUIRectSpaceCompound<ForInventoryFourByNine, GUICompoundElement<ForInventoryFourByNine>>? =
        null

    fun addContent(element: GUICompoundElement<ForInventoryFourByNine>) {
        compound?.addContent(element)
    }

    class TeamEntry(
        team: Team,
        onClick: ((GUIClickEvent<ForInventoryFourByNine>) -> Unit)? = null
    ) : GUICompoundElement<ForInventoryFourByNine>(
        teamItem(team),
        onClick
    )

}

fun teamItem(team: Team): ItemStack {
    return itemStack(Material.WHITE_BED) {
        meta {
            lore?.clear()
            name = "${team.color}#${team.id}"
            val loreList = arrayListOf("${KColors.LIGHTGOLDENRODYELLOW}Member:", " ")
            for(member in team.players) {
                loreList.add("${KColors.SADDLEBROWN}- ${KColors.LIGHTSALMON}${member.name}")
            }
            lore = loreList
        }
    }
}

object BackpackCommand : CommandExecutor {

    init {
        Bingo.bingo.getCommand("backpack")?.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if(Settings.teams && GameManager.isStarted) {
                sender.openInventory(sender.getTeam()!!.inventory)
            } else {
                sender.sendMessage(Localization.getMessage("bingo.teams.NotEnabled", sender.locale))
            }
        }
        return false
    }
}

object TeamChatCommand : CommandExecutor {

    init {
        Bingo.bingo.getCommand("tc")?.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if(Settings.teams && GameManager.isStarted && args.size > 0) {
                var message = ""
                for (element in args) {
                    message = "$message $element"
                }
                for (member in sender.getTeam()!!.players) {
                    member.sendMessage("${sender.getTeam()!!.color}${sender.name}${KColors.DARKGRAY}:${KColors.WHITE}${message}")
                }
            } else {
                sender.sendMessage(Localization.getMessage("bingo.teams.NotEnabled", sender.locale))
            }
        }
        return false
    }
}