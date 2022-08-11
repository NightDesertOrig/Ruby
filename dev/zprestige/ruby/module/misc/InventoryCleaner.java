//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class InventoryCleaner extends Module
{
    public final Slider throwDelay;
    public final Parent items;
    public final Switch chorusFruits;
    public final Switch obsidian;
    public final Switch enderChests;
    public final Switch swords;
    public final Switch pickaxe;
    public final Switch pearls;
    public final Switch bows;
    public Timer throwTimer;
    
    public InventoryCleaner() {
        this.throwDelay = this.Menu.Slider("Throw Delay", 0, 500);
        this.items = this.Menu.Parent("Items");
        this.chorusFruits = this.Menu.Switch("Chorus Fruits").parent(this.items);
        this.obsidian = this.Menu.Switch("Obsidian").parent(this.items);
        this.enderChests = this.Menu.Switch("EnderChest").parent(this.items);
        this.swords = this.Menu.Switch("Swords").parent(this.items);
        this.pickaxe = this.Menu.Switch("Pickaxe").parent(this.items);
        this.pearls = this.Menu.Switch("Pearls").parent(this.items);
        this.bows = this.Menu.Switch("Bows").parent(this.items);
        this.throwTimer = new Timer();
    }
    
    @Override
    public void onTick() {
        if (this.throwTimer.getTime((long)this.throwDelay.GetSlider()) && this.getItemSlot() != -1) {
            this.throwAway(this.getItemSlot());
        }
    }
    
    public int getItemSlot() {
        if (this.chorusFruits.GetSwitch()) {
            final int hotbarSlot = InventoryUtil.getItemFromHotbar(Items.CHORUS_FRUIT);
            if (hotbarSlot != -1) {
                final int nonHotbarSlot = InventoryUtil.getItemSlotNonHotbar(Items.CHORUS_FRUIT);
                if (nonHotbarSlot != -1) {
                    return nonHotbarSlot;
                }
            }
        }
        if (this.obsidian.GetSwitch()) {
            final int hotbarSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
            if (hotbarSlot != -1) {
                final int nonHotbarSlot = InventoryUtil.getItemSlotNonHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
                if (nonHotbarSlot != -1) {
                    return nonHotbarSlot;
                }
            }
        }
        if (this.enderChests.GetSwitch()) {
            final int hotbarSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
            if (hotbarSlot != -1) {
                final int nonHotbarSlot = InventoryUtil.getItemSlotNonHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
                if (nonHotbarSlot != -1) {
                    return nonHotbarSlot;
                }
            }
        }
        if (this.swords.GetSwitch()) {
            final int hotbarSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_SWORD);
            if (hotbarSlot != -1) {
                final int nonHotbarSlot = InventoryUtil.getItemSlotNonHotbar(Items.DIAMOND_SWORD);
                if (nonHotbarSlot != -1) {
                    return nonHotbarSlot;
                }
            }
        }
        if (this.pickaxe.GetSwitch()) {
            final int hotbarSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
            if (hotbarSlot != -1) {
                final int nonHotbarSlot = InventoryUtil.getItemSlotNonHotbar(Items.DIAMOND_PICKAXE);
                if (nonHotbarSlot != -1) {
                    return nonHotbarSlot;
                }
            }
        }
        if (this.pearls.GetSwitch()) {
            final int hotbarSlot = InventoryUtil.getItemFromHotbar(Items.ENDER_PEARL);
            if (hotbarSlot != -1) {
                final int nonHotbarSlot = InventoryUtil.getItemSlotNonHotbar(Items.ENDER_PEARL);
                if (nonHotbarSlot != -1) {
                    return nonHotbarSlot;
                }
            }
        }
        if (this.bows.GetSwitch()) {
            final int hotbarSlot = InventoryUtil.getItemFromHotbar((Item)Items.BOW);
            if (hotbarSlot != -1) {
                return InventoryUtil.getItemSlotNonHotbar((Item)Items.BOW);
            }
        }
        return -1;
    }
    
    public void throwAway(final int i) {
        this.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(0, -999, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.throwTimer.setTime(0);
    }
}
