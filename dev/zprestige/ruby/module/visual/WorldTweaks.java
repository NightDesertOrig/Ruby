//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.settings.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;

public class WorldTweaks extends Module
{
    public static WorldTweaks Instance;
    public final Parent weather;
    public final ColorBox fogColor;
    public final Slider density;
    public final ComboBox weatherMode;
    public final Slider time;
    public final Parent player;
    public final Slider fov;
    public final Slider chunkLoadDelay;
    public final Switch antiParticles;
    public final Switch noEffects;
    public final Switch noSwing;
    public final Switch noSwitchAnim;
    public final Switch fullBright;
    
    public WorldTweaks() {
        this.weather = this.Menu.Parent("Weather");
        this.fogColor = this.Menu.Color("Fog Color").parent(this.weather);
        this.density = this.Menu.Slider("Density", 0.0f, 1000.0f).parent(this.weather);
        this.weatherMode = this.Menu.ComboBox("Weather", new String[] { "Clear", "Rain", "Thunder" }).parent(this.weather);
        this.time = this.Menu.Slider("Time", 0, 24000).parent(this.weather);
        this.player = this.Menu.Parent("Player");
        this.fov = this.Menu.Slider("Fov", 50.0f, 200.0f).parent(this.player);
        this.chunkLoadDelay = this.Menu.Slider("Chunk Load Delay", 0, 300).parent(this.player);
        this.antiParticles = this.Menu.Switch("AntiParticles").parent(this.player);
        this.noEffects = this.Menu.Switch("No SPacketEffects").parent(this.player);
        this.noSwing = this.Menu.Switch("No Swing").parent(this.player);
        this.noSwitchAnim = this.Menu.Switch("No Switch Anim").parent(this.player);
        this.fullBright = this.Menu.Switch("FullBright").parent(this.player);
        WorldTweaks.Instance = this;
    }
    
    @RegisterListener
    public void onParticle(final ParticleEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        event.setCancelled(this.antiParticles.GetSwitch());
    }
    
    @SubscribeEvent
    public void onWorld(final EntityViewRenderEvent.RenderFogEvent event) {
        if (this.isEnabled()) {
            this.mc.world.setTotalWorldTime((long)this.time.GetSlider());
        }
        this.mc.world.setWorldTime((long)this.time.GetSlider());
    }
    
    public void onTick() {
        this.mc.world.setWorldTime((long)this.time.GetSlider());
        final String getCombo = this.weatherMode.GetCombo();
        switch (getCombo) {
            case "Clear": {
                this.mc.world.setRainStrength(0.0f);
                break;
            }
            case "Rain": {
                this.mc.world.setRainStrength(1.0f);
                break;
            }
            case "Thunder": {
                this.mc.world.setRainStrength(2.0f);
                break;
            }
        }
        if (this.noSwitchAnim.GetSwitch()) {
            if (this.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
                this.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
                this.mc.entityRenderer.itemRenderer.itemStackMainHand = this.mc.player.getHeldItemMainhand();
            }
            if (this.mc.entityRenderer.itemRenderer.prevEquippedProgressOffHand >= 0.9) {
                this.mc.entityRenderer.itemRenderer.equippedProgressOffHand = 1.0f;
                this.mc.entityRenderer.itemRenderer.itemStackOffHand = this.mc.player.getHeldItemOffhand();
            }
        }
        if (this.noSwing.GetSwitch()) {
            this.mc.player.isSwingInProgress = false;
            this.mc.player.swingProgressInt = 0;
            this.mc.player.swingProgress = 0.0f;
            this.mc.player.prevSwingProgress = 0.0f;
        }
        this.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, this.fov.GetSlider());
        if (this.fullBright.GetSwitch() && this.mc.gameSettings.gammaSetting != 6900.0f) {
            this.mc.gameSettings.gammaSetting = 6900.0f;
        }
    }
    
    @SubscribeEvent
    public void onFogColor(final EntityViewRenderEvent.FogColors event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        event.setRed(this.fogColor.GetColor().getRed() / 255.0f);
        event.setGreen(this.fogColor.GetColor().getGreen() / 255.0f);
        event.setBlue(this.fogColor.GetColor().getBlue() / 255.0f);
    }
    
    @SubscribeEvent
    public void onFogDensity(final EntityViewRenderEvent.FogDensity event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        event.setDensity(this.density.GetSlider());
        if (this.mc.player.isInWater() || this.mc.player.isInLava()) {
            event.setCanceled(true);
        }
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !this.noEffects.GetSwitch() || !(event.getPacket() instanceof SPacketEffect)) {
            return;
        }
        event.setCancelled(true);
    }
}
