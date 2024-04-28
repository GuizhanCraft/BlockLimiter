package net.guizhanss.blocklimiter.core.listeners

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockBreakEvent
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.ChunkPosition
import net.guizhanss.blocklimiter.BlockLimiter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class BlockBreakListener(plugin: BlockLimiter) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onPlace(e: SlimefunBlockBreakEvent) {
        val playerLimit = BlockLimiter.limiter.getPlayerLimit(e.player, e.slimefunItem.id)
        if (playerLimit == -1) return

        BlockLimiter.limiter.decrement(ChunkPosition(e.blockBroken.chunk), e.slimefunItem.id)
    }
}
