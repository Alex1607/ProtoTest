package com.pluoi.prototest;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ProtoTest extends JavaPlugin {

    int ticks = 0;
    int packetsTick = 0;

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ticks++;
                System.out.print("-----------------------");
                System.out.print("Tick: " + ticks);
                System.out.print("Packets/Tick: " + packetsTick);
                packetsTick = 0;
            }
        }.runTaskTimer(this, 0, 1);

        PacketAdapter packetAdapter = new PacketAdapter(this, ListenerPriority.HIGHEST,
                PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.LOOK, PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.ARM_ANIMATION,
                PacketType.Play.Client.BLOCK_DIG, PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.ENTITY_DESTROY, PacketType.Play.Server.ENTITY_VELOCITY, PacketType.Play.Server.SPAWN_ENTITY_LIVING,
                PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, PacketType.Play.Server.ENTITY_LOOK, PacketType.Play.Server.POSITION, PacketType.Play.Server.ENTITY_TELEPORT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                System.out.print("Recieved Packet Data: " + event.getPlayer().getName() + " - " + event.getPacketType());
                packetsTick++;
            }

            @Override
            public void onPacketSending(PacketEvent event) {
                System.out.print("Sending Packet Data: " + event.getPlayer().getName() + " - " + event.getPacketType());
                packetsTick++;
            }
        };

        ProtocolLibrary.getProtocolManager().addPacketListener(packetAdapter);
    }
}
