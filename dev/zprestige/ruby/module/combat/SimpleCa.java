//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.*;
import java.util.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;

public class SimpleCa extends Module
{
    public static SimpleCa Instance;
    public final Slider targetRange;
    public final Slider minDamage;
    public final Slider maxSelfDamage;
    public ArrayList<Long> crystalsPerSecond;
    public BlockPos pos;
    public boolean cantPlace;
    
    public SimpleCa() {
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f);
        this.minDamage = this.Menu.Slider("Min Damage", 0.1f, 15.0f);
        this.maxSelfDamage = this.Menu.Slider("Max Self Damage", 0.1f, 15.0f);
        this.crystalsPerSecond = new ArrayList<Long>();
        this.pos = null;
        this.cantPlace = false;
        SimpleCa.Instance = this;
    }
    
    @Override
    public void onTick() {
        final EntityPlayer entityPlayer = EntityUtil.getTarget(this.targetRange.GetSlider());
        this.pos = null;
        if (entityPlayer == null) {
            return;
        }
        final TreeMap<Double, BlockPos> posses = new TreeMap<Double, BlockPos>();
        for (final BlockPos pos : BlockUtil.getSphere(this.mc.playerController.getBlockReachDistance(), BlockUtil.AirType.IgnoreAir, (EntityPlayer)this.mc.player)) {
            if (this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
                if (!this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN) && !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK)) {
                    continue;
                }
                final double selfDamage = EntityUtil.calculatePosDamage(pos, (EntityPlayer)this.mc.player);
                final double enemyDamage = EntityUtil.calculatePosDamage(pos, entityPlayer);
                final ArrayList<Entity> intersecting = (ArrayList<Entity>)this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos.up())).stream().filter(entity -> !(entity instanceof EntityEnderCrystal)).collect(Collectors.toCollection(ArrayList::new));
                if (selfDamage >= this.maxSelfDamage.GetSlider() || this.minDamage.GetSlider() >= enemyDamage || !intersecting.isEmpty()) {
                    continue;
                }
                posses.put(enemyDamage - selfDamage, pos);
            }
        }
        this.cantPlace = posses.isEmpty();
        if (!posses.isEmpty()) {
            final BlockPos pos2 = posses.lastEntry().getValue();
            final int slot = InventoryUtil.getItemFromHotbar(Items.END_CRYSTAL);
            final int currentItem = this.mc.player.inventory.currentItem;
            if (slot != -1) {
                InventoryUtil.switchToSlot(slot);
            }
            EnumFacing facing = null;
            if (BlockUtil.hasBlockEnumFacing(pos2)) {
                facing = BlockUtil.getFirstFacing(pos2);
            }
            Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos2, (facing != null) ? facing : EnumFacing.UP, (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
            if (slot != -1) {
                this.mc.player.inventory.currentItem = currentItem;
                this.mc.playerController.updateController();
            }
            this.pos = pos2;
        }
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect sPacketSoundEffect = new SPacketSoundEffect();
            if (sPacketSoundEffect.getCategory() == SoundCategory.BLOCKS && sPacketSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                final List<Entity> loadedEntityList = (List<Entity>)this.mc.world.loadedEntityList;
                final SPacketSoundEffect sPacketSoundEffect2;
                loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal && entity.getDistanceSq(sPacketSoundEffect2.getX(), sPacketSoundEffect2.getY(), sPacketSoundEffect2.getZ()) < this.mc.playerController.getBlockReachDistance() * this.mc.playerController.getBlockReachDistance()).forEach(entity -> {
                    Objects.requireNonNull(this.mc.world.getEntityByID(entity.getEntityId())).setDead();
                    this.mc.world.removeEntityFromWorld(entity.entityId);
                });
            }
        }
    }
    
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
            EntityEnderCrystal entityEnderCrystal = null;
            for (final BlockPos pos : BlockUtil.getSphere(this.mc.playerController.getBlockReachDistance(), BlockUtil.AirType.IgnoreAir, (EntityPlayer)this.mc.player)) {
                if (this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
                    if (!this.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
                        continue;
                    }
                    for (final Entity entity : this.mc.world.loadedEntityList) {
                        if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(pos.up()) < 1.0) {
                            entityEnderCrystal = (EntityEnderCrystal)entity;
                        }
                    }
                }
            }
            if (entityEnderCrystal == null) {
                return;
            }
            boolean switched = false;
            int currentItem = -1;
            final PotionEffect weakness = this.mc.player.getActivePotionEffect(MobEffects.WEAKNESS);
            if (weakness != null && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_SWORD)) {
                final int swordSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_SWORD);
                currentItem = this.mc.player.inventory.currentItem;
                InventoryUtil.switchToSlot(swordSlot);
                switched = true;
            }
            final CPacketUseEntity cPacketUseEntity = new CPacketUseEntity();
            cPacketUseEntity.entityId = entityEnderCrystal.getEntityId();
            cPacketUseEntity.action = CPacketUseEntity.Action.ATTACK;
            this.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            this.mc.player.connection.sendPacket((Packet)cPacketUseEntity);
            if (switched) {
                this.mc.player.inventory.currentItem = currentItem;
                this.mc.playerController.updateController();
            }
            entityEnderCrystal.setDead();
            this.crystalsPerSecond.add(System.currentTimeMillis() + 1000L);
        }
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        final Long currentTime = System.currentTimeMillis();
        int i = 0;
        final ArrayList<Long> crystalsPerSecond1 = new ArrayList<Long>(this.crystalsPerSecond);
        for (final Long currentTimeMillis : crystalsPerSecond1) {
            if (currentTimeMillis < currentTime) {
                this.crystalsPerSecond.remove(currentTimeMillis);
            }
            else {
                ++i;
            }
        }
        if (this.pos != null) {
            final AxisAlignedBB bb = new AxisAlignedBB(this.pos);
            RenderUtil.drawBBBox(bb, Color.WHITE, 100);
            RenderUtil.drawBlockOutlineBB(bb, Color.WHITE, 1.0f);
            RenderUtil.drawText(this.pos, i + "");
        }
    }
}
