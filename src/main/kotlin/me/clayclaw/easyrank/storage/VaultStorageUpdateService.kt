package me.clayclaw.easyrank.storage

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.core.dependency.injection.Inject
import dev.reactant.reactant.service.spec.config.Config
import dev.reactant.reactant.service.spec.server.EventService
import me.clayclaw.easyrank.config.ConfigRank
import me.clayclaw.easyrank.event.RankUpdateEvent
import me.clayclaw.easyrank.extension.decodeChatColor
import net.milkbowl.vault.chat.Chat
import org.bukkit.Bukkit

@Component
class VaultStorageUpdateService(
        private val eventService: EventService,
        @Inject("plugins/EasyRank/rank.yml")
        private val configRank : Config<ConfigRank>
) : LifeCycleHook {

    override fun onEnable() {

        val chatService : Chat? = Bukkit.getServer().servicesManager.getRegistration(Chat::class.java)?.provider

        eventService.registerBy(this) {
            RankUpdateEvent::class.observable()
                    .filter { it.player.player != null }
                    .filter { configRank.content.profileMap.containsKey(it.rankId) }
                    .subscribe {
                        chatService?.setPlayerPrefix(
                                it.player.player,
                                configRank.content.profileMap[it.rankId]!!.name.decodeChatColor())
                    }
        }

    }

}
