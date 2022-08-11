//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class Step extends Module
{
    public static Step Instance;
    public final ComboBox mode;
    public final Slider stepHeight;
    public final Switch autoMarkConveyerOnStep;
    
    public Step() {
        this.mode = this.Menu.ComboBox("Mode", new String[] { "Vanilla", "Ncp" });
        this.stepHeight = this.Menu.Slider("Height", 0.1f, 4.0f);
        this.autoMarkConveyerOnStep = this.Menu.Switch("Auto Mark Conveyor On Step");
        Step.Instance = this;
    }
    
    public void onDisable() {
        this.mc.player.stepHeight = 0.6f;
    }
    
    @RegisterListener
    public void onMove(final MoveEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (this.autoMarkConveyerOnStep.GetSwitch() && this.mc.player.collidedHorizontally) {
            TickShift.Instance.enableModule();
        }
        final String getCombo = this.mode.GetCombo();
        switch (getCombo) {
            case "Vanilla": {
                this.mc.player.stepHeight = this.stepHeight.GetSlider();
                break;
            }
            case "Ncp": {
                final double[] forward = EntityUtil.getSpeed(0.1);
                boolean stage1 = false;
                boolean stage2 = false;
                boolean stage3 = false;
                boolean stage4 = false;
                if (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(forward[0], 2.6, forward[1])).isEmpty() && !this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(forward[0], 2.4, forward[1])).isEmpty()) {
                    stage1 = true;
                }
                if (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(forward[0], 2.1, forward[1])).isEmpty() && !this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(forward[0], 1.9, forward[1])).isEmpty()) {
                    stage2 = true;
                }
                if (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(forward[0], 1.6, forward[1])).isEmpty() && !this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(forward[0], 1.4, forward[1])).isEmpty()) {
                    stage3 = true;
                }
                if (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(forward[0], 1.0, forward[1])).isEmpty() && !this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(forward[0], 0.6, forward[1])).isEmpty()) {
                    stage4 = true;
                }
                if (!this.mc.player.collidedHorizontally || (this.mc.player.moveForward == 0.0f && this.mc.player.moveStrafing == 0.0f) || !this.mc.player.onGround) {
                    break;
                }
                if (stage4 && this.stepHeight.GetSlider() >= 1.0) {
                    final double[] array = { 0.42, 0.753 };
                    for (int length = array.length, i = 0; i < length; ++i) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + array[i], this.mc.player.posZ, this.mc.player.onGround));
                    }
                    this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 1.0, this.mc.player.posZ);
                }
                if (stage3 && this.stepHeight.GetSlider() >= 1.5) {
                    final double[] array5;
                    final double[] array2 = array5 = new double[] { 0.42, 0.75, 1.0, 1.16, 1.23, 1.2 };
                    for (final double v : array5) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + v, this.mc.player.posZ, this.mc.player.onGround));
                    }
                    this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 1.5, this.mc.player.posZ);
                }
                if (stage2 && this.stepHeight.GetSlider() >= 2.0) {
                    final double[] array6;
                    final double[] array3 = array6 = new double[] { 0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43 };
                    for (final double v : array6) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + v, this.mc.player.posZ, this.mc.player.onGround));
                    }
                    this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 2.0, this.mc.player.posZ);
                }
                if (stage1 && this.stepHeight.GetSlider() >= 2.5) {
                    final double[] array7;
                    final double[] array4 = array7 = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 };
                    for (final double v : array7) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + v, this.mc.player.posZ, this.mc.player.onGround));
                    }
                    this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 2.5, this.mc.player.posZ);
                    break;
                }
                break;
            }
        }
    }
}
