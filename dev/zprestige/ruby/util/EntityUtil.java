//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util;

import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.math.*;
import dev.zprestige.ruby.*;
import net.minecraft.potion.*;

public class EntityUtil
{
    public static Minecraft mc;
    
    public static void swingArm(final SwingType swingType) {
        if (swingType.equals(SwingType.MainHand)) {
            EntityUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        else if (swingType.equals(SwingType.OffHand)) {
            EntityUtil.mc.player.swingArm(EnumHand.OFF_HAND);
        }
        else if (swingType.equals(SwingType.Packet)) {
            EntityUtil.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EntityUtil.mc.player.getHeldItemMainhand().getItem().equals(Items.END_CRYSTAL) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND));
        }
    }
    
    public static void setSpeed(final EntityLivingBase entity, final double speed) {
        final double[] dir = getSpeed(speed);
        entity.motionX = dir[0];
        entity.motionZ = dir[1];
    }
    
    public static void setMoveSpeed(final MoveEvent event, final double speed) {
        double forward = EntityUtil.mc.player.movementInput.moveForward;
        double strafe = EntityUtil.mc.player.movementInput.moveStrafe;
        float yaw = EntityUtil.mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.motionX = 0.0;
            event.motionZ = 0.0;
            EntityUtil.mc.player.motionX = 0.0;
            EntityUtil.mc.player.motionZ = 0.0;
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            final double x = forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));
            final double z = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));
            event.motionX = x;
            event.motionZ = z;
            EntityUtil.mc.player.motionX = x;
            EntityUtil.mc.player.motionZ = z;
        }
    }
    
    public static double getDefaultSpeed() {
        double defaultSpeed = 0.2873;
        if (EntityUtil.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(EntityUtil.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        if (EntityUtil.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            final int amplifier = Objects.requireNonNull(EntityUtil.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }
        return defaultSpeed;
    }
    
    public static Entity getPredictedPosition(final Entity entity, final double x) {
        if (x == 0.0) {
            return entity;
        }
        EntityPlayer e = null;
        final double motionX = entity.posX - entity.lastTickPosX;
        double motionY = entity.posY - entity.lastTickPosY;
        final double motionZ = entity.posZ - entity.lastTickPosZ;
        boolean shouldPredict = false;
        boolean shouldStrafe = false;
        final double motion = Math.sqrt(Math.pow(motionX, 2.0) + Math.pow(motionZ, 2.0) + Math.pow(motionY, 2.0));
        if (motion > 0.1) {
            shouldPredict = true;
        }
        if (!shouldPredict) {
            return entity;
        }
        if (motion > 0.31) {
            shouldStrafe = true;
        }
        for (int i = 0; i < x; ++i) {
            if (e == null) {
                if (isOnGround(0.0, 0.0, 0.0, entity)) {
                    motionY = (shouldStrafe ? 0.4 : -0.07840015258789);
                }
                else {
                    motionY -= 0.08;
                    motionY *= 0.9800000190734863;
                }
                e = placeValue(motionX, motionY, motionZ, (EntityPlayer)entity);
            }
            else {
                if (isOnGround(0.0, 0.0, 0.0, (Entity)e)) {
                    motionY = (shouldStrafe ? 0.4 : -0.07840015258789);
                }
                else {
                    motionY -= 0.08;
                    motionY *= 0.9800000190734863;
                }
                e = placeValue(motionX, motionY, motionZ, e);
            }
        }
        return (Entity)e;
    }
    
    public static boolean isOnGround(final double x, double y, final double z, final Entity entity) {
        try {
            final double d3 = y;
            final List<AxisAlignedBB> list1 = (List<AxisAlignedBB>)EntityUtil.mc.world.getCollisionBoxes(entity, entity.getEntityBoundingBox().expand(x, y, z));
            if (y != 0.0) {
                for (int k = 0, l = list1.size(); k < l; ++k) {
                    y = list1.get(k).calculateYOffset(entity.getEntityBoundingBox(), y);
                }
            }
            return d3 != y && d3 < 0.0;
        }
        catch (Exception ignored) {
            return false;
        }
    }
    
    public static EntityPlayer placeValue(double x, double y, double z, final EntityPlayer entity) {
        final List<AxisAlignedBB> list1 = (List<AxisAlignedBB>)EntityUtil.mc.world.getCollisionBoxes((Entity)entity, entity.getEntityBoundingBox().expand(x, y, z));
        if (y != 0.0) {
            for (int k = 0, l = list1.size(); k < l; ++k) {
                y = list1.get(k).calculateYOffset(entity.getEntityBoundingBox(), y);
            }
            if (y != 0.0) {
                entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(0.0, y, 0.0));
            }
        }
        if (x != 0.0) {
            for (int j5 = 0, l2 = list1.size(); j5 < l2; ++j5) {
                x = calculateXOffset(entity.getEntityBoundingBox(), x, list1.get(j5));
            }
            if (x != 0.0) {
                entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(x, 0.0, 0.0));
            }
        }
        if (z != 0.0) {
            for (int k2 = 0, i6 = list1.size(); k2 < i6; ++k2) {
                z = calculateZOffset(entity.getEntityBoundingBox(), z, list1.get(k2));
            }
            if (z != 0.0) {
                entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(0.0, 0.0, z));
            }
        }
        return entity;
    }
    
    public static double calculateXOffset(final AxisAlignedBB other, double offsetX, final AxisAlignedBB this1) {
        if (other.maxY > this1.minY && other.minY < this1.maxY && other.maxZ > this1.minZ && other.minZ < this1.maxZ) {
            if (offsetX > 0.0 && other.maxX <= this1.minX) {
                final double d1 = this1.minX - 0.3 - other.maxX;
                if (d1 < offsetX) {
                    offsetX = d1;
                }
            }
            else if (offsetX < 0.0 && other.minX >= this1.maxX) {
                final double d2 = this1.maxX + 0.3 - other.minX;
                if (d2 > offsetX) {
                    offsetX = d2;
                }
            }
        }
        return offsetX;
    }
    
    public static double calculateZOffset(final AxisAlignedBB other, double offsetZ, final AxisAlignedBB this1) {
        if (other.maxX > this1.minX && other.minX < this1.maxX && other.maxY > this1.minY && other.minY < this1.maxY) {
            if (offsetZ > 0.0 && other.maxZ <= this1.minZ) {
                final double d1 = this1.minZ - 0.3 - other.maxZ;
                if (d1 < offsetZ) {
                    offsetZ = d1;
                }
            }
            else if (offsetZ < 0.0 && other.minZ >= this1.maxZ) {
                final double d2 = this1.maxZ + 0.3 - other.minZ;
                if (d2 > offsetZ) {
                    offsetZ = d2;
                }
            }
        }
        return offsetZ;
    }
    
    public static double yawDist(final Entity e) {
        if (e != null) {
            final Vec3d difference = e.getPositionVector().add(0.0, (double)(e.getEyeHeight() / 2.0f), 0.0).subtract(EntityUtil.mc.player.getPositionEyes(EntityUtil.mc.getRenderPartialTicks()));
            final double d = Math.abs(EntityUtil.mc.player.rotationYaw - (Math.toDegrees(Math.atan2(difference.z, difference.x)) - 90.0)) % 360.0;
            return (d > 180.0) ? (360.0 - d) : d;
        }
        return 0.0;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        final float green = (stack.getMaxDamage() - (float)stack.getItemDamage()) / stack.getMaxDamage();
        final float red = 1.0f - green;
        return (float)(100 - (int)(red * 100.0f));
    }
    
    public static float getHealth(final Entity entity) {
        if (isLiving(entity)) {
            final EntityLivingBase livingBase = (EntityLivingBase)entity;
            return livingBase.getHealth() + livingBase.getAbsorptionAmount();
        }
        return 0.0f;
    }
    
    public static BlockPos getPlayerPos(final EntityPlayer player) {
        return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    }
    
    public static float calculateEntityDamage(final EntityEnderCrystal crystal, final EntityPlayer player) {
        return calculatePosDamage(crystal.posX, crystal.posY, crystal.posZ, (Entity)player);
    }
    
    public static float calculatePosDamage(final BlockPos position, final EntityPlayer player) {
        return calculatePosDamage(position.getX() + 0.5, position.getY() + 1.0, position.getZ() + 0.5, (Entity)player);
    }
    
    public static float calculatePosDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleSize = 12.0f;
        final double size = entity.getDistance(posX, posY, posZ) / doubleSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        final double value = (1.0 - size) * blockDensity;
        final float damage = (float)(int)((value * value + value) / 2.0 * 7.0 * doubleSize + 1.0);
        double finalDamage = 1.0;
        if (entity instanceof EntityLivingBase) {
            finalDamage = getBlastReduction((EntityLivingBase)entity, getMultipliedDamage(damage), new Explosion((World)EntityUtil.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finalDamage;
    }
    
    private static float getMultipliedDamage(final float damage) {
        return damage * ((EntityUtil.mc.world.getDifficulty().getId() == 0) ? 0.0f : ((EntityUtil.mc.world.getDifficulty().getId() == 2) ? 1.0f : ((EntityUtil.mc.world.getDifficulty().getId() == 1) ? 0.5f : 1.5f)));
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        final int k = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), ds);
        damage *= 1.0f - MathHelper.clamp((float)k, 0.0f, 20.0f) / 25.0f;
        if (entity.isPotionActive(MobEffects.RESISTANCE)) {
            damage -= damage / 4.0f;
        }
        return damage;
    }
    
    public static EntityPlayer getTarget(final float range) {
        EntityPlayer currentTarget = null;
        for (int size = EntityUtil.mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer player = EntityUtil.mc.world.playerEntities.get(i);
            if (!isntValid((Entity)player, range)) {
                if (currentTarget == null) {
                    currentTarget = player;
                }
                else if (EntityUtil.mc.player.getDistanceSq((Entity)player) < EntityUtil.mc.player.getDistanceSq((Entity)currentTarget)) {
                    currentTarget = player;
                }
            }
        }
        return currentTarget;
    }
    
    public static EntityPlayer getTargetSafe(final float range) {
        EntityPlayer currentTarget = null;
        for (int size = EntityUtil.mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer player = EntityUtil.mc.world.playerEntities.get(i);
            if (!isntValid((Entity)player, range)) {
                if (BlockUtil.isPlayerSafe(player)) {
                    if (currentTarget == null) {
                        currentTarget = player;
                    }
                    else if (EntityUtil.mc.player.getDistanceSq((Entity)player) < EntityUtil.mc.player.getDistanceSq((Entity)currentTarget)) {
                        currentTarget = player;
                    }
                }
            }
        }
        return currentTarget;
    }
    
    public static boolean isntValid(final Entity entity, final double range) {
        return entity == null || isDead(entity) || entity.equals((Object)EntityUtil.mc.player) || (entity instanceof EntityPlayer && Ruby.friendManager.isFriend(entity.getName())) || EntityUtil.mc.player.getDistanceSq(entity) > range * range;
    }
    
    public static boolean isAlive(final Entity entity) {
        return isLiving(entity) && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }
    
    public static boolean isDead(final Entity entity) {
        return !isAlive(entity);
    }
    
    public static boolean isLiving(final Entity entity) {
        return entity instanceof EntityLivingBase;
    }
    
    public static boolean isMoving() {
        return EntityUtil.mc.player.moveForward != 0.0 || EntityUtil.mc.player.moveStrafing != 0.0;
    }
    
    public static double getBaseMotionSpeed() {
        double event = 0.272;
        if (EntityUtil.mc.player.isPotionActive(MobEffects.SPEED)) {
            final int var3 = Objects.requireNonNull(EntityUtil.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            event *= 1.0 + 0.2 * var3;
        }
        return event;
    }
    
    public static double getMaxSpeed() {
        double maxModifier = 0.2873;
        if (EntityUtil.mc.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionById(1)))) {
            maxModifier *= 1.0 + 0.2 * (Objects.requireNonNull(EntityUtil.mc.player.getActivePotionEffect((Potion)Objects.requireNonNull(Potion.getPotionById(1)))).getAmplifier() + 1);
        }
        return maxModifier;
    }
    
    public static double[] getSpeed(final double speed) {
        float moveForward = EntityUtil.mc.player.movementInput.moveForward;
        float moveStrafe = EntityUtil.mc.player.movementInput.moveStrafe;
        float rotationYaw = EntityUtil.mc.player.prevRotationYaw + (EntityUtil.mc.player.rotationYaw - EntityUtil.mc.player.prevRotationYaw) * EntityUtil.mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
            }
            else if (moveStrafe < 0.0f) {
                rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        final double posX = moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        final double posZ = moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[] { posX, posZ };
    }
    
    static {
        EntityUtil.mc = Minecraft.getMinecraft();
    }
    
    public enum SwingType
    {
        MainHand, 
        OffHand, 
        Packet;
    }
}
