//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class AutoHolePush extends Module
{
    public final Slider delay;
    public final Slider targetRange;
    public final Slider placeRange;
    public final Switch packet;
    public final Switch rotate;
    protected final Timer timer;
    protected final Vec3i[] vec3is;
    protected final Vec3i[] vec3is1;
    
    public AutoHolePush() {
        this.delay = this.Menu.Slider("Delay", 0, 500);
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f);
        this.placeRange = this.Menu.Slider("Place Range", 0.1f, 6.0f);
        this.packet = this.Menu.Switch("Packet");
        this.rotate = this.Menu.Switch("Rotate");
        this.timer = new Timer();
        this.vec3is = new Vec3i[] { new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1), new Vec3i(0, 1, 0), new Vec3i(0, -1, 0) };
        this.vec3is1 = new Vec3i[] { new Vec3i(1, 0, 0), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(0, 0, -1) };
    }
    
    @Override
    public void onTick() {
        final EntityPlayer entityPlayer = EntityUtil.getTarget(this.targetRange.GetSlider());
        if (entityPlayer == null) {
            return;
        }
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer);
        if (!this.air(pos.up()) || !this.air(pos.up().up())) {
            return;
        }
        final TreeMap<Double, BlockPos> posses = new TreeMap<Double, BlockPos>();
        for (final Vec3i vec3i : this.vec3is1) {
            final BlockPos pos2 = pos.add(vec3i);
            final BlockPos pos3 = pos2.up();
            if (this.empty(pos3) && this.mc.player.getDistanceSq(pos2) / 2.0 < this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos3) / 2.0 < this.placeRange.GetSlider() && this.isSolid(pos2) && this.pistonOrAir(pos3) && (this.hasAnyRedstonablePos(pos3) || this.isPowered(pos3))) {
                final EnumFacing facing = this.getFace(pos, pos3);
                if (this.isOppositeEmpty(pos3, facing)) {
                    posses.put(this.mc.player.getDistanceSq(pos2) / 2.0, pos3);
                }
            }
        }
        if (!posses.isEmpty() && this.timer.getTime((long)this.delay.GetSlider())) {
            final BlockPos pistonPos = posses.firstEntry().getValue();
            final EnumFacing enumFacing = this.getFace(pos, pistonPos);
            if (this.air(pistonPos)) {
                final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.PISTON));
                if (slot != -1) {
                    this.rotateByEnumFacing(enumFacing);
                    BlockUtil.placeBlockWithSwitch(pistonPos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot, this.timer);
                }
                return;
            }
            if (!this.isPowered(pistonPos)) {
                final BlockPos redstonePos = this.redstonePos(pistonPos);
                if (redstonePos != null) {
                    final int slot2 = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK));
                    if (slot2 != -1) {
                        this.rotateByEnumFacing(enumFacing);
                        BlockUtil.placeBlockWithSwitch(redstonePos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot2, this.timer);
                    }
                }
            }
        }
    }
    
    protected boolean isOppositeEmpty(final BlockPos pos, final EnumFacing facing) {
        switch (facing) {
            case NORTH: {
                if (!this.air(pos.add(new Vec3i(0, 0, 2))) || !this.air(pos.add(new Vec3i(0, 1, 2)))) {
                    return false;
                }
            }
            case EAST: {
                if (!this.air(pos.add(new Vec3i(-2, 0, 0))) || !this.air(pos.add(new Vec3i(-2, 1, 0)))) {
                    return false;
                }
            }
            case SOUTH: {
                if (!this.air(pos.add(new Vec3i(0, 0, -2))) || !this.air(pos.add(new Vec3i(0, 1, -2)))) {
                    return false;
                }
            }
            case WEST: {
                if (!this.air(pos.add(new Vec3i(2, 0, 0))) || !this.air(pos.add(new Vec3i(2, 1, 0)))) {
                    return false;
                }
                break;
            }
        }
        return true;
    }
    
    protected void rotateByEnumFacing(final EnumFacing enumFacing) {
        int yaw = 0;
        switch (enumFacing) {
            case NORTH: {
                yaw = 180;
                break;
            }
            case EAST: {
                yaw = -90;
                break;
            }
            case WEST: {
                yaw = 90;
                break;
            }
        }
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)yaw, this.mc.player.rotationPitch, this.mc.player.onGround));
    }
    
    protected EnumFacing getFace(final BlockPos entityPlayerPos, final BlockPos pos) {
        if (entityPlayerPos.getZ() > pos.getZ()) {
            return EnumFacing.NORTH;
        }
        if (entityPlayerPos.getZ() < pos.getZ()) {
            return EnumFacing.SOUTH;
        }
        if (entityPlayerPos.getX() > pos.getX()) {
            return EnumFacing.WEST;
        }
        return EnumFacing.EAST;
    }
    
    protected boolean empty(final BlockPos pos) {
        return this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).isEmpty();
    }
    
    protected boolean isPowered(final BlockPos pos) {
        return Arrays.stream(this.vec3is).map((Function<? super Vec3i, ?>)pos::add).anyMatch(pos1 -> this.mc.world.getBlockState(pos1).getBlock().equals(Blocks.REDSTONE_BLOCK));
    }
    
    protected boolean isSolid(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.REDSTONE_BLOCK) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.ENDER_CHEST);
    }
    
    protected boolean hasAnyRedstonablePos(final BlockPos pos) {
        return Arrays.stream(this.vec3is).map((Function<? super Vec3i, ?>)pos::add).anyMatch(pos1 -> this.air(pos1) && this.mc.player.getDistanceSq(pos1) / 2.0 < this.placeRange.GetSlider() && this.empty(pos1));
    }
    
    protected boolean pistonOrAir(final BlockPos pos) {
        return this.air(pos) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.PISTON);
    }
    
    protected boolean air(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
    }
    
    protected BlockPos redstonePos(final BlockPos pos) {
        final ArrayList<BlockPos> blackListedPosses = Arrays.stream(this.vec3is).map((Function<? super Vec3i, ?>)pos::add).collect((Collector<? super Object, ?, ArrayList<BlockPos>>)Collectors.toCollection((Supplier<R>)ArrayList::new));
        final ArrayList list;
        final TreeMap<Double, BlockPos> redstonablePosses = Arrays.stream(this.vec3is).map((Function<? super Vec3i, ?>)pos::add).filter(pos1 -> this.air(pos1) && this.empty(pos1) && list.contains(pos1)).collect((Collector<? super Object, ?, TreeMap<Double, BlockPos>>)Collectors.toMap(pos1 -> this.mc.player.getDistanceSq(pos1) / 2.0, pos1 -> pos1, (a, b) -> b, (Supplier<R>)TreeMap::new));
        return redstonablePosses.isEmpty() ? null : redstonablePosses.firstEntry().getValue();
    }
}
