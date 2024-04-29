package net.guizhanss.blocklimiter.core.commands

import net.guizhanss.blocklimiter.core.commands.subcommands.CountCommand
import net.guizhanss.blocklimiter.core.commands.subcommands.DebugCommand
import net.guizhanss.blocklimiter.core.commands.subcommands.InfoCommand
import net.guizhanss.blocklimiter.core.commands.subcommands.ListCommand
import net.guizhanss.blocklimiter.core.commands.subcommands.ReloadCommand
import net.guizhanss.guizhanlib.minecraft.commands.BaseCommand
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand

class MainCommand(command: PluginCommand) : BaseCommand(
    command, { _, _ -> "" }, "<subcommand>"
) {
    init {
        addSubCommand(CountCommand(this))
        addSubCommand(DebugCommand(this))
        addSubCommand(InfoCommand(this))
        addSubCommand(ListCommand(this))
        addSubCommand(ReloadCommand(this))
    }

    override fun onExecute(sender: CommandSender, args: Array<String>) {
        // there are sub commands so the root command does nothing
    }
}
