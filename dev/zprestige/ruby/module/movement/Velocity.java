//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.network.play.server.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.entity.*;

public class Velocity extends Module
{
    public final Switch explosions;
    public final Switch push;
    public final Switch blocks;
    public final Switch pistons;
    
    public Velocity() {
        this.explosions = this.Menu.Switch("Explosions");
        this.push = this.Menu.Switch("Push");
        this.blocks = this.Menu.Switch("Blocks");
        this.pistons = this.Menu.Switch("Pistons");
    }
    
    @RegisterListener
    public void onPacketReceived(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !this.explosions.GetSwitch()) {
            return;
        }
        event.setCancelled((event.getPacket() instanceof SPacketEntityVelocity) ? (((SPacketEntityVelocity)event.getPacket()).getEntityID() == this.mc.player.entityId) : (event.getPacket() instanceof SPacketExplosion));
    }
    
    @RegisterListener
    public void onBlockPush(final BlockPushEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !this.blocks.GetSwitch()) {
            return;
        }
        event.setCancelled(true);
    }
    
    @RegisterListener
    public void onEntityCollision(final EntityPushEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !this.push.GetSwitch()) {
            return;
        }
        event.setCancelled(true);
    }
    
    @RegisterListener
    public void onMove(final MoveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !this.pistons.GetSwitch() || (!event.getType().equals((Object)MoverType.PISTON) && !event.getType().equals((Object)MoverType.SHULKER_BOX))) {
            return;
        }
        event.setCancelled(true);
    }
}
