//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraftforge.fml.common.gameevent.*;
import dev.zprestige.ruby.*;
import org.lwjgl.input.*;
import com.mojang.realmsclient.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import dev.zprestige.ruby.module.visual.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import java.util.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;

public class Speed extends Module
{
    public static Speed Instance;
    public final Parent modes;
    public final ComboBox mode;
    public final Key switchKey;
    public final Switch announceSwitch;
    public final Switch switchPullToGround;
    public final ComboBox speedMode;
    public final Parent misc;
    public final Switch returnOnShift;
    public final Switch slowdownOnGroundNearHoles;
    public final Slider slowDownValue;
    public final Switch liquids;
    public final Switch useTimer;
    public final Slider timerAmount;
    public final Parent factoring;
    public final ComboBox strafeFactorMode;
    public final ComboBox reFactorizeMode;
    public final Slider reFactorizeStartDelay;
    public final Slider reFactorizeStart;
    public final Slider reFactorizeTarget;
    public final Slider accelerationFactor;
    public final Slider strafeFactor;
    public float f;
    public final Slider strafeFactorSpeed;
    public double previousDistance;
    public double motionSpeed;
    public int currentState;
    public Timer factorTimer;
    public Timer postSwitchTimer;
    public boolean isCloseToHole;
    public boolean isTimering;
    
    public Speed() {
        this.modes = this.Menu.Parent("Modes");
        this.mode = this.Menu.ComboBox("Mode", new String[] { "Normal", "Switch" }).parent(this.modes);
        this.switchKey = this.Menu.Key("Switch Key", 0).parent(this.modes);
        this.announceSwitch = this.Menu.Switch("Announce Switch").parent(this.modes);
        this.switchPullToGround = this.Menu.Switch("Switch Pull To Ground").parent(this.modes);
        this.speedMode = this.Menu.ComboBox("Speed Mode", new String[] { "OnGround", "Strafe" }).parent(this.modes);
        this.misc = this.Menu.Parent("Misc");
        this.returnOnShift = this.Menu.Switch("Return On Shift").parent(this.misc);
        this.slowdownOnGroundNearHoles = this.Menu.Switch("Slow Down On Ground Near Holes").parent(this.misc);
        this.slowDownValue = this.Menu.Slider("Slow Down Value", 0.1f, 5.0f).parent(this.misc);
        this.liquids = this.Menu.Switch("Liquids").parent(this.misc);
        this.useTimer = this.Menu.Switch("Use Timer").parent(this.misc);
        this.timerAmount = this.Menu.Slider("Timer Amount", 0.9f, 2.0f).parent(this.misc);
        this.factoring = this.Menu.Parent("Factoring");
        this.strafeFactorMode = this.Menu.ComboBox("Strafe Factor Mode", new String[] { "Manual", "Auto" }).parent(this.factoring);
        this.reFactorizeMode = this.Menu.ComboBox("Re-Factorize Mode", new String[] { "Accelerating", "Instant" }).parent(this.factoring);
        this.reFactorizeStartDelay = this.Menu.Slider("Re-Factorize Start Delay", 10, 500).parent(this.factoring);
        this.reFactorizeStart = this.Menu.Slider("Re-Factorize Start Value", 0.1f, 1.0f).parent(this.factoring);
        this.reFactorizeTarget = this.Menu.Slider("Re-Factorize Target", 0.1f, 3.0f).parent(this.factoring);
        this.accelerationFactor = this.Menu.Slider("Acceleration Factor", 0.1f, 5.0f).parent(this.factoring);
        this.strafeFactor = this.Menu.Slider("Strafe Factor", 0.1f, 3.0f).parent(this.factoring);
        this.f = this.strafeFactor.GetSlider();
        this.strafeFactorSpeed = this.Menu.Slider("Strafe Factor Speed Amplifier", 0.1f, 3.0f).parent(this.factoring);
        this.currentState = 1;
        this.factorTimer = new Timer();
        this.postSwitchTimer = new Timer();
        Speed.Instance = this;
    }
    
    public void onDisable() {
        this.mc.timer.tickLength = 50.0f;
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Ruby.mc.player == null || Ruby.mc.world == null || !this.mode.GetCombo().equals("Switch") || this.mc.currentScreen != null || !this.isEnabled()) {
            return;
        }
        if (Keyboard.getEventKeyState() && this.switchKey.GetKey() != -1 && this.switchKey.GetKey() == Keyboard.getEventKey()) {
            final String getCombo = this.speedMode.GetCombo();
            switch (getCombo) {
                case "OnGround": {
                    this.speedMode.setValue("Strafe");
                    break;
                }
                case "Strafe": {
                    this.speedMode.setValue("OnGround");
                    if (this.switchPullToGround.GetSwitch()) {
                        this.postSwitchTimer.setTime(0);
                        break;
                    }
                    break;
                }
            }
            if (this.announceSwitch.GetSwitch()) {
                Ruby.chatManager.sendRemovableMessage(ChatFormatting.BOLD + "Speed " + ChatFormatting.RESET + "switched mode to " + ChatFormatting.RED + this.speedMode.GetCombo() + ChatFormatting.RESET + ".", 1);
            }
        }
    }
    
    public void onTick() {
        this.previousDistance = Math.sqrt((this.mc.player.posX - this.mc.player.prevPosX) * (this.mc.player.posX - this.mc.player.prevPosX) + (this.mc.player.posZ - this.mc.player.prevPosZ) * (this.mc.player.posZ - this.mc.player.prevPosZ));
        if (!this.strafeFactorMode.GetCombo().equals("Manual") && this.f < this.reFactorizeTarget.getMin()) {
            final String getCombo = this.reFactorizeMode.GetCombo();
            switch (getCombo) {
                case "Instant": {
                    if (this.factorTimer.getTime((long)this.reFactorizeStartDelay.GetSlider())) {
                        this.f = this.reFactorizeTarget.GetSlider();
                        break;
                    }
                    break;
                }
                case "Accelerating": {
                    if (this.factorTimer.getTime((long)this.reFactorizeStartDelay.GetSlider())) {
                        this.f += this.accelerationFactor.GetSlider() / 10.0f;
                        break;
                    }
                    break;
                }
            }
        }
        if (this.switchPullToGround.GetSwitch() && this.postSwitchTimer.getTimeSub(20L)) {
            this.mc.player.motionY = -1.0;
        }
        if (this.slowdownOnGroundNearHoles.GetSwitch()) {
            this.isCloseToHole = false;
            if (ESP.Instance.bedrockHoles != null) {
                for (final BlockPos pos : ESP.Instance.bedrockHoles) {
                    if (this.mc.player.getDistanceSq(pos) < 1.5) {
                        this.isCloseToHole = true;
                    }
                }
            }
            if (ESP.Instance.obsidianHoles != null) {
                for (final BlockPos pos : ESP.Instance.obsidianHoles) {
                    if (this.mc.player.getDistanceSq(pos) < 1.5) {
                        this.isCloseToHole = true;
                    }
                }
            }
        }
    }
    
    @RegisterListener
    public void onMove(final MoveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || (!this.liquids.GetSwitch() && (this.mc.player.isInWater() || this.mc.player.isInLava() || this.mc.player.isSpectator())) || (this.switchPullToGround.GetSwitch() && this.postSwitchTimer.getTimeSub(20L)) || this.mc.player.isElytraFlying()) {
            return;
        }
        if (this.returnOnShift.GetSwitch() && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            return;
        }
        if (!this.mc.player.isSprinting()) {
            this.mc.player.setSprinting(true);
        }
        if (!dev.zprestige.ruby.module.exploit.Timer.Instance.isEnabled() && !TickShift.Instance.isEnabled() && this.timerAmount.GetSlider() != 1.0f) {
            if (this.useTimer.GetSwitch() && (Ruby.mc.player.moveForward != 0.0 || Ruby.mc.player.moveStrafing != 0.0)) {
                this.mc.timer.tickLength = 50.0f / this.timerAmount.GetSlider();
                this.isTimering = true;
            }
            else if (this.isTimering) {
                this.mc.timer.tickLength = 50.0f;
                this.isTimering = false;
            }
        }
        final PotionEffect speed = this.mc.player.getActivePotionEffect(MobEffects.SPEED);
        final String getCombo = this.speedMode.GetCombo();
        switch (getCombo) {
            case "Strafe": {
                switch (this.currentState) {
                    case 0: {
                        ++this.currentState;
                        this.previousDistance = 0.0;
                        break;
                    }
                    default: {
                        if ((this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, this.mc.player.motionY, 0.0)).size() > 0 || this.mc.player.collidedVertically) && this.currentState > 0) {
                            this.currentState = ((this.mc.player.moveForward != 0.0f || this.mc.player.moveStrafing != 0.0f) ? 1 : 0);
                        }
                        this.motionSpeed = this.previousDistance - this.previousDistance / 159.0;
                        break;
                    }
                    case 2: {
                        double var2 = 0.40123128;
                        if ((this.mc.player.moveForward != 0.0f || this.mc.player.moveStrafing != 0.0f) && this.mc.player.onGround) {
                            if (this.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                                var2 += (Objects.requireNonNull(this.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST)).getAmplifier() + 1) * 0.1f;
                            }
                            final EntityPlayerSP player = this.mc.player;
                            final double n2 = var2;
                            player.motionY = n2;
                            event.motionY = n2;
                            this.motionSpeed *= 2.149;
                            break;
                        }
                        break;
                    }
                    case 3: {
                        this.motionSpeed = this.previousDistance - 0.76 * (this.previousDistance - EntityUtil.getBaseMotionSpeed() * (this.strafeFactorMode.GetCombo().equals("Auto") ? this.f : ((speed != null) ? this.strafeFactorSpeed.GetSlider() : this.strafeFactor.GetSlider())));
                        break;
                    }
                }
                this.motionSpeed = Math.max(this.motionSpeed, EntityUtil.getBaseMotionSpeed() * (this.strafeFactorMode.GetCombo().equals("Auto") ? this.f : ((speed != null) ? this.strafeFactorSpeed.GetSlider() : this.strafeFactor.GetSlider())));
                double var3 = this.mc.player.movementInput.moveForward;
                double var4 = this.mc.player.movementInput.moveStrafe;
                final double var5 = this.mc.player.rotationYaw;
                if (var3 != 0.0 && var4 != 0.0) {
                    var3 *= Math.sin(0.7853981633974483);
                    var4 *= Math.cos(0.7853981633974483);
                }
                event.motionX = (var3 * this.motionSpeed * -Math.sin(Math.toRadians(var5)) + var4 * this.motionSpeed * Math.cos(Math.toRadians(var5))) * 0.99;
                event.motionZ = (var3 * this.motionSpeed * Math.cos(Math.toRadians(var5)) - var4 * this.motionSpeed * -Math.sin(Math.toRadians(var5))) * 0.99;
                ++this.currentState;
                break;
            }
            case "OnGround": {
                if ((this.mc.player.isSneaking() || (this.mc.player.movementInput.moveForward == 0.0f && this.mc.player.movementInput.moveStrafe == 0.0f)) && this.mc.player.onGround) {
                    break;
                }
                final MovementInput movementInput = this.mc.player.movementInput;
                final float moveForward = movementInput.moveForward;
                float moveStrafe = movementInput.moveStrafe;
                float rotationYaw = this.mc.player.rotationYaw;
                if (moveForward == 0.0 && moveStrafe == 0.0) {
                    event.motionX = 0.0;
                    event.motionZ = 0.0;
                    break;
                }
                if (moveForward != 0.0) {
                    if (moveStrafe > 0.0) {
                        rotationYaw += ((moveForward > 0.0) ? -45 : 45);
                    }
                    else if (moveStrafe < 0.0) {
                        rotationYaw += ((moveForward > 0.0) ? 45 : -45);
                    }
                    moveStrafe = 0.0f;
                }
                moveStrafe = ((moveStrafe == 0.0f) ? moveStrafe : ((moveStrafe > 0.0) ? 1.0f : -1.0f));
                final double cos = Math.cos(Math.toRadians(rotationYaw + 90.0f));
                final double sin = Math.sin(Math.toRadians(rotationYaw + 90.0f));
                event.motionX = (moveForward * EntityUtil.getMaxSpeed() * cos + moveStrafe * EntityUtil.getMaxSpeed() * sin) / ((this.slowdownOnGroundNearHoles.GetSwitch() && this.isCloseToHole) ? this.slowDownValue.GetSlider() : 1.0);
                event.motionZ = (moveForward * EntityUtil.getMaxSpeed() * sin - moveStrafe * EntityUtil.getMaxSpeed() * cos) / ((this.slowdownOnGroundNearHoles.GetSwitch() && this.isCloseToHole) ? this.slowDownValue.GetSlider() : 1.0);
                break;
            }
        }
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || this.strafeFactorMode.GetCombo().equals("Manual") || !(event.getPacket() instanceof SPacketPlayerPosLook)) {
            return;
        }
        this.f = this.reFactorizeStart.GetSlider();
        this.factorTimer.setTime(0);
    }
}
