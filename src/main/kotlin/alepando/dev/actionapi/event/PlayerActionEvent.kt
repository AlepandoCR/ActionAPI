package alepando.dev.actionapi.event

import alepando.dev.actionapi.wrapper.PlayerAction
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerActionEvent(player: Player, private val action: PlayerAction): PlayerEvent(player) {
    private val handlerList: HandlerList = HandlerList()

    override fun getHandlers(): HandlerList {
        return this.handlerList
    }

    fun getAction():PlayerAction{
        return action
    }
}