package de.soho5k

import soholo.utils.Hashids
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.WorldCreator
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import de.soho5k.utils.NoHoloException
import java.io.File
import java.util.*

/**
 * @author soho5k
 * Lightweight HologramAPI for Spigot
 */
class HoloManager(plugin: JavaPlugin) {

    private var origin: File = File("${plugin.dataFolder}/holos/holograms.yml")
    private var config: YamlConfiguration = YamlConfiguration.loadConfiguration(origin)
    private val holos = hashMapOf<String, Holo>()

    fun start() {
        if (config.contains("holos")) {
            val configHolos = config.getConfigurationSection("holos")!!.getKeys(false)
            println(configHolos.size)
            for (i in configHolos) {
                println(i)
                if (Bukkit.getWorld("${config.get("holos.$i.location.world")}") != null) {
                    val world = Bukkit.createWorld(WorldCreator("${config.get("holos.$i.location.world")}"))
                    val x = "${config.get("holos.$i.location.x")}".toDouble()
                    val y = "${config.get("holos.$i.location.y")}".toDouble()
                    val z = "${config.get("holos.$i.location.z")}".toDouble()
                    var text = "${config.get("holos.$i.text")}"
                    val small = config.get("holos.$i.small") as Boolean
                    text = text.replace("[", "").replace("]", "")
                    val holo = Holo(Location(world, x, y, z), text.split(", "), small)
                    holos[i] = holo
                }
            }
        }
    }


    fun end() {
        for (i in holos.values) {
            for (a in i.armorstands) a.remove()
        }
    }

    /**
     * @param loc           The Location where you want your Hologram to appear
     * @param persistent    Wether it should stay there even after Reloads/Restarts
     * @param exact         **Either** Exact Coordinates (such as 0.04) **or** default 0.5
     * @param text          What the Hologram should display
     */
    fun createHolo(
        loc: Location,
        persistent: Boolean = true,
        exact: Boolean = false,
        small: Boolean = true,
        vararg text: String
    ) {
        val id = Hashids("${loc.x}${loc.y}${loc.z}${Date().time}").encode(1, 2, 3)
        if (persistent) {
            config.set("holos.${id}.location.world", loc.world?.name)
            config.set("holos.${id}.location.x", if (exact) loc.x else loc.blockX.toDouble() + 0.5)
            config.set("holos.${id}.location.y", if (exact) loc.y else loc.blockY.toDouble())
            config.set("holos.${id}.location.z", if (exact) loc.z else loc.blockZ.toDouble() + 0.5)
            config.set("holos.${id}.text", text)
            config.set("holos.${id}.small", small)
            saveConfig()
        }
        val holo = Holo(loc, text.asList(), small)
        holos[id] = holo
    }


    fun getHolo(id: String): Holo {
        return holos[id] ?: throw NoHoloException()
    }

    private fun saveConfig() {
        config.save(origin)
    }


}