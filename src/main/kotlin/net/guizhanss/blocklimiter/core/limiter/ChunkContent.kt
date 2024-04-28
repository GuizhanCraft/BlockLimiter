package net.guizhanss.blocklimiter.core.limiter

import net.guizhanss.blocklimiter.BlockLimiter

/**
 * This class stores the amount of items in a chunk.
 */
class ChunkContent {
    private val content: MutableMap<String, Int> = mutableMapOf()

    /**
     * Increase the amount of an item in the chunk.
     *
     * @param id [String] the item id
     * @param amount [Int] the amount to increase, default is 1
     */
    fun increment(id: String, amount: Int = 1) {
        setAmount(id, getAmount(id) + amount)
    }

    /**
     * Decrease the amount of an item in the chunk.
     * The amount will not drop below 0.
     *
     * @param id [String] the item id
     * @param amount [Int] the amount to decrease, default is 1
     */
    fun decrement(id: String, amount: Int = 1) {
        setAmount(id, getAmount(id) - amount)
    }

    /**
     * Get the total amount of items in a group in the chunk.
     *
     * @param id [String] the item id
     *
     * @return [Int] the amount of the group total in the chunk, -1 when the item is not limited.
     */
    fun getGroupTotal(id: String): Int {
        val group = BlockLimiter.limiter.getGroupByItem(id) ?: return -1

        return getGroupTotal(group)
    }

    /**
     * Get the total amount of items in a group in the chunk.
     *
     * @param group [Group] the group
     *
     * @return [Int] the amount of the group total in the chunk
     */
    fun getGroupTotal(group: Group): Int {
        return group.items.sumOf { getAmount(it) }
    }

    private fun setAmount(id: String, amount: Int) {
        content[id] = if (amount >= 0) amount else 0
    }

    private fun getAmount(id: String) = content[id] ?: 0
}
