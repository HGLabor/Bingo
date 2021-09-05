package de.hglabor.core.mechanics

import de.hglabor.core.mechanics.MapManager.giveBingoMap
import de.hglabor.listener.player.User
import de.hglabor.listener.player.UserState
import de.hglabor.settings.Settings
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.extensions.onlinePlayers
import org.bukkit.*
import org.bukkit.attribute.Attribute
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class PlayerScattering(
    private val toTeleport: MutableList<User>,
    private val amountToTeleportEachRun: Int,
    private val spawnRadius: Int = 50,
    private val world: World,
) :
    BukkitRunnable() {
    private val loadBar = Bukkit.createBossBar(ChatColor.BOLD.toString() + "Teleporting - Don't logout",
        BarColor.GREEN,
        BarStyle.SOLID)
    private val playerAmount: Int = toTeleport.size
    private val playerCounter: AtomicInteger = AtomicInteger()

    init {
        loadBar.progress = 0.0
        onlinePlayers.forEach { loadBar.addPlayer(it) }
    }

    override fun run() {
        if (toTeleport.isEmpty()) {
            loadBar.removeAll()
            cancel()
            return
        }
        val teleportedPlayers: MutableList<User> = ArrayList()
        toTeleport.take(amountToTeleportEachRun).forEach {
            val player = Bukkit.getPlayer(it.uuid)
            player?.inventory?.clear()
            if (!Settings.hitCooldown) player?.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?.baseValue = 100.0
            if (Settings.usingMap) player?.giveBingoMap()
            player?.teleport(randomLocation())
            player?.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 100000,50))
            player?.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 100000,50))
            player?.gameMode = GameMode.ADVENTURE
            it.state = UserState.ALIVE
            it.bingoField = MapManager.createBingoField(MaterialManager.materials)
            teleportedPlayers.add(it)
            toTeleport.removeAll(teleportedPlayers)
            broadcast("Es wurden ${playerCounter.incrementAndGet()} von $playerAmount teleportiert")
            loadBar.progress = playerCounter.get().toDouble() / playerAmount
        }
        toTeleport.removeAll(teleportedPlayers)
    }

    private fun randomLocation(): Location {
        val x = Random.nextInt(-spawnRadius, spawnRadius)
        val z = Random.nextInt(-spawnRadius, spawnRadius)
        val y = world.getHighestBlockYAt(x, z) + 2
        return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
    }
}
