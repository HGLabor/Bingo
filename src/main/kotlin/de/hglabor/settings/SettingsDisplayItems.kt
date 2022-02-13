package de.hglabor.settings

import de.hglabor.loot.LootSet
import net.axay.kspigot.items.*
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object SettingsDisplayItems {

    val gray_placeholder = itemStack(Material.CYAN_STAINED_GLASS_PANE) {
        meta {
            name = " "
        }
    }

    fun kickAfterDeath(): ItemStack{
        return itemStack(Material.TOTEM_OF_UNDYING) {
            meta {
                name = "§7Kick On Death §8(${if (Settings.kickOnDeath) "§aAN" else "§cAUS"}§8)"
            }
        }
    }

    //General Settings
    fun map(): ItemStack {
        return itemStack(Material.MAP) {
            meta {
                name = "§7Map §8(${if (Settings.usingMap) "§aAN" else "§cAUS"}§8)"
                addLore {
                    +""
                    +"§7Stelle ein, ob zum anzeigen"
                    +"§7der Items eine Karte benutzt"
                    +"§7werden soll."
                }

            }
        }
    }

    fun teams(): ItemStack {
        return itemStack(Material.LIGHT_BLUE_BED) {
            meta {
                name = "§7Teams §8(${if (Settings.teams) "§aAN" else "§cAUS"}§8)"
                addLore {
                    +""
                    +"§7Stelle ein, ob diese Runde"
                    +"§7eine Team-Runde sein soll."
                }
            }
        }
    }

    fun teamCap(): ItemStack {
        return itemStack(Material.LIGHT_BLUE_CARPET) {
            meta {
                amount = Settings.teamCap
                name = "§7Team Member Cap: §b${Settings.teamCap}"
                addLore {
                    +""
                    +"§7Stelle ein, wie viele Leute"
                    +"§7in ein Team passen."
                }
            }
        }
    }

    fun itemcount(): ItemStack {
        return itemStack(Material.NAME_TAG) {
            meta {
                amount = Settings.itemCount
                name = "§7Itemzahl: §b${Settings.itemCount}"
                addLore {
                    +""
                    +"§7Stelle ein, wieviele Items"
                    +"§7benutzt werden."
                    +""
                    +"§7Min: §b1"
                    +"§7Max: §e49"
                    +""
                    +"§bRechts Click §7niedriger"
                    +"§eLinks Click §7höher"
                }
            }
        }
    }

    fun rowsToComplete(): ItemStack {
        return itemStack(Material.NAME_TAG) {
            meta {
                amount = Settings.rowsToComplete
                name = "§7Reihen: §b${Settings.rowsToComplete}"
                addLore {
                    +""
                    +"§bRechts Click §7niedriger"
                    +"§eLinks Click §7höher"
                }
            }
        }
    }

    //PVP & damage
    fun hitcooldown(): ItemStack {
        return itemStack(Material.SHIELD) {
            meta {
                flag(ItemFlag.HIDE_ATTRIBUTES)
                name = "§7Hit-Cooldown §8(${if (Settings.hitCooldown) "§aAN" else "§cAUS"}§8)"
            }
        }
    }

    fun pvp(): ItemStack {
        return itemStack(Material.WOODEN_SWORD) {
            meta {
                flag(ItemFlag.HIDE_ATTRIBUTES)
                name = "§7PVP §8(${if (Settings.pvp) "§aAN" else "§cAUS"}§8)"
            }
        }
    }

    fun hunger(): ItemStack {
        return itemStack(Material.COOKED_BEEF) {
            meta {
                flag(ItemFlag.HIDE_ATTRIBUTES)
                name = "§7Hunger verlieren? §8(${if (Settings.loseHunger) "§aJa" else "§cNein"}§8)"
            }
        }
    }

    fun damage(): ItemStack {
        return itemStack(Material.SWEET_BERRIES) {
            meta {
                flag(ItemFlag.HIDE_ATTRIBUTES)
                name = "§7Damage §8(${if (Settings.allDamage) "§aAN" else "§cAUS"}§8)"
            }
        }
    }

    fun falldamage(): ItemStack {
        return itemStack(Material.IRON_BOOTS) {
            meta {
                flag(ItemFlag.HIDE_ATTRIBUTES)
                name = "§7Fallschaden §8(${if (Settings.falldamage) "§aAN" else "§cAUS"}§8)"
            }
        }
    }

    fun mobdamage(): ItemStack {
        return itemStack(Material.ZOMBIE_HEAD) {
            meta {
                name = "§7Mob-Schaden §8(${if (Settings.mobdamage) "§aAN" else "§cAUS"}§8)"
            }
        }
    }

    //Item Stuff

    fun overworld(): ItemStack {
        return itemStack(Material.GRASS_BLOCK) {
            meta {
                name = "§7Oberwelt §8(${if (LootSet.OVERWORLD.isEnabled) "§aAN" else "§cAUS"}§8)"
            }
        }
    }

    fun nether(): ItemStack {
        return itemStack(Material.NETHERRACK) {
            meta {
                name = "§7Nether §8(${if (LootSet.NETHER.isEnabled) "§aAN" else "§cAUS"}§8)"
            }
        }
    }

    /*
    fun water(): ItemStack {
        return itemStack(Material.WATER_BUCKET) {
            meta {
                name = "§7Wasser §8(${if (LootSet.WATER.isEnabled) "§aAN" else "§cAUS"}§8)"
                addLore {
                    +""
                    +"§7Seltene Items wie:"
                    +"§8 - §7Prismarin"
                    +"§8 - §7Herz des Meeres"
                    +"§8 - §7Nasser-/Schwamm"
                }
            }
        }
    }

    fun turtle(): ItemStack{
        return itemStack(Material.SCUTE) {
            meta {
                name = "§7Turtle §8(${if (LootSet.TURTLE.isEnabled) "§aAN" else "§cAUS"}§8)"
                addLore {
                    +""
                    +"§7Super seltene Items wie:"
                    +"§8 - §7Trident"
                    +"§8 - §7Turtle Helmet"
                    +"§8 - §7Scute"
                }
            }
        }
    }
     */
}
