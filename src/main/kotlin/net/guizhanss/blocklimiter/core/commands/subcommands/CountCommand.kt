@file:Suppress("deprecation")

package net.guizhanss.blocklimiter.core.commands.subcommands

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.ChunkPosition
import net.guizhanss.blocklimiter.BlockLimiter
import net.guizhanss.blocklimiter.core.commands.AbstractSubCommand
import net.guizhanss.guizhanlib.minecraft.commands.AbstractCommand
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CountCommand(parent: AbstractCommand) : AbstractSubCommand(
    parent, "count", { _, _ -> "获取当前区块的限制信息" }, "[分组名称]"
) {
    override fun onExecute(p: CommandSender, args: Array<String>) {
        if (p !is Player) {
            p.sendMessage("${ChatColor.RED}只有玩家才能执行该命令")
            return
        }

        val chunkContent = BlockLimiter.limiter.getChunkContent(ChunkPosition(p.location.chunk))

        val msg = StringBuilder()

        if (args.size == 1) {
            // 指定分组的数量限制信息
            val groupName = args[0]
            val group = BlockLimiter.limiter.getGroups().find { it.name == groupName }

            if (group == null) {
                p.sendMessage("${ChatColor.RED}未找到名为${ChatColor.YELLOW}$groupName${ChatColor.RED}的分组")
                return
            }

            msg.append("${ChatColor.GREEN}分组名称: ")
                .append("${ChatColor.YELLOW}${group.name}\n")

            msg.append("${ChatColor.GREEN}当前限制: ")
                .append("${ChatColor.WHITE}${chunkContent?.getGroupTotal(group) ?: 0}")
                .append("${ChatColor.GRAY} / ")
                .append("${ChatColor.WHITE}${group.getPlayerLimit(p)}")
                .append("\n")

            msg.append("${ChatColor.GREEN}默认限制: ")
                .append("${ChatColor.WHITE}${group.defaultLimit}")
                .append("\n")

            msg.append("${ChatColor.GREEN}包含物品: \n")
            for (itemId in group.items) {
                val sfItem = SlimefunItem.getById(itemId)
                if (sfItem != null) {
                    msg.append("${ChatColor.YELLOW}${chunkContent?.getAmount(itemId) ?: 0} x ")
                        .append("${ChatColor.WHITE}${sfItem.itemName} ${ChatColor.WHITE}(${itemId})\n")
                }
            }

        } else {
            // 获取所有分组的数量限制信息
            if (p.hasPermission("blocklimiter.bypass")) {
                msg.append("${ChatColor.GREEN}你拥有绕过所有分组限制的权限\n")
            }

            for (group in BlockLimiter.limiter.getGroups()) {
                msg.append("${ChatColor.YELLOW}${group.name}: ")
                    .append("${ChatColor.WHITE}${chunkContent?.getGroupTotal(group) ?: 0}")
                    .append("${ChatColor.GRAY} / ")
                    .append("${ChatColor.WHITE}${group.getPlayerLimit(p)}")
                    .append("\n")
            }

            msg.append("${ChatColor.GREEN}如需查看限制组的详细信息，请使用指令${ChatColor.YELLOW}/blocklimiter count <分组名称>")
        }
        p.sendMessage(msg.toString())
    }

    override fun onTab(sender: CommandSender, args: Array<String>): List<String> {
        return if (args.size == 1) {
            BlockLimiter.limiter.getGroups().map { it.name }.filter { it.startsWith(args[0]) }
        } else {
            emptyList()
        }
    }
}
