package alepando.dev.actionapi.listener

import alepando.dev.actionapi.packets.PacketSniffer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent
import org.bukkit.plugin.Plugin

internal class ServerStatusListener(private val plugin: Plugin): Listener {
    @EventHandler
    fun onDisable(event: PluginDisableEvent){
        PacketSniffer.cleanOrphanedPipelines(plugin)
    }

    @EventHandler
    fun onEnable(event: PluginEnableEvent){
        PacketSniffer.cleanOrphanedPipelines(plugin)
    }
}