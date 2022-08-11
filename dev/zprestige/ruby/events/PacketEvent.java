//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import net.minecraft.network.*;
import dev.zprestige.ruby.eventbus.event.*;

public class PacketEvent extends Event
{
    Packet<?> packet;
    
    public PacketEvent(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
    
    @IsCancellable
    public static class PacketReceiveEvent extends PacketEvent
    {
        public PacketReceiveEvent(final Packet<?> packet) {
            super(packet);
        }
    }
    
    @IsCancellable
    public static class PacketSendEvent extends PacketEvent
    {
        public PacketSendEvent(final Packet<?> packet) {
            super(packet);
        }
    }
}
