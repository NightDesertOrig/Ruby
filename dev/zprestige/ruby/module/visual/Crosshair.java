//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class Crosshair extends Module
{
    public final Slider distance;
    public final Slider length;
    public final Slider thickness;
    public final Switch dynamic;
    public final Switch dynamicAnimated;
    public final Slider dynamicGap;
    public final Slider dynamicAnimationSpeed;
    public final ColorBox color;
    float newGap;
    
    public Crosshair() {
        this.distance = this.Menu.Slider("Gap", 0, 100);
        this.length = this.Menu.Slider("Length", 0, 100);
        this.thickness = this.Menu.Slider("Thickness", 0, 50);
        this.dynamic = this.Menu.Switch("Dynamic");
        this.dynamicAnimated = this.Menu.Switch("Dynamic Animated");
        this.dynamicGap = this.Menu.Slider("Dynamic Gap", 0, 100);
        this.dynamicAnimationSpeed = this.Menu.Slider("Dynamic Animation Speed", 0.1f, 5.0f);
        this.color = this.Menu.Color("Color");
    }
    
    public void onTick() {
        if (this.dynamicAnimated.GetSwitch()) {
            if (EntityUtil.isMoving()) {
                this.newGap = AnimationUtil.increaseNumber(this.newGap, this.dynamicGap.GetSlider() / 10.0f, this.dynamicAnimationSpeed.GetSlider());
            }
            else {
                this.newGap = AnimationUtil.decreaseNumber(this.newGap, 0.0f, this.dynamicAnimationSpeed.GetSlider());
            }
        }
        else if (EntityUtil.isMoving()) {
            this.newGap = this.dynamicGap.GetSlider() / 10.0f;
        }
        else {
            this.newGap = 0.0f;
        }
    }
    
    @SubscribeEvent
    public void onRenderGameOverlay(final RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS && this.isEnabled()) {
            event.setCanceled(true);
            this.drawCrosshairs(this.distance.GetSlider() / 10.0f, this.length.GetSlider() / 10.0f, this.thickness.GetSlider() / 10.0f, this.dynamic.GetSwitch(), this.newGap, this.color.GetColor().getRGB());
        }
    }
    
    public void drawCrosshairs(double gap, final double width, final double thickness, final boolean dynamic, final float dynamicSeparation, final int color) {
        final int scaledScreenWidth = new ScaledResolution(this.mc).getScaledWidth();
        final int scaledScreenHeight = new ScaledResolution(this.mc).getScaledHeight();
        if (dynamic) {
            gap += dynamicSeparation;
        }
        this.drawLine((float)(scaledScreenWidth / 2 - gap), (float)(scaledScreenHeight / 2), (float)(scaledScreenWidth / 2 - gap - width), (float)(scaledScreenHeight / 2), (float)(int)thickness, color);
        this.drawLine((float)(scaledScreenWidth / 2 + gap), (float)(scaledScreenHeight / 2), (float)(scaledScreenWidth / 2 + gap + width), (float)(scaledScreenHeight / 2), (float)(int)thickness, color);
        this.drawLine((float)(scaledScreenWidth / 2), (float)(scaledScreenHeight / 2 - gap), (float)(scaledScreenWidth / 2), (float)(scaledScreenHeight / 2 - gap - width), (float)(int)thickness, color);
        this.drawLine((float)(scaledScreenWidth / 2), (float)(scaledScreenHeight / 2 + gap), (float)(scaledScreenWidth / 2), (float)(scaledScreenHeight / 2 + gap + width), (float)(int)thickness, color);
    }
    
    public void drawLine(final float x, final float y, final float x1, final float y1, final float thickness, final int hex) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glLineWidth(thickness);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}
