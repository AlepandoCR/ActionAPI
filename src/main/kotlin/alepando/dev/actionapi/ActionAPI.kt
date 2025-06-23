package alepando.dev.actionapi

import alepando.dev.actionapi.listener.PlayerConnectionStatus
import alepando.dev.actionapi.listener.ServerStatusListener
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

object ActionAPI {
    private var initialized = false

    fun initialize(plugin: Plugin) {
        if (initialized) return
        initialized = true

        val pm: PluginManager = Bukkit.getPluginManager()
        pm.registerEvents(PlayerConnectionStatus(plugin), plugin)
        pm.registerEvents(ServerStatusListener(plugin),plugin)
    }
}