//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.util.*;

public class CCPhaseBuilder extends Module
{
    protected final Timer timer;
    
    public CCPhaseBuilder() {
        this.timer = new Timer();
    }
    
    @Override
    public void onTick() {
        if (this.timer.getTime(50L)) {
            for (final BlockPos pos : BlockUtil.getSphere(3.0, BlockUtil.AirType.None, (EntityPlayer)this.mc.player)) {
                final BlockPos pos2 = pos.up();
                final BlockPos pos3 = pos.up().up();
                final AxisAlignedBB bb = new AxisAlignedBB(pos);
                final AxisAlignedBB bb2 = new AxisAlignedBB(pos2);
                final AxisAlignedBB bb3 = new AxisAlignedBB(pos3);
                if (!this.isComplete(pos) && pos.getY() == 4 && (this.obsidian(pos) || this.bedrock(pos) || this.air(pos)) && (this.anvil(pos2) || this.air(pos2)) && (this.obsidian(pos3) || this.air(pos3)) && this.empty(bb) && this.empty(bb2) && this.empty(bb3)) {
                    this.build(pos);
                    this.timer.setTime(0);
                }
            }
        }
    }
    
    protected boolean isComplete(final BlockPos pos) {
        return !this.air(pos) && !this.air(pos.up()) && !this.air(pos.up().up());
    }
    
    protected boolean empty(final AxisAlignedBB bb) {
        return this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, bb).isEmpty();
    }
    
    protected boolean anvil(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.ANVIL);
    }
    
    protected boolean obsidian(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN);
    }
    
    protected boolean bedrock(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK);
    }
    
    protected boolean air(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
    }
    
    protected void build(final BlockPos pos) {
        final int obbySlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        final int anvilSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ANVIL));
        if (obbySlot == -1 || anvilSlot == -1) {
            return;
        }
        if (this.air(pos)) {
            BlockUtil.placeBlockWithSwitch(pos, EnumHand.MAIN_HAND, false, true, obbySlot);
            return;
        }
        final BlockPos pos2 = pos.up();
        if (this.air(pos2)) {
            BlockUtil.placeBlockWithSwitch(pos2, EnumHand.MAIN_HAND, false, true, anvilSlot);
            return;
        }
        final BlockPos pos3 = pos.up().up();
        if (this.air(pos3)) {
            BlockUtil.placeBlockWithSwitch(pos3, EnumHand.MAIN_HAND, false, true, obbySlot);
        }
    }
}
