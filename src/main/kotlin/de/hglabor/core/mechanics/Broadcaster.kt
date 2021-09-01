package de.hglabor.core.mechanics

import de.hglabor.core.GamePhaseManager
import de.hglabor.settings.Settings
import de.hglabor.utils.checkedItems
import de.hglabor.utils.getTeam
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.bukkit.actionBar
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.KSpigotRunnable
import net.axay.kspigot.runnables.task

object Broadcaster {
    fun startActionBarMessage(): KSpigotRunnable? {
        return task(period = 40, sync = false) {
            onlinePlayers.forEach {
                //TODO lel
                val list = listOf(
                    if (Settings.teams) "${KColors.GRAY}Team ${it.getTeam()?.color}#${it.getTeam()?.id}" else "${KColors.CORNFLOWERBLUE}HGlabor.de Bingo ${KColors.DARKGRAY}| ${KColors.BLUE}1.16.5",
                    "${KColors.BLUE}${it.checkedItems().size} ${KColors.DARKGRAY}/ ${KColors.BLUE}${Settings.itemCount}",
                    "${KColors.BLUE}/bingo ${KColors.DARKGRAY}| ${KColors.BLUE}/top ${if (Settings.teams) "${KColors.DARKGRAY}| ${KColors.BLUE}/backpack ${KColors.DARKGRAY}| ${KColors.BLUE}/tc <message>" else ""}",
                    "${KColors.BLUE}PvP${KColors.DARKGRAY}: ${if (Settings.pvp) "§ayes" else "§cno"} ${KColors.DARKGRAY}| ${KColors.BLUE}Hardcore${KColors.DARKGRAY}: ${if (Settings.kickOnDeath) "§ayes" else "§cno"}",
                    "${KColors.BLUE}Position${KColors.DARKGRAY}: ${
                        when (GamePhaseManager.posInRanking(it)) {
                            1 -> "${KColors.GOLDENROD}${KColors.UNDERLINE}#1"; 2 -> "${KColors.LIGHTSTEELBLUE}${KColors.UNDERLINE}#2"; 3 -> "${KColors.SADDLEBROWN}${KColors.UNDERLINE}#3"; else -> "${KColors.CORNFLOWERBLUE}${KColors.UNDERLINE}#${
                            GamePhaseManager.posInRankingString(it)
                        }"
                        }
                    }"
                )
                it.actionBar(list.random())
            }
        }
    }
}
