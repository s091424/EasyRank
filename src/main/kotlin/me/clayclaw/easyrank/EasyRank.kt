package me.clayclaw.easyrank

import dev.reactant.reactant.core.ReactantPlugin
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bukkit.plugin.java.JavaPlugin

@ReactantPlugin(["me.clayclaw.easyrank"])
class EasyRank : JavaPlugin() {

    companion object {
        @JvmStatic
        val log: Logger = LogManager.getLogger("EasyRank")
    }

}
