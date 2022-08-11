//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.player;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.init.*;
import org.lwjgl.input.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.network.*;
import java.util.stream.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

public class FastExp extends Module
{
    public static FastExp Instance;
    public final ComboBox mode;
    public final ComboBox triggerMode;
    public final Key customKey;
    public final Slider packets;
    public final Switch handOnly;
    
    public FastExp() {
        this.mode = this.Menu.ComboBox("Mode", new String[] { "Vanilla", "Packet" });
        this.triggerMode = this.Menu.ComboBox("Trigger Mode", new String[] { "RightClick", "MiddleClick", "Custom" });
        this.customKey = this.Menu.Key("Custom Key", 0);
        this.packets = this.Menu.Slider("Packets", 1, 10);
        this.handOnly = this.Menu.Switch("Hand Only");
        FastExp.Instance = this;
    }
    
    public void onTick() {
        if (this.mc.currentScreen != null) {
            return;
        }
        final String getCombo = this.mode.GetCombo();
        switch (getCombo) {
            case "Vanilla": {
                if (this.handOnly.GetSwitch() && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.EXPERIENCE_BOTTLE)) {
                    return;
                }
                this.mc.rightClickDelayTimer = 0;
                break;
            }
            case "Packet": {
                if (this.triggerMode.GetCombo().equals("RightClick") && !this.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                    return;
                }
                if (this.triggerMode.GetCombo().equals("MiddleClick") && !Mouse.isButtonDown(2)) {
                    return;
                }
                if (this.triggerMode.GetCombo().equals("Custom") && !Keyboard.isKeyDown(this.customKey.GetKey())) {
                    return;
                }
                if (this.handOnly.GetSwitch() && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.EXPERIENCE_BOTTLE)) {
                    return;
                }
                if (InventoryUtil.getItemFromHotbar(Items.EXPERIENCE_BOTTLE) == -1) {
                    return;
                }
                this.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getItemFromHotbar(Items.EXPERIENCE_BOTTLE)));
                IntStream.range(0, (int)this.packets.GetSlider()).forEach(i -> this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND)));
                this.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.mc.player.inventory.currentItem));
                break;
            }
        }
    }
}
