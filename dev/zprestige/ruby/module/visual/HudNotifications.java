//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import dev.zprestige.ruby.util.*;
import java.util.*;

public class HudNotifications extends Module
{
    public final ColorBox backgroundColor;
    public final ColorBox surroundColor;
    public final Slider y;
    public final Slider removeSpeed;
    public HashMap<String, Integer> totemPopMap;
    public HashMap<String, Float> notificationMap;
    
    public HudNotifications() {
        this.backgroundColor = this.Menu.Color("Background Color");
        this.surroundColor = this.Menu.Color("Surround Color");
        this.y = this.Menu.Slider("Y", 0, 600);
        this.removeSpeed = this.Menu.Slider("Remove Speed", 0.1f, 3.0f);
        this.totemPopMap = new HashMap<String, Integer>();
        this.notificationMap = new HashMap<String, Float>();
    }
    
    @RegisterListener
    public void onTotemPop(final PlayerChangeEvent.TotemPop event) {
        if (this.nullCheck() || !this.isEnabled() || event.entityPlayer.equals((Object)this.mc.player)) {
            return;
        }
        int pops = 1;
        if (this.totemPopMap.containsKey(event.entityPlayer.getName())) {
            pops = this.totemPopMap.get(event.entityPlayer.getName());
            this.totemPopMap.put(event.entityPlayer.getName(), ++pops);
        }
        else {
            this.totemPopMap.put(event.entityPlayer.getName(), pops);
        }
        String string;
        if (pops == 1) {
            string = event.entityPlayer.getName() + " has popped 1 totem";
        }
        else {
            string = event.entityPlayer.getName() + " has popped " + pops + " totems";
        }
        this.notificationMap.put(string, Ruby.fontManager.getStringWidth(string) / 2.0f);
    }
    
    @RegisterListener
    public void onDeath(final PlayerChangeEvent.Death event) {
        if (this.nullCheck() || !this.isEnabled() || event.entityPlayer.equals((Object)this.mc.player)) {
            return;
        }
        if (this.totemPopMap.containsKey(event.entityPlayer.getName())) {
            final int pops = this.totemPopMap.get(event.entityPlayer.getName());
            this.totemPopMap.remove(event.entityPlayer.getName());
            String string;
            if (pops == 1) {
                string = event.entityPlayer.getName() + " has died after popping 1 totem";
            }
            else {
                string = event.entityPlayer.getName() + " has died after popping " + pops + " totems";
            }
            this.notificationMap.put(string, Ruby.fontManager.getStringWidth(string) / 2.0f);
        }
    }
    
    public void onFrame2D() {
        final int screenWidth = new ScaledResolution(this.mc).getScaledWidth();
        int i = (int)(this.y.GetSlider() - 17.0f);
        if (!Display.isActive() || !Display.isVisible()) {
            this.notificationMap.clear();
            return;
        }
        final HashMap<String, Float> notificationMap1 = new HashMap<String, Float>(this.notificationMap);
        for (final Map.Entry<String, Float> entry : notificationMap1.entrySet()) {
            notificationMap1.put(entry.getKey(), entry.getValue() - ((entry.getValue() > 1.0f) ? (entry.getValue() / this.removeSpeed.GetSlider()) : this.removeSpeed.GetSlider()));
            if (entry.getValue() > 1.0f) {
                final float x = screenWidth / 2.0f - Ruby.fontManager.getStringWidth((String)entry.getKey()) / 2.0f - 3.0f;
                i += 17;
                RenderUtil.drawRect(x, (float)i, screenWidth / 2.0f - Ruby.fontManager.getStringWidth((String)entry.getKey()) / 2.0f - 3.0f + entry.getValue(), (float)(i + 16), this.surroundColor.GetColor().getRGB());
                RenderUtil.drawRect(screenWidth / 2.0f + Ruby.fontManager.getStringWidth((String)entry.getKey()) / 2.0f - entry.getValue(), (float)i, screenWidth / 2.0f + Ruby.fontManager.getStringWidth((String)entry.getKey()) / 2.0f + 3.0f, (float)(i + 16), this.surroundColor.GetColor().getRGB());
                RenderUtil.drawRect(screenWidth / 2.0f - Ruby.fontManager.getStringWidth((String)entry.getKey()) / 2.0f - 2.0f, (float)(i + 1), screenWidth / 2.0f + Ruby.fontManager.getStringWidth((String)entry.getKey()) / 2.0f + 2.0f, (float)(i + 15), this.backgroundColor.GetColor().getRGB());
                Ruby.fontManager.drawStringWithShadow((String)entry.getKey(), screenWidth / 2.0f - Ruby.fontManager.getStringWidth((String)entry.getKey()) / 2.0f, i + 7.5f - Ruby.fontManager.getFontHeight() / 2.0f, -1);
            }
            else {
                if (entry.getValue() <= -16.0) {
                    this.notificationMap.remove(entry.getKey());
                    return;
                }
                i += (int)(16.0f + entry.getValue());
            }
        }
        this.notificationMap = notificationMap1;
    }
}
