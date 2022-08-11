//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.util.*;

public class TickShift extends Module
{
    public static TickShift Instance;
    public final Slider timer;
    public final Slider disableTicks;
    public final ComboBox offGroundAction;
    public int ticks;
    
    public TickShift() {
        this.timer = this.Menu.Slider("Timer", 0.1f, 10.0f);
        this.disableTicks = this.Menu.Slider("Disable Ticks", 1, 100);
        this.offGroundAction = this.Menu.ComboBox("Off Ground Action", new String[] { "None", "Ignore", "Disable" });
        this.ticks = 0;
        TickShift.Instance = this;
    }
    
    public void onDisable() {
        this.mc.timer.tickLength = 50.0f;
        this.ticks = 0;
    }
    
    public void onTick() {
        ++this.ticks;
        if (this.ticks >= this.disableTicks.GetSlider()) {
            this.disableModule();
            return;
        }
        if (!this.mc.player.onGround) {
            final String getCombo = this.offGroundAction.GetCombo();
            switch (getCombo) {
                case "Ignore": {
                    return;
                }
                case "Disable": {
                    this.disableModule();
                    return;
                }
            }
        }
        if (EntityUtil.isMoving() && !this.mc.player.isSneaking() && !this.mc.player.isInLava() && !this.mc.player.isInWater() && !this.mc.player.isOnLadder()) {
            this.mc.timer.tickLength = 50.0f / this.timer.GetSlider();
        }
    }
}
