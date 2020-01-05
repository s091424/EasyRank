package me.clayclaw.easyrank.storage

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.core.dependency.injection.Inject
import dev.reactant.reactant.service.spec.config.Config
import dev.reactant.reactant.service.spec.dsl.register
import dev.reactant.reactant.service.spec.server.EventService
import me.clayclaw.easyrank.config.ConfigEZRank
import me.clayclaw.easyrank.event.RankUpdateEvent
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import java.util.*
import kotlin.collections.HashMap

@Component
class PlayerStorageService(
        private val eventService: EventService,
        @Inject("plugins/EasyRank/data.json")
        private val storage : Config<StoragePlayerData>,
        @Inject("plugins/EasyRank/config.yml")
        private val config : Config<ConfigEZRank>
) : LifeCycleHook {

    private var playerSelection : HashMap<String, String> = hashMapOf()

    override fun onEnable() {

        storage.content.players.forEach { updateSelectedRank(it.id, it.selectedRank) }

        register(eventService) {

            PlayerJoinEvent::class.observable()
                    .filter { !playerSelection.containsKey(getKey(it.player)) }
                    .subscribe {
                        updateSelectedRank(it.player, "unknown")
                    }

            PlayerJoinEvent::class.observable()
                    .filter { playerSelection.containsKey(getKey(it.player)) }
                    .subscribe {
                        updateSelectedRank(it.player, getSelectedRank(it.player))
                    }

        }
    }

    override fun onSave() {
        storage.content.players.clear()
        playerSelection.forEach { (id, selected) ->
            storage.content.players.add(
                    with(StoragePlayerData.PlayerRankDataProfile()) {
                        this.id = id
                        this.selectedRank = selected
                        this
                    }
            )
        }
        storage.save().blockingAwait()
    }

    fun getKey(player : Player) : String {
        return if(config.content.UUIDMode) player.uniqueId.toString() else player.name
    }

    fun getSelectedRank(player : Player) : String {
        return playerSelection.getOrDefault(getKey(player), "unknown")
    }

    fun updateSelectedRank(player : Player, rankId : String){
        updateSelectedRank(getKey(player), rankId)
    }

    fun updateSelectedRank(key : String, rankId : String){
        playerSelection[key] = rankId
        Bukkit.getPluginManager().callEvent(RankUpdateEvent(getPlayerByKey(key), rankId))
    }

    private fun getPlayerByKey(key : String) : OfflinePlayer {
        return if(config.content.UUIDMode) Bukkit.getOfflinePlayer(UUID.fromString(key)) else Bukkit.getOfflinePlayer(key)
    }

}
