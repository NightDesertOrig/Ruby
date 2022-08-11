//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.events.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class Offhand extends Module
{
    public final ComboBox item;
    public final Parent crystalMisc;
    public final Switch crystalOnSword;
    public final Switch crystalOnPickaxe;
    public final Parent health;
    public final Slider totemHealth;
    public final Switch hole;
    public final Slider holeHealth;
    public final Parent timing;
    public final Slider switchDelay;
    public final Parent forcing;
    public final Switch postPopForceTotem;
    public final Slider forceTime;
    public final Switch fallDistance;
    public final Slider minDistance;
    public final Parent misc;
    public final Switch fallBack;
    public final Switch gapple;
    public final Switch rightClick;
    public final Parent threading;
    public final Switch threadSwap;
    public final Slider threadSwapAmount;
    public final Switch threadFindingItem;
    public final Slider threadFindingItemAmount;
    public Timer switchTimer;
    public Timer postPopTimer;
    public int offhandSlot;
    public Thread itemThread;
    public Thread offhandThread;
    
    public Offhand() {
        this.item = this.Menu.ComboBox("Item", new String[] { "Crystal", "Totem" });
        this.crystalMisc = this.Menu.Parent("Crystal Misc");
        this.crystalOnSword = this.Menu.Switch("Sword Crystal").parent(this.crystalMisc);
        this.crystalOnPickaxe = this.Menu.Switch("Pickaxe Crystal").parent(this.crystalMisc);
        this.health = this.Menu.Parent("Health");
        this.totemHealth = this.Menu.Slider("Totem Health", 0.0f, 20.0f).parent(this.health);
        this.hole = this.Menu.Switch("Hole Check").parent(this.health);
        this.holeHealth = this.Menu.Slider("Totem Hole Health", 0.0f, 20.0f).parent(this.health);
        this.timing = this.Menu.Parent("Timing");
        this.switchDelay = this.Menu.Slider("Switch Delay", 0, 200).parent(this.timing);
        this.forcing = this.Menu.Parent("Forcing");
        this.postPopForceTotem = this.Menu.Switch("Post Pop Force Totem").parent(this.forcing);
        this.forceTime = this.Menu.Slider("Force Time", 0, 3000).parent(this.forcing);
        this.fallDistance = this.Menu.Switch("Fall Distance Check").parent(this.forcing);
        this.minDistance = this.Menu.Slider("Min Distance", 1.0f, 100.0f).parent(this.forcing);
        this.misc = this.Menu.Parent("Misc");
        this.fallBack = this.Menu.Switch("FallBack").parent(this.misc);
        this.gapple = this.Menu.Switch("Gapple Switch").parent(this.misc);
        this.rightClick = this.Menu.Switch("Right Click Only").parent(this.misc);
        this.threading = this.Menu.Parent("Threading");
        this.threadSwap = this.Menu.Switch("Thread Swap").parent(this.threading);
        this.threadSwapAmount = this.Menu.Slider("Thread Swap Amount", 1, 10).parent(this.threading);
        this.threadFindingItem = this.Menu.Switch("Thread Finding Item").parent(this.threading);
        this.threadFindingItemAmount = this.Menu.Slider("Thread Finding Item Amount", 1, 10).parent(this.threading);
        this.switchTimer = new Timer();
        this.postPopTimer = new Timer();
        this.offhandSlot = -1;
        this.itemThread = new Thread(() -> this.offhandSlot = InventoryUtil.getItemSlot(this.getOffhandItem()));
        this.offhandThread = new Thread(this::execute);
    }
    
    @Override
    public void onTick() {
        if (this.mc.currentScreen != null) {
            return;
        }
        if (this.threadFindingItem.GetSwitch()) {
            for (int i = 0; i < this.threadFindingItemAmount.GetSlider(); ++i) {
                final Thread thread = new Thread(this.itemThread);
                thread.start();
            }
        }
        else {
            this.offhandSlot = InventoryUtil.getItemSlot(this.getOffhandItem());
        }
        if (this.threadSwap.GetSwitch()) {
            for (int i = 0; i < this.threadSwapAmount.GetSlider(); ++i) {
                final Thread thread = new Thread(this.offhandThread);
                thread.start();
            }
        }
        else {
            this.execute();
        }
    }
    
    public void execute() {
        if (this.mc.player.getHeldItemOffhand().getItem() != this.getOffhandItem() && this.offhandSlot != -1 && this.switchTimer.getTime((long)this.switchDelay.GetSlider())) {
            final int slot = (this.offhandSlot < 9) ? (this.offhandSlot + 36) : this.offhandSlot;
            this.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.updateController();
            this.switchTimer.setTime(0);
        }
    }
    
    public Item getOffhandItem() {
        final boolean safeToSwap = this.safeToSwap();
        if (this.postPopForceTotem.GetSwitch() && this.postPopTimer.getTimeSub((long)this.forceTime.GetSlider())) {
            return Items.TOTEM_OF_UNDYING;
        }
        final String getCombo = this.item.GetCombo();
        switch (getCombo) {
            case "Totem": {
                if (safeToSwap) {
                    if (this.gapple.GetSwitch() && ((this.rightClick.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD && this.mc.gameSettings.keyBindUseItem.isKeyDown()) || (!this.rightClick.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD))) {
                        return Items.GOLDEN_APPLE;
                    }
                    if (this.crystalOnSword.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
                        return Items.END_CRYSTAL;
                    }
                    if (this.crystalOnPickaxe.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE) {
                        return Items.END_CRYSTAL;
                    }
                    if (this.fallBack.GetSwitch() && InventoryUtil.getStackCount(Items.TOTEM_OF_UNDYING) == 0) {
                        return Items.END_CRYSTAL;
                    }
                }
                return Items.TOTEM_OF_UNDYING;
            }
            case "Crystal": {
                if (this.fallBack.GetSwitch() && InventoryUtil.getStackCount(Items.END_CRYSTAL) == 0) {
                    return Items.TOTEM_OF_UNDYING;
                }
                if (this.fallDistance.GetSwitch() && this.mc.player.fallDistance > this.minDistance.GetSlider()) {
                    return Items.TOTEM_OF_UNDYING;
                }
                if (!safeToSwap) {
                    return Items.TOTEM_OF_UNDYING;
                }
                if (this.gapple.GetSwitch() && ((this.rightClick.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD && this.mc.gameSettings.keyBindUseItem.isKeyDown()) || (!this.rightClick.GetSwitch() && this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD))) {
                    return Items.GOLDEN_APPLE;
                }
                return Items.END_CRYSTAL;
            }
            default: {
                return null;
            }
        }
    }
    
    public boolean safeToSwap() {
        return (!this.hole.GetSwitch() || !BlockUtil.isPlayerSafe((EntityPlayer)this.mc.player) || !this.mc.player.onGround || EntityUtil.getHealth((Entity)this.mc.player) >= this.holeHealth.GetSlider()) && EntityUtil.getHealth((Entity)this.mc.player) >= this.totemHealth.GetSlider();
    }
    
    @RegisterListener
    public void onTotemPop(final PlayerChangeEvent.TotemPop event) {
        if (this.nullCheck() || !this.isEnabled() || !this.postPopForceTotem.GetSwitch() || !event.entityPlayer.equals((Object)this.mc.player)) {
            return;
        }
        this.postPopTimer.setTime(0);
    }
}
