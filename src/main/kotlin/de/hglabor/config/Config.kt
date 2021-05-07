package de.hglabor.config

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object Config {

    var yamlConfiguration: YamlConfiguration
    private val configFile: File = File("./plugins/Bingo/config.yml")

    val playerCountToStart: Int
    get() = yamlConfiguration.getInt("playerCountToStart")


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
            yamlConfiguration.set("playerCountToStart", 5)
            yamlConfiguration.save(configFile)
        }
    }

}