package de.hglabor.localization

import com.google.common.collect.ImmutableMap
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.onlinePlayers

import org.bukkit.configuration.file.YamlConfiguration

import java.io.File




object Localization {

    private var yamlConfiguration: YamlConfiguration? = null
    private var file: File? = null
    private val prefix = "${KColors.DARKGRAY}${KColors.BOLD}> ${KColors.GRAY}"
    private const val colorKey = "&"
    private const val colorValue = "\u00A7"

    fun load() {
        try {
            file = File("./plugins/", "config.yml")
            if (!file!!.exists()) {
                file!!.createNewFile()
            }
            yamlConfiguration = YamlConfiguration.loadConfiguration(file!!)
        } catch (e: Exception) {
        }
    }

    fun broadcastMessage(key: String) {
        for (player in onlinePlayers) {
            player.sendMessage(getMessage(key, player.locale))
        }
    }

    fun broadcastMessage(key: String, map: ImmutableMap<String, String?>) {
        for (player in onlinePlayers) {
            player.sendMessage(getMessage(key, map, player.locale))
        }
    }

    fun getMessage(key: String, locale: String): String {
        val lang = locale.split("_").toTypedArray()[0]
        if (yamlConfiguration!!.contains("messages.$lang.$key")) {
            return "$prefix${yamlConfiguration!!.getString("messages.$lang.$key")!!.replace(colorKey, colorValue)}"
        } else {
            try {
                yamlConfiguration!!["messages.$lang.$key"] = "messages.$lang.$key"
                yamlConfiguration!!.save(file!!)
            } catch (e: Exception) {
            }
        }
        return "messages.$lang.$key"
    }

    fun getMessage(key: String, map: ImmutableMap<String, String?>, locale: String): String {
        val lang = locale.split("_").toTypedArray()[0]
        if (yamlConfiguration!!.contains("messages.$lang.$key")) {
            var result = yamlConfiguration!!.getString("messages.$lang.$key")
            for (s in map.keys) {
                result = yamlConfiguration!!.getString("messages.$lang.$key")!!.replace("$$s", "${KColors.CORNFLOWERBLUE}${map[s]!!}${KColors.GRAY}")
            }
            return "$prefix$result"
        } else {
            try {
                yamlConfiguration!!["messages.$lang.$key"] = "messages.$lang.$key"
                yamlConfiguration!!.save(file!!)
            } catch (e: Exception) {
            }
        }
        return "messages.$lang.$key"
    }


    fun getUnprefixedMessage(key: String, locale: String): String {
        val lang = locale.split("_").toTypedArray()[0]
        if (yamlConfiguration!!.contains("messages.$lang.$key")) {
            return "${yamlConfiguration!!.getString("messages.$lang.$key")!!.replace(colorKey, colorValue)}"
        } else {
            try {
                yamlConfiguration!!["messages.$lang.$key"] = "messages.$lang.$key"
                yamlConfiguration!!.save(file!!)
            } catch (e: Exception) {
            }
        }
        return "messages.$lang.$key"
    }

    fun getUnprefixedMessage(key: String, map: ImmutableMap<String, String?>, locale: String): String {
        val lang = locale.split("_").toTypedArray()[0]
        if (yamlConfiguration!!.contains("messages.$lang.$key")) {
            var result = yamlConfiguration!!.getString("messages.$lang.$key")
            for (s in map.keys) {
                result = yamlConfiguration!!.getString("messages.$lang.$key")!!.replace("$$s", "${KColors.CORNFLOWERBLUE}${map[s]!!}${KColors.GRAY}")
            }
            return "$result"
        } else {
            try {
                yamlConfiguration!!["messages.$lang.$key"] = "messages.$lang.$key"
                yamlConfiguration!!.save(file!!)
            } catch (e: Exception) {
            }
        }
        return "messages.$lang.$key"
    }

}