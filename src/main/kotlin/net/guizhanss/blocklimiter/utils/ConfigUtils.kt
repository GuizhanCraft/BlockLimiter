package net.guizhanss.blocklimiter.utils

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.plugin.Plugin
import java.io.File

object ConfigUtils {
    fun saveDefaultFile(plugin: Plugin, fileName: String) {
        val file = File(plugin.dataFolder, fileName)
        if (!file.exists()) {
            plugin.saveResource(fileName, false)
        }
    }

    fun getStringIntMap(section: ConfigurationSection?): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        section?.getKeys(false)?.forEach {
            map[it] = section.getInt(it)
        }
        return map
    }
}
