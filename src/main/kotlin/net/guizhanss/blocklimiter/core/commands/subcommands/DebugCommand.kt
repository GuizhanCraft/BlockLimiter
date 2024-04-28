@file:Suppress("deprecation")

package net.guizhanss.blocklimiter.core.commands.subcommands

import net.guizhanss.blocklimiter.BlockLimiter
import net.guizhanss.blocklimiter.core.commands.AbstractSubCommand
import net.guizhanss.guizhanlib.minecraft.commands.AbstractCommand
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class DebugCommand(parent: AbstractCommand) : AbstractSubCommand(
    parent, "debug", { _, _ -> "切换调试模式" }, ""
) {
    override fun onExecute(sender: CommandSender, args: Array<String>) {
        if (!sender.hasPermission("blocklimiter.debug")) {
            sender.sendMessage("${ChatColor.RED}你没有权限执行该命令")
            return
        }

        BlockLimiter.debugEnabled = !BlockLimiter.debugEnabled
        sender.sendMessage(
            if (BlockLimiter.debugEnabled) "${ChatColor.GREEN}调试模式已开启" else "${ChatColor.RED}调试模式已关闭"
        )
    }
}
