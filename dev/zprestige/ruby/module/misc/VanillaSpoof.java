//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.events.*;
import net.minecraftforge.fml.common.network.internal.*;
import net.minecraft.network.play.client.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class VanillaSpoof extends Module
{
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (event.getPacket() instanceof FMLProxyPacket && !this.mc.isSingleplayer()) {
            event.setCancelled(true);
        }
        if (event.getPacket() instanceof CPacketCustomPayload) {
            final CPacketCustomPayload packet = (CPacketCustomPayload)event.getPacket();
            if (packet.getChannelName().equals("MC|Brand")) {
                packet.data = new PacketBuffer(Unpooled.buffer()).writeString("vanilla");
            }
        }
    }
}
