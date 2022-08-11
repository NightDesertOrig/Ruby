//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.misc;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import org.lwjgl.input.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.util.math.*;
import dev.zprestige.ruby.eventbus.annotation.*;

public class FreeLook extends Module
{
    public final Key holdBind;
    public float yaw;
    public float pitch;
    
    public FreeLook() {
        this.holdBind = this.Menu.Key("Hold Bind", 0);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }
    
    @Override
    public void onTick() {
        if (this.holdBind.GetKey() != -1 && Keyboard.isKeyDown(this.holdBind.GetKey())) {
            this.mc.gameSettings.thirdPersonView = 1;
        }
        else if (this.mc.gameSettings.thirdPersonView == 1) {
            this.mc.gameSettings.thirdPersonView = 0;
            this.yaw = 0.0f;
            this.pitch = 0.0f;
        }
    }
    
    @SubscribeEvent
    public void onCameraSetup(final EntityViewRenderEvent.CameraSetup event) {
        if (this.nullCheck() || !this.isEnabled() || this.holdBind.GetKey() == -1 || !Keyboard.isKeyDown(this.holdBind.GetKey())) {
            return;
        }
        event.setYaw(event.getYaw() + this.yaw);
        event.setPitch(event.getPitch() + this.pitch);
    }
    
    @RegisterListener
    public void onTurnEvent(final TurnEvent event) {
        if (this.nullCheck() || !this.isEnabled() || this.holdBind.GetKey() == -1 || !Keyboard.isKeyDown(this.holdBind.GetKey())) {
            return;
        }
        this.yaw += (float)(event.getYaw() * 0.15);
        this.pitch -= (float)(event.getPitch() * 0.15);
        this.pitch = MathHelper.clamp(this.pitch, -90.0f, 90.0f);
        event.setCancelled(true);
    }
}
