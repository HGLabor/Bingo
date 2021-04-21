package de.hglabor.team

import de.hglabor.Bingo
import de.hglabor.loot.LootSet
import de.hglabor.settings.Settings
import de.hglabor.settings.SettingsDisplayItems
import de.hglabor.utils.joinTeam
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.gui.*
import net.axay.kspigot.gui.elements.GUICompoundElement
import net.axay.kspigot.gui.elements.GUIRectSpaceCompound
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

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
                    material = Material.WHITE_BED,
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
            return false
        }
    }

    private var compound: GUIRectSpaceCompound<ForInventoryFourByNine, GUICompoundElement<ForInventoryFourByNine>>? =
        null



    fun addContent(element: GUICompoundElement<ForInventoryFourByNine>) {
        compound?.addContent(element)
    }

    class TeamEntry(
        material: Material,
        team: Team,
        onClick: ((GUIClickEvent<ForInventoryFourByNine>) -> Unit)? = null
    ) : GUICompoundElement<ForInventoryFourByNine>(
        itemStack(material) {
            meta {
                name = "${team.color}#${team.id}"
                val loreList = arrayListOf("${KColors.LIGHTGOLDENRODYELLOW}Member:", " ")
                for(member in team.players) {
                    loreList.add("${KColors.SADDLEBROWN}- ${KColors.LIGHTSALMON}${member.name}")
                }
                lore = loreList
            }
        },
        onClick
    )

}