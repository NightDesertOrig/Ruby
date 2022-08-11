//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.client.gui.inventory.*;
import dev.zprestige.ruby.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.client.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import java.util.*;
import java.util.stream.*;
import dev.zprestige.ruby.util.*;

public class AutoEcMeDupe extends Module
{
    public final Slider actionDelay;
    public final Slider timeoutTime;
    public final Slider restartTimeout;
    public final Switch afkScreenFix;
    public final Switch autoDismount;
    public final Slider dismountRetryDelay;
    public Timer timer;
    public Timer dismountTimer;
    public int stage;
    public int shulkers;
    public boolean restart;
    public boolean bok;
    public boolean joe;
    
    public AutoEcMeDupe() {
        this.actionDelay = this.Menu.Slider("Action Delay (MS)", 1, 1000);
        this.timeoutTime = this.Menu.Slider("Timeout Time (S)", 1, 15);
        this.restartTimeout = this.Menu.Slider("Restart Timeout (S)", 1, 15);
        this.afkScreenFix = this.Menu.Switch("Afk Screen Fix");
        this.autoDismount = this.Menu.Switch("Auto Dismount");
        this.dismountRetryDelay = this.Menu.Slider("Dismount Retry Delay (MS)", 1, 1000);
        this.timer = new Timer();
        this.dismountTimer = new Timer();
        this.stage = 0;
        this.shulkers = 0;
    }
    
    public void onEnable() {
        this.timer.setTime(0);
        this.stage = 0;
        this.shulkers = 0;
        this.restart = false;
        this.bok = false;
    }
    
    public void onTick() {
        final EntityDonkey entityDonkey = this.getClosestDonkey();
        if (entityDonkey == null) {
            this.disableModule("No donkey found in rage, disabling AutoEcMeDupe.");
            return;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock((Block)Blocks.CHEST));
        if (slot == -1) {
            this.disableModule("No chest found in hotbar, disabling AutoEcMeDupe.");
            return;
        }
        if (this.afkScreenFix.GetSwitch() && this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiScreenHorseInventory)) {
            this.mc.currentScreen = null;
        }
        if (this.joe) {
            this.mc.gameSettings.keyBindSneak.pressed = false;
            this.joe = false;
        }
        if (this.autoDismount.GetSwitch() && this.dismountTimer.getTime((long)this.dismountRetryDelay.GetSlider()) && this.mc.player.isRiding()) {
            this.mc.gameSettings.keyBindSneak.pressed = true;
            this.dismountTimer.setTime(0);
            this.joe = true;
        }
        switch (this.stage) {
            case 0: {
                if (this.timer.getTime((long)(this.restart ? (this.restartTimeout.GetSlider() * 1000.0f) : this.actionDelay.GetSlider()))) {
                    if (this.restart) {
                        this.restart = false;
                    }
                    if (entityDonkey.hasChest()) {
                        this.stage = 1;
                    }
                    else {
                        InventoryUtil.switchToSlot(slot);
                        Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " rotating to donkey.");
                        this.entityRotate((Entity)entityDonkey);
                        this.mc.playerController.interactWithEntity((EntityPlayer)this.mc.player, (Entity)entityDonkey, EnumHand.MAIN_HAND);
                        Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " chesting donkey.");
                    }
                    this.timer.setTime(0);
                    break;
                }
                break;
            }
            case 1: {
                if (this.timer.getTime((long)this.actionDelay.GetSlider())) {
                    this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    this.entityRotate((Entity)entityDonkey);
                    this.mc.playerController.interactWithEntity((EntityPlayer)this.mc.player, (Entity)entityDonkey, EnumHand.MAIN_HAND);
                    Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " opening donkey.");
                    this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    this.stage = 2;
                    this.timer.setTime(0);
                    break;
                }
                break;
            }
            case 2: {
                if (this.shulkers >= 15) {
                    this.stage = 3;
                    break;
                }
                if (this.timer.getTime((long)this.actionDelay.GetSlider()) && this.mc.currentScreen instanceof GuiScreenHorseInventory) {
                    Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " transferring items started.");
                    final GuiScreenHorseInventory chest = (GuiScreenHorseInventory)this.mc.currentScreen;
                    for (int i = 0; i < this.mc.player.inventoryContainer.inventorySlots.size(); ++i) {
                        final ItemStack itemStack = this.mc.player.inventoryContainer.getSlot(i).getStack();
                        if (!itemStack.isEmpty() && itemStack.getItem() != Items.AIR) {
                            if (Block.getBlockFromItem(itemStack.getItem()) instanceof BlockShulkerBox) {
                                this.mc.playerController.windowClick(chest.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                                Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " transferring slot " + ChatFormatting.GRAY + i + ChatFormatting.WHITE + ".");
                                ++this.shulkers;
                            }
                        }
                    }
                    Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " transferring items finished.");
                    break;
                }
                break;
            }
            case 3: {
                if (this.timer.getTime((long)this.actionDelay.GetSlider())) {
                    Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " closing donkey.");
                    this.mc.displayGuiScreen((GuiScreen)null);
                    this.stage = 4;
                    this.timer.setTime(0);
                    break;
                }
                break;
            }
            case 4: {
                if (this.restart) {
                    Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " setting use item not pressed.");
                    this.mc.gameSettings.keyBindUseItem.pressed = false;
                    Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " finishing up, setting stage to 0.");
                    this.stage = 0;
                }
                if (this.timer.getTime((long)(this.timeoutTime.GetSlider() * 1000.0f))) {
                    Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " rotating to donkey.");
                    this.entityRotate((Entity)entityDonkey);
                    Ruby.chatManager.sendMessage("[AutoEcMeDupe]" + ChatFormatting.GRAY + "[Stage][" + ChatFormatting.WHITE + this.stage + "]" + ChatFormatting.WHITE + " setting vanilla use item pressed.");
                    this.mc.gameSettings.keyBindUseItem.pressed = true;
                    this.timer.setTime(0);
                    this.shulkers = 0;
                    this.restart = true;
                    break;
                }
                break;
            }
        }
    }
    
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock)) {
            return;
        }
        if (this.restart && this.stage == 4) {
            event.setCancelled(true);
        }
    }
    
    public EntityDonkey getClosestDonkey() {
        final TreeMap<Float, EntityDonkey> entityDonkeyTreeMap = (TreeMap<Float, EntityDonkey>)this.mc.world.loadedEntityList.stream().filter(entity -> this.mc.player.getDistance(entity) <= 5.0f && entity instanceof EntityDonkey).collect(Collectors.toMap(entity -> this.mc.player.getDistance(entity), entity -> entity, (a, b) -> b, TreeMap::new));
        if (!entityDonkeyTreeMap.isEmpty()) {
            return entityDonkeyTreeMap.firstEntry().getValue();
        }
        return null;
    }
    
    public void entityRotate(final Entity entity) {
        final float[] angle = BlockUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), entity.getPositionVector());
        this.mc.player.rotationYaw = angle[0];
        this.mc.player.rotationPitch = angle[1];
    }
}
