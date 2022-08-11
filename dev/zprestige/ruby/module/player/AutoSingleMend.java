//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.network.*;
import java.util.stream.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class AutoSingleMend extends Module
{
    public final Slider threshold;
    public final Slider actionDelay;
    public final Parent exp;
    public final Switch packetExp;
    public final Switch rotateDown;
    public final Slider packetSpeed;
    public Timer timer;
    public boolean turnedOffAutoArmor;
    
    public AutoSingleMend() {
        this.threshold = this.Menu.Slider("Threshold", 1, 100);
        this.actionDelay = this.Menu.Slider("Action Delay", 0, 1000);
        this.exp = this.Menu.Parent("Exp");
        this.packetExp = this.Menu.Switch("Packet Exp").parent(this.exp);
        this.rotateDown = this.Menu.Switch("Rotate Down").parent(this.exp);
        this.packetSpeed = this.Menu.Slider("Packet Speed", 1, 10).parent(this.exp);
        this.timer = new Timer();
    }
    
    public static float getPercentage(final ItemStack stack) {
        final float durability = (float)(stack.getMaxDamage() - stack.getItemDamage());
        return durability / stack.getMaxDamage() * 100.0f;
    }
    
    public void onEnable() {
        if (AutoArmor.Instance.isEnabled()) {
            AutoArmor.Instance.disableModule();
            this.turnedOffAutoArmor = true;
        }
    }
    
    public void onDisable() {
        if (this.turnedOffAutoArmor && !AutoArmor.Instance.isEnabled()) {
            AutoArmor.Instance.enableModule();
            this.turnedOffAutoArmor = false;
        }
    }
    
    public void onTick() {
        final int mendableArmor = this.getMendableArmorInArmorSlots();
        if (this.timer.getTime((long)this.actionDelay.GetSlider()) && this.takeOff(mendableArmor)) {
            this.takeOff(mendableArmor);
            this.timer.setTime(0);
            return;
        }
        if (this.packetExp.GetSwitch()) {
            final float prevPitch = this.mc.player.rotationPitch;
            final int slot = InventoryUtil.getItemFromHotbar(Items.EXPERIENCE_BOTTLE);
            if (slot == -1) {
                this.disableModule("No exp found in hotbar, disabling AutoSingleMend.");
                return;
            }
            if (this.rotateDown.GetSwitch()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.mc.player.rotationYaw, 90.0f, this.mc.player.onGround));
            }
            this.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            IntStream.range(0, (int)this.packetSpeed.GetSlider()).forEach(i -> this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND)));
            this.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.mc.player.inventory.currentItem));
            if (this.rotateDown.GetSwitch()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.mc.player.rotationYaw, prevPitch, this.mc.player.onGround));
            }
        }
    }
    
    public void quickMovePiece(final int i) {
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
    }
    
    public boolean takeOff(final int i) {
        switch (i) {
            case 5: {
                if (!this.mc.player.inventory.getStackInSlot(38).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(6);
                    return true;
                }
                if (!this.mc.player.inventory.getStackInSlot(37).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(7);
                    return true;
                }
                if (!this.mc.player.inventory.getStackInSlot(36).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(8);
                    return true;
                }
                break;
            }
            case 6: {
                if (!this.mc.player.inventory.getStackInSlot(39).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(5);
                    return true;
                }
                if (!this.mc.player.inventory.getStackInSlot(37).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(7);
                    return true;
                }
                if (!this.mc.player.inventory.getStackInSlot(36).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(8);
                    return true;
                }
                break;
            }
            case 7: {
                if (!this.mc.player.inventory.getStackInSlot(39).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(5);
                    return true;
                }
                if (!this.mc.player.inventory.getStackInSlot(38).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(6);
                    return true;
                }
                if (!this.mc.player.inventory.getStackInSlot(36).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(8);
                    return true;
                }
                break;
            }
            case 8: {
                if (!this.mc.player.inventory.getStackInSlot(39).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(5);
                    return true;
                }
                if (!this.mc.player.inventory.getStackInSlot(38).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(6);
                    return true;
                }
                if (!this.mc.player.inventory.getStackInSlot(37).getItem().equals(Items.AIR)) {
                    this.quickMovePiece(7);
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    public int getMendableArmorInArmorSlots() {
        final ItemStack head = this.mc.player.inventory.getStackInSlot(39);
        final ItemStack chest = this.mc.player.inventory.getStackInSlot(38);
        final ItemStack leggings = this.mc.player.inventory.getStackInSlot(37);
        final ItemStack feet = this.mc.player.inventory.getStackInSlot(36);
        if (!head.getItem().equals(Items.AIR) && getPercentage(head) < this.threshold.GetSlider()) {
            return 5;
        }
        if (!chest.getItem().equals(Items.AIR) && getPercentage(chest) < this.threshold.GetSlider()) {
            return 6;
        }
        if (!leggings.getItem().equals(Items.AIR) && getPercentage(leggings) < this.threshold.GetSlider()) {
            return 7;
        }
        if (!feet.getItem().equals(Items.AIR) && getPercentage(feet) < this.threshold.GetSlider()) {
            return 8;
        }
        return -1;
    }
}
