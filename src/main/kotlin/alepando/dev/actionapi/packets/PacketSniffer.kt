package alepando.dev.actionapi.packets

import alepando.dev.actionapi.event.PlayerActionEvent
import alepando.dev.actionapi.wrapper.ActionTranslator.toApi
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket
import net.minecraft.server.MinecraftServer
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

internal object PacketSniffer {

    private val injectedPlayers = mutableSetOf<UUID>()

    fun inject(player: Player, plugin: Plugin) {
        val handlerName = "${plugin.name.lowercase()}_action_sniffer"
        val nmsPlayer = (player as CraftPlayer).handle
        val connection: Connection = nmsPlayer.connection.connection
        val channel = connection.channel

        if (channel.pipeline().get(handlerName) != null) return

        val handler = object : ChannelDuplexHandler() {
            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                if(msg is ServerboundPlayerActionPacket){
                    object : BukkitRunnable(){
                        override fun run() {
                            PlayerActionEvent(player,msg.action.toApi()).callEvent()
                        }

                    }.runTask(plugin)
                }
                super.channelRead(ctx, msg)
            }
        }

        channel.pipeline().addBefore("packet_handler", handlerName, handler)
        injectedPlayers.add(player.uniqueId)
    }


    fun eject(player: Player) {
        val nmsPlayer = (player as CraftPlayer).handle
        val connection = nmsPlayer.connection.connection
        val channel = connection.channel

        channel.pipeline().names().filter { it.endsWith("_action_sniffer") }.forEach {
            channel.pipeline().remove(it)
        }

        injectedPlayers.remove(player.uniqueId)
    }


    fun unregisterAllPlayers() {
        Bukkit.getOnlinePlayers().forEach { eject(it) }
        injectedPlayers.clear()
    }


    fun cleanOrphanedPipelines(plugin: Plugin) {
        val serverConnections = MinecraftServer.getServer().connection.connections

        val handlerSuffix = "_action_sniffer"
        val handlerName = "${plugin.name.lowercase()}$handlerSuffix"

        serverConnections.forEach { conn ->
            val channel = conn.channel
            if (channel.isOpen && channel.pipeline().names().contains(handlerName)) {
                channel.pipeline().remove(handlerName)
            }
        }
    }
}
