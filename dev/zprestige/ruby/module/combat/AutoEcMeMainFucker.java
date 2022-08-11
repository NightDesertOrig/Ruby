//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.util.math.*;

public class AutoEcMeMainFucker extends Module
{
    public final Parent timing;
    public final Slider actionDelay;
    public final Slider extraRedstoneDelay;
    public final Slider postCompleteNewPosDelay;
    public final Parent ranges;
    public final Slider targetRange;
    public final Slider placeRange;
    public final Parent misc;
    public final Switch autoMine;
    public final Switch pickSwitch;
    public final Switch attemptSelfDestruct;
    public final Switch packet;
    public final Switch rotate;
    public final Switch crystalRotate;
    public final Switch placeSwing;
    public final ComboBox placeSwingHand;
    public Timer timer;
    public Timer post;
    public int boob;
    
    public AutoEcMeMainFucker() {
        this.timing = this.Menu.Parent("Timing");
        this.actionDelay = this.Menu.Slider("Action Delay", 0, 1000).parent(this.timing);
        this.extraRedstoneDelay = this.Menu.Slider("Extra Redstone Delay", 0, 1000).parent(this.timing);
        this.postCompleteNewPosDelay = this.Menu.Slider("Post Complete New Pos Delay", 0, 1000).parent(this.timing);
        this.ranges = this.Menu.Parent("Ranges");
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f).parent(this.ranges);
        this.placeRange = this.Menu.Slider("Place Range", 0.1f, 6.0f).parent(this.ranges);
        this.misc = this.Menu.Parent("Misc");
        this.autoMine = this.Menu.Switch("Auto Mine Redstone").parent(this.misc);
        this.pickSwitch = this.Menu.Switch("Pick Switch").parent(this.misc);
        this.attemptSelfDestruct = this.Menu.Switch("Attempt Self Destruct").parent(this.misc);
        this.packet = this.Menu.Switch("Packet").parent(this.misc);
        this.rotate = this.Menu.Switch("Rotate").parent(this.misc);
        this.crystalRotate = this.Menu.Switch("Crystal Rotate").parent(this.misc);
        this.placeSwing = this.Menu.Switch("Place Swing").parent(this.misc);
        this.placeSwingHand = this.Menu.ComboBox("Place Swing Hand", new String[] { "Mainhand", "Offhand", "Packet" }).parent(this.misc);
        this.timer = new Timer();
        this.post = new Timer();
        this.boob = -1;
    }
    
    @Override
    public void onEnable() {
        this.boob = -1;
    }
    
    @Override
    public void onTick() {
        final EntityPlayer target = EntityUtil.getTarget(this.targetRange.GetSlider());
        if (target == null) {
            this.disableModule("No target found, disabling AutoEcMeMainFucker.");
            return;
        }
        if (!this.isFaggot(target)) {
            return;
        }
        final BlockPos pos = EntityUtil.getPlayerPos(target).up();
        if (this.boob == -1 && this.post.getTime((long)this.postCompleteNewPosDelay.GetSlider())) {
            this.boob = this.getPossiblePosition(target);
        }
        if (this.timer.getTime((long)(this.actionDelay.GetSlider() + (this.isOperatingRedstone(pos, this.boob) ? this.extraRedstoneDelay.GetSlider() : 0.0f)))) {
            switch (this.boob) {
                case 1: {
                    this.execute(pos.north().north(), pos.north().north().up(), 1, pos.north(), pos.north().north().north().up(), pos.up(), pos.up().south());
                    this.timer.setTime(0);
                    break;
                }
                case 2: {
                    this.execute(pos.east().east(), pos.east().east().up(), 2, pos.east(), pos.east().east().east().up(), pos.up(), pos.up().west());
                    this.timer.setTime(0);
                    break;
                }
                case 3: {
                    this.execute(pos.south().south(), pos.south().south().up(), 3, pos.south(), pos.south().south().south().up(), pos.up(), pos.up().north());
                    this.timer.setTime(0);
                    break;
                }
                case 4: {
                    this.execute(pos.west().west(), pos.west().west().up(), 4, pos.west(), pos.west().west().west().up(), pos.up(), pos.up().east());
                    this.timer.setTime(0);
                    break;
                }
            }
        }
    }
    
    public boolean isOperatingRedstone(final BlockPos pos, final int i) {
        switch (i) {
            case 1: {
                return !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.north().north().up()).shrink(0.5)).isEmpty();
            }
            case 2: {
                return !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.east().east().up()).shrink(0.5)).isEmpty();
            }
            case 3: {
                return !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.south().south().up()).shrink(0.5)).isEmpty();
            }
            case 4: {
                return !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.west().west().up()).shrink(0.5)).isEmpty();
            }
            default: {
                return false;
            }
        }
    }
    
    public void execute(final BlockPos obsidian, final BlockPos piston, final int i, final BlockPos crystal, final BlockPos redstoneBlock, final BlockPos top, final BlockPos opposite) {
        if (!this.isntAir(obsidian)) {
            final int obsidianSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
            if (obsidianSlot == -1) {
                this.disableModule("No obsidian found, disabling AutoEcMeMainFucker.");
                return;
            }
            BlockUtil.placeBlockWithSwitch(obsidian, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), obsidianSlot);
        }
        else if (!this.isntAir(piston)) {
            final int pistonSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.PISTON));
            if (pistonSlot == -1) {
                this.disableModule("No piston found, disabling AutoEcMeMainFucker.");
                return;
            }
            switch (i) {
                case 1: {
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
                    break;
                }
                case 2: {
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
                    break;
                }
                case 3: {
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
                    break;
                }
                case 4: {
                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, this.mc.player.rotationPitch, this.mc.player.onGround));
                    break;
                }
            }
            BlockUtil.placeBlockWithSwitch(piston, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), pistonSlot);
        }
        else if (this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(crystal.up())).isEmpty()) {
            final int slot = InventoryUtil.getItemFromHotbar(Items.END_CRYSTAL);
            if (slot == -1) {
                this.disableModule("No end crystals found, disabling AutoEcMeMainFucker.");
                return;
            }
            final int currentItem = this.mc.player.inventory.currentItem;
            InventoryUtil.switchToSlot(slot);
            final float[] rotation = { this.mc.player.rotationYaw, this.mc.player.rotationPitch };
            if (this.crystalRotate.GetSwitch()) {
                this.posRotate(crystal);
            }
            Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(crystal, EnumFacing.UP, (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
            if (this.crystalRotate.GetSwitch()) {
                this.mc.player.rotationYaw = rotation[0];
                this.mc.player.rotationPitch = rotation[1];
            }
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
            if (this.placeSwing.GetSwitch()) {
                EntityUtil.swingArm(this.placeSwingHand.GetCombo().equals("Mainhand") ? EntityUtil.SwingType.MainHand : (this.placeSwingHand.GetCombo().equals("Offhand") ? EntityUtil.SwingType.OffHand : EntityUtil.SwingType.Packet));
            }
        }
        else if (this.attemptSelfDestruct.GetSwitch() && !this.isntAir(opposite)) {
            final int obsidianSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
            if (obsidianSlot == -1) {
                this.disableModule("No obsidian found, disabling AutoEcMeMainFucker.");
                return;
            }
            BlockUtil.placeBlockWithSwitch(opposite, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), obsidianSlot);
        }
        else {
            if (!this.isntAir(redstoneBlock)) {
                if (this.timer.getTime((long)(this.actionDelay.GetSlider() + this.extraRedstoneDelay.GetSlider()))) {
                    final int redstoneSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK));
                    if (redstoneSlot == -1) {
                        this.disableModule("No redstone blocks found, disabling AutoEcMeMainFucker.");
                        return;
                    }
                    BlockUtil.placeBlockWithSwitch(redstoneBlock, EnumHand.MAIN_HAND, this.rotate.GetSwitch(), this.packet.GetSwitch(), redstoneSlot);
                    if (this.autoMine.GetSwitch()) {
                        if (this.pickSwitch.GetSwitch()) {
                            final int slot2 = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
                            if (slot2 == -1) {
                                return;
                            }
                            InventoryUtil.switchToSlot(slot2);
                        }
                        EntityUtil.swingArm(EntityUtil.SwingType.MainHand);
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, redstoneBlock, EnumFacing.UP));
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, redstoneBlock, EnumFacing.UP));
                    }
                }
                return;
            }
            if (!this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(top)).isEmpty()) {
                for (final Entity entity : this.mc.world.loadedEntityList) {
                    if (entity instanceof EntityEnderCrystal && this.mc.player.getDistance(entity) < this.placeRange.GetSlider()) {
                        final float[] rotation = { this.mc.player.rotationYaw, this.mc.player.rotationPitch };
                        if (this.crystalRotate.GetSwitch()) {
                            this.entityRotate(entity);
                        }
                        Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketUseEntity(entity));
                        if (this.crystalRotate.GetSwitch()) {
                            this.mc.player.rotationYaw = rotation[0];
                            this.mc.player.rotationPitch = rotation[1];
                        }
                        this.boob = -1;
                        this.post.setTime(0);
                    }
                }
            }
        }
    }
    
    public void entityRotate(final Entity entity) {
        final float[] angle = BlockUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), entity.getPositionVector());
        this.mc.player.rotationYaw = angle[0];
        this.mc.player.rotationPitch = angle[1];
    }
    
    public void posRotate(final BlockPos pos) {
        final float[] angle = BlockUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() - 0.5f), (double)(pos.getZ() + 0.5f)));
        this.mc.player.rotationYaw = angle[0];
        this.mc.player.rotationPitch = angle[1];
    }
    
    public int getPossiblePosition(final EntityPlayer entityPlayer) {
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer).up();
        if ((this.mc.world.getBlockState(pos.north().north()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.north().north()).getBlock().equals(Blocks.BEDROCK) || this.mc.world.getBlockState(pos.north().north()).getBlock().equals(Blocks.AIR)) && this.mc.world.getBlockState(pos.north().up()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.north().up().up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.north().north().up()).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(pos.north().north().up()).getBlock().equals(Blocks.PISTON)) && this.mc.world.getBlockState(pos.north().north().north().up()).getBlock().equals(Blocks.AIR) && this.mc.player.getDistanceSq(pos.up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.north().north()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.north().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.north().up().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.north().north().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.north().north().north().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
            return 1;
        }
        if ((this.mc.world.getBlockState(pos.east().east()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.east().east()).getBlock().equals(Blocks.BEDROCK) || this.mc.world.getBlockState(pos.east().east()).getBlock().equals(Blocks.AIR)) && this.mc.world.getBlockState(pos.east().up()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.east().up().up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.east().east().up()).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(pos.east().east().up()).getBlock().equals(Blocks.PISTON)) && this.mc.world.getBlockState(pos.east().east().east().up()).getBlock().equals(Blocks.AIR) && this.mc.player.getDistanceSq(pos.up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.east().east()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.east().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.east().up().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.east().east().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.east().east().east().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
            return 2;
        }
        if ((this.mc.world.getBlockState(pos.south().south()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.south().south()).getBlock().equals(Blocks.BEDROCK) || this.mc.world.getBlockState(pos.south().south()).getBlock().equals(Blocks.AIR)) && this.mc.world.getBlockState(pos.south().up()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.south().up().up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.south().south().up()).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(pos.south().south().up()).getBlock().equals(Blocks.PISTON)) && this.mc.world.getBlockState(pos.south().south().south().up()).getBlock().equals(Blocks.AIR) && this.mc.player.getDistanceSq(pos.up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.south().south()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.south().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.south().up().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.south().south().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.south().south().south().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
            return 3;
        }
        if ((this.mc.world.getBlockState(pos.west().west()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.west().west()).getBlock().equals(Blocks.BEDROCK) || this.mc.world.getBlockState(pos.west().west()).getBlock().equals(Blocks.AIR)) && this.mc.world.getBlockState(pos.west().up()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.west().up().up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.west().west().up()).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(pos.west().west().up()).getBlock().equals(Blocks.PISTON)) && this.mc.world.getBlockState(pos.west().west().west().up()).getBlock().equals(Blocks.AIR) && this.mc.player.getDistanceSq(pos.up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.west().west()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.west().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.west().up().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.west().west().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && this.mc.player.getDistanceSq(pos.west().west().west().up()) < this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
            return 4;
        }
        return -1;
    }
    
    public boolean isFaggot(final EntityPlayer entityPlayer) {
        final BlockPos playerPosUp = EntityUtil.getPlayerPos(entityPlayer).up();
        return BlockUtil.isPlayerSafe(entityPlayer) && this.isntAir(playerPosUp.north()) && this.isntAir(playerPosUp.east()) && this.isntAir(playerPosUp.south()) && this.isntAir(playerPosUp.west()) && this.isntAir(playerPosUp.up());
    }
    
    public boolean isntAir(final BlockPos pos) {
        return !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
    }
}
