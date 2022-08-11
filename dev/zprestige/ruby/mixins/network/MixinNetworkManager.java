//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.network;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.event.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    protected void onPacketSend(final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.PacketSendEvent event = new PacketEvent.PacketSendEvent((Packet)packet);
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "channelRead0*" }, at = { @At("HEAD") }, cancellable = true)
    protected void onPacketReceive(final ChannelHandlerContext chc, final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.PacketReceiveEvent event = new PacketEvent.PacketReceiveEvent((Packet)packet);
        Ruby.eventBus.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
