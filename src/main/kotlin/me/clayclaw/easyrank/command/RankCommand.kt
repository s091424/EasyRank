package me.clayclaw.easyrank.command

import dev.reactant.reactant.extra.command.ReactantCommand
import me.clayclaw.easyrank.gui.RankViewUI
import org.bukkit.entity.Player
import picocli.CommandLine

@CommandLine.Command(
        name = "rank2",
        mixinStandardHelpOptions = true,
        description = ["RankUI display command"]
)
class RankCommand(
        private val rankViewUI: RankViewUI
) : ReactantCommand() {

    override fun run() {
        if(sender !is Player) return
        rankViewUI.createView(sender as Player)
    }

}
