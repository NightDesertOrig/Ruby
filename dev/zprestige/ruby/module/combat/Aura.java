//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;
import java.util.*;

public class Aura extends Module
{
    public static Aura Instance;
    public final Parent ranges;
    public final Slider range;
    public final Slider wallRange;
    public final Parent misc;
    public final Switch swordOnly;
    public final Switch eggOnly;
    public final Switch autoSwitch;
    public final Switch autoDelay;
    public final Slider hitDelay;
    public final Switch packet;
    public final Switch swing;
    public final ComboBox swingHand;
    public Timer timer;
    
    public Aura() {
        this.ranges = this.Menu.Parent("Ranges");
        this.range = this.Menu.Slider("Range", 0.1f, 6.0f).parent(this.ranges);
        this.wallRange = this.Menu.Slider("Through Wall Range", 0.1f, 6.0f).parent(this.ranges);
        this.misc = this.Menu.Parent("Misc");
        this.swordOnly = this.Menu.Switch("Sword Only").parent(this.misc);
        this.eggOnly = this.Menu.Switch("Egg Only").parent(this.misc);
        this.autoSwitch = this.Menu.Switch("Auto Switch").parent(this.misc);
        this.autoDelay = this.Menu.Switch("Delay").parent(this.misc);
        this.hitDelay = this.Menu.Slider("Hit Delay", 1, 1000);
        this.packet = this.Menu.Switch("Packet").parent(this.misc);
        this.swing = this.Menu.Switch("Swing").parent(this.misc);
        this.swingHand = this.Menu.ComboBox("Swing Hand", new String[] { "Mainhand", "Offhand", "Packet" }).parent(this.misc);
        this.timer = new Timer();
        Aura.Instance = this;
    }
    
    @Override
    public void onTick() {
        if (this.timer.getTime(this.autoDelay.GetSwitch() ? 600L : ((long)this.hitDelay.GetSlider()))) {
            final EntityPlayer target = this.getTarget();
            if (target == null) {
                return;
            }
            final int swordSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_SWORD);
            if (this.autoSwitch.GetSwitch() && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_SWORD) && swordSlot != -1) {
                InventoryUtil.switchToSlot(swordSlot);
            }
            if (!this.swordOnly.GetSwitch()) {
                if (this.eggOnly.GetSwitch() && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.EGG)) {
                    return;
                }
            }
            else if (!this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_SWORD)) {
                return;
            }
            if (this.packet.GetSwitch()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)target));
            }
            else {
                this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)target);
            }
            if (this.swing.GetSwitch()) {
                EntityUtil.swingArm(this.swingHand.GetCombo().equals("Mainhand") ? EntityUtil.SwingType.MainHand : (this.swingHand.GetCombo().equals("Offhand") ? EntityUtil.SwingType.OffHand : EntityUtil.SwingType.Packet));
            }
            this.timer.setTime(0);
        }
    }
    
    public EntityPlayer getTarget() {
        final TreeMap<Float, EntityPlayer> target = new TreeMap<Float, EntityPlayer>();
        for (final EntityPlayer entityPlayer : this.mc.world.playerEntities) {
            if (entityPlayer.equals((Object)this.mc.player)) {
                continue;
            }
            if (Ruby.friendManager.isFriend(entityPlayer.getName())) {
                continue;
            }
            if (this.mc.player.getDistance((Entity)entityPlayer) > this.range.GetSlider()) {
                continue;
            }
            if (this.mc.player.canEntityBeSeen((Entity)entityPlayer) && this.mc.player.getDistance((Entity)entityPlayer) > this.wallRange.GetSlider()) {
                continue;
            }
            target.put(this.mc.player.getDistance((Entity)entityPlayer), entityPlayer);
        }
        if (!target.isEmpty()) {
            return target.firstEntry().getValue();
        }
        return null;
    }
}
