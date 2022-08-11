//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class NoRotations extends Module
{
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof SPacketPlayerPosLook) || this.mc.currentScreen != null) {
            return;
        }
        ((SPacketPlayerPosLook)event.getPacket()).yaw = this.mc.player.rotationYaw;
        ((SPacketPlayerPosLook)event.getPacket()).pitch = this.mc.player.rotationPitch;
        ((SPacketPlayerPosLook)event.getPacket()).getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
        ((SPacketPlayerPosLook)event.getPacket()).getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
    }
}
