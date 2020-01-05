package me.clayclaw.easyrank.event

import org.bukkit.OfflinePlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class RankUpdateEvent(
        val player : OfflinePlayer,
        val rankId : String
) : Event() {
    
    override fun getHandlers(): HandlerList {
        return getHandlerList()
    }

    companion object {
        private val HANDLER_LIST = HandlerList()
        @JvmStatic
        fun getHandlerList() = HANDLER_LIST
    }

}
