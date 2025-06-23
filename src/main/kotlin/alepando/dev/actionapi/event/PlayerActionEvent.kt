package alepando.dev.actionapi.event

import alepando.dev.actionapi.wrapper.ActionType
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class PlayerActionEvent(player: Player, private val action: ActionType) : PlayerEvent(player) {

    override fun getHandlers(): HandlerList = handlerList

    fun getAction(): ActionType = action

    companion object {
        @JvmStatic
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = handlerList
    }
}
