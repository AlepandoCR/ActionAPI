package alepando.dev.actionapi.wrapper

/**
 * Wrapper for the [ServerboundPlayerActionPacket.Action] enum in NMS.
 * Provides a safer and cleaner way to interact with player action packets.
 */
enum class ActionType {
    /** Player starts to break a block.  */
    START_DESTROY_BLOCK,

    /** Player cancels breaking a block.  */
    ABORT_DESTROY_BLOCK,

    /** Player finishes breaking a block.  */
    STOP_DESTROY_BLOCK,

    /** Player drops the entire inventory.  */
    DROP_ALL_ITEMS,

    /** Player drops one item.  */
    DROP_ITEM,

    /** Player stops using an item (e.g., bows, shields).  */
    RELEASE_USE_ITEM,

    /** Player swaps item with the offhand.  */
    SWAP_ITEM_WITH_OFFHAND
}