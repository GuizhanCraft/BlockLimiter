@file:Suppress("deprecation")

package net.guizhanss.blocklimiter.core.commands.subcommands

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import net.guizhanss.blocklimiter.BlockLimiter
import net.guizhanss.blocklimiter.core.commands.AbstractSubCommand
import net.guizhanss.blocklimiter.utils.Permissions
import net.guizhanss.blocklimiter.utils.hasPermission
import net.guizhanss.guizhanlib.minecraft.commands.AbstractCommand
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class InfoCommand(parent: AbstractCommand) : AbstractSubCommand(
    parent, "info", { _, _ -> "列出所有的限制组" }, "<分组名称>"
) {
    override fun onExecute(sender: CommandSender, args: Array<String>) {
        if (!sender.hasPermission(Permissions.INFO)) {
            sender.sendMessage("${ChatColor.RED}你没有权限执行该命令")
            return
        }

        val msg = StringBuilder()

        val groupName = args[0]
        val group = BlockLimiter.limiter.getGroups().find { it.name == groupName }

        if (group == null) {
            sender.sendMessage("${ChatColor.RED}未找到名为${ChatColor.YELLOW}$groupName${ChatColor.RED}的分组")
            return
        }

        msg.append("${ChatColor.GREEN}分组名称: ")
            .append("${ChatColor.YELLOW}${group.name}\n")

        msg.append("${ChatColor.GREEN}默认限制: ")
            .append("${ChatColor.WHITE}${group.defaultLimit}")
            .append("\n")

        msg.append("${ChatColor.GREEN}包含物品: \n")
        for (itemId in group.items) {
            val sfItem = SlimefunItem.getById(itemId)
            if (sfItem != null) {
                msg.append("${ChatColor.WHITE}${sfItem.itemName} ${ChatColor.WHITE}(${itemId})\n")
            }
        }

        if (group.permissionNodes.isNotEmpty()) {
            msg.append("${ChatColor.GREEN}权限节点: \n")
            for ((node, limit) in group.permissionNodes) {
                msg.append("${ChatColor.YELLOW}$node${ChatColor.GRAY}=${ChatColor.WHITE}${limit}\n")
            }
        }

        sender.sendMessage(msg.toString())
    }
}
