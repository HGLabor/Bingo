package de.hglabor.settings

import de.hglabor.loot.LootSet
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.gui.GUIType
import net.axay.kspigot.gui.Slots
import net.axay.kspigot.gui.kSpigotGUI
import net.axay.kspigot.gui.rectTo
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import org.bukkit.Material

class SettingsGUI {

    val gui = kSpigotGUI(GUIType.SIX_BY_NINE) {

        title = "${KColors.PALEVIOLETRED}"
        defaultPage = 0
        //General Settings
        page(0) {

            placeholder(Slots.Border, itemStack(Material.CYAN_STAINED_GLASS_PANE) { meta { name = null } })

            placeholder(Slots.RowOneSlotOne rectTo Slots.RowSixSlotNine, SettingsDisplayItems.gray_placeholder)

            button(Slots.RowFiveSlotTwo, SettingsDisplayItems.map()) {
                Settings.usingMap = !Settings.usingMap
                it.bukkitEvent.currentItem = SettingsDisplayItems.map()
                println("Settings")
            }
            button(Slots.RowFiveSlotThree, SettingsDisplayItems.itemcount()) {
                if (it.bukkitEvent.isLeftClick) {
                    if (Settings.itemCount < 7) {
                        Settings.itemCount = (Settings.itemCount + 1).coerceAtMost(7)
                    }
                }
                if (it.bukkitEvent.isRightClick) {
                    if (Settings.itemCount > 1) {
                        Settings.itemCount -= 1
                    }
                }
                it.bukkitEvent.currentItem = SettingsDisplayItems.itemcount()
            }
            button(Slots.RowFourSlotThree, SettingsDisplayItems.rowsToComplete()) {
                if (it.bukkitEvent.isLeftClick) {
                    if (Settings.rowsToComplete < Settings.itemCount+2) {
                        Settings.rowsToComplete += 1
                    }
                }
                if (it.bukkitEvent.isRightClick) {
                    if (Settings.rowsToComplete > 1) {
                        Settings.rowsToComplete -= 1
                    }
                }
                it.bukkitEvent.currentItem = SettingsDisplayItems.rowsToComplete()
            }

            button(Slots.RowThreeSlotSeven, SettingsDisplayItems.teams()) {
                Settings.teams = !Settings.teams
                it.bukkitEvent.currentItem = SettingsDisplayItems.teams()
            }

            button(Slots.RowThreeSlotSix, SettingsDisplayItems.teamCap()) {
                if (it.bukkitEvent.isLeftClick) {
                    Settings.teamCap += 1
                }
                if (it.bukkitEvent.isRightClick) {
                    if (Settings.teamCap > 1) {
                        Settings.teamCap -= 1
                    }
                }
                it.bukkitEvent.currentItem = SettingsDisplayItems.teamCap()
            }

            button(Slots.RowFiveSlotFour, SettingsDisplayItems.kickAfterDeath()) {
                Settings.kickOnDeath = !Settings.kickOnDeath
                it.bukkitEvent.currentItem = SettingsDisplayItems.kickAfterDeath()
            }

            button(Slots.RowFiveSlotFive, SettingsDisplayItems.hitcooldown()) {
                Settings.hitCooldown = !Settings.hitCooldown
                it.bukkitEvent.currentItem = SettingsDisplayItems.hitcooldown()
            }


            //damage
            button(Slots.RowFiveSlotEight, SettingsDisplayItems.falldamage()) {
                Settings.falldamage = !Settings.falldamage
                it.bukkitEvent.currentItem = SettingsDisplayItems.falldamage()
            }

            button(Slots.RowFourSlotEight, SettingsDisplayItems.mobdamage()) {
                Settings.mobdamage = !Settings.mobdamage
                it.bukkitEvent.currentItem = SettingsDisplayItems.mobdamage()
            }

            button(Slots.RowThreeSlotEight, SettingsDisplayItems.damage()) {
                Settings.allDamage = !Settings.allDamage
                it.bukkitEvent.currentItem = SettingsDisplayItems.damage()
            }

            button(Slots.RowTwoSlotEight, SettingsDisplayItems.pvp()) {
                Settings.pvp = !Settings.pvp
                it.bukkitEvent.currentItem = SettingsDisplayItems.pvp()
            }

            button(Slots.RowTwoSlotNine, SettingsDisplayItems.hunger()) {
                Settings.loseHunger = !Settings.loseHunger
                it.bukkitEvent.currentItem = SettingsDisplayItems.hunger()
            }

            button(Slots.RowTwoSlotEight, SettingsDisplayItems.pvp()) {
                Settings.pvp = !Settings.pvp
                it.bukkitEvent.currentItem = SettingsDisplayItems.pvp()
            }

            //Item Sets

            button(Slots.RowTwoSlotTwo, SettingsDisplayItems.overworld()) {
                LootSet.OVERWORLD.isEnabled = !LootSet.OVERWORLD.isEnabled
                it.bukkitEvent.currentItem = SettingsDisplayItems.overworld()
            }

            button(Slots.RowTwoSlotThree, SettingsDisplayItems.nether()) {
                LootSet.NETHER.isEnabled = !LootSet.NETHER.isEnabled
                it.bukkitEvent.currentItem = SettingsDisplayItems.nether()
            }

            /*
            button(Slots.RowTwoSlotFour, SettingsDisplayItems.water()) {
                LootSet.WATER.isEnabled = !LootSet.WATER.isEnabled
                it.bukkitEvent.currentItem = SettingsDisplayItems.water()
            }

            button(Slots.RowTwoSlotFive, SettingsDisplayItems.turtle()) {
                LootSet.TURTLE.isEnabled = !LootSet.TURTLE.isEnabled
                it.bukkitEvent.currentItem = SettingsDisplayItems.turtle()
            }
             */
        }
    }
}
