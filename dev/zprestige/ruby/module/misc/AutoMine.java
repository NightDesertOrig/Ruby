//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;

public class AutoMine extends Module
{
    public final ComboBox mineMode;
    public final ComboBox priority;
    public final Slider targetRange;
    public final Slider breakRange;
    public final Switch rotateToPos;
    public final Switch silentSwitch;
    public final Switch preSwitch;
    public final ColorSwitch renderPacket;
    public Timer timer;
    public ArrayList<BlockPos> surround;
    public ArrayList<BlockPos> perfectCityPosses;
    public BlockPos burrowPos;
    public BlockPos currentMinePos;
    public BlockPos prevMinePosEcMe;
    public float size;
    
    public AutoMine() {
        this.mineMode = this.Menu.ComboBox("Mine Mode", new String[] { "Vanilla", "Packet", "EcMe" });
        this.priority = this.Menu.ComboBox("Priority", new String[] { "City > Surround > AnvilBurrow", "City > AnvilBurrow > Surround", "Surround > City > AnvilBurrow", "Surround > AnvilBurrow > City", "AnvilBurrow > City > Surround", "AnvilBurrow > Surround > City" });
        this.targetRange = this.Menu.Slider("Target Range", 0.1f, 15.0f);
        this.breakRange = this.Menu.Slider("Break Range", 0.1f, 6.0f);
        this.rotateToPos = this.Menu.Switch("Rotate To Pos");
        this.silentSwitch = this.Menu.Switch("Silent Switch");
        this.preSwitch = this.Menu.Switch("Pre Switch");
        this.renderPacket = this.Menu.ColorSwitch("Render Packet");
        this.timer = new Timer();
        this.surround = new ArrayList<BlockPos>();
        this.perfectCityPosses = new ArrayList<BlockPos>();
        this.burrowPos = null;
        this.currentMinePos = null;
        this.prevMinePosEcMe = null;
        this.size = 0.0f;
    }
    
    public void setup(final BlockPos pos) {
        if (this.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.ENDER_CHEST)) {
            this.addToSurround(pos.north());
        }
        if (this.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.ENDER_CHEST)) {
            this.addToSurround(pos.east());
        }
        if (this.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.ENDER_CHEST)) {
            this.addToSurround(pos.south());
        }
        if (this.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.ENDER_CHEST)) {
            this.addToSurround(pos.west());
        }
        if (this.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN) && this.mc.world.getBlockState(pos.north().north()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.north().north().up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.north().north().down()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.north().north().down()).getBlock().equals(Blocks.BEDROCK))) {
            this.addToCity(pos.north());
        }
        if (this.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN) && this.mc.world.getBlockState(pos.east().east()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.east().east().up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.east().east().down()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.east().east().down()).getBlock().equals(Blocks.BEDROCK))) {
            this.addToCity(pos.east());
        }
        if (this.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN) && this.mc.world.getBlockState(pos.south().south()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.south().south().up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.south().south().down()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.south().south().down()).getBlock().equals(Blocks.BEDROCK))) {
            this.addToCity(pos.south());
        }
        if (this.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN) && this.mc.world.getBlockState(pos.west().west()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(pos.west().west().up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.west().west().down()).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos.west().west().down()).getBlock().equals(Blocks.BEDROCK))) {
            this.addToCity(pos.west());
        }
        if (!this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
            this.burrowPos = pos;
        }
    }
    
    public void addToSurround(final BlockPos blockPos) {
        this.surround.addAll(Collections.singletonList(blockPos));
    }
    
    public void addToCity(final BlockPos blockPos) {
        this.perfectCityPosses.addAll(Collections.singletonList(blockPos));
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        if (this.currentMinePos != null && !this.mc.world.getBlockState(this.currentMinePos).getBlock().equals(Blocks.AIR) && this.renderPacket.GetSwitch() && this.mineMode.GetCombo().equals("Packet")) {
            RenderUtil.drawFullBox(true, true, this.renderPacket.GetColor(), this.renderPacket.GetColor(), 1.0f, this.currentMinePos);
        }
    }
    
    public void posRotate(final BlockPos pos) {
        final float[] angle = BlockUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() - 0.5f), (double)(pos.getZ() + 0.5f)));
        this.mc.player.rotationYaw = angle[0];
        this.mc.player.rotationPitch = angle[1];
    }
    
    @Override
    public void onTick() {
        final EntityPlayer entityPlayer = EntityUtil.getTarget(this.targetRange.GetSlider());
        final int pickSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
        if (entityPlayer == null || pickSlot == -1) {
            return;
        }
        final BlockPos targetPos = EntityUtil.getPlayerPos(entityPlayer);
        this.surround.clear();
        this.perfectCityPosses.clear();
        this.burrowPos = null;
        this.setup(targetPos);
        if (this.mc.player.getDistanceSq(targetPos.north()) > this.breakRange.GetSlider() * this.breakRange.GetSlider() || this.mc.player.getDistanceSq(targetPos.east()) > this.breakRange.GetSlider() * this.breakRange.GetSlider() || this.mc.player.getDistanceSq(targetPos.south()) > this.breakRange.GetSlider() * this.breakRange.GetSlider() || this.mc.player.getDistanceSq(targetPos.west()) > this.breakRange.GetSlider() * this.breakRange.GetSlider() || this.mc.player.getDistanceSq(targetPos) > this.breakRange.GetSlider() * this.breakRange.GetSlider()) {
            return;
        }
        if (this.mineMode.GetCombo().equals("EcMe")) {
            if (!this.surround.isEmpty()) {
                final BlockPos pos = this.surround.get(0);
                if (pos != this.prevMinePosEcMe) {
                    this.size = 0.0f;
                }
                final float[] rotations = { this.mc.player.rotationYaw, this.mc.player.rotationPitch };
                if (this.rotateToPos.GetSwitch()) {
                    this.posRotate(pos);
                }
                final int slot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
                final int currentItem = this.mc.player.inventory.currentItem;
                if (this.silentSwitch.GetSwitch() && slot != -1) {
                    InventoryUtil.switchToSlot(slot);
                }
                if (this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE) || this.silentSwitch.GetSwitch()) {
                    this.mc.playerController.onPlayerDamageBlock(pos, this.mc.objectMouseOver.sideHit);
                    this.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                this.size += 0.25f;
                if (this.silentSwitch.GetSwitch() && slot != -1) {
                    this.mc.player.inventory.currentItem = currentItem;
                    this.mc.playerController.updateController();
                }
                if (this.rotateToPos.GetSwitch()) {
                    this.mc.player.rotationYaw = rotations[0];
                    this.mc.player.rotationPitch = rotations[1];
                }
                this.prevMinePosEcMe = pos;
            }
            return;
        }
        if (this.currentMinePos != null) {
            if (this.timer.getTime(2000L)) {
                final int slot2 = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
                if (!this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE) && slot2 != -1 && this.currentMinePos != null) {
                    final int currentItem2 = this.mc.player.inventory.currentItem;
                    InventoryUtil.switchToSlot(slot2);
                    Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.currentMinePos, EnumFacing.UP));
                    this.mc.player.inventory.currentItem = currentItem2;
                    this.mc.playerController.updateController();
                }
                this.currentMinePos = null;
            }
            return;
        }
        final String getCombo = this.priority.GetCombo();
        switch (getCombo) {
            case "City > Surround > AnvilBurrow": {
                if (!this.perfectCityPosses.isEmpty()) {
                    final BlockPos pos2 = this.perfectCityPosses.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                if (!this.surround.isEmpty()) {
                    final BlockPos pos2 = this.surround.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                if (this.burrowPos != null) {
                    this.mineBlock(this.burrowPos);
                    break;
                }
                break;
            }
            case "City > AnvilBurrow > Surround": {
                if (!this.perfectCityPosses.isEmpty()) {
                    final BlockPos pos2 = this.perfectCityPosses.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                if (this.burrowPos != null) {
                    this.mineBlock(this.burrowPos);
                    break;
                }
                if (!this.surround.isEmpty()) {
                    final BlockPos pos2 = this.surround.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                break;
            }
            case "Surround > City > AnvilBurrow": {
                if (!this.surround.isEmpty()) {
                    final BlockPos pos2 = this.surround.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                if (!this.perfectCityPosses.isEmpty()) {
                    final BlockPos pos2 = this.perfectCityPosses.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                if (this.burrowPos != null) {
                    this.mineBlock(this.burrowPos);
                    break;
                }
                break;
            }
            case "Surround > AnvilBurrow > City": {
                if (!this.surround.isEmpty()) {
                    final BlockPos pos2 = this.surround.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                if (this.burrowPos != null) {
                    this.mineBlock(this.burrowPos);
                    break;
                }
                if (!this.perfectCityPosses.isEmpty()) {
                    final BlockPos pos2 = this.perfectCityPosses.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                break;
            }
            case "AnvilBurrow > City > Surround": {
                if (this.burrowPos != null) {
                    this.mineBlock(this.burrowPos);
                    break;
                }
                if (!this.perfectCityPosses.isEmpty()) {
                    final BlockPos pos2 = this.perfectCityPosses.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                if (!this.surround.isEmpty()) {
                    final BlockPos pos2 = this.surround.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                break;
            }
            case "AnvilBurrow > Surround > City": {
                if (this.burrowPos != null) {
                    this.mineBlock(this.burrowPos);
                    break;
                }
                if (!this.surround.isEmpty()) {
                    final BlockPos pos2 = this.surround.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                if (!this.perfectCityPosses.isEmpty()) {
                    final BlockPos pos2 = this.perfectCityPosses.get(0);
                    this.mineBlock(pos2);
                    break;
                }
                break;
            }
        }
    }
    
    public void mineBlock(final BlockPos pos) {
        final int currentItem = this.mc.player.inventory.currentItem;
        if (this.preSwitch.GetSwitch()) {
            final int slot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
            InventoryUtil.switchToSlot(slot);
        }
        final String getCombo = this.mineMode.GetCombo();
        switch (getCombo) {
            case "Packet": {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
                EntityUtil.swingArm(EntityUtil.SwingType.MainHand);
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
                this.timer.setTime(0);
                this.currentMinePos = pos;
                break;
            }
            case "Vanilla": {
                this.mc.playerController.onPlayerDamageBlock(pos, this.mc.player.getHorizontalFacing());
                EntityUtil.swingArm(EntityUtil.SwingType.MainHand);
                this.timer.setTime(0);
                this.currentMinePos = pos;
                break;
            }
        }
        if (this.preSwitch.GetSwitch()) {
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
        }
    }
}
