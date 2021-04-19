package de.hglabor.settings

import de.hglabor.loot.LootSet
import net.axay.kspigot.gui.*
import org.bukkit.entity.Player

class SettingsGUI {

    val gui = kSpigotGUI(GUIType.SIX_BY_NINE) {

        title = "Settings:"
        defaultPage = 0

        //General Settings
        page(0) {
            placeholder(Slots.RowSixSlotFive, SettingsDisplayItems.general())

            button(Slots.RowFourSlotFour, SettingsDisplayItems.map()) {
                Settings.usingMap = !Settings.usingMap
                it.bukkitEvent.currentItem = SettingsDisplayItems.map()
                println("Settings")
            }

            button(Slots.RowFourSlotFive, SettingsDisplayItems.itemcount()) {
                if (it.bukkitEvent.isLeftClick) {
                    if (Settings.itemCount < 49) {
                        Settings.itemCount += 1
                    }
                }
                if (it.bukkitEvent.isRightClick) {
                    if (Settings.itemCount > 1) {
                        Settings.itemCount -= 1
                    }
                }
                it.bukkitEvent.currentItem = SettingsDisplayItems.itemcount()
            }

            button(Slots.RowFourSlotSix, SettingsDisplayItems.kickAfterDeath()) {
                Settings.kickOnDeath = !Settings.kickOnDeath
                it.bukkitEvent.currentItem = SettingsDisplayItems.kickAfterDeath()
            }

            placeholder(Slots.RowTwoSlotOne rectTo Slots.RowTwoSlotNine, SettingsDisplayItems.gray_placeholder)
            button(Slots.RowOneSlotFour, SettingsDisplayItems.general()) { updateGUI(it.player, 0) }
            button(Slots.RowOneSlotFive, SettingsDisplayItems.pvpdamage()) { updateGUI(it.player, 1) }
            button(Slots.RowOneSlotSix, SettingsDisplayItems.itemsets()) { updateGUI(it.player, 2) }
        }
        //PVP & Damage Settings
        page(1) {
            placeholder(Slots.RowSixSlotFive, SettingsDisplayItems.pvpdamage())

            button(Slots.RowFourSlotThree, SettingsDisplayItems.hitcooldown()) {
                Settings.hitCooldown = !Settings.hitCooldown
                it.bukkitEvent.currentItem = SettingsDisplayItems.hitcooldown()
            }

            button(Slots.RowFourSlotFour, SettingsDisplayItems.falldamage()) {
                Settings.falldamage = !Settings.falldamage
                it.bukkitEvent.currentItem = SettingsDisplayItems.falldamage()
            }

            button(Slots.RowFourSlotFive, SettingsDisplayItems.mobdamage()) {
                Settings.mobdamage = !Settings.mobdamage
                it.bukkitEvent.currentItem = SettingsDisplayItems.mobdamage()
            }

            button(Slots.RowFourSlotSix, SettingsDisplayItems.lavadamage()) {
                Settings.lavadamage = !Settings.lavadamage
                it.bukkitEvent.currentItem = SettingsDisplayItems.lavadamage()
            }

            button(Slots.RowFourSlotSeven, SettingsDisplayItems.pvp()) {
                Settings.pvp = !Settings.pvp
                it.bukkitEvent.currentItem = SettingsDisplayItems.pvp()
            }



            placeholder(Slots.RowTwoSlotOne rectTo Slots.RowTwoSlotNine, SettingsDisplayItems.gray_placeholder)
            button(Slots.RowOneSlotFour, SettingsDisplayItems.general()) { updateGUI(it.player, 0) }
            button(Slots.RowOneSlotFive, SettingsDisplayItems.pvpdamage()) { updateGUI(it.player, 1) }
            button(Slots.RowOneSlotSix, SettingsDisplayItems.itemsets()) { updateGUI(it.player, 2) }
        }
        //Item Sets

        page(2) {
            placeholder(Slots.RowSixSlotFive, SettingsDisplayItems.itemsets())


            button(Slots.RowFourSlotFour, SettingsDisplayItems.overworld()) {
                LootSet.OVERWORLD.isEnabled = !LootSet.OVERWORLD.isEnabled
                it.bukkitEvent.currentItem = SettingsDisplayItems.overworld()
            }

            button(Slots.RowFourSlotFive, SettingsDisplayItems.nether()) {
                LootSet.NETHER.isEnabled = !LootSet.NETHER.isEnabled
                it.bukkitEvent.currentItem = SettingsDisplayItems.nether()
            }

            button(Slots.RowFourSlotSix, SettingsDisplayItems.water()) {
                LootSet.WATER.isEnabled = !LootSet.WATER.isEnabled
                it.bukkitEvent.currentItem = SettingsDisplayItems.water()
            }

            button(Slots.RowThreeSlotFive, SettingsDisplayItems.turtle()) {
                LootSet.TURTLE.isEnabled = !LootSet.TURTLE.isEnabled
                it.bukkitEvent.currentItem = SettingsDisplayItems.turtle()
            }

            placeholder(Slots.RowTwoSlotOne rectTo Slots.RowTwoSlotNine, SettingsDisplayItems.gray_placeholder)
            button(Slots.RowOneSlotFour, SettingsDisplayItems.general()) { updateGUI(it.player, 0) }
            button(Slots.RowOneSlotFive, SettingsDisplayItems.pvpdamage()) { updateGUI(it.player, 1) }
            button(Slots.RowOneSlotSix, SettingsDisplayItems.itemsets()) { updateGUI(it.player, 2) }
        }
    }


    private fun updateGUI(player: Player, page: Int) {
        player.openGUI(SettingsGUI().gui)
    }
}