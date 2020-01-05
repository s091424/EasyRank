package me.clayclaw.easyrank.command

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook
import dev.reactant.reactant.extra.command.PicocliCommandService
import dev.reactant.reactant.service.spec.dsl.register
import me.clayclaw.easyrank.gui.RankViewUI

@Component
class CommandRegistry(
        private val rankViewUI: RankViewUI,
        private val commandService: PicocliCommandService
) : LifeCycleHook {

    override fun onEnable() {
        register(commandService) {
            command({ RankCommand(rankViewUI) })
        }
    }

}
