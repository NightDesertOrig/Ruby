//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import java.math.*;
import net.minecraft.network.play.server.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import dev.zprestige.ruby.*;

public class LongJump extends Module
{
    public final Slider speed;
    public final Switch disableOnLag;
    public final Switch damageCheck;
    public final Slider minDamage;
    public final Switch renderInfo;
    public double prevDistance;
    public double moveSpeed;
    public int stage;
    public int ticks;
    public float prevTickDamage;
    public float damage;
    
    public LongJump() {
        this.speed = this.Menu.Slider("Speed", 0.1, 10.0);
        this.disableOnLag = this.Menu.Switch("Disable On Lag");
        this.damageCheck = this.Menu.Switch("Damage Check");
        this.minDamage = this.Menu.Slider("Min Damage", 0.1f, 36.0f);
        this.renderInfo = this.Menu.Switch("Render Info");
    }
    
    public static float roundNumber(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(places, RoundingMode.FLOOR);
        return decimal.floatValue();
    }
    
    public void onEnable() {
        this.stage = 0;
        this.ticks = 2;
        this.prevTickDamage = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
        this.damage = 0.0f;
    }
    
    public void onTick() {
        if (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() < this.prevTickDamage) {
            this.damage += this.prevTickDamage - (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount());
        }
        this.prevTickDamage = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof SPacketPlayerPosLook)) {
            return;
        }
        if (this.disableOnLag.GetSwitch()) {
            this.disableModule("Rubberband detected, disabling LongJump.");
        }
    }
    
    @RegisterListener
    public void onMotionUpdate(final MotionUpdateEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        final double xDist = this.mc.player.posX - this.mc.player.prevPosX;
        final double zDist = this.mc.player.posZ - this.mc.player.prevPosZ;
        this.prevDistance = Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    @RegisterListener
    public void onMove(final MoveEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (this.damageCheck.GetSwitch() && this.damage < this.minDamage.GetSlider()) {
            return;
        }
        if (this.mc.player.collidedHorizontally || (this.mc.player.moveForward == 0.0f && this.mc.player.moveStrafing == 0.0f)) {
            this.stage = 0;
            this.ticks = 2;
            event.setCancelled(true);
            event.motionX = 0.0;
            event.motionZ = 0.0;
        }
        else {
            if (this.ticks > 0) {
                this.moveSpeed = 0.09;
                --this.ticks;
            }
            else if (this.stage == 1 && this.mc.player.collidedVertically) {
                this.moveSpeed = 1.0 + EntityUtil.getDefaultSpeed() - 0.05;
            }
            else if (this.stage == 2 && this.mc.player.collidedVertically) {
                final EntityPlayerSP player = this.mc.player;
                final double n = 0.0;
                player.motionY = n;
                event.motionY = n;
                this.moveSpeed *= this.speed.GetSlider();
            }
            else if (this.stage == 3) {
                this.moveSpeed = this.prevDistance - 0.66 * (this.prevDistance - EntityUtil.getDefaultSpeed());
            }
            else {
                this.moveSpeed = this.prevDistance - this.prevDistance / 159.0;
            }
            event.setCancelled(true);
            EntityUtil.setMoveSpeed(event, this.moveSpeed * this.speed.GetSlider());
            if (!this.mc.player.collidedVertically && (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, this.mc.player.motionY, 0.0)).size() > 0 || this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, -0.4, 0.0)).size() > 0) && this.stage > 10) {
                if (this.stage >= 38) {
                    final EntityPlayerSP player2 = this.mc.player;
                    final double n2 = 0.0;
                    player2.motionY = n2;
                    event.motionY = n2;
                    this.stage = 0;
                    this.ticks = 5;
                }
                else {
                    final EntityPlayerSP player3 = this.mc.player;
                    final double n3 = 0.0;
                    player3.motionY = n3;
                    event.motionY = n3;
                }
            }
            if (this.ticks <= 0 && (this.mc.player.moveForward != 0.0f || this.mc.player.moveStrafing != 0.0f)) {
                ++this.stage;
            }
        }
    }
    
    public void onFrame2D() {
        if (!this.renderInfo.GetSwitch()) {
            return;
        }
        final int screenWidth = new ScaledResolution(this.mc).getScaledWidth();
        final int screenHeight = new ScaledResolution(this.mc).getScaledHeight();
        final String string = "LongJump: [Stage: " + this.stage + " | PrevDistance: " + roundNumber(this.prevDistance, 1) + " | Ticks: " + this.ticks + " | MoveSpeed: " + roundNumber(this.moveSpeed, 1) + " | DamageCheck: " + roundNumber(this.damage, 1) + " - " + (this.damage > this.minDamage.GetSlider()) + "]";
        Ruby.fontManager.drawStringWithShadow(string, screenWidth / 2.0f - Ruby.fontManager.getStringWidth(string) / 2.0f, (float)(screenHeight - 100), -1);
    }
}
