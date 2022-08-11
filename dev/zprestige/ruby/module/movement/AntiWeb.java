//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.movement;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.client.entity.*;

public class AntiWeb extends Module
{
    public final ComboBox mode;
    public final Slider horizontalSpeed;
    public final Slider verticalSpeed;
    public final Slider timerAmount;
    public boolean isTimering;
    
    public AntiWeb() {
        this.mode = this.Menu.ComboBox("Mode", new String[] { "Vanilla", "Motion", "Timer" });
        this.horizontalSpeed = this.Menu.Slider("Horizontal Speed", 0.1f, 2.0f);
        this.verticalSpeed = this.Menu.Slider("Vertical Speed", 0.1f, 2.0f);
        this.timerAmount = this.Menu.Slider("Timer Amount", 0.1f, 10.0f);
    }
    
    public void onTick() {
        if (!this.mc.player.isInWeb) {
            if (this.isTimering) {
                this.mc.timer.tickLength = 50.0f;
                this.isTimering = false;
            }
            return;
        }
        final String getCombo = this.mode.GetCombo();
        switch (getCombo) {
            case "Vanilla": {
                this.mc.player.isInWeb = false;
                break;
            }
            case "Motion": {
                final EntityPlayerSP player = this.mc.player;
                player.motionX *= 1.0f + this.horizontalSpeed.GetSlider();
                this.mc.player.motionY = -this.verticalSpeed.GetSlider();
                final EntityPlayerSP player2 = this.mc.player;
                player2.motionZ *= 1.0f + this.horizontalSpeed.GetSlider();
                break;
            }
            case "Timer": {
                this.mc.timer.tickLength = 50.0f / this.timerAmount.GetSlider();
                this.isTimering = true;
                break;
            }
        }
    }
}
