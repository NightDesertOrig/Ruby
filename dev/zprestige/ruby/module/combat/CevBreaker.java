//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

public class CevBreaker extends Module
{
    public final Slider targetRange;
    public final Slider actionDelay;
    public final Parent placing;
    public final Switch silentSwitchCrystal;
    public final Switch packetPlace;
    public final Switch placeSwing;
    public final ComboBox placeSwingHand;
    public final Parent exploding;
    public final Switch packetExplodeCrystal;
    public final Switch explodeSwing;
    public final ComboBox explodeSwingHand;
    public final Parent misc;
    public final Switch selfSafe;
    public final Switch packet;
    public final Switch rotate;
    public Timer timer;
    public boolean isMining;
    public boolean needsCrystal;
    
    public CevBreaker() {
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 6.0f);
        this.actionDelay = this.Menu.Slider("Action Delay", 0, 1000);
        this.placing = this.Menu.Parent("Placing");
        this.silentSwitchCrystal = this.Menu.Switch("Silent Switch Crystal").parent(this.placing);
        this.packetPlace = this.Menu.Switch("Packet Place Crystal").parent(this.placing);
        this.placeSwing = this.Menu.Switch("Place Swing Crystal").parent(this.placing);
        this.placeSwingHand = this.Menu.ComboBox("Place Swing Hand Crystal", new String[] { "Mainhand", "Offhand", "Packet" }).parent(this.placing);
        this.exploding = this.Menu.Parent("Exploding");
        this.packetExplodeCrystal = this.Menu.Switch("Packet Explode Crystal").parent(this.exploding);
        this.explodeSwing = this.Menu.Switch("Explode Swing Crystal").parent(this.exploding);
        this.explodeSwingHand = this.Menu.ComboBox("Explode Swing Hand Crystal", new String[] { "Mainhand", "Offhand", "Packet" }).parent(this.exploding);
        this.misc = this.Menu.Parent("Misc");
        this.selfSafe = this.Menu.Switch("Self Safe").parent(this.misc);
        this.packet = this.Menu.Switch("Packet").parent(this.misc);
        this.rotate = this.Menu.Switch("Rotate").parent(this.misc);
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
        this.needsCrystal = false;
        this.isMining = false;
        this.timer.setTime(0);
    }
    
    @Override
    public void onTick() {
        final EntityPlayer entityPlayer = EntityUtil.getTarget(this.targetRange.GetSlider());
        if (entityPlayer == null) {
            return;
        }
        if (!BlockUtil.isPlayerSafe(entityPlayer) || (this.selfSafe.GetSwitch() && !BlockUtil.isPlayerSafe((EntityPlayer)this.mc.player))) {
            return;
        }
        if (!this.isPlayerCevBreakable(entityPlayer)) {
            return;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        if (slot == -1) {
            this.disableModule("No obsidian found in hotbar, disabling CevBreaker.");
            return;
        }
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer).up().up();
        if (!this.needsCrystal && this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && this.isPosSurroundedByBlocks(pos) && this.timer.getTime((long)this.actionDelay.GetSlider())) {
            BlockUtil.placeBlockWithSwitch(pos, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), slot);
            this.timer.setTime(0);
            this.needsCrystal = true;
        }
        if (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN) && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up())).isEmpty() && this.timer.getTime((long)this.actionDelay.GetSlider())) {
            this.placeCrystal(pos);
            this.clickBlock(pos);
            this.isMining = true;
            this.timer.setTime(0);
        }
        if (this.isMining && this.timer.getTime(2000L)) {
            final int slot2 = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
            if (!this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE) && slot2 != -1) {
                this.clickBlock(pos);
            }
            this.isMining = false;
        }
        if (this.timer.getTime((long)(2000.0f + this.actionDelay.GetSlider()))) {
            for (final Entity entity : this.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    continue;
                }
                if (entity.getDistanceSq(pos) > 4.0) {
                    continue;
                }
                if (this.packetExplodeCrystal.GetSwitch()) {
                    Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketUseEntity(entity));
                }
                else {
                    this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, entity);
                }
                if (this.explodeSwing.GetSwitch()) {
                    EntityUtil.swingArm(this.explodeSwingHand.GetCombo().equals("Mainhand") ? EntityUtil.SwingType.MainHand : (this.explodeSwingHand.GetCombo().equals("Offhand") ? EntityUtil.SwingType.OffHand : EntityUtil.SwingType.Packet));
                }
                this.needsCrystal = false;
            }
        }
    }
    
    public void placeCrystal(final BlockPos pos) {
        if (!this.silentSwitchCrystal.GetSwitch() && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.END_CRYSTAL) && !this.mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL)) {
            return;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Items.END_CRYSTAL);
        final int currentItem = this.mc.player.inventory.currentItem;
        if (this.silentSwitchCrystal.GetSwitch() && slot != -1 && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.END_CRYSTAL) && !this.mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL)) {
            InventoryUtil.switchToSlot(slot);
        }
        if (this.packetPlace.GetSwitch()) {
            Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        }
        else {
            this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, pos, EnumFacing.UP, new Vec3d(this.mc.player.posX, -this.mc.player.posY, -this.mc.player.posZ), this.mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
        if (this.silentSwitchCrystal.GetSwitch()) {
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
        }
        if (this.placeSwing.GetSwitch()) {
            EntityUtil.swingArm(this.placeSwingHand.GetCombo().equals("Mainhand") ? EntityUtil.SwingType.MainHand : (this.placeSwingHand.GetCombo().equals("Offhand") ? EntityUtil.SwingType.OffHand : EntityUtil.SwingType.Packet));
        }
    }
    
    public boolean isPosSurroundedByBlocks(final BlockPos blockPos) {
        return !this.mc.world.getBlockState(blockPos.north()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(blockPos.east()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(blockPos.south()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(blockPos.west()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(blockPos.down()).getBlock().equals(Blocks.AIR) || !this.mc.world.getBlockState(blockPos.up()).getBlock().equals(Blocks.AIR);
    }
    
    public boolean isPlayerCevBreakable(final EntityPlayer entityPlayer) {
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer).up().up();
        return (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN)) && this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR);
    }
    
    public EnumFacing getEmptyNeighbour(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (this.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(this.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = this.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }
    
    public void clickBlock(final BlockPos pos) {
        final EnumFacing facing = this.getEmptyNeighbour(pos);
        EntityUtil.swingArm(EntityUtil.SwingType.MainHand);
        this.mc.playerController.clickBlock(pos, facing);
    }
}
