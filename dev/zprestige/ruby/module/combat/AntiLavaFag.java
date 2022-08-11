//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import java.util.stream.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class AntiLavaFag extends Module
{
    public final Slider placeDelay;
    public final Slider targetRange;
    public final Slider placeRange;
    public final Switch packet;
    public final Switch rotate;
    public final Timer timer;
    public final Vec3i[] vec3is;
    public BlockPos targetPos;
    
    public AntiLavaFag() {
        this.placeDelay = this.Menu.Slider("Place Delay", 0, 500);
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f);
        this.placeRange = this.Menu.Slider("Place Range", 0.1f, 6.0f);
        this.packet = this.Menu.Switch("Packet");
        this.rotate = this.Menu.Switch("Rotate");
        this.timer = new Timer();
        this.vec3is = new Vec3i[] { new Vec3i(0, 0, -1), new Vec3i(0, 0, 1), new Vec3i(-1, 0, 0), new Vec3i(1, 0, 0) };
        this.targetPos = null;
    }
    
    @Override
    public void onEnable() {
        this.timer.setTime(0);
        this.targetPos = null;
        final BlockPos pos = BlockUtil.getPlayerPos();
        for (final Vec3i vec3i : this.vec3is) {
            final BlockPos pos2 = pos.add(vec3i);
            if (this.isPlaceable(pos2) && this.isValid(pos2.up()) && this.isValid(pos2.up().up()) && this.isValid(pos2.up().up().up()) && ((ArrayList)this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos2.up())).stream().filter(entity -> entity instanceof EntityPlayer).collect(Collectors.toCollection(ArrayList::new))).isEmpty()) {
                this.targetPos = pos2;
                return;
            }
        }
        if (this.targetPos == null) {
            this.disableModule("No valid placement(s) found, disabling Anti Lava Fag.");
        }
    }
    
    @Override
    public void onTick() {
        final int netherrackSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.NETHERRACK));
        final int pickaxeSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
        final int obsidianSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        if (netherrackSlot == -1 || pickaxeSlot == -1 || obsidianSlot == -1) {
            this.disableModule("Not all materials found, disabling Anti Lava Fag.");
            return;
        }
        if (this.timer.getTime((long)this.placeDelay.GetSlider())) {
            final Stage stage = this.searchStage(this.targetPos);
            if (stage != null) {
                switch (stage) {
                    case PlaceFirstNetherrack: {
                        BlockUtil.placeBlockWithSwitch(this.targetPos.up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), netherrackSlot, this.timer);
                        break;
                    }
                    case PlaceSecondNetherrack: {
                        BlockUtil.placeBlockWithSwitch(this.targetPos.up().up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), netherrackSlot, this.timer);
                        break;
                    }
                    case PlaceObsidian: {
                        BlockUtil.placeBlockWithSwitch(this.targetPos.up().up().up(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), obsidianSlot, this.timer);
                        break;
                    }
                    case BreakFirstNetherrack: {
                        this.mc.playerController.onPlayerDamageBlock(this.targetPos.up().up(), this.mc.objectMouseOver.sideHit);
                        this.mc.player.swingArm(EnumHand.MAIN_HAND);
                        break;
                    }
                    case BreakSecondNetherrack: {
                        this.mc.playerController.onPlayerDamageBlock(this.targetPos.up(), this.mc.objectMouseOver.sideHit);
                        this.mc.player.swingArm(EnumHand.MAIN_HAND);
                        break;
                    }
                }
            }
        }
    }
    
    protected Stage searchStage(final BlockPos pos) {
        if (this.isValid(pos.up()) && !this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
            return Stage.PlaceFirstNetherrack;
        }
        if (this.isValid(pos.up().up()) && !this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
            return Stage.PlaceSecondNetherrack;
        }
        if (this.isValid(pos.up().up().up())) {
            return Stage.PlaceObsidian;
        }
        if (this.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.NETHERRACK)) {
            return Stage.BreakFirstNetherrack;
        }
        if (this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.NETHERRACK)) {
            return Stage.BreakSecondNetherrack;
        }
        return Stage.Crystal;
    }
    
    protected boolean isPlaceable(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK);
    }
    
    protected boolean isValid(final BlockPos pos) {
        return this.mc.player.getDistanceSq(pos) / 2.0 <= this.placeRange.GetSlider() && (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.LAVA) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_LAVA));
    }
    
    protected enum Stage
    {
        PlaceFirstNetherrack, 
        PlaceSecondNetherrack, 
        PlaceObsidian, 
        BreakFirstNetherrack, 
        BreakSecondNetherrack, 
        Crystal;
    }
}
