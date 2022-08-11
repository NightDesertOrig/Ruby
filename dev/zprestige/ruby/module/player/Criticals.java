//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class Criticals extends Module
{
    public final Slider offset;
    public final Switch allowMoving;
    public final Switch onGroundOnly;
    
    public Criticals() {
        this.offset = this.Menu.Slider("Offset", 0.1f, 1.0f);
        this.allowMoving = this.Menu.Switch("Allow Moving");
        this.onGroundOnly = this.Menu.Switch("On Ground Only");
    }
    
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof CPacketUseEntity) || ((CPacketUseEntity)event.getPacket()).getAction() != CPacketUseEntity.Action.ATTACK || !(((CPacketUseEntity)event.getPacket()).getEntityFromWorld((World)this.mc.world) instanceof EntityLivingBase) || !this.mc.player.onGround || this.mc.player.isInWater() || this.mc.player.isInLava()) {
            return;
        }
        if ((!this.allowMoving.GetSwitch() && EntityUtil.isMoving()) || (this.onGroundOnly.GetSwitch() && !this.mc.player.onGround)) {
            return;
        }
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + this.offset.GetSlider(), this.mc.player.posZ, false));
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
    }
}
