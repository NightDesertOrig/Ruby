//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.init.*;
import java.util.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.client.entity.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class LiquidSpeed extends Module
{
    public final ComboBox mode;
    public final Slider horizontalSpeed;
    public final Slider downFactor;
    public final Slider upFactor;
    public final ComboBox downMode;
    public final Switch boost;
    public final Switch onlyBoostOnHoldSprint;
    public final Slider boostReduction;
    public HashMap<Long, Double> damagePerSecond;
    public double lastTickHealth;
    
    public LiquidSpeed() {
        this.mode = this.Menu.ComboBox("Mode", new String[] { "Vanilla", "Factor", "Teleport" });
        this.horizontalSpeed = this.Menu.Slider("Horizontal Factor", 0.1f, 10.0f);
        this.downFactor = this.Menu.Slider("Down Factor", 0.1f, 10.0f);
        this.upFactor = this.Menu.Slider("Up Factor", 0.1f, 10.0f);
        this.downMode = this.Menu.ComboBox("Down Mode", new String[] { "None", "NoDownForce", "NCP-Bypass" });
        this.boost = this.Menu.Switch("Boost");
        this.onlyBoostOnHoldSprint = this.Menu.Switch("Only Boost On Hold Sprint");
        this.boostReduction = this.Menu.Slider("Boost Reduction", 0.1f, 10.0f);
        this.damagePerSecond = new HashMap<Long, Double>();
        this.lastTickHealth = 0.0;
    }
    
    public void onTick() {
        if ((!this.mc.player.isInLava() && !this.mc.player.isInWater()) || this.mc.world.getBlockState(BlockUtil.getPlayerPos()).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(BlockUtil.getPlayerPos().up()).getBlock().equals(Blocks.AIR)) {
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final Double n;
        new HashMap(this.damagePerSecond).entrySet().stream().filter(entry -> entry.getKey() < currentTimeMillis).forEach(entry -> n = this.damagePerSecond.remove(entry.getKey()));
        final double boostAmount = this.damagePerSecond.values().stream().mapToDouble(aDouble -> aDouble).sum();
        final double[] motion = EntityUtil.getSpeed(this.horizontalSpeed.GetSlider() / 10.0f);
        final String getCombo = this.mode.GetCombo();
        switch (getCombo) {
            case "Vanilla": {
                final double value = this.horizontalSpeed.GetSlider() + ((this.boost.GetSwitch() && (!this.onlyBoostOnHoldSprint.GetSwitch() || this.mc.gameSettings.keyBindSprint.isKeyDown())) ? (boostAmount / (this.boostReduction.GetSlider() * 10.0f)) : 0.0);
                final EntityPlayerSP player = this.mc.player;
                player.motionX *= value;
                final EntityPlayerSP player2 = this.mc.player;
                player2.motionZ *= value;
                break;
            }
            case "Factor": {
                this.mc.player.motionX = motion[0];
                this.mc.player.motionZ = motion[1];
                break;
            }
            case "Teleport": {
                this.mc.player.setPosition(this.mc.player.posX + motion[0], this.mc.player.posY, this.mc.player.posZ + motion[1]);
                break;
            }
        }
        if (this.mode.GetCombo().equals("Teleport")) {
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + this.upFactor.GetSlider() / 10.0f, this.mc.player.posZ);
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY - this.downFactor.GetSlider() / 10.0f, this.mc.player.posZ);
            }
        }
        else {
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player3 = this.mc.player;
                player3.motionY += this.upFactor.GetSlider() / 100.0f;
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player4 = this.mc.player;
                player4.motionY -= this.downFactor.GetSlider() / 100.0f;
            }
        }
        if (!this.mc.gameSettings.keyBindJump.isKeyDown() && !this.mc.gameSettings.keyBindSneak.isKeyDown()) {
            final String getCombo2 = this.downMode.GetCombo();
            switch (getCombo2) {
                case "NoDownForce": {
                    this.mc.player.motionY = 0.0;
                    break;
                }
                case "NCP-Bypass": {
                    this.mc.player.motionY = -0.005;
                    break;
                }
            }
        }
        final double health = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
        final double damage = this.lastTickHealth - health;
        if (health < this.lastTickHealth) {
            this.damagePerSecond.put(currentTimeMillis + 1000L, damage);
        }
        this.lastTickHealth = health;
    }
    
    @RegisterListener
    public void onMove(final MoveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || (!this.mc.player.isInLava() && !this.mc.player.isInWater()) || this.mc.world.getBlockState(BlockUtil.getPlayerPos()).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(BlockUtil.getPlayerPos().up()).getBlock().equals(Blocks.AIR)) {
            return;
        }
        event.setCancelled(true);
    }
}
