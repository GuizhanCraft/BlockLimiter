package net.guizhanss.blocklimiter.core.services

import net.guizhanss.blocklimiter.BlockLimiter
import net.guizhanss.blocklimiter.core.listeners.BlockBreakListener
import net.guizhanss.blocklimiter.core.listeners.BlockPlaceListener
import net.guizhanss.blocklimiter.core.listeners.ChunkLoadListener

class ListenerService(plugin: BlockLimiter) {
    init {
        BlockBreakListener(plugin)
        BlockPlaceListener(plugin)
        ChunkLoadListener(plugin)
    }
}
