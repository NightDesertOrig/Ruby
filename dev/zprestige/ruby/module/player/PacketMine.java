//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import java.awt.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.init.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import net.minecraft.util.*;

public class PacketMine extends Module
{
    public static PacketMine Instance;
    public final Parent misc;
    public final Switch lmbBreak;
    public final Switch posFix;
    public final Switch preSwitch;
    public final Slider setNullRange;
    public final Parent rendering;
    public final ComboBox renderMode;
    public final ComboBox colorMode;
    public final Slider fadeBoxColorAlpha;
    public final Slider fadeOutlineColorAlpha;
    public final ColorSwitch box;
    public final ColorSwitch outline;
    public final Slider outlineWidth;
    public final Slider maxAlpha;
    public final Slider minAlpha;
    public Timer timer;
    public BlockPos currentPos;
    public float currState;
    public float boxRed;
    public float boxGreen;
    public float boxBlue;
    public float boxAlpha;
    public float outlineRed;
    public float outlineGreen;
    public float outlineBlue;
    public float outlineAlpha;
    
    public PacketMine() {
        this.misc = this.Menu.Parent("Misc");
        this.lmbBreak = this.Menu.Switch("LMB Break").parent(this.misc);
        this.posFix = this.Menu.Switch("Pos Fix").parent(this.misc);
        this.preSwitch = this.Menu.Switch("Pre Switch").parent(this.misc);
        this.setNullRange = this.Menu.Slider("Set Null Range", 0.1f, 20.0f).parent(this.misc);
        this.rendering = this.Menu.Parent("Rendering");
        this.renderMode = this.Menu.ComboBox("Render Mode", new String[] { "AlphaIncrease", "AlphaDecrease", "Shrink", "Grow", "ShrinkGrow", "HeightIncrease", "HeightDecrease", "ShrinkGrowHeightIncrease", "ShrinkGrowHeightDecrease" }).parent(this.rendering);
        this.colorMode = this.Menu.ComboBox("Color Mode", new String[] { "Static", "Fade" }).parent(this.rendering);
        this.fadeBoxColorAlpha = this.Menu.Slider("Fade Box Color Alpha", 0.0f, 255.0f).parent(this.rendering);
        this.fadeOutlineColorAlpha = this.Menu.Slider("Fade Outline Color Alpha", 0.0f, 255.0f).parent(this.rendering);
        this.box = this.Menu.ColorSwitch("Box").parent(this.rendering);
        this.outline = this.Menu.ColorSwitch("Outline").parent(this.rendering);
        this.outlineWidth = this.Menu.Slider("Outline Width", 0.1f, 5.0f).parent(this.rendering);
        this.maxAlpha = this.Menu.Slider("Max Alpha", 0.0f, 255.0f).parent(this.rendering);
        this.minAlpha = this.Menu.Slider("Min Alpha", 0.0f, 255.0f).parent(this.rendering);
        this.timer = new Timer();
        PacketMine.Instance = this;
    }
    
    public void onTick() {
        this.mc.playerController.blockHitDelay = 0;
        if (this.currentPos == null) {
            return;
        }
        if (this.currState < 1.0f) {
            if (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.OBSIDIAN)) {
                this.currState += 0.025f;
            }
            else if (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.ENDER_CHEST)) {
                this.currState += 0.05f;
            }
            else if (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.NETHERRACK)) {
                this.currState += 0.5f;
            }
        }
        final String getCombo = this.colorMode.GetCombo();
        switch (getCombo) {
            case "Static": {
                this.boxRed = this.box.GetColor().getRed() / 255.0f;
                this.boxBlue = this.box.GetColor().getBlue() / 255.0f;
                this.boxGreen = this.box.GetColor().getGreen() / 255.0f;
                this.boxAlpha = this.box.GetColor().getAlpha() / 255.0f;
                this.outlineRed = this.outline.GetColor().getRed() / 255.0f;
                this.outlineGreen = this.outline.GetColor().getGreen() / 255.0f;
                this.outlineBlue = this.outline.GetColor().getBlue() / 255.0f;
                this.outlineAlpha = this.outline.GetColor().getAlpha() / 255.0f;
                break;
            }
            case "Fade": {
                this.boxRed = 1.0f - this.currState + (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.OBSIDIAN) ? 0.025f : (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.NETHERRACK) ? 0.5f : 0.05f));
                this.boxGreen = this.currState - (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.OBSIDIAN) ? 0.025f : (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.NETHERRACK) ? 0.5f : 0.05f));
                this.boxBlue = 0.0f;
                this.boxAlpha = this.fadeBoxColorAlpha.GetSlider() / 255.0f;
                this.outlineRed = 1.0f - this.currState + (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.OBSIDIAN) ? 0.025f : (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.NETHERRACK) ? 0.5f : 0.05f));
                this.outlineGreen = this.currState - (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.OBSIDIAN) ? 0.025f : (this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.NETHERRACK) ? 0.5f : 0.05f));
                this.outlineBlue = 0.0f;
                this.outlineAlpha = this.fadeOutlineColorAlpha.GetSlider() / 255.0f;
                break;
            }
        }
        if (!this.mc.world.getBlockState(this.currentPos).equals(this.mc.world.getBlockState(this.currentPos)) || this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(this.currentPos).getBlock().equals(Blocks.BEDROCK)) {
            this.currentPos = null;
        }
        else if (this.mc.player.getDistanceSq(this.currentPos) > this.setNullRange.GetSlider() * this.setNullRange.GetSlider()) {
            this.currentPos = null;
        }
    }
    
    public void onFrame(final float partialTicks) {
        if (this.currentPos != null) {
            AxisAlignedBB bb = new AxisAlignedBB(this.currentPos);
            final String getCombo = this.renderMode.GetCombo();
            switch (getCombo) {
                case "AlphaIncrease": {
                    RenderUtil.drawFullBox(this.outline.GetSwitch(), this.box.GetSwitch(), new Color(this.boxRed, this.boxGreen, this.boxBlue, Math.min(this.currState, this.maxAlpha.GetSlider() / 255.0f)), new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, Math.min(this.currState, this.maxAlpha.GetSlider() / 255.0f)), this.outlineWidth.GetSlider(), this.currentPos);
                    break;
                }
                case "BreakIncrease": {
                    RenderUtil.drawFullBox(this.outline.GetSwitch(), this.box.GetSwitch(), new Color(this.boxRed, this.boxGreen, this.boxBlue, Math.max(1.0f - this.currState, this.minAlpha.GetSlider() / 255.0f)), new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, Math.max(1.0f - this.currState, this.minAlpha.GetSlider() / 255.0f)), this.outlineWidth.GetSlider(), this.currentPos);
                    break;
                }
                case "Shrink": {
                    bb = new AxisAlignedBB(this.currentPos).shrink((double)(this.currState / 2.0f));
                    if (this.box.GetSwitch()) {
                        RenderUtil.drawBBBox(bb, new Color(this.boxRed, this.boxGreen, this.boxBlue), (int)(this.boxAlpha * 255.0f));
                    }
                    if (this.outline.GetSwitch()) {
                        RenderUtil.drawBlockOutlineBB(bb, new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, this.outlineAlpha), this.outlineWidth.GetSlider());
                        break;
                    }
                    break;
                }
                case "Grow": {
                    bb = new AxisAlignedBB(this.currentPos).shrink(0.5 + this.currState / 2.0f);
                    if (this.box.GetSwitch()) {
                        RenderUtil.drawBBBox(bb, new Color(this.boxRed, this.boxGreen, this.boxBlue), (int)(this.boxAlpha * 255.0f));
                    }
                    if (this.outline.GetSwitch()) {
                        RenderUtil.drawBlockOutlineBB(bb, new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, this.outlineAlpha), this.outlineWidth.GetSlider());
                        break;
                    }
                    break;
                }
                case "ShrinkGrow": {
                    bb = new AxisAlignedBB(this.currentPos).shrink((double)this.currState);
                    if (this.box.GetSwitch()) {
                        RenderUtil.drawBBBox(bb, new Color(this.boxRed, this.boxGreen, this.boxBlue), (int)(this.boxAlpha * 255.0f));
                    }
                    if (this.outline.GetSwitch()) {
                        RenderUtil.drawBlockOutlineBB(bb, new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, this.outlineAlpha), this.outlineWidth.GetSlider());
                        break;
                    }
                    break;
                }
                case "HeightIncrease": {
                    if (this.box.GetSwitch()) {
                        RenderUtil.drawBBBoxWithHeight(bb, new Color(this.boxRed, this.boxGreen, this.boxBlue), (int)(this.boxAlpha * 255.0f), this.currState);
                    }
                    if (this.outline.GetSwitch()) {
                        RenderUtil.drawBlockOutlineBBWithHeight(bb, new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, this.outlineAlpha), this.outlineWidth.GetSlider(), this.currState);
                        break;
                    }
                    break;
                }
                case "HeightDecrease": {
                    if (this.box.GetSwitch()) {
                        RenderUtil.drawBBBoxWithHeight(bb, new Color(this.boxRed, this.boxGreen, this.boxBlue), (int)(this.boxAlpha * 255.0f), 1.0f - this.currState);
                    }
                    if (this.outline.GetSwitch()) {
                        RenderUtil.drawBlockOutlineBBWithHeight(bb, new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, this.outlineAlpha), this.outlineWidth.GetSlider(), 1.0f - this.currState);
                        break;
                    }
                    break;
                }
                case "ShrinkGrowHeightIncrease": {
                    bb = new AxisAlignedBB(this.currentPos).shrink((double)this.currState);
                    if (this.box.GetSwitch()) {
                        RenderUtil.drawBBBoxWithHeight(bb, new Color(this.boxRed, this.boxGreen, this.boxBlue), (int)(this.boxAlpha * 255.0f), this.currState);
                    }
                    if (this.outline.GetSwitch()) {
                        RenderUtil.drawBlockOutlineBBWithHeight(bb, new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, this.outlineAlpha), this.outlineWidth.GetSlider(), this.currState);
                        break;
                    }
                    break;
                }
                case "ShrinkGrowHeightDecrease": {
                    bb = new AxisAlignedBB(this.currentPos).shrink((double)this.currState);
                    if (this.box.GetSwitch()) {
                        RenderUtil.drawBBBoxWithHeight(bb, new Color(this.boxRed, this.boxGreen, this.boxBlue), (int)(this.boxAlpha * 255.0f), 1.0f - this.currState);
                    }
                    if (this.outline.GetSwitch()) {
                        RenderUtil.drawBlockOutlineBBWithHeight(bb, new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, this.outlineAlpha), this.outlineWidth.GetSlider(), 1.0f - this.currState);
                        break;
                    }
                    break;
                }
                case "Complete": {
                    if (this.box.GetSwitch()) {
                        RenderUtil.drawBBBoxWithHeight(bb, new Color(this.boxRed, this.boxGreen, this.boxBlue), (int)(this.boxAlpha * 255.0f), (this.currState > 0.25f) ? (this.currState - 0.25f) : 0.0f);
                    }
                    if (this.outline.GetSwitch()) {
                        RenderUtil.drawBlockOutlineBBWithHeight(bb, new Color(this.outlineRed, this.outlineGreen, this.outlineBlue, this.outlineAlpha), this.outlineWidth.GetSlider(), (this.currState > 0.25f) ? (this.currState - 0.25f) : 0.0f);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @RegisterListener
    public void onBlockEvent(final BlockInteractEvent.ClickBlock event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (this.mc.playerController.curBlockDamageMP > 0.1f) {
            this.mc.playerController.isHittingBlock = true;
        }
        if (this.posFix.GetSwitch()) {
            this.currentPos = event.pos;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
        if (this.lmbBreak.GetSwitch() && this.currState >= 1.0f && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE) && slot != -1 && this.currentPos != null && this.currentPos.equals((Object)event.pos)) {
            final int currentItem = this.mc.player.inventory.currentItem;
            InventoryUtil.switchToSlot(slot);
            Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.pos, event.facing));
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
        }
    }
    
    @RegisterListener
    public void onDamageBlock(final BlockInteractEvent.DamageBlock event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (!this.mc.world.getBlockState(event.pos).getBlock().equals(Blocks.OBSIDIAN) && !this.mc.world.getBlockState(event.pos).getBlock().equals(Blocks.ENDER_CHEST) && !this.mc.world.getBlockState(event.pos).getBlock().equals(Blocks.NETHERRACK)) {
            return;
        }
        this.mc.playerController.isHittingBlock = false;
        if (this.currentPos == null) {
            this.currentPos = event.pos;
            this.timer.setTime(0);
            this.currState = 0.0f;
        }
        final int currentItem = this.mc.player.inventory.currentItem;
        if (this.preSwitch.GetSwitch()) {
            final int slot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
            InventoryUtil.switchToSlot(slot);
        }
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.pos, event.facing));
        this.mc.player.swingArm(EnumHand.MAIN_HAND);
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.pos, event.facing));
        if (this.preSwitch.GetSwitch()) {
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
        }
        event.setCancelled(true);
    }
}
