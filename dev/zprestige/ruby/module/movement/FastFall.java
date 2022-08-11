//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.module.exploit.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.init.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class FastFall extends Module
{
    public final ComboBox mode;
    public final Slider timerAmount;
    public final Switch preventHorizontalMotion;
    public final Slider height;
    public final Switch strict;
    public double prevTickY;
    public boolean isTimering;
    public boolean prevOnGround;
    
    public FastFall() {
        this.mode = this.Menu.ComboBox("Mode", new String[] { "Motion", "Timer" });
        this.timerAmount = this.Menu.Slider("Timer Amount", 0.1f, 10.0f);
        this.preventHorizontalMotion = this.Menu.Switch("Prevent Horizontal Motion");
        this.height = this.Menu.Slider("Height", 0.1f, 10.0f);
        this.strict = this.Menu.Switch("Strict");
    }
    
    public void onDisable() {
        this.mc.timer.tickLength = 50.0f;
    }
    
    public void onTick() {
        final String getCombo = this.mode.GetCombo();
        switch (getCombo) {
            case "Timer": {
                if (this.prevTickY < this.mc.player.posY || this.mc.gameSettings.keyBindJump.isKeyDown() || this.mc.player.isInWater() || this.mc.player.isInLava() || this.mc.player.isInWeb || this.mc.player.isElytraFlying()) {
                    this.prevOnGround = false;
                }
                if (this.prevTickY > this.mc.player.posY && this.prevOnGround) {
                    this.mc.timer.tickLength = 50.0f / this.timerAmount.GetSlider();
                    this.isTimering = true;
                }
                else if (this.isTimering && !TickShift.Instance.isEnabled() && !Timer.Instance.isEnabled()) {
                    this.mc.timer.tickLength = 50.0f;
                    this.isTimering = false;
                }
                this.prevTickY = this.mc.player.posY;
                if (this.mc.player.onGround) {
                    this.prevOnGround = true;
                    break;
                }
                break;
            }
            case "Motion": {
                if (!this.mc.player.onGround || this.mc.player.isOnLadder() || this.mc.player.isInWeb || this.mc.player.isInLava() || this.mc.player.isInWater() || this.mc.world.getBlockState(BlockUtil.getPlayerPos()).getBlock().equals(Blocks.WATER) || this.mc.player.noClip) {
                    return;
                }
                this.mc.player.motionY = (this.strict.GetSwitch() ? -1.0 : -5.0);
                break;
            }
        }
    }
    
    @RegisterListener
    public void onMove(final MoveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !this.mode.GetCombo().equals("Timer") || !this.preventHorizontalMotion.GetSwitch() || !this.prevOnGround || !this.isTimering) {
            return;
        }
        event.setMotion(0.0, event.motionY, 0.0);
    }
}
