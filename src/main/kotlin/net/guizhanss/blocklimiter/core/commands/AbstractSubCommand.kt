package net.guizhanss.blocklimiter.core.commands

import net.guizhanss.guizhanlib.minecraft.commands.AbstractCommand
import net.guizhanss.guizhanlib.minecraft.commands.SubCommand
import org.bukkit.command.CommandSender

abstract class AbstractSubCommand(
    parent: AbstractCommand?,
    name: String,
    description: (AbstractCommand, CommandSender) -> String,
    usage: String,
    vararg subCommands: SubCommand
) : SubCommand(parent, name, description, usage, *subCommands) {
    constructor(
        name: String,
        description: (AbstractCommand, CommandSender) -> String,
        usage: String,
        vararg subCommands: SubCommand
    ) : this(null, name, description, usage, *subCommands)
}
