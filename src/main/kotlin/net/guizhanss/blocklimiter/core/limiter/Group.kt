package net.guizhanss.blocklimiter.core.limiter

import org.bukkit.entity.Player

/**
 * This class represents a limit group.
 */
data class Group(
    val name: String,
    val defaultLimit: Int,
    val items: Set<String>,
    val permissionNodes: Map<String, Int> = emptyMap(),
) {
    /**
     * Get the player limit for a specific item.
     *
     * @param p [Player] the player
     * @param id [String] the item id
     *
     * @return [Int] the player limit, -1 when the item is not in this group
     */
    fun getPlayerLimit(p: Player, id: String? = null): Int {
        // check if the item is in this group
        if (id != null && !items.contains(id)) return -1

        // check for permission node limits
        for ((node, limit) in permissionNodes.entries) {
            if (p.hasPermission("blocklimiter.permission.$node")) return limit
        }
        // return default limit
        return defaultLimit
    }
}
