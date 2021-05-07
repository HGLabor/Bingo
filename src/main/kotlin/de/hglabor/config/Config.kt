package de.hglabor.config

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object Config {

    var yamlConfiguration: YamlConfiguration
    private val configFile: File = File("./plugins/Bingo/config.yml")

    var playerCountToStart: Int
    get() = yamlConfiguration.getInt("playerCountToStart")
    set(value) = yamlConfiguration.set("playerCountToStart", value)

    init {
        val dir = File("./plugins/Bingo/")
        if(!dir.exists()) {
            dir.mkdirs()
        }
        if(!configFile.exists()) {
            configFile.createNewFile()
        }
        yamlConfiguration = YamlConfiguration.loadConfiguration(configFile)
        if(!yamlConfiguration.contains("playerCountToStart")) {
            playerCountToStart = 5
        }
    }

}