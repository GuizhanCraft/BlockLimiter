@file:Suppress("deprecation")

package net.guizhanss.blocklimiter.core.commands.subcommands

import net.guizhanss.blocklimiter.BlockLimiter
import net.guizhanss.blocklimiter.core.commands.AbstractSubCommand
import net.guizhanss.blocklimiter.utils.Permissions
import net.guizhanss.blocklimiter.utils.hasPermission
import net.guizhanss.guizhanlib.minecraft.commands.AbstractCommand
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class ListCommand(parent: AbstractCommand) : AbstractSubCommand(
    parent, "list", { _, _ -> "列出所有的限制分组" }, ""
) {
    override fun onExecute(sender: CommandSender, args: Array<String>) {
        if (!sender.hasPermission(Permissions.LIST)) {
            sender.sendMessage("${ChatColor.RED}你没有权限执行该命令")
            return
        }

        val msg = StringBuilder()

        msg.append("${ChatColor.GREEN}所有的限制分组: \n")

        val groups = BlockLimiter.limiter.getGroups().joinToString("${ChatColor.WHITE}, ") { "${ChatColor.YELLOW}${it.name}" }

        msg.append(groups)

        sender.sendMessage(msg.toString())
    }
}
