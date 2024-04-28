package net.guizhanss.blocklimiter.core.listeners

import com.xzavier0722.mc.plugin.slimefun4.storage.event.SlimefunChunkDataLoadEvent
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.ChunkPosition
import net.guizhanss.blocklimiter.BlockLimiter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ChunkLoadListener(plugin: BlockLimiter) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun onChunkLoad(e: SlimefunChunkDataLoadEvent) {
        BlockLimiter.limiter.loadChunk(ChunkPosition(e.chunk), e.chunkData.allBlockData)
    }
}
