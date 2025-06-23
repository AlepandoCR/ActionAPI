package alepando.dev.actionapi.wrapper

import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket

internal object ActionTranslator {
    /**
     * Converts from NMS enum to the wrapper.
     *
     * @return Corresponding [ActionType].
     */
    fun ServerboundPlayerActionPacket.Action.toApi(): ActionType {
        return ActionType.valueOf(this.name)
    }

    /**
     * Converts from this wrapper to the NMS equivalent.
     *
     * @return Corresponding [ServerboundPlayerActionPacket.Action].
     */
    fun ActionType.toNMS(): ServerboundPlayerActionPacket.Action {
        return ServerboundPlayerActionPacket.Action.valueOf(name)
    }


}