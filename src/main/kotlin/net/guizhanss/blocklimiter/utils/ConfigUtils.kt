package net.guizhanss.blocklimiter.utils

import org.bukkit.configuration.ConfigurationSection

object ConfigUtils {
    fun getStringIntMap(section: ConfigurationSection?): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        section?.getKeys(false)?.forEach {
            map[it] = section.getInt(it)
        }
        return map
    }
}
