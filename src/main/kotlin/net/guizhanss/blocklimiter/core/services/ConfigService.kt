package net.guizhanss.blocklimiter.core.services

import net.guizhanss.blocklimiter.BlockLimiter
import net.guizhanss.guizhanlib.slimefun.addon.AddonConfig
import org.bukkit.configuration.ConfigurationSection

class ConfigService(plugin: BlockLimiter) {
    private val config = AddonConfig(plugin, "config.yml")

    var autoUpdate = true
        private set
    var limitGroups: ConfigurationSection? = null
        private set

    init {
        reload()
    }

    fun reload() {
        config.reload()
        config.addMissingKeys()

        autoUpdate = config.getBoolean("options.auto-update", true)
        limitGroups = config.getConfigurationSection("limit-groups")

        config.save()
    }
}
