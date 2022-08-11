//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraftforge.client.event.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;

public class NoRender extends Module
{
    public static NoRender Instance;
    public final Switch hurtCam;
    public final Switch fire;
    public final Switch explosions;
    public final Switch insideBlocks;
    public final Switch armor;
    
    public NoRender() {
        this.hurtCam = this.Menu.Switch("Hurt Cam");
        this.fire = this.Menu.Switch("Fire");
        this.explosions = this.Menu.Switch("Explosions");
        this.insideBlocks = this.Menu.Switch("Inside Blocks");
        this.armor = this.Menu.Switch("Armor");
        NoRender.Instance = this;
    }
    
    @RegisterListener
    public void onRenderBlockOverlay(final RenderBlockOverlayEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (event.getOverlayType().equals((Object)RenderBlockOverlayEvent.OverlayType.FIRE)) {
            event.setCanceled(this.fire.GetSwitch());
        }
        if (event.getOverlayType().equals((Object)RenderBlockOverlayEvent.OverlayType.BLOCK)) {
            event.setCanceled(this.insideBlocks.GetSwitch());
        }
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            event.setCancelled(this.explosions.GetSwitch());
        }
    }
}
