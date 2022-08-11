//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;

public class AutoWither extends Module
{
    public final Slider placeDelay;
    public final Slider placeRange;
    public final Switch packet;
    public final Switch rotate;
    public Timer timer;
    public Timer restartTimer;
    public trolleyBus trolleyPos;
    
    public AutoWither() {
        this.placeDelay = this.Menu.Slider("Place Delay", 0, 1000);
        this.placeRange = this.Menu.Slider("Place Range", 0.1f, 6.0f);
        this.packet = this.Menu.Switch("Packet");
        this.rotate = this.Menu.Switch("Rotate");
        this.timer = new Timer();
        this.restartTimer = new Timer();
        this.trolleyPos = null;
    }
    
    @Override
    public void onEnable() {
        this.timer.setTime(0);
        this.trolleyPos = null;
    }
    
    @Override
    public void onTick() {
        if (this.trolleyPos == null && this.restartTimer.getTime(1000L)) {
            final TreeMap<Double, trolleyBus> treeMap = new TreeMap<Double, trolleyBus>();
            for (final BlockPos pos1 : BlockUtil.getSphere(this.placeRange.GetSlider(), BlockUtil.AirType.IgnoreAir, (EntityPlayer)this.mc.player)) {
                if (this.mc.player.getDistanceSq(pos1) < 4.0) {
                    continue;
                }
                final trolleyBus canPlaceWither = this.canPlaceWither(pos1);
                if (canPlaceWither.i == -1) {
                    continue;
                }
                treeMap.put(this.mc.player.getDistanceSq(pos1) / 2.0, canPlaceWither);
            }
            if (!treeMap.isEmpty()) {
                this.trolleyPos = treeMap.firstEntry().getValue();
            }
        }
        else if (this.timer.getTime((long)this.placeDelay.GetSlider())) {
            if (this.trolleyPos != null && this.mc.player.getDistanceSq(this.trolleyPos.pos) > this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
                this.trolleyPos = null;
                this.restartTimer.setTime(0);
                return;
            }
            this.placeWither();
            this.timer.setTime(0);
        }
    }
    
    public void placeWither() {
        final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.SOUL_SAND));
        final int skull = InventoryUtil.getItemFromHotbar(Items.SKULL);
        if (slot == -1) {
            this.disableModule("No soul sand found, disabling AutoWither.");
            return;
        }
        if (skull == -1) {
            this.disableModule("No skulls found, disabling AutoWither.");
            return;
        }
        if (this.trolleyPos == null) {
            return;
        }
        if (this.mc.world.getBlockState(this.trolleyPos.pos.up()).getBlock().equals(Blocks.AIR)) {
            BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
            return;
        }
        if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up()).getBlock().equals(Blocks.AIR)) {
            BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
            return;
        }
        switch (this.trolleyPos.side) {
            case NorthSouth: {
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().north()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().north(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
                    return;
                }
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().south()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().south(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
                    return;
                }
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().up()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), skull);
                    return;
                }
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().up().north()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().up().north(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), skull);
                    return;
                }
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().up().south()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().up().south(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), skull);
                    this.trolleyPos = null;
                    this.restartTimer.setTime(0);
                    return;
                }
                break;
            }
            case EastWest: {
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().east()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().east(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
                    return;
                }
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().west()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().west(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
                    return;
                }
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().up()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), skull);
                    return;
                }
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().up().east()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().up().east(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), skull);
                    return;
                }
                if (this.mc.world.getBlockState(this.trolleyPos.pos.up().up().up().west()).getBlock().equals(Blocks.AIR)) {
                    BlockUtil.placeBlockWithSwitch(this.trolleyPos.pos.up().up().up().west(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), skull);
                    this.trolleyPos = null;
                    this.restartTimer.setTime(0);
                    return;
                }
                break;
            }
        }
    }
    
    public trolleyBus canPlaceWither(final BlockPos pos) {
        if (!this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up())).isEmpty()) {
            return new trolleyBus(-1, null, null);
        }
        if (!this.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR) || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up())).isEmpty()) {
            return new trolleyBus(-1, null, null);
        }
        Side currSide = null;
        if (this.mc.world.getBlockState(pos.up().up().north()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.up().up().south()).getBlock().equals(Blocks.AIR) && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().north())).isEmpty() && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().south())).isEmpty()) {
            currSide = Side.NorthSouth;
        }
        if (this.mc.world.getBlockState(pos.up().up().east()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.up().up().west()).getBlock().equals(Blocks.AIR) && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().east())).isEmpty() && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().west())).isEmpty()) {
            currSide = Side.EastWest;
        }
        if (currSide == null) {
            return new trolleyBus(-1, null, null);
        }
        switch (currSide) {
            case NorthSouth: {
                if (!this.mc.world.getBlockState(pos.up().north()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(pos.up().south()).getBlock().equals(Blocks.AIR)) {
                    return new trolleyBus(-1, null, null);
                }
                if (!this.mc.world.getBlockState(pos.up().up().up().north()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(pos.up().up().up().south()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(pos.up().up().up()).getBlock().equals(Blocks.AIR) || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().up())).isEmpty() || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().up().north())).isEmpty() || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().up().south())).isEmpty()) {
                    return new trolleyBus(-1, null, null);
                }
                break;
            }
            case EastWest: {
                if (!this.mc.world.getBlockState(pos.up().east()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(pos.up().west()).getBlock().equals(Blocks.AIR)) {
                    return new trolleyBus(-1, null, null);
                }
                if (!this.mc.world.getBlockState(pos.up().up().up().east()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(pos.up().up().up().west()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(pos.up().up().up()).getBlock().equals(Blocks.AIR) || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().up())).isEmpty() || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().up().east())).isEmpty() || !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().up().west())).isEmpty()) {
                    return new trolleyBus(-1, null, null);
                }
                break;
            }
        }
        return new trolleyBus(0, currSide, pos);
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        if (this.trolleyPos != null) {
            RenderUtil.drawBox(this.trolleyPos.pos, new Color(-1));
        }
    }
    
    public enum Side
    {
        NorthSouth, 
        EastWest;
    }
    
    public static class trolleyBus
    {
        int i;
        Side side;
        BlockPos pos;
        
        public trolleyBus(final int i, final Side side, final BlockPos pos) {
            this.i = i;
            this.side = side;
            this.pos = pos;
        }
    }
}
