//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class ElytraFlight extends Module
{
    public final Slider speed;
    public final Slider verticalSpeed;
    public final Switch rocketOnRubberband;
    public final Switch chinaSettingFor06d;
    public final Slider pitchToChina;
    public final Slider lengthOfPitch;
    public boolean needsCorrection;
    public boolean needsCorrection2;
    public Timer pitchLength;
    
    public ElytraFlight() {
        this.speed = this.Menu.Slider("Speed", 0.1f, 10.0f);
        this.verticalSpeed = this.Menu.Slider("Vertical Speed", 0.1f, 10.0f);
        this.rocketOnRubberband = this.Menu.Switch("Rocket On Rubberband");
        this.chinaSettingFor06d = this.Menu.Switch("China Setting For 06d");
        this.pitchToChina = this.Menu.Slider("Pitch To China", 0.1f, 90.0f);
        this.lengthOfPitch = this.Menu.Slider("Length Of Pitch", 1, 1000);
        this.pitchLength = new Timer();
    }
    
    @RegisterListener
    public void onMove(final MoveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !this.mc.player.isElytraFlying() || this.mc.player.movementInput.jump) {
            return;
        }
        if (this.needsCorrection2 && this.pitchLength.getTime((long)this.lengthOfPitch.GetSlider())) {
            this.needsCorrection = false;
            this.needsCorrection2 = false;
        }
        if (this.needsCorrection) {
            this.mc.player.rotationPitch = -this.pitchToChina.GetSlider();
            this.needsCorrection2 = true;
            return;
        }
        if (this.mc.player.movementInput.sneak) {
            this.mc.player.motionY = -this.verticalSpeed.GetSlider();
        }
        else {
            event.motionY = -0.001;
            this.mc.player.motionY = -0.001;
        }
        EntityUtil.setMoveSpeed(event, this.speed.GetSlider());
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !this.rocketOnRubberband.GetSwitch() || !(event.getPacket() instanceof SPacketPlayerPosLook)) {
            return;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Items.FIREWORKS);
        if (slot == -1) {
            return;
        }
        final int currentItem = this.mc.player.inventory.currentItem;
        InventoryUtil.switchToSlot(slot);
        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        this.mc.player.inventory.currentItem = currentItem;
        this.mc.playerController.updateController();
        if (this.chinaSettingFor06d.GetSwitch()) {
            this.needsCorrection = true;
        }
        this.pitchLength.setTime(0);
    }
}
