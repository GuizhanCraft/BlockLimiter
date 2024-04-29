package net.guizhanss.blocklimiter.utils

import org.bukkit.command.CommandSender

enum class Permissions(val permission: String) {
    BYPASS("blocklimiter.bypass"),
    DEBUG("blocklimiter.debug"),
    INFO("blocklimiter.info"),
    LIST("blocklimiter.list"),
    RELOAD("blocklimiter.reload");

    fun has(sender: CommandSender) = sender.hasPermission(permission)
}

fun CommandSender.hasPermission(permission: Permissions) = permission.has(this)
