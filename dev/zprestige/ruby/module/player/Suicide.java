//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.*;

public class Suicide extends Module
{
    public final Slider throwDelay;
    public final Parent placing;
    public final Slider placeDelay;
    public final Slider placeRange;
    public final Switch silentSwitchCrystal;
    public final Switch packetPlaceCrystal;
    public final Switch placeSwing;
    public final ComboBox placeSwingHand;
    public final Parent breaking;
    public final Slider breakDelay;
    public final Slider breakRange;
    public final Switch explodeAntiWeakness;
    public final Switch packetBreakCrystal;
    public final Switch breakSwing;
    public final ComboBox breakSwingHand;
    public Timer throwTimer;
    public Timer placeTimer;
    public Timer breakTimer;
    public int currentTakeoff;
    
    public Suicide() {
        this.throwDelay = this.Menu.Slider("Throw Delay", 0, 500);
        this.placing = this.Menu.Parent("Placing");
        this.placeDelay = this.Menu.Slider("Place Delay Crystal", 0, 500).parent(this.placing);
        this.placeRange = this.Menu.Slider("Place Range Crystal", 0.0f, 6.0f).parent(this.placing);
        this.silentSwitchCrystal = this.Menu.Switch("Silent Switch Crystal").parent(this.placing);
        this.packetPlaceCrystal = this.Menu.Switch("Packet Place Crystal").parent(this.placing);
        this.placeSwing = this.Menu.Switch("Place Swing Crystal").parent(this.placing);
        this.placeSwingHand = this.Menu.ComboBox("Place Swing Hand Crystal", new String[] { "Mainhand", "Offhand", "Packet" }).parent(this.placing);
        this.breaking = this.Menu.Parent("Breaking");
        this.breakDelay = this.Menu.Slider("Break Delay Crystal", 0, 500).parent(this.breaking);
        this.breakRange = this.Menu.Slider("Break Range Crystal", 0.0f, 6.0f).parent(this.breaking);
        this.explodeAntiWeakness = this.Menu.Switch("Explode Anti Weakness Crystal").parent(this.breaking);
        this.packetBreakCrystal = this.Menu.Switch("Packet Break Crystal").parent(this.breaking);
        this.breakSwing = this.Menu.Switch("Break Swing").parent(this.breaking);
        this.breakSwingHand = this.Menu.ComboBox("Break Swing", new String[] { "Mainhand", "Offhand", "Packet" }).parent(this.breaking);
        this.throwTimer = new Timer();
        this.placeTimer = new Timer();
        this.breakTimer = new Timer();
    }
    
    public void onTick() {
        this.currentTakeoff = this.getCurrentTakeOff();
        if (this.currentTakeoff != -1 && this.throwTimer.getTime((long)this.throwDelay.GetSlider())) {
            this.takeOff(this.currentTakeoff);
            return;
        }
        if (this.mc.player.getHeldItemOffhand().getItem().equals(Items.TOTEM_OF_UNDYING)) {
            this.takeOff(45);
            return;
        }
        final int totemSlot = InventoryUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
        if (totemSlot != -1 && this.throwTimer.getTime((long)this.throwDelay.GetSlider())) {
            this.takeOff(totemSlot);
            return;
        }
        if (!this.placeTimer.getTime((long)this.placeDelay.GetSlider())) {
            if (this.breakTimer.getTime((long)this.breakDelay.GetSlider())) {
                for (final Entity entity : this.mc.world.loadedEntityList) {
                    if (entity instanceof EntityEnderCrystal) {
                        if (this.mc.player.getDistance(entity) > this.breakRange.GetSlider()) {
                            continue;
                        }
                        final float selfDamage = EntityUtil.calculatePosDamage(new BlockPos(entity.posX + 0.5, entity.posY, entity.posZ + 0.5), (EntityPlayer)this.mc.player);
                        if (selfDamage < this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) {
                            continue;
                        }
                        this.breakCrystal((EntityEnderCrystal)entity);
                    }
                }
            }
            return;
        }
        final BlockPos pos = this.getPosition();
        if (pos == null) {
            return;
        }
        this.placeCrystal(pos);
    }
    
    public void takeOff(final int i) {
        this.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(0, -999, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.throwTimer.setTime(0);
    }
    
    public int getCurrentTakeOff() {
        final Item head = this.mc.player.inventory.getStackInSlot(39).getItem();
        final Item chest = this.mc.player.inventory.getStackInSlot(38).getItem();
        final Item legs = this.mc.player.inventory.getStackInSlot(37).getItem();
        final Item feet = this.mc.player.inventory.getStackInSlot(36).getItem();
        if (!head.equals(Items.AIR)) {
            return 5;
        }
        if (!chest.equals(Items.AIR)) {
            return 6;
        }
        if (!legs.equals(Items.AIR)) {
            return 7;
        }
        if (!feet.equals(Items.AIR)) {
            return 8;
        }
        return -1;
    }
    
    public BlockPos getPosition() {
        final TreeMap<Float, BlockPos> treeMap = new TreeMap<Float, BlockPos>();
        for (final BlockPos pos : BlockUtil.getSphereAutoCrystal(this.placeRange.GetSlider(), true)) {
            final float selfDamage = EntityUtil.calculatePosDamage(pos, (EntityPlayer)this.mc.player);
            if (this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up())).isEmpty()) {
                treeMap.put(selfDamage, pos);
            }
        }
        if (!treeMap.isEmpty()) {
            return treeMap.lastEntry().getValue();
        }
        return null;
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
        if (this.packetPlaceCrystal.GetSwitch()) {
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
        this.placeTimer.setTime(0);
    }
    
    public void breakCrystal(final EntityEnderCrystal entity) {
        boolean switched = false;
        int currentItem = -1;
        if (this.explodeAntiWeakness.GetSwitch()) {
            final PotionEffect weakness = this.mc.player.getActivePotionEffect(MobEffects.WEAKNESS);
            if (weakness != null && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_SWORD)) {
                final int swordSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_SWORD);
                currentItem = this.mc.player.inventory.currentItem;
                InventoryUtil.switchToSlot(swordSlot);
                switched = true;
            }
        }
        if (this.packetBreakCrystal.GetSwitch()) {
            Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketUseEntity((Entity)entity));
        }
        else {
            this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)entity);
        }
        if (switched) {
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
        }
        if (this.breakSwing.GetSwitch()) {
            EntityUtil.swingArm(this.breakSwingHand.GetCombo().equals("Mainhand") ? EntityUtil.SwingType.MainHand : (this.breakSwingHand.GetCombo().equals("Offhand") ? EntityUtil.SwingType.OffHand : EntityUtil.SwingType.Packet));
        }
        this.breakTimer.setTime(0);
        this.disableModule("Completed suicide");
    }
}
