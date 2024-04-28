@file:Suppress("deprecation")

package net.guizhanss.blocklimiter.core.commands.subcommands

import net.guizhanss.blocklimiter.BlockLimiter
import net.guizhanss.blocklimiter.core.commands.AbstractSubCommand
import net.guizhanss.guizhanlib.minecraft.commands.AbstractCommand
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class ReloadCommand(parent: AbstractCommand) : AbstractSubCommand(
    parent, "reload", { _, _ -> "重载配置文件" }, ""
) {
    override fun onExecute(sender: CommandSender, args: Array<String>) {
        if (!sender.hasPermission("blocklimiter.reload")) {
            sender.sendMessage("${ChatColor.RED}你没有权限执行该命令")
            return
        }

        BlockLimiter.limiter.loadGroups()
        sender.sendMessage("${ChatColor.GREEN}配置文件已重载")
    }
}
