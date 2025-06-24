# ActionAPI

ActionAPI is a Spigot/Paper plugin that provides an API for other plugins to listen to player actions and server status events.

## Installation

To use ActionAPI in your plugin, add the following to your `build.gradle.kts` file:

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.AlepandoCR:ActionAPI:v1.0.4")
}
```


## Usage



ActionAPI allows you to listen to player's pressing keys attached to actions such as dropping an item. It works by injecting a packet sniffer into the player's network channel and then firing a Bukkit event when a relevant packet is detected.

1.  **Initialize ActionAPI:**
    In your plugin's `onEnable` method, initialize ActionAPI. This will automatically handle injecting and ejecting the packet sniffer when players join and quit.

    ```kotlin
    import alepando.dev.actionapi.ActionAPI
    import org.bukkit.plugin.java.JavaPlugin

    class MyPlugin : JavaPlugin() {
        override fun onEnable() {
            ActionAPI.initialize(this)
        }
    }
    ```

2. **Listen for PlayerActionEvent:**
    Register an event listener for `PlayerActionEvent` to get notified of player actions.

   ```kotlin
   import alepando.dev.actionapi.event.PlayerActionEvent
   import alepando.dev.actionapi.wrapper.ActionType
   import org.bukkit.event.EventHandler
   import org.bukkit.event.Listener
   import org.bukkit.plugin.java.JavaPlugin

   class MyPlugin : JavaPlugin(), Listener {
       override fun onEnable() {
           ActionAPI.initialize(this)
           server.pluginManager.registerEvents(this, this)
       }

       @EventHandler
       fun onPlayerAction(event: PlayerActionEvent) {
           val player = event.player
           when (val actionType = event.getAction()) {
               ActionType.DROP_ITEM -> {
                   logger.info("${player.name} dropped an item.") // pressed "Q" on default controls
               }
               ActionType.SWAP_ITEM_WITH_OFFHAND -> {
                   // Player stopped sneaking
                   logger.info("${player.name} swapped main and offhand.") // pressed "F" on default controls
               }
               // Handle other action types as needed
               else -> {
                   logger.info("${player.name} performed action: ${actionType.name}")
               }
           }
       }
   }
   ```

    You can find all available action types in the `alepando.dev.actionapi.wrapper.ActionType` enum.