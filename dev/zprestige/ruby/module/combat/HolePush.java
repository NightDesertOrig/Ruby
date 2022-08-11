//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.network.play.client.*;

public class HolePush extends Module
{
    public final Parent ranges;
    public final Slider targetRange;
    public final Slider placeRange;
    public final Parent timing;
    public final Slider placeDelay;
    public final Parent rotations;
    public final ComboBox rotationMode;
    public final Switch rotateBack;
    public final Parent placements;
    public final Switch inLiquids;
    public final Switch packet;
    public final Parent mining;
    public final Switch mineRedstone;
    public final ComboBox mineMode;
    public final Switch consistent;
    public final Switch silentSwitch;
    public final Parent rendering;
    public final Switch render;
    public final Slider lineWidth;
    public Side side;
    public EntityPlayer entityPlayer;
    public BlockPos entityPlayerPos;
    public BlockPos placedRedstonePos;
    public BlockPos placedPistonPos;
    public Timer timer;
    public boolean rotated;
    public boolean mined;
    
    public HolePush() {
        this.ranges = this.Menu.Parent("Ranges");
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f).parent(this.ranges);
        this.placeRange = this.Menu.Slider("Place Range", 0.1f, 6.0f).parent(this.ranges);
        this.timing = this.Menu.Parent("Timing");
        this.placeDelay = this.Menu.Slider("Place Delay", 0, 1000).parent(this.timing);
        this.rotations = this.Menu.Parent("Rotations");
        this.rotationMode = this.Menu.ComboBox("Rotation Mode", new String[] { "Packet", "Vanilla", "TickWait" }).parent(this.rotations);
        this.rotateBack = this.Menu.Switch("Rotate Back").parent(this.rotations);
        this.placements = this.Menu.Parent("Placements");
        this.inLiquids = this.Menu.Switch("In Liquids").parent(this.placements);
        this.packet = this.Menu.Switch("Packet").parent(this.placements);
        this.mining = this.Menu.Parent("Mining");
        this.mineRedstone = this.Menu.Switch("Mine Redstone").parent(this.mining);
        this.mineMode = this.Menu.ComboBox("Mine Mode", new String[] { "Packet", "Click", "Vanilla" }).parent(this.mining);
        this.consistent = this.Menu.Switch("Consistent").parent(this.mining);
        this.silentSwitch = this.Menu.Switch("Silent Switch").parent(this.mining);
        this.rendering = this.Menu.Parent("Rendering");
        this.render = this.Menu.Switch("Render Bounding Boxes").parent(this.rendering);
        this.lineWidth = this.Menu.Slider("Line Width", 0.1f, 5.0f).parent(this.rendering);
        this.side = null;
        this.entityPlayer = null;
        this.entityPlayerPos = null;
        this.placedRedstonePos = null;
        this.placedPistonPos = null;
        this.timer = new Timer();
        this.rotated = false;
        this.mined = false;
    }
    
    @Override
    public void onEnable() {
        this.entityPlayer = this.getUntrappedClosestEntityPlayer(this.targetRange.GetSlider(), true);
        if (this.entityPlayer == null) {
            this.disableModule("No safe Targets found, disabling HolePushRewrite!");
            return;
        }
        this.entityPlayerPos = EntityUtil.getPlayerPos(this.entityPlayer).up();
        this.side = this.getSide(this.entityPlayerPos);
        if (this.side == null) {
            this.disableModule("No possible place sides found, disabling HolePushRewrite!");
            return;
        }
        this.rotated = false;
        this.mined = false;
        this.placedRedstonePos = null;
    }
    
    @Override
    public void onTick() {
        if (!this.timer.getTime((long)this.placeDelay.GetSlider())) {
            return;
        }
        this.entityPlayer = this.getUntrappedClosestEntityPlayer(this.targetRange.GetSlider(), false);
        if (this.entityPlayer == null) {
            this.disableModule("No safe Targets found, disabling HolePushRewrite!");
            return;
        }
        final BlockPos pistonPos = this.getPistonPos(this.entityPlayerPos, this.side);
        final BlockPos redstonePos = this.getRedstonePos(this.entityPlayerPos, this.side);
        if (!BlockUtil.isPlayerSafe(this.entityPlayer)) {
            this.disableModule("Target no longer safe, disabling HolePushRewrite!");
            return;
        }
        if (!this.placedPiston(this.entityPlayerPos)) {
            final float rotationYaw = this.mc.player.rotationYaw;
            final String getCombo = this.rotationMode.GetCombo();
            switch (getCombo) {
                case "Packet": {
                    this.rotatePacket(this.side);
                    break;
                }
                case "Vanilla": {
                    this.rotateVanilla(this.side);
                    break;
                }
                case "TickWait": {
                    if (!this.rotated) {
                        this.rotateVanilla(this.side);
                        this.rotated = true;
                        return;
                    }
                    break;
                }
            }
            final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.PISTON));
            if (slot == -1) {
                this.disableModule("No Pistons found, disabling HolePushRewrite.");
                return;
            }
            BlockUtil.placeBlockWithSwitch(pistonPos, EnumHand.MAIN_HAND, false, this.packet.GetSwitch(), slot, this.timer);
            this.placedPistonPos = pistonPos;
            if (this.rotateBack.GetSwitch() && !this.rotationMode.GetCombo().equals("Packet")) {
                this.mc.player.rotationYaw = rotationYaw;
            }
            this.rotated = false;
        }
        else {
            if (this.isPistonTriggered(pistonPos) || redstonePos == null) {
                if (this.isPistonTriggered(pistonPos) && this.mineRedstone.GetSwitch() && !this.mined) {
                    if (!this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE)) {
                        return;
                    }
                    final String getCombo2 = this.mineMode.GetCombo();
                    switch (getCombo2) {
                        case "Packet": {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.placedRedstonePos, EnumFacing.UP));
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.placedRedstonePos, EnumFacing.UP));
                            this.mined = !this.consistent.GetSwitch();
                        }
                        case "Click": {
                            this.mc.playerController.onPlayerDamageBlock(this.placedRedstonePos, this.mc.player.getHorizontalFacing());
                            EntityUtil.swingArm(EntityUtil.SwingType.MainHand);
                            this.mined = !this.consistent.GetSwitch();
                        }
                        case "Vanilla": {
                            final int currentItem = this.mc.player.inventory.currentItem;
                            final int slot2 = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
                            if (this.silentSwitch.GetSwitch()) {
                                if (slot2 == -1) {
                                    return;
                                }
                                InventoryUtil.switchToSlot(slot2);
                            }
                            this.mc.playerController.onPlayerDamageBlock(this.placedRedstonePos, this.mc.objectMouseOver.sideHit);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            if (this.silentSwitch.GetSwitch() && slot2 != -1) {
                                this.mc.player.inventory.currentItem = currentItem;
                                this.mc.playerController.updateController();
                                break;
                            }
                            break;
                        }
                    }
                }
                return;
            }
            final float rotationYaw = this.mc.player.rotationYaw;
            final String getCombo3 = this.rotationMode.GetCombo();
            switch (getCombo3) {
                case "Packet": {
                    this.rotatePacket(this.side);
                    break;
                }
                case "Vanilla": {
                    this.rotateVanilla(this.side);
                    break;
                }
                case "TickWait": {
                    if (!this.rotated) {
                        this.rotateVanilla(this.side);
                        this.rotated = true;
                        return;
                    }
                    break;
                }
            }
            final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK));
            if (slot == -1) {
                this.disableModule("No redstone blocks found, disabling HolePushRewrite.");
                return;
            }
            BlockUtil.placeBlockWithSwitch(redstonePos, EnumHand.MAIN_HAND, false, this.packet.GetSwitch(), slot, this.timer);
            if (this.rotateBack.GetSwitch() && !this.rotationMode.GetCombo().equals("Packet")) {
                this.mc.player.rotationYaw = rotationYaw;
            }
            this.placedRedstonePos = redstonePos;
            this.rotated = false;
            this.mined = false;
        }
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        if (this.render.GetSwitch()) {
            if (this.placedPistonPos != null) {
                RenderUtil.drawBlockOutlineBB(new AxisAlignedBB(this.placedPistonPos), new Color(16777215), this.lineWidth.GetSlider());
            }
            if (this.placedRedstonePos != null) {
                RenderUtil.drawBlockOutlineBB(new AxisAlignedBB(this.placedRedstonePos), new Color(16777215), this.lineWidth.GetSlider());
            }
        }
    }
    
    public EntityPlayer getUntrappedClosestEntityPlayer(final float range, final boolean pistonCheck) {
        final TreeMap<Float, EntityPlayer> treeMap = new TreeMap<Float, EntityPlayer>();
        for (final EntityPlayer entityPlayer : this.mc.world.playerEntities) {
            if (!entityPlayer.equals((Object)this.mc.player) && !Ruby.friendManager.isFriend(entityPlayer.getName()) && this.mc.player.getDistance((Entity)entityPlayer) < range && BlockUtil.isPlayerSafe(entityPlayer) && this.canPlace(EntityUtil.getPlayerPos(entityPlayer).up().up()) && (!pistonCheck || this.mc.world.getBlockState(EntityUtil.getPlayerPos(entityPlayer).up()).getBlock().equals(Blocks.AIR))) {
                treeMap.put(this.mc.player.getDistance((Entity)entityPlayer), entityPlayer);
            }
        }
        if (!treeMap.isEmpty()) {
            return treeMap.firstEntry().getValue();
        }
        return null;
    }
    
    public BlockPos getPistonPos(final BlockPos pos, final Side side) {
        switch (side) {
            case North: {
                return pos.north();
            }
            case East: {
                return pos.east();
            }
            case South: {
                return pos.south();
            }
            case West: {
                return pos.west();
            }
            default: {
                return null;
            }
        }
    }
    
    public BlockPos getRedstonePos(final BlockPos pos, final Side side) {
        switch (side) {
            case North: {
                if (this.canPlace(pos.north().north())) {
                    return pos.north().north();
                }
                if (this.canPlace(pos.north().up())) {
                    return pos.north().up();
                }
                if (this.canPlace(pos.north().east())) {
                    return pos.north().east();
                }
                if (this.canPlace(pos.north().west())) {
                    return pos.north().west();
                }
                break;
            }
            case East: {
                if (this.canPlace(pos.east().east())) {
                    return pos.east().east();
                }
                if (this.canPlace(pos.east().up())) {
                    return pos.east().up();
                }
                if (this.canPlace(pos.east().north())) {
                    return pos.east().north();
                }
                if (this.canPlace(pos.east().south())) {
                    return pos.east().south();
                }
                break;
            }
            case South: {
                if (this.canPlace(pos.south().south())) {
                    return pos.south().south();
                }
                if (this.canPlace(pos.south().up())) {
                    return pos.south().up();
                }
                if (this.canPlace(pos.south().east())) {
                    return pos.south().east();
                }
                if (this.canPlace(pos.south().west())) {
                    return pos.south().west();
                }
                break;
            }
            case West: {
                if (this.canPlace(pos.west().west())) {
                    return pos.west().west();
                }
                if (this.canPlace(pos.west().up())) {
                    return pos.west().up();
                }
                if (this.canPlace(pos.west().north())) {
                    return pos.west().north();
                }
                if (this.canPlace(pos.west().south())) {
                    return pos.west().south();
                }
                break;
            }
        }
        return null;
    }
    
    public boolean isPistonTriggered(final BlockPos pos) {
        return this.isRedstone(pos.north()) || this.isRedstone(pos.east()) || this.isRedstone(pos.south()) || this.isRedstone(pos.west()) || this.isRedstone(pos.up());
    }
    
    public boolean isRedstone(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.REDSTONE_BLOCK);
    }
    
    public boolean isPiston(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.PISTON);
    }
    
    public boolean canPlace(final BlockPos pos) {
        final ArrayList<Entity> intersecting = (ArrayList<Entity>)this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos)).stream().filter(entity -> !(entity instanceof EntityEnderCrystal)).collect(Collectors.toCollection(ArrayList::new));
        return intersecting.isEmpty() && this.mc.player.getDistanceSq(pos) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).isEmpty() && (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || (this.inLiquids.GetSwitch() && (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.LAVA) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_LAVA))));
    }
    
    public boolean canPlacePiston(final BlockPos pos) {
        return this.mc.player.getDistanceSq(pos) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).isEmpty() && (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.PISTON) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || (this.inLiquids.GetSwitch() && (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_WATER) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.LAVA) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_LAVA))));
    }
    
    public void rotatePacket(final Side side) {
        switch (side) {
            case North: {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
                break;
            }
            case East: {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
                break;
            }
            case South: {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
                break;
            }
            case West: {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
                break;
            }
        }
    }
    
    public void rotateVanilla(final Side side) {
        switch (side) {
            case North: {
                this.mc.player.rotationYaw = 180.0f;
                break;
            }
            case East: {
                this.mc.player.rotationYaw = -90.0f;
                break;
            }
            case South: {
                this.mc.player.rotationYaw = 0.0f;
                break;
            }
            case West: {
                this.mc.player.rotationYaw = 90.0f;
                break;
            }
        }
    }
    
    public Side getSide(final BlockPos pos) {
        if (this.canPlacePiston(pos.north()) && this.canPlace(pos.south()) && this.canPlace(pos.south().up()) && (this.canPlace(pos.north().north()) || this.canPlace(pos.north().east()) || this.canPlace(pos.north().west()) || this.canPlace(pos.north().up()))) {
            return Side.North;
        }
        if (this.canPlacePiston(pos.east()) && this.canPlace(pos.west()) && this.canPlace(pos.west().up()) && (this.canPlace(pos.east().east()) || this.canPlace(pos.east().north()) || this.canPlace(pos.east().south()) || this.canPlace(pos.east().up()))) {
            return Side.East;
        }
        if (this.canPlacePiston(pos.south()) && this.canPlace(pos.north()) && this.canPlace(pos.north().up()) && (this.canPlace(pos.south().south()) || this.canPlace(pos.south().east()) || this.canPlace(pos.south().west()) || this.canPlace(pos.south().up()))) {
            return Side.South;
        }
        if (this.canPlacePiston(pos.west()) && this.canPlace(pos.east()) && this.canPlace(pos.east().up()) && (this.canPlace(pos.west().west()) || this.canPlace(pos.west().north()) || this.canPlace(pos.west().east()) || this.canPlace(pos.west().up()))) {
            return Side.West;
        }
        return null;
    }
    
    public boolean placedPiston(final BlockPos pos) {
        switch (this.side) {
            case North: {
                return this.isPiston(pos.north());
            }
            case East: {
                return this.isPiston(pos.east());
            }
            case South: {
                return this.isPiston(pos.south());
            }
            case West: {
                return this.isPiston(pos.west());
            }
            default: {
                return false;
            }
        }
    }
    
    public enum Side
    {
        North, 
        East, 
        South, 
        West;
    }
}
