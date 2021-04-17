package de.hglabor.settings

import de.hglabor.loot.LootSet
import net.axay.kspigot.gui.GUIType
import net.axay.kspigot.gui.Slots
import net.axay.kspigot.gui.kSpigotGUI
import net.axay.kspigot.gui.openGUI
import net.axay.kspigot.items.addLore
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.name
import org.bukkit.Material
import org.bukkit.entity.Player

class SettingsGUI {

    val gui = kSpigotGUI(GUIType.SIX_BY_NINE) {

        title = "Settings:"
        defaultPage = 0

        page(0) {
            placeholder(Slots.RowFiveSlotFive, itemStack(Material.COMPARATOR) {
                meta {
                    name = "§cGeneral Settings"
                    addLore {
                        +""
                        +"§9Allgemeine Einstellungen wie:"
                        +" §8- §7Map"
                        +" §8- §7Item Anzahl"
                        +" §8- §7Schaden"
                        +" §8- §7Kick wenn Tot"
                        +" §8- §7PVP"
                        +" §8- §7Hit Cooldown"
                    }
                }
            })

            button(Slots.RowThreeSlotTwo, itemStack(Material.MAP) {
                meta {
                    name = if (Settings.usingMap) "§7Map §8- §aAN" else "§cMap §8- §cAUS"
                    addLore {
                        +""
                        +"§7Stelle ein ob eine Karte benutzt"
                        +"§7wird um Items anzuzeigen oder nicht"
                    }
                }
            }) {
                Settings.usingMap = !Settings.usingMap
                updateGUI(it.player)
            }

            button(Slots.RowThreeSlotThree, itemStack(Material.NAME_TAG) {
                meta {
                    name = "§7Itemzahl: §b${Settings.itemCount}"
                    addLore {
                        +""
                        +" §7Stelle ein, wieviele Items"
                        +"§7benutzt werden."
                        +"§7Min: 1"
                        +"§7Max: 49"
                        +""
                        +"§eLinks Click §7höher"
                        +"§bRechts Click §7niedriger"
                    }
                }
            }) {
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
                updateGUI(it.player)
            }

            button(Slots.RowThreeSlotFive, itemStack(Material.SPLASH_POTION) {
                meta {
                    name = "§7Schaden ist: ${if (Settings.damage) "§aAN" else "§cAUS"}"
                    addLore {
                        +""
                        +"§7Stelle ein, ob Spieler Schaden"
                        +"§7nehmen können oder nicht."
                    }
                }
            }) {
                Settings.damage = !Settings.damage
                updateGUI(it.player)
            }

            button(Slots.RowThreeSlotSix, itemStack(Material.TOTEM_OF_UNDYING) {
                meta {
                    name = "§7Kicken nach Tod: ${if (Settings.kickOnDeath) "§aAN" else "§cAUS"}"
                    addLore {
                        +""
                        +"§7Stelle ein, ob Spieler nach dem"
                        +"§7Tod gekickt werden oder nicht."
                    }
                }
            }) {
                Settings.kickOnDeath = !Settings.kickOnDeath
                updateGUI(it.player)
            }

            button(Slots.RowThreeSlotSeven, itemStack(Material.IRON_SWORD) {
                meta {
                    name = "§7PVP ist: ${if (Settings.pvp) "§aAN" else "§cAUS"}"
                    addLore {
                        +""
                        +"§7Stelle ein, ob PVP erlaubt"
                        +"§7ist oder nicht."
                    }
                }
            }) {
                Settings.pvp = !Settings.pvp
                updateGUI(it.player)
            }

            button(Slots.RowTwoSlotThree, itemStack(Material.SHIELD) {
                meta {
                    name = "Hit-Cooldown: ${if (Settings.hitCooldown) "§aAN" else "§cAUS"}"
                    addLore {
                        +""
                        +"§7Stelle Hit-Cooldown an"
                        +"§7oder aus."
                    }
                }
            }) {
                Settings.hitCooldown = !Settings.hitCooldown
            }
            button(Slots.RowTwoSlotFour, itemStack(Material.GRASS_BLOCK) {
                meta {
                    name = "Oberwelt Items: ${if (LootSet.OVERWORLD.isEnabled) "§aAN" else "§cAUS"}"
                    addLore {
                        +""
                        +"§7Stelle Oberwelt Items an"
                        +"§7oder aus."
                    }
                }
            }) {
                LootSet.OVERWORLD.isEnabled = !LootSet.OVERWORLD.isEnabled
            }

            button(Slots.RowTwoSlotFive, itemStack(Material.NETHERRACK) {
                meta {
                    name = "Nether Items: ${if (LootSet.NETHER.isEnabled) "§aAN" else "§cAUS"}"
                    addLore {
                        +""
                        +"§7Stelle Nether Items an"
                        +"§7oder aus."
                    }
                }
            }) {
                LootSet.NETHER.isEnabled = !LootSet.NETHER.isEnabled
            }
        }
    }

    private fun updateGUI(player: Player) {
        player.openGUI(SettingsGUI().gui)
    }
}