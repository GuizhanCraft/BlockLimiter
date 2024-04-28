package net.guizhanss.blocklimiter

import net.guizhanss.blocklimiter.core.commands.MainCommand
import net.guizhanss.blocklimiter.core.limiter.Limiter
import net.guizhanss.blocklimiter.core.services.ConfigService
import net.guizhanss.blocklimiter.core.services.ListenerService
import net.guizhanss.guizhanlib.slimefun.addon.AbstractAddon
import net.guizhanss.guizhanlib.updater.GuizhanBuildsUpdater
import org.bstats.bukkit.Metrics
import org.bukkit.plugin.Plugin
import java.io.File
import java.text.MessageFormat
import java.util.logging.Level

class BlockLimiter : AbstractAddon(
    "ybw0014", "BlockLimiter", "master", "options.auto-update"
) {
    companion object {
        lateinit var instance: BlockLimiter
            private set
        lateinit var configService: ConfigService
            private set
        lateinit var limiter: Limiter
            private set
        var debugEnabled = false

        fun log(level: Level, message: String) {
            instance.logger.log(level, message)
        }

        fun debug(message: String) {
            if (debugEnabled) log(Level.INFO, "[DEBUG] $message")
        }
    }

    override fun enable() {
        instance = this

        if (!server.pluginManager.isPluginEnabled("GuizhanLibPlugin")) {
            logger.log(Level.SEVERE, "本插件需要 鬼斩前置库插件(GuizhanLibPlugin) 才能运行!")
            logger.log(Level.SEVERE, "从此处下载: https://50L.cc/gzlib")
            server.pluginManager.disablePlugin(this)
            return
        }

        // config
        configService = ConfigService(this)

        // limiter
        limiter = Limiter()

        // listener
        ListenerService(this)

        // commands
        val pluginCommand = getCommand("blocklimiter")
        if (pluginCommand == null) {
            logger.log(Level.SEVERE, "无法注册插件指令，请汇报该问题")
        } else {
            MainCommand(pluginCommand).register()
        }

        // metrics
        setupMetrics()
    }

    override fun disable() {
        // nothing to do
    }

    private fun setupMetrics() {
        Metrics(this, 21726)
    }

    override fun autoUpdate() {
        if (pluginVersion.startsWith("Build")) {
            try {
                // use updater in lib plugin
                val clazz = Class.forName("net.guizhanss.guizhanlibplugin.updater.GuizhanUpdater")
                val updaterStart = clazz.getDeclaredMethod(
                    "start",
                    Plugin::class.java,
                    File::class.java,
                    String::class.java,
                    String::class.java,
                    String::class.java
                )
                updaterStart.invoke(null, this, file, githubUser, githubRepo, githubBranch)
            } catch (ignored: Exception) {
                // use updater in lib
                GuizhanBuildsUpdater(this, file, githubUser, githubRepo, githubBranch).start()
            }
        }
    }
}
