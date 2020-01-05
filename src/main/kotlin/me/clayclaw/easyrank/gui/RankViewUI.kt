package me.clayclaw.easyrank.gui

import dev.reactant.reactant.core.component.Component
import dev.reactant.reactant.core.dependency.injection.Inject
import dev.reactant.reactant.service.spec.config.Config
import dev.reactant.reactant.ui.ReactantUIService
import dev.reactant.reactant.ui.editing.event
import dev.reactant.reactant.ui.element.style.actual
import dev.reactant.reactant.ui.element.style.fillParent
import dev.reactant.reactant.ui.event
import dev.reactant.reactant.ui.event.interact.element.UIElementClickEvent
import dev.reactant.reactant.ui.kits.ReactantUIDivElement
import dev.reactant.reactant.ui.kits.div
import dev.reactant.reactant.ui.kits.item
import dev.reactant.reactant.ui.kits.span
import dev.reactant.reactant.ui.query.getElementById
import dev.reactant.reactant.utils.content.item.itemStackOf
import me.clayclaw.easyrank.config.ConfigEZRank
import me.clayclaw.easyrank.config.ConfigRank
import me.clayclaw.easyrank.extension.decodeChatColor
import me.clayclaw.easyrank.storage.PlayerStorageService
import me.clayclaw.easyrank.util.XEnchantment
import me.clayclaw.easyrank.util.XMaterial
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.SkullMeta

@Component
class RankViewUI(
        private val uiService: ReactantUIService,
        private val storageService: PlayerStorageService,
        @Inject("plugins/EasyRank/rank.yml")
        private val configRank : Config<ConfigRank>,
        @Inject("plugins/EasyRank/config.yml")
        private val config : Config<ConfigEZRank>
) {

    fun createView(player : Player){

        uiService.createUI(player, config.content.Locale.UI_Name.decodeChatColor(), 6) {

            view.event<UIElementClickEvent>().subscribe {
                it.isCancelled = true
            }

            div {

                id = "page"
                size(fillParent, fillParent)

                fun displayPage(pageNumber : Int){

                    if(pageNumber < 0){
                        displayPage(0)
                        return
                    }

                    if(configRank.content.profileMap.size < pageNumber * 9 * 4) {
                        displayPage(pageNumber - 1)
                        return
                    }

                    view.getElementById<ReactantUIDivElement>("page")?.edit().apply {

                        div {

                            size(fillParent, actual(4))

                            configRank.content.profileMap
                                    .forEach { (id, profile) ->

                                span {

                                    item(itemStackOf {
                                        type = if(player.hasPermission("rank.$id")) Material.valueOf(profile.displayMaterial) else XMaterial.BARRIER.parseMaterial()!!
                                        itemMeta {
                                            setDisplayName(profile.name.decodeChatColor())
                                            setLore(profile.displayLore.map { it.decodeChatColor() })
                                            addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                        }
                                        if(storageService.getSelectedRank(player) == id) {
                                            enchantments {
                                                XEnchantment.ARROW_DAMAGE.parseEnchantment()!! level 10
                                            }
                                        }
                                    })

                                    if(player.hasPermission("rank.$id")){
                                        event<UIElementClickEvent>()
                                                .subscribe {
                                                    storageService.updateSelectedRank(player, id)
                                                    player.closeInventory()
                                                    createView(player)
                                                }
                                    }

                                }

                            }

                        }

                        div {

                            size(fillParent, actual(1))
                            fill(itemStackOf {
                                type = XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()!!
                                itemMeta {
                                    setDisplayName("")
                                }
                            })

                        }

                        div {

                            size(fillParent, actual(1))

                            div {

                                margin(0,3)

                                item (
                                    displayItem = itemStackOf {
                                        type = XMaterial.NETHER_STAR.parseMaterial()!!
                                        itemMeta {
                                            setDisplayName(config.content.Locale.UI_PreviousPage.decodeChatColor())
                                        }

                                        event<UIElementClickEvent>().subscribe {
                                            displayPage(pageNumber - 1)
                                        }

                                    }
                                )

                                val headItem = XMaterial.PLAYER_HEAD.parseItem()
                                headItem.itemMeta = (headItem.itemMeta as SkullMeta).let {
                                    it.owningPlayer = player
                                    it.lore = listOf(config.content.Locale.UI_PageNumber.replace("{page}", "${pageNumber + 1}").decodeChatColor())
                                    it.setDisplayName(config.content.Locale.UI_PlayerDisplay_Name.replace("{player}", player.name).decodeChatColor())
                                    it
                                }

                                /*
                                val headItem = itemStackOf {
                                    type = XMaterial.PLAYER_HEAD.parseMaterial()!!
                                    itemMeta {
                                        this as SkullMeta
                                        owningPlayer = player
                                        lore = listOf(config.content.Locale.UI_PageNumber.replace("{page}", "${pageNumber + 1}").decodeChatColor())
                                        setDisplayName(config.content.Locale.UI_PlayerDisplay_Name.replace("{player}", player.name).decodeChatColor())
                                    }
                                }

                                 */

                                item (headItem)

                                item(itemStackOf {
                                    type = XMaterial.NETHER_STAR.parseMaterial()!!
                                    itemMeta {

                                        setDisplayName(config.content.Locale.UI_NextPage.decodeChatColor())

                                        event<UIElementClickEvent>().subscribe {
                                            displayPage(pageNumber + 1)
                                        }

                                    }
                                })

                            }

                        }
                    }
                }

                displayPage(0)

            }

        }
    }

}
