//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.util.*;
import java.util.stream.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class HotbarFiller extends Module
{
    public final Slider delay;
    public final Slider fillAt;
    public Timer timer;
    
    public HotbarFiller() {
        this.delay = this.Menu.Slider("Delay", 0, 500);
        this.fillAt = this.Menu.Slider("Fill At", 1, 64);
        this.timer = new Timer();
    }
    
    public void onTick() {
        if (this.mc.currentScreen != null || !this.timer.getTime((long)this.delay.GetSlider())) {
            return;
        }
        if (IntStream.range(0, 9).anyMatch(this::refillSlot)) {
            this.timer.setTime(0);
        }
    }
    
    public boolean refillSlot(final int slot) {
        final ItemStack stack = this.mc.player.inventory.getStackInSlot(slot);
        if (stack.isEmpty() || stack.getItem() == Items.AIR || !stack.isStackable() || stack.getCount() >= stack.getMaxStackSize() || (stack.getItem().equals(Items.GOLDEN_APPLE) && stack.getCount() >= this.fillAt.GetSlider()) || (stack.getItem().equals(Items.EXPERIENCE_BOTTLE) && stack.getCount() > this.fillAt.GetSlider())) {
            return false;
        }
        for (int i = 9; i < 36; ++i) {
            final ItemStack item = this.mc.player.inventory.getStackInSlot(i);
            if (!item.isEmpty() && stack.getItem() == item.getItem() && stack.getDisplayName().equals(item.getDisplayName())) {
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                this.mc.playerController.updateController();
                return true;
            }
        }
        return false;
    }
}
