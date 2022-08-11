//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util;

import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import dev.zprestige.ruby.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import java.util.*;

public class BlockUtil
{
    public static Minecraft mc;
    public static List<Block> blackList;
    public static List<Block> shulkerList;
    
    public static boolean hasBlockEnumFacing(final BlockPos pos) {
        return isAir(pos.up()) || isAir(pos.down()) || isAir(pos.north()) || isAir(pos.east()) || isAir(pos.south()) || isAir(pos.west());
    }
    
    public static EnumFacing getClosestEnumFacing(final BlockPos pos) {
        final TreeMap<Double, EnumFacing> facingTreeMap = new TreeMap<Double, EnumFacing>();
        if (isAir(pos.up())) {
            facingTreeMap.put(BlockUtil.mc.player.getDistanceSq(pos.up()), EnumFacing.UP);
        }
        if (isAir(pos.down())) {
            facingTreeMap.put(BlockUtil.mc.player.getDistanceSq(pos.down()), EnumFacing.DOWN);
        }
        if (isAir(pos.north())) {
            facingTreeMap.put(BlockUtil.mc.player.getDistanceSq(pos.north()), EnumFacing.NORTH);
        }
        if (isAir(pos.east())) {
            facingTreeMap.put(BlockUtil.mc.player.getDistanceSq(pos.east()), EnumFacing.EAST);
        }
        if (isAir(pos.south())) {
            facingTreeMap.put(BlockUtil.mc.player.getDistanceSq(pos.south()), EnumFacing.SOUTH);
        }
        if (isAir(pos.west())) {
            facingTreeMap.put(BlockUtil.mc.player.getDistanceSq(pos.west()), EnumFacing.WEST);
        }
        if (!facingTreeMap.isEmpty()) {
            return facingTreeMap.firstEntry().getValue();
        }
        return null;
    }
    
    public static boolean isAir(final BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static boolean rayTraceCheckPos(final BlockPos pos) {
        return BlockUtil.mc.world.rayTraceBlocks(new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ), new Vec3d((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), false, true, false) != null;
    }
    
    public static boolean isPlayerSafe(final EntityPlayer target) {
        final BlockPos playerPos = new BlockPos(Math.floor(target.posX), Math.floor(target.posY), Math.floor(target.posZ));
        return (BlockUtil.mc.world.getBlockState(playerPos.down()).getBlock() == Blocks.OBSIDIAN || BlockUtil.mc.world.getBlockState(playerPos.down()).getBlock() == Blocks.BEDROCK) && (BlockUtil.mc.world.getBlockState(playerPos.north()).getBlock() == Blocks.OBSIDIAN || BlockUtil.mc.world.getBlockState(playerPos.north()).getBlock() == Blocks.BEDROCK) && (BlockUtil.mc.world.getBlockState(playerPos.east()).getBlock() == Blocks.OBSIDIAN || BlockUtil.mc.world.getBlockState(playerPos.east()).getBlock() == Blocks.BEDROCK) && (BlockUtil.mc.world.getBlockState(playerPos.south()).getBlock() == Blocks.OBSIDIAN || BlockUtil.mc.world.getBlockState(playerPos.south()).getBlock() == Blocks.BEDROCK) && (BlockUtil.mc.world.getBlockState(playerPos.west()).getBlock() == Blocks.OBSIDIAN || BlockUtil.mc.world.getBlockState(playerPos.west()).getBlock() == Blocks.BEDROCK);
    }
    
    public static boolean isPlayerSafe2(final EntityPlayer entityPlayer) {
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer);
        if (isNotIntersecting(entityPlayer)) {
            return isBedrockOrObsidianOrEchest(pos.north()) && isBedrockOrObsidianOrEchest(pos.east()) && isBedrockOrObsidianOrEchest(pos.south()) && isBedrockOrObsidianOrEchest(pos.west()) && isBedrockOrObsidianOrEchest(pos.down());
        }
        return isIntersectingSafe(entityPlayer);
    }
    
    public static boolean isNotIntersecting(final EntityPlayer entityPlayer) {
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer);
        final AxisAlignedBB bb = entityPlayer.getEntityBoundingBox();
        return (!isAir(pos.north()) || !bb.intersects(new AxisAlignedBB(pos.north()))) && (!isAir(pos.east()) || !bb.intersects(new AxisAlignedBB(pos.east()))) && (!isAir(pos.south()) || !bb.intersects(new AxisAlignedBB(pos.south()))) && (!isAir(pos.west()) || !bb.intersects(new AxisAlignedBB(pos.west())));
    }
    
    public static boolean isIntersectingSafe(final EntityPlayer entityPlayer) {
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer);
        final AxisAlignedBB bb = entityPlayer.getEntityBoundingBox();
        if (isAir(pos.north()) && bb.intersects(new AxisAlignedBB(pos.north()))) {
            final BlockPos pos2 = pos.north();
            if (!isBedrockOrObsidianOrEchest(pos2.north()) || !isBedrockOrObsidianOrEchest(pos2.east()) || !isBedrockOrObsidianOrEchest(pos2.west()) || !isBedrockOrObsidianOrEchest(pos2.down())) {
                return false;
            }
        }
        if (isAir(pos.east()) && bb.intersects(new AxisAlignedBB(pos.east()))) {
            final BlockPos pos2 = pos.east();
            if (!isBedrockOrObsidianOrEchest(pos2.north()) || !isBedrockOrObsidianOrEchest(pos2.east()) || !isBedrockOrObsidianOrEchest(pos2.south()) || !isBedrockOrObsidianOrEchest(pos2.down())) {
                return false;
            }
        }
        if (isAir(pos.south()) && bb.intersects(new AxisAlignedBB(pos.south()))) {
            final BlockPos pos2 = pos.south();
            if (!isBedrockOrObsidianOrEchest(pos2.east()) || !isBedrockOrObsidianOrEchest(pos2.south()) || !isBedrockOrObsidianOrEchest(pos2.west()) || !isBedrockOrObsidianOrEchest(pos2.down())) {
                return false;
            }
        }
        if (isAir(pos.west()) && bb.intersects(new AxisAlignedBB(pos.west()))) {
            final BlockPos pos2 = pos.west();
            return isBedrockOrObsidianOrEchest(pos2.north()) && isBedrockOrObsidianOrEchest(pos2.south()) && isBedrockOrObsidianOrEchest(pos2.west()) && isBedrockOrObsidianOrEchest(pos2.down());
        }
        return true;
    }
    
    public static boolean isBedrockOrObsidianOrEchest(final BlockPos pos) {
        return BlockUtil.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) || BlockUtil.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN) || BlockUtil.mc.world.getBlockState(pos).getBlock().equals(Blocks.ENDER_CHEST);
    }
    
    public static boolean isPosValidForCrystal(final BlockPos pos, final boolean onepointthirteen) {
        if (BlockUtil.mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK && BlockUtil.mc.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        if (BlockUtil.mc.world.getBlockState(pos.up()).getBlock() != Blocks.AIR || (!onepointthirteen && BlockUtil.mc.world.getBlockState(pos.up().up()).getBlock() != Blocks.AIR)) {
            return false;
        }
        for (final Entity entity : BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos.up()))) {
            if (!entity.isDead) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                return false;
            }
        }
        for (final Entity entity : BlockUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos.up().up()))) {
            if (!entity.isDead) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public static List<BlockPos> getSphereAutoCrystal(final double radius, final boolean noAir) {
        final ArrayList<BlockPos> posList = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(Math.floor(BlockUtil.mc.player.posX), Math.floor(BlockUtil.mc.player.posY), Math.floor(BlockUtil.mc.player.posZ));
        for (int x = pos.getX() - (int)radius; x <= pos.getX() + radius; ++x) {
            for (int y = pos.getY() - (int)radius; y < pos.getY() + radius; ++y) {
                for (int z = pos.getZ() - (int)radius; z <= pos.getZ() + radius; ++z) {
                    final double distance = (pos.getX() - x) * (pos.getX() - x) + (pos.getZ() - z) * (pos.getZ() - z) + (pos.getY() - y) * (pos.getY() - y);
                    final BlockPos position = new BlockPos(x, y, z);
                    if (distance < radius * radius && noAir && !BlockUtil.mc.world.getBlockState(position).getBlock().equals(Blocks.AIR)) {
                        posList.add(position);
                    }
                }
            }
        }
        return posList;
    }
    
    public static BlockPos getClosestHoleToPlayer(final EntityPlayer entityPlayer, final float radius, final boolean doubleHoles) {
        if (entityPlayer == null || entityPlayer.isDead || Ruby.friendManager.isFriend(entityPlayer.getName()) || entityPlayer.equals((Object)BlockUtil.mc.player)) {
            return null;
        }
        final TreeMap<Double, Hole> holes = new TreeMap<Double, Hole>();
        for (final BlockPos pos : getSphere(radius, AirType.OnlyAir, entityPlayer)) {
            if (Ruby.mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK) {
                holes.put(entityPlayer.getDistanceSq(pos), new Hole(pos, entityPlayer.getDistanceSq(pos)));
            }
            else if (Ruby.mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos).getBlock() == Blocks.AIR && (Ruby.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK || Ruby.mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) && (Ruby.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN || Ruby.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK) && (Ruby.mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN || Ruby.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK) && (Ruby.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN || Ruby.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK) && (Ruby.mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN || Ruby.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK)) {
                holes.put(entityPlayer.getDistanceSq(pos), new Hole(pos, entityPlayer.getDistanceSq(pos)));
            }
            if (doubleHoles) {
                if (Ruby.mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos.north().up()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.north().down()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.north()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.north().east()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.north().west()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK) {
                    holes.put(entityPlayer.getDistanceSq(pos), new Hole(pos, entityPlayer.getDistanceSq(pos)));
                    holes.put(entityPlayer.getDistanceSq(pos.north()), new Hole(pos, entityPlayer.getDistanceSq(pos.north())));
                }
                else if (Ruby.mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos.north().up()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos).getBlock() == Blocks.AIR && (Ruby.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK || Ruby.mc.world.getBlockState(pos.down()).getBlock() == Blocks.OBSIDIAN) && Ruby.mc.world.getBlockState(pos.north()).getBlock() == Blocks.AIR && (Ruby.mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN || Ruby.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK) && (Ruby.mc.world.getBlockState(pos.west()).getBlock() == Blocks.OBSIDIAN || Ruby.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK) && (Ruby.mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN || Ruby.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK) && (Ruby.mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.BEDROCK || Ruby.mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.OBSIDIAN) && (Ruby.mc.world.getBlockState(pos.north().east()).getBlock() == Blocks.BEDROCK || Ruby.mc.world.getBlockState(pos.north().east()).getBlock() == Blocks.OBSIDIAN) && (Ruby.mc.world.getBlockState(pos.north().west()).getBlock() == Blocks.BEDROCK || Ruby.mc.world.getBlockState(pos.north().west()).getBlock() == Blocks.OBSIDIAN) && (Ruby.mc.world.getBlockState(pos.north().down()).getBlock() == Blocks.OBSIDIAN || Ruby.mc.world.getBlockState(pos.north().down()).getBlock() == Blocks.BEDROCK)) {
                    holes.put(entityPlayer.getDistanceSq(pos), new Hole(pos, entityPlayer.getDistanceSq(pos)));
                    holes.put(entityPlayer.getDistanceSq(pos.north()), new Hole(pos, entityPlayer.getDistanceSq(pos.north())));
                }
                else if (Ruby.mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos.west().up()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west().down()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west()).getBlock() == Blocks.AIR && Ruby.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west().south()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west().north()).getBlock() == Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west().west()).getBlock() == Blocks.BEDROCK) {
                    holes.put(entityPlayer.getDistanceSq(pos), new Hole(pos, entityPlayer.getDistanceSq(pos)));
                    holes.put(entityPlayer.getDistanceSq(pos.west()), new Hole(pos, entityPlayer.getDistanceSq(pos.west())));
                }
                else {
                    if (Ruby.mc.world.getBlockState(pos.up()).getBlock() != Blocks.AIR || Ruby.mc.world.getBlockState(pos.west().up()).getBlock() != Blocks.AIR || Ruby.mc.world.getBlockState(pos).getBlock() != Blocks.AIR || (Ruby.mc.world.getBlockState(pos.down()).getBlock() != Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.down()).getBlock() != Blocks.OBSIDIAN) || (Ruby.mc.world.getBlockState(pos.west().down()).getBlock() != Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west().down()).getBlock() != Blocks.OBSIDIAN) || (Ruby.mc.world.getBlockState(pos.north()).getBlock() != Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.north()).getBlock() != Blocks.OBSIDIAN) || (Ruby.mc.world.getBlockState(pos.south()).getBlock() != Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.south()).getBlock() != Blocks.OBSIDIAN) || Ruby.mc.world.getBlockState(pos.west()).getBlock() != Blocks.AIR || (Ruby.mc.world.getBlockState(pos.east()).getBlock() != Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.east()).getBlock() != Blocks.OBSIDIAN) || (Ruby.mc.world.getBlockState(pos.west().south()).getBlock() != Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west().south()).getBlock() != Blocks.OBSIDIAN) || (Ruby.mc.world.getBlockState(pos.west().north()).getBlock() != Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west().north()).getBlock() != Blocks.OBSIDIAN) || (Ruby.mc.world.getBlockState(pos.west().west()).getBlock() != Blocks.BEDROCK && Ruby.mc.world.getBlockState(pos.west().west()).getBlock() != Blocks.OBSIDIAN)) {
                        continue;
                    }
                    holes.put(entityPlayer.getDistanceSq(pos), new Hole(pos, entityPlayer.getDistanceSq(pos)));
                    holes.put(entityPlayer.getDistanceSq(pos.west()), new Hole(pos, entityPlayer.getDistanceSq(pos.west())));
                }
            }
        }
        if (!holes.isEmpty()) {
            return holes.lastEntry().getValue().pos;
        }
        return null;
    }
    
    public static List<BlockPos> nearbyAirPosses(final double radius) {
        final ArrayList<BlockPos> posses = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(BlockUtil.mc.player.getPositionVector());
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        final int radiuss = (int)radius;
        for (int x = posX - radiuss; x <= posX + radius; ++x) {
            for (int z = posZ - radiuss; z <= posZ + radius; ++z) {
                for (int y = posY - radiuss; y < posY + radius; ++y) {
                    final double dist = (posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y);
                    final BlockPos position = new BlockPos(x, y, z);
                    if (dist < radius * radius && BlockUtil.mc.world.getBlockState(position).getBlock().equals(Blocks.AIR)) {
                        posses.add(position);
                    }
                }
            }
        }
        return posses;
    }
    
    public static List<BlockPos> getSphere(final double radius, final AirType airType, final EntityPlayer entityPlayer) {
        final ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(entityPlayer.getPositionVector());
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        final int radiuss = (int)radius;
        for (int x = posX - radiuss; x <= posX + radius; ++x) {
            for (int z = posZ - radiuss; z <= posZ + radius; ++z) {
                for (int y = posY - radiuss; y < posY + radius; ++y) {
                    final double dist = (posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y);
                    if (dist < radius * radius) {
                        final BlockPos position = new BlockPos(x, y, z);
                        if (!BlockUtil.mc.world.getBlockState(position).getBlock().equals(Blocks.AIR) || !airType.equals(AirType.IgnoreAir)) {
                            if (BlockUtil.mc.world.getBlockState(position).getBlock().equals(Blocks.AIR) || !airType.equals(AirType.OnlyAir)) {
                                sphere.add(position);
                            }
                        }
                    }
                }
            }
        }
        return sphere;
    }
    
    public static void placeBlockWithSwitch(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final int slot) {
        if (slot != -1) {
            final int currentItem = BlockUtil.mc.player.inventory.currentItem;
            InventoryUtil.switchToSlot(slot);
            placeBlock(pos, hand, rotate, packet);
            BlockUtil.mc.player.inventory.currentItem = currentItem;
            BlockUtil.mc.playerController.updateController();
        }
    }
    
    public static void placeBlockWithSwitch(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet, final int slot, final Timer timer) {
        if (slot != -1) {
            final int currentItem = BlockUtil.mc.player.inventory.currentItem;
            InventoryUtil.switchToSlot(slot);
            placeBlock(pos, hand, rotate, packet);
            BlockUtil.mc.player.inventory.currentItem = currentItem;
            BlockUtil.mc.playerController.updateController();
            timer.setTime(0);
        }
    }
    
    public static void placeBlock(final BlockPos pos, final EnumHand hand, final boolean rotate, final boolean packet) {
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = BlockUtil.mc.world.getBlockState(neighbour).getBlock();
        if (!BlockUtil.mc.player.isSneaking() && (BlockUtil.blackList.contains(neighbourBlock) || BlockUtil.shulkerList.contains(neighbourBlock))) {
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BlockUtil.mc.player.setSneaking(true);
        }
        if (rotate) {
            faceVector(hitVec, true);
        }
        rightClickBlock(neighbour, hitVec, hand, opposite, packet);
        BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        BlockUtil.mc.rightClickDelayTimer = 4;
    }
    
    public static void rightClickBlock(final BlockPos pos, final Vec3d vec, final EnumHand hand, final EnumFacing direction, final boolean packet) {
        if (packet) {
            final float f = (float)(vec.x - pos.getX());
            final float f2 = (float)(vec.y - pos.getY());
            final float f3 = (float)(vec.z - pos.getZ());
            BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f2, f3));
        }
        else {
            BlockUtil.mc.playerController.processRightClickBlock(BlockUtil.mc.player, BlockUtil.mc.world, pos, direction, vec, hand);
        }
        BlockUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        BlockUtil.mc.rightClickDelayTimer = 4;
    }
    
    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final ArrayList<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BlockUtil.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtil.mc.world.getBlockState(neighbour), false)) {
                if (!BlockUtil.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static void faceVector(final Vec3d vec, final boolean normalizeAngle) {
        final float[] rotations = getLegitRotations(vec);
        BlockUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], BlockUtil.mc.player.onGround));
    }
    
    public static float[] getLegitRotations(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { BlockUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - BlockUtil.mc.player.rotationYaw), BlockUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - BlockUtil.mc.player.rotationPitch) };
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(BlockUtil.mc.player.posX, BlockUtil.mc.player.posY + BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ);
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(BlockUtil.mc.player.posX), Math.floor(BlockUtil.mc.player.posY), Math.floor(BlockUtil.mc.player.posZ));
    }
    
    static {
        BlockUtil.mc = Minecraft.getMinecraft();
        BlockUtil.blackList = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE);
        BlockUtil.shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
    }
    
    public enum AirType
    {
        OnlyAir, 
        IgnoreAir, 
        None;
    }
    
    public static class Hole
    {
        public BlockPos pos;
        public double range;
        
        public Hole(final BlockPos pos, final double range) {
            this.pos = pos;
            this.range = range;
        }
    }
}
