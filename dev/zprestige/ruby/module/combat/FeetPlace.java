//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.function.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import java.util.*;
import net.minecraft.entity.*;
import java.util.stream.*;

public class FeetPlace extends Module
{
    public static FeetPlace Instance;
    public final Parent placing;
    public final ComboBox placeMode;
    public final ComboBox block;
    public final Slider placeDelay;
    public final Parent misc;
    public final Switch inLiquids;
    public final Switch packet;
    public final Switch rotate;
    public final Switch onMoveCancel;
    public final Switch support;
    public final Switch reCalcOnMove;
    public final Switch smartExtend;
    public final Slider smartExtendSize;
    public final Switch hitboxCheck;
    public final Switch retry;
    public final Slider retries;
    public ArrayList<BlockPos> blockPosList;
    public ArrayList<BlockPos> upperPosList;
    public ArrayList<BlockPos> bottomPosList;
    public ArrayList<BlockPos> extendBlocks;
    public Timer placeTimer;
    public BlockPos startPos;
    public BlockPos supportPos;
    
    public FeetPlace() {
        this.placing = this.Menu.Parent("Placing");
        this.placeMode = this.Menu.ComboBox("Place Mode", new String[] { "Instant", "Gradually", "Linear", "Obscure" }).parent(this.placing);
        this.block = this.Menu.ComboBox("Blocks", new String[] { "Obsidian", "EnderChests", "Fallback" }).parent(this.placing);
        this.placeDelay = this.Menu.Slider("Place Delay", 0, 500).parent(this.placing);
        this.misc = this.Menu.Parent("Misc");
        this.inLiquids = this.Menu.Switch("In Liquids").parent(this.misc);
        this.packet = this.Menu.Switch("Packet").parent(this.misc);
        this.rotate = this.Menu.Switch("Rotate").parent(this.misc);
        this.onMoveCancel = this.Menu.Switch("On Move Cancel").parent(this.misc);
        this.support = this.Menu.Switch("Support").parent(this.misc);
        this.reCalcOnMove = this.Menu.Switch("Re-Calc On Move").parent(this.misc);
        this.smartExtend = this.Menu.Switch("Smart Extend").parent(this.misc);
        this.smartExtendSize = this.Menu.Slider("Smart Extend Size", 0.0f, 10.0f).parent(this.misc);
        this.hitboxCheck = this.Menu.Switch("Hitbox Check").parent(this.misc);
        this.retry = this.Menu.Switch("Retry").parent(this.misc);
        this.retries = this.Menu.Slider("Retries", 1, 10).parent(this.misc);
        this.blockPosList = new ArrayList<BlockPos>();
        this.upperPosList = new ArrayList<BlockPos>();
        this.bottomPosList = new ArrayList<BlockPos>();
        this.extendBlocks = new ArrayList<BlockPos>();
        this.placeTimer = new Timer();
        this.startPos = null;
        this.supportPos = null;
        FeetPlace.Instance = this;
    }
    
    @Override
    public void onEnable() {
        this.startPos = BlockUtil.getPlayerPos();
        this.setup();
    }
    
    public void setup() {
        final BlockPos pos = BlockUtil.getPlayerPos();
        this.addPossesMainList(pos.down(), pos.down().north(), pos.down().east(), pos.down().south(), pos.down().west(), pos.north(), pos.east(), pos.south(), pos.west());
        this.addPossesUpperList(pos.north(), pos.east(), pos.south(), pos.west());
        this.addPossesBottomList(pos.down(), pos.down().north(), pos.down().east(), pos.down().south(), pos.down().west());
    }
    
    public void addPossesMainList(final BlockPos... blockPos) {
        this.blockPosList.clear();
        this.blockPosList.addAll(Arrays.asList(blockPos));
    }
    
    public void addPossesBottomList(final BlockPos... blockPos) {
        this.bottomPosList.clear();
        this.bottomPosList.addAll(Arrays.asList(blockPos));
    }
    
    public void addPossesUpperList(final BlockPos... blockPos) {
        this.upperPosList.clear();
        this.upperPosList.addAll(Arrays.asList(blockPos));
    }
    
    @Override
    public void onTick() {
        if ((this.startPos != null && this.mc.player.getDistanceSq(this.startPos) > 1.0) || this.mc.player.stepHeight > 0.6f || (this.startPos != null && this.mc.player.posY > this.startPos.y) || !this.mc.player.onGround) {
            this.disableModule();
            return;
        }
        if (this.onMoveCancel.GetSwitch() && EntityUtil.isMoving()) {
            return;
        }
        if (!this.placeTimer.getTime((long)this.placeDelay.GetSlider())) {
            return;
        }
        int slot = -1;
        final String getCombo = this.block.GetCombo();
        switch (getCombo) {
            case "Obsidian": {
                slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
                break;
            }
            case "EnderChests": {
                slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
                break;
            }
            case "Fallback": {
                slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
                if (slot == -1) {
                    slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
                    break;
                }
                break;
            }
        }
        if (slot == -1) {
            this.disableModule("No Blocks found in hotbar, disabling Feet Place.");
            return;
        }
        final int finalSlot = slot;
        if (this.reCalcOnMove.GetSwitch() && EntityUtil.isMoving()) {
            final BlockPos pos = BlockUtil.getPlayerPos();
            this.addPossesMainList(pos.down(), pos.down().north(), pos.down().east(), pos.down().south(), pos.down().west(), pos.north(), pos.east(), pos.south(), pos.west());
        }
        final String getCombo2 = this.placeMode.GetCombo();
        switch (getCombo2) {
            case "Instant": {
                for (int i = 0; i < (this.retry.GetSwitch() ? this.retries.GetSlider() : 1.0f); ++i) {
                    final boolean b;
                    this.blockPosList.stream().filter(blockPos -> {
                        if (this.canPlace(blockPos)) {
                            if (this.smartExtend.GetSwitch() || this.hitboxCheck.GetSwitch()) {
                                if (!(!this.mc.player.getEntityBoundingBox().intersects(new AxisAlignedBB(blockPos)))) {
                                    return 0 != 0;
                                }
                            }
                            return b;
                        }
                        return b;
                    }).forEach(blockPos -> BlockUtil.placeBlockWithSwitch(blockPos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), finalSlot));
                    if (this.smartExtend.GetSwitch()) {
                        this.extendBlocks.clear();
                        this.addExtendedPosses();
                        if (!this.extendBlocks.isEmpty()) {
                            this.extendBlocks.stream().filter((Predicate<? super Object>)this::canPlace).forEach(blockPos -> BlockUtil.placeBlockWithSwitch(blockPos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), finalSlot));
                        }
                    }
                }
                break;
            }
            case "Gradually": {
                if (this.getTargetPos() != null) {
                    BlockUtil.placeBlockWithSwitch(this.getTargetPos(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot, this.placeTimer);
                    break;
                }
                break;
            }
            case "Linear": {
                if (this.bottomBlocks() != null && !this.bottomBlocks().isEmpty()) {
                    this.bottomBlocks().forEach(blockPos -> BlockUtil.placeBlockWithSwitch(blockPos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), finalSlot, this.placeTimer));
                    break;
                }
                if (this.upperBlocks() != null && !this.upperBlocks().isEmpty()) {
                    this.upperBlocks().forEach(blockPos -> BlockUtil.placeBlockWithSwitch(blockPos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), finalSlot, this.placeTimer));
                    break;
                }
                break;
            }
            case "Obscure": {
                if (this.obscureBottomToUpper() != null) {
                    BlockUtil.placeBlockWithSwitch(this.obscureBottomToUpper(), EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot, this.placeTimer);
                    break;
                }
                break;
            }
        }
        if (!this.support.GetSwitch()) {
            return;
        }
        if (this.supportPos != null) {
            BlockUtil.placeBlockWithSwitch(this.supportPos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot, this.placeTimer);
            if (!this.canPlace(this.supportPos)) {
                this.supportPos = null;
            }
        }
    }
    
    public boolean canPlace(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || (this.inLiquids.GetSwitch() && (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.LAVA) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_LAVA)));
    }
    
    public void addExtendedPosses() {
        for (final BlockPos pos : this.blockPosList) {
            final AxisAlignedBB bb = new AxisAlignedBB(pos).grow((double)(this.smartExtendSize.GetSlider() / 20.0f));
            final AxisAlignedBB playerBox = this.mc.player.getEntityBoundingBox();
            if (playerBox.intersects(bb)) {
                if (!playerBox.intersects(new AxisAlignedBB(pos.north()))) {
                    if (this.isPosSurroundedByBlocks(pos.down().north()) && this.canPlace(pos.down().north())) {
                        this.extendBlocks.add(pos.down().north());
                    }
                    if (this.isPosSurroundedByBlocks(pos.north()) && this.canPlace(pos.north())) {
                        this.extendBlocks.add(pos.north());
                    }
                }
                if (!playerBox.intersects(new AxisAlignedBB(pos.east()))) {
                    if (this.isPosSurroundedByBlocks(pos.down().east()) && this.canPlace(pos.down().east())) {
                        this.extendBlocks.add(pos.down().east());
                    }
                    if (this.isPosSurroundedByBlocks(pos.east()) && this.canPlace(pos.east())) {
                        this.extendBlocks.add(pos.east());
                    }
                }
                if (!playerBox.intersects(new AxisAlignedBB(pos.south()))) {
                    if (this.isPosSurroundedByBlocks(pos.down().south()) && this.canPlace(pos.down().south())) {
                        this.extendBlocks.add(pos.down().south());
                    }
                    if (this.isPosSurroundedByBlocks(pos.south()) && this.canPlace(pos.south())) {
                        this.extendBlocks.add(pos.south());
                    }
                }
                if (playerBox.intersects(new AxisAlignedBB(pos.west()))) {
                    continue;
                }
                if (this.isPosSurroundedByBlocks(pos.down().west()) && this.canPlace(pos.down().west())) {
                    this.extendBlocks.add(pos.down().west());
                }
                if (!this.isPosSurroundedByBlocks(pos.west()) || !this.canPlace(pos.west())) {
                    continue;
                }
                this.extendBlocks.add(pos.west());
            }
        }
    }
    
    public boolean isPosSurroundedByBlocks(final BlockPos blockPos) {
        return !this.canPlace(blockPos.north()) || !this.canPlace(blockPos.east()) || !this.canPlace(blockPos.south()) || !this.canPlace(blockPos.west()) || !this.canPlace(blockPos.down()) || !this.canPlace(blockPos.up());
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (!(event.getPacket() instanceof SPacketBlockBreakAnim)) {
            return;
        }
        final SPacketBlockBreakAnim packet = (SPacketBlockBreakAnim)event.getPacket();
        if (this.upperPosList.contains(packet.getPosition())) {
            if (packet.getPosition().equals((Object)this.startPos.north()) && !this.canPlace(this.startPos.north()) && this.canPlace(packet.getPosition().north())) {
                this.supportPos = packet.getPosition().north();
                return;
            }
            if (packet.getPosition().equals((Object)this.startPos.east()) && !this.canPlace(this.startPos.east()) && this.canPlace(packet.getPosition().east())) {
                this.supportPos = packet.getPosition().east();
                return;
            }
            if (packet.getPosition().equals((Object)this.startPos.south()) && !this.canPlace(this.startPos.south()) && this.canPlace(packet.getPosition().south())) {
                this.supportPos = packet.getPosition().south();
                return;
            }
            if (packet.getPosition().equals((Object)this.startPos.west()) && !this.canPlace(this.startPos.west()) && this.canPlace(packet.getPosition().west())) {
                this.supportPos = packet.getPosition().west();
            }
        }
    }
    
    public List<BlockPos> bottomBlocks() {
        final boolean b;
        final List<BlockPos> posList = this.bottomPosList.stream().filter(blockPos -> {
            if (this.canPlace(blockPos)) {
                if (this.hitboxCheck.GetSwitch()) {
                    if (!this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos)).isEmpty()) {
                        return 1 != 0;
                    }
                }
                if (!this.smartExtend.GetSwitch()) {
                    return 0 != 0;
                }
                return b;
            }
            return b;
        }).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
        if (!posList.isEmpty()) {
            return posList;
        }
        return null;
    }
    
    public List<BlockPos> upperBlocks() {
        final boolean b;
        final List<BlockPos> posList = this.upperPosList.stream().filter(blockPos -> {
            if (this.canPlace(blockPos)) {
                if (this.hitboxCheck.GetSwitch()) {
                    if (!this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos)).isEmpty()) {
                        return 1 != 0;
                    }
                }
                if (!this.smartExtend.GetSwitch()) {
                    return 0 != 0;
                }
                return b;
            }
            return b;
        }).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
        if (!posList.isEmpty()) {
            return posList;
        }
        return null;
    }
    
    public BlockPos obscureBottomToUpper() {
        final boolean b;
        final List<BlockPos> upper = this.upperPosList.stream().filter(blockPos -> {
            if (this.canPlace(blockPos)) {
                if (this.hitboxCheck.GetSwitch()) {
                    if (!this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos)).isEmpty()) {
                        return 1 != 0;
                    }
                }
                if (!this.smartExtend.GetSwitch()) {
                    return 0 != 0;
                }
                return b;
            }
            return b;
        }).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
        final boolean b2;
        final List<BlockPos> bottom = this.bottomPosList.stream().filter(blockPos -> {
            if (this.canPlace(blockPos)) {
                if (this.hitboxCheck.GetSwitch()) {
                    if (!this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos)).isEmpty()) {
                        return 1 != 0;
                    }
                }
                if (!this.smartExtend.GetSwitch()) {
                    return 0 != 0;
                }
                return b2;
            }
            return b2;
        }).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
        if (!bottom.isEmpty()) {
            return bottom.stream().findFirst().orElse(null);
        }
        if (!upper.isEmpty()) {
            return upper.stream().findFirst().orElse(null);
        }
        return null;
    }
    
    public BlockPos getTargetPos() {
        final boolean b;
        return this.blockPosList.stream().filter(blockPos -> {
            if (this.canPlace(blockPos)) {
                if (this.hitboxCheck.GetSwitch()) {
                    if (!this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos)).isEmpty()) {
                        return 1 != 0;
                    }
                }
                if (!this.smartExtend.GetSwitch()) {
                    return 0 != 0;
                }
                return b;
            }
            return b;
        }).findFirst().orElse(null);
    }
}
