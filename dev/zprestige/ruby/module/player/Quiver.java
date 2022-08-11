//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;

public class Quiver extends Module
{
    public static Quiver Instance;
    public int timer;
    public int stage;
    public int returnSlot;
    
    public Quiver() {
        this.timer = 0;
        this.stage = 1;
        this.returnSlot = -1;
        Quiver.Instance = this;
    }
    
    public void onDisable() {
        this.timer = 0;
        this.stage = 0;
        this.mc.gameSettings.keyBindUseItem.pressed = false;
        if (this.returnSlot != -1) {
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 9, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.returnSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 9, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.returnSlot = -1;
        }
    }
    
    public void onTick() {
        if (this.mc.currentScreen != null) {
            return;
        }
        InventoryUtil.switchToHotbarSlot(ItemBow.class, false);
        if (InventoryUtil.findHotbarBlock(ItemBow.class) == -1) {
            this.disableModule("No bow found, disabling Quiver.");
            return;
        }
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.mc.player.rotationYaw, -90.0f, this.mc.player.onGround));
        if (this.stage == 0) {
            if (!this.swapArrows()) {
                this.disableModule();
                return;
            }
            ++this.stage;
        }
        else {
            if (this.stage == 1) {
                ++this.stage;
                ++this.timer;
                return;
            }
            if (this.stage == 2) {
                this.mc.gameSettings.keyBindUseItem.pressed = true;
                this.timer = 0;
                ++this.stage;
            }
            else if (this.stage == 3) {
                if (this.timer > 4) {
                    ++this.stage;
                }
            }
            else if (this.stage == 4) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
                this.mc.player.resetActiveHand();
                this.mc.gameSettings.keyBindUseItem.pressed = false;
                this.timer = 0;
                ++this.stage;
            }
            else if (this.stage == 5) {
                if (this.timer < 10) {
                    ++this.timer;
                    return;
                }
                this.stage = 0;
                this.timer = 0;
            }
        }
        ++this.timer;
    }
    
    public boolean swapArrows() {
        for (int i = 9; i < 45; ++i) {
            if (((ItemStack)this.mc.player.inventoryContainer.getInventory().get(i)).getItem() instanceof ItemTippedArrow) {
                final ItemStack arrow = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
                if ((PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_STRENGTH)) && !this.mc.player.isPotionActive(MobEffects.STRENGTH)) {
                    this.swapSlots(i);
                    return true;
                }
                if ((PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.SWIFTNESS) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_SWIFTNESS) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_SWIFTNESS)) && !this.mc.player.isPotionActive(MobEffects.SPEED)) {
                    this.swapSlots(i);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void swapSlots(final int from) {
        if (from == 9) {
            return;
        }
        this.returnSlot = from;
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 9, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, from, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.updateController();
    }
}
