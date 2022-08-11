//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.client.*;

public class ItemModification extends Module
{
    public static ItemModification Instance;
    public final Parent misc;
    public final ColorBox color;
    public final Switch animationTest;
    public final ComboBox direction;
    public final Slider animationSpeed;
    public final Slider animationDistance;
    public final Parent mainHands;
    public final Switch mainhand;
    public final Switch mainhandTranslation;
    public final Slider mainhandX;
    public final Slider mainhandY;
    public final Slider mainhandZ;
    public final Switch mainhandScaling;
    public final Slider mainhandScaleX;
    public final Slider mainhandScaleY;
    public final Slider mainhandScaleZ;
    public final Switch mainhandRotation;
    public final Slider mainhandRotationX;
    public final Slider mainhandRotationY;
    public final Slider mainhandRotationZ;
    public final Parent offHands;
    public final Switch offhand;
    public final Switch offhandTranslation;
    public final Slider offhandX;
    public final Slider offhandY;
    public final Slider offhandZ;
    public final Switch offhandScaling;
    public final Slider offhandScaleX;
    public final Slider offhandScaleY;
    public final Slider offhandScaleZ;
    public final Switch offhandRotation;
    public final Slider offhandRotationX;
    public final Slider offhandRotationY;
    public final Slider offhandRotationZ;
    public boolean swung;
    public boolean forwarded;
    public float anim;
    
    public ItemModification() {
        this.misc = this.Menu.Parent("Misc");
        this.color = this.Menu.Color("Item Colors").parent(this.misc);
        this.animationTest = this.Menu.Switch("Animation").parent(this.misc);
        this.direction = this.Menu.ComboBox("Direction", new String[] { "Forwards", "Backwards" }).parent(this.misc);
        this.animationSpeed = this.Menu.Slider("Animation Speed", 0.1f, 1.0f).parent(this.misc);
        this.animationDistance = this.Menu.Slider("Animation Distance", 0.1f, 10.0f).parent(this.misc);
        this.mainHands = this.Menu.Parent("MainHands");
        this.mainhand = this.Menu.Switch("Mainhand").parent(this.mainHands);
        this.mainhandTranslation = this.Menu.Switch("Mainhand Translation").parent(this.mainHands);
        this.mainhandX = this.Menu.Slider("Mainhand X", -10.0f, 10.0f).parent(this.mainHands);
        this.mainhandY = this.Menu.Slider("Mainhand Y", -10.0f, 10.0f).parent(this.mainHands);
        this.mainhandZ = this.Menu.Slider("Mainhand Z", -10.0f, 10.0f).parent(this.mainHands);
        this.mainhandScaling = this.Menu.Switch("Mainhand Scaling").parent(this.mainHands);
        this.mainhandScaleX = this.Menu.Slider("Mainhand Scale X", -10.0f, 10.0f).parent(this.mainHands);
        this.mainhandScaleY = this.Menu.Slider("Mainhand Scale Y", -10.0f, 10.0f).parent(this.mainHands);
        this.mainhandScaleZ = this.Menu.Slider("Mainhand Scale Z", -10.0f, 10.0f).parent(this.mainHands);
        this.mainhandRotation = this.Menu.Switch("Mainhand Rotation").parent(this.mainHands);
        this.mainhandRotationX = this.Menu.Slider("Mainhand Rotation X", 0.0f, 10.0f).parent(this.mainHands);
        this.mainhandRotationY = this.Menu.Slider("Mainhand Rotation Y", 0.0f, 10.0f).parent(this.mainHands);
        this.mainhandRotationZ = this.Menu.Slider("Mainhand Rotation Z", 0.0f, 10.0f).parent(this.mainHands);
        this.offHands = this.Menu.Parent("OffHands");
        this.offhand = this.Menu.Switch("Offhand").parent(this.offHands);
        this.offhandTranslation = this.Menu.Switch("Offhand Translation").parent(this.offHands);
        this.offhandX = this.Menu.Slider("Offhand X", -10.0f, 10.0f).parent(this.offHands);
        this.offhandY = this.Menu.Slider("Offhand Y", -10.0f, 10.0f).parent(this.offHands);
        this.offhandZ = this.Menu.Slider("Offhand Z", -10.0f, 10.0f).parent(this.offHands);
        this.offhandScaling = this.Menu.Switch("Offhand Scaling").parent(this.offHands);
        this.offhandScaleX = this.Menu.Slider("Offhand Scale X", -10.0f, 10.0f).parent(this.offHands);
        this.offhandScaleY = this.Menu.Slider("Offhand Scale Y", -10.0f, 10.0f).parent(this.offHands);
        this.offhandScaleZ = this.Menu.Slider("Offhand Scale Z", -10.0f, 10.0f).parent(this.offHands);
        this.offhandRotation = this.Menu.Switch("Offhand Rotation");
        this.offhandRotationX = this.Menu.Slider("Offhand Rotation X", 0.0f, 10.0f).parent(this.offHands);
        this.offhandRotationY = this.Menu.Slider("Offhand Rotation Y", 0.0f, 10.0f).parent(this.offHands);
        this.offhandRotationZ = this.Menu.Slider("Offhand Rotation Z", 0.0f, 10.0f).parent(this.offHands);
        ItemModification.Instance = this;
    }
    
    public void onFrame(final float partialTicks) {
        if (this.swung) {
            final String getCombo = this.direction.GetCombo();
            switch (getCombo) {
                case "Forwards": {
                    if (!this.forwarded) {
                        if (this.anim < this.animationDistance.GetSlider()) {
                            this.anim += this.animationSpeed.GetSlider();
                            break;
                        }
                        this.forwarded = true;
                        break;
                    }
                    else {
                        if (this.anim > 0.0f) {
                            this.anim -= this.animationSpeed.GetSlider();
                            break;
                        }
                        this.swung = false;
                        this.forwarded = false;
                        break;
                    }
                    break;
                }
                case "Backwards": {
                    if (!this.forwarded) {
                        if (this.anim > -this.animationDistance.GetSlider()) {
                            this.anim -= this.animationSpeed.GetSlider();
                            break;
                        }
                        this.forwarded = true;
                        break;
                    }
                    else {
                        if (this.anim < 0.0f) {
                            this.anim += this.animationSpeed.GetSlider();
                            break;
                        }
                        this.swung = false;
                        this.forwarded = false;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @RegisterListener
    public void onRenderMainhand(final RenderItemEvent.MainHand event) {
        if (this.isEnabled() && event.entityLivingBase.equals((Object)this.mc.player) && this.mainhand.GetSwitch()) {
            if (this.animationTest.GetSwitch()) {
                GL11.glTranslated((double)(this.mainhandX.GetSlider() / 40.0f), (double)(this.mainhandY.GetSlider() / 40.0f), (double)((this.mainhandZ.GetSlider() - this.anim) / 40.0f));
            }
            if (this.mainhandTranslation.GetSwitch()) {
                GL11.glTranslated((double)(this.mainhandX.GetSlider() / 40.0f), (double)(this.mainhandY.GetSlider() / 40.0f), (double)(this.mainhandZ.GetSlider() / 40.0f));
            }
            if (this.mainhandScaling.GetSwitch()) {
                GlStateManager.scale(this.mainhandScaleX.GetSlider() / 10.0f + 1.0f, this.mainhandScaleY.GetSlider() / 10.0f + 1.0f, this.mainhandScaleZ.GetSlider() / 10.0f + 1.0f);
            }
            if (!this.mainhandRotation.GetSwitch()) {
                return;
            }
            GlStateManager.rotate(this.mainhandRotationX.GetSlider() * 36.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(this.mainhandRotationY.GetSlider() * 36.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(this.mainhandRotationZ.GetSlider() * 36.0f, 0.0f, 0.0f, 1.0f);
        }
    }
    
    @RegisterListener
    public void onRenderOffhand(final RenderItemEvent.Offhand event) {
        if (this.isEnabled() && event.entityLivingBase.equals((Object)this.mc.player) && this.offhand.GetSwitch()) {
            if (this.offhandTranslation.GetSwitch()) {
                GL11.glTranslated((double)(this.offhandX.GetSlider() / 40.0f), (double)(this.offhandY.GetSlider() / 40.0f), (double)(this.offhandZ.GetSlider() / 40.0f));
            }
            if (this.offhandScaling.GetSwitch()) {
                GlStateManager.scale(this.offhandScaleX.GetSlider() / 10.0f + 1.0f, this.offhandScaleY.GetSlider() / 10.0f + 1.0f, this.offhandScaleZ.GetSlider() / 10.0f + 1.0f);
            }
            if (!this.offhandRotation.GetSwitch()) {
                return;
            }
            GlStateManager.rotate(this.offhandRotationX.GetSlider() * 36.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(this.offhandRotationY.GetSlider() * 36.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(this.offhandRotationZ.GetSlider() * 36.0f, 0.0f, 0.0f, 1.0f);
        }
    }
    
    @RegisterListener
    public void onPacketSent(final PacketEvent.PacketSendEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof CPacketAnimation)) {
            return;
        }
        this.swung = true;
    }
}
