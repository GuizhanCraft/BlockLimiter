package net.guizhanss.blocklimiter.core.limiter

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.ChunkPosition
import net.guizhanss.blocklimiter.BlockLimiter
import net.guizhanss.blocklimiter.utils.ConfigUtils
import org.bukkit.entity.Player
import java.util.logging.Level

/**
 * The core limiter class.
 */
class Limiter {
    private val groups: MutableSet<Group> = mutableSetOf()
    private val chunkData: MutableMap<ChunkPosition, ChunkContent> = mutableMapOf()

    init {
        // load groups from config file
        if (!loadGroups()) BlockLimiter.log(Level.WARNING, "配置文件中没有任何组")
    }

    /**
     * Load the limit groups from the config file.
     */
    fun loadGroups(): Boolean {
        groups.clear()
        val cfgSection = BlockLimiter.configService.limitGroups ?: return false
        cfgSection.getKeys(false).forEach {
            val section = cfgSection.getConfigurationSection(it) ?: return@forEach
            val amount = section.getInt("amount")
            val items = HashSet(section.getStringList("items"))
            val permissionNodes = ConfigUtils.getStringIntMap(section.getConfigurationSection("permission-nodes"))

            groups.add(Group(it, amount, items, permissionNodes))
        }
        return true
    }

    /**
     * Load the chunk data from event.
     *
     * @param chunkPos [ChunkPosition] the chunk position
     * @param allBlockData [Set]<[SlimefunBlockData]> all block data in the chunk
     */
    fun loadChunk(chunkPos: ChunkPosition, allBlockData: Set<SlimefunBlockData>) {
        // no need to create chunk content if there is no block data
        if (allBlockData.isEmpty()) return

        val chunkContent = chunkData.getOrPut(chunkPos) { ChunkContent() }
        allBlockData.forEach { chunkContent.increment(it.sfId) }
    }

    fun getGroups() = groups.toSet()

    /**
     * Get the group by item id.
     *
     * @param id [String] the item id
     *
     * @return [Group] the group containing the item, or null if not found
     */
    fun getGroupByItem(id: String) = groups.firstOrNull { it.items.contains(id) }

    /**
     * Get the player limit for a specific item.
     *
     * @param p [Player] the player
     * @param id [String] the item id
     *
     * @return [Int] the player limit, -1 when the item is not in any group
     */
    fun getPlayerLimit(p: Player, id: String): Int {
        // check if the item is in any group (only first match is considered)
        val group = getGroupByItem(id) ?: return -1

        return group.getPlayerLimit(p, id)
    }

    /**
     * Get the chunk content.
     *
     * @param chunkPos [ChunkPosition] the chunk position
     *
     * @return [ChunkContent] the chunk content, or null if not found
     */
    fun getChunkContent(chunkPos: ChunkPosition) = chunkData[chunkPos]

    /**
     * Increase the amount of an item in the chunk.
     *
     * @param chunkPos [ChunkPosition] the chunk position
     * @param id [String] the item id
     * @param amount [Int] the amount to increase, default is 1
     */
    fun increment(chunkPos: ChunkPosition, id: String, amount: Int = 1) {
        chunkData.getOrPut(chunkPos) { ChunkContent() }.increment(id, amount)
    }

    /**
     * Decrease the amount of an item in the chunk.
     * The amount will not drop below 0.
     *
     * @param chunkPos [ChunkPosition] the chunk position
     * @param id [String] the item id
     * @param amount [Int] the amount to decrease, default is 1
     */
    fun decrement(chunkPos: ChunkPosition, id: String, amount: Int = 1) {
        chunkData.getOrPut(chunkPos) { ChunkContent() }.decrement(id, amount)
    }
}
