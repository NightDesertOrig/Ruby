//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.item.*;

public class AutoArmor extends Module
{
    public static AutoArmor Instance;
    public final Slider takeOnDelay;
    public Timer timer;
    
    public AutoArmor() {
        this.takeOnDelay = this.Menu.Slider("Take On Delay", 0, 500);
        this.timer = new Timer();
        AutoArmor.Instance = this;
    }
    
    public void onTick() {
        if (this.mc.currentScreen != null) {
            return;
        }
        if (this.getSlot() != -1 && this.timer.getTime((long)this.takeOnDelay.GetSlider())) {
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.getSlot(), 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
            this.timer.setTime(0);
        }
    }
    
    public int getSlot() {
        if (this.mc.player.inventory.getStackInSlot(39).getItem().equals(Items.AIR) && InventoryUtil.getItemSlot((Item)Items.DIAMOND_HELMET) != -1) {
            return InventoryUtil.getItemSlot((Item)Items.DIAMOND_HELMET);
        }
        if (this.mc.player.inventory.getStackInSlot(38).getItem().equals(Items.AIR) && InventoryUtil.getItemSlot((Item)Items.DIAMOND_CHESTPLATE) != -1) {
            return InventoryUtil.getItemSlot((Item)Items.DIAMOND_CHESTPLATE);
        }
        if (this.mc.player.inventory.getStackInSlot(37).getItem().equals(Items.AIR) && InventoryUtil.getItemSlot((Item)Items.DIAMOND_LEGGINGS) != -1) {
            return InventoryUtil.getItemSlot((Item)Items.DIAMOND_LEGGINGS);
        }
        if (this.mc.player.inventory.getStackInSlot(36).getItem().equals(Items.AIR) && InventoryUtil.getItemSlot((Item)Items.DIAMOND_BOOTS) != -1) {
            return InventoryUtil.getItemSlot((Item)Items.DIAMOND_BOOTS);
        }
        return -1;
    }
}
