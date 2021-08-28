package de.hglabor.listener.player

import com.google.common.collect.ImmutableMap
import de.hglabor.core.GameManager
import de.hglabor.localization.Localization
import de.hglabor.settings.Settings
import de.hglabor.utils.broadcast
import de.hglabor.utils.check
import de.hglabor.utils.checkedItems
import de.hglabor.utils.hasChecked
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.extensions.onlinePlayers
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent

object PlayerPickupListener {
    init {
        listen<EntityPickupItemEvent> {
            if (it.entity !is Player) return@listen
            val player = it.entity as Player
            val material = it.item.itemStack.type
            if (GameManager.isStarted) {
                if (GameManager.materials.contains(material)) {
                    if (!player.hasChecked(material)) {
                        player.check(material)
                        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 10.0f)
                        player.title(Localization.getUnprefixedMessage("bingo.checkedItem",
                            ImmutableMap.of("item", material.name.toLowerCase().replace("_", " ")),
                            player.locale),
                            "${KColors.CORNFLOWERBLUE}${player.checkedItems().size} ${KColors.GRAY}of ${KColors.CORNFLOWERBLUE}${Settings.itemCount}")
                        broadcast("${player.name} hat ${material.name} gefunden [${player.checkedItems().size}/${Settings.itemCount}]")
                        if (player.checkedItems().size >= Settings.itemCount) {
                            player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 10.0f)
                            player.title(Localization.getUnprefixedMessage("bingo.finished", player.locale),
                                "${KColors.LIME}gg")
                            for (others in onlinePlayers) {
                                others.playSound(others.location, Sound.BLOCK_BEACON_ACTIVATE, 10.0f, 1.0f)
                                others.title("${KColors.CORNFLOWERBLUE}${player.name}",
                                    Localization.getUnprefixedMessage("bingo.word.wins", others.locale))
                            }
                            GameManager.endGame(player)
                        }
                    }
                }
            }
        }
    }
}
