package me.clayclaw.easyrank.extension

import org.bukkit.ChatColor

fun String.decodeChatColor() : String {
    return ChatColor.translateAlternateColorCodes('&', this)
}
