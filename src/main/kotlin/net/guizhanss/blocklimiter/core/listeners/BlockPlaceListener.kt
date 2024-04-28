@file:Suppress("deprecation")

package net.guizhanss.blocklimiter.core.listeners

import io.github.thebusybiscuit.slimefun4.api.events.SlimefunBlockPlaceEvent
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.ChunkPosition
import net.guizhanss.blocklimiter.BlockLimiter
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class BlockPlaceListener(plugin: BlockLimiter) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlace(e: SlimefunBlockPlaceEvent) {
        val sfId = e.slimefunItem.id
        val p = e.player
        val playerLimit = BlockLimiter.limiter.getPlayerLimit(p, sfId)
        if (playerLimit == -1) return

        val chunkPos = ChunkPosition(e.blockPlaced.chunk)
        val chunk = BlockLimiter.limiter.getChunkContent(chunkPos)
        BlockLimiter.debug("chunk: $chunkPos, sfId: $sfId, playerLimit: $playerLimit, chunkAmount: ${chunk?.getGroupTotal(sfId) ?: 0}")
        if (chunk == null || chunk.getGroupTotal(sfId) < playerLimit || p.hasPermission("blocklimiter.bypass")) {
            BlockLimiter.limiter.increment(ChunkPosition(e.blockPlaced.chunk), e.slimefunItem.id)
        } else {
            e.isCancelled = true
            p.sendMessage("${ChatColor.RED}已达到限制，无法在该区块中放置更多")
        }
    }
}
