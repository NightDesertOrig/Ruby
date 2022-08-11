//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util.shader;

import net.minecraft.client.shader.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.mixins.render.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public abstract class FramebufferShader extends Shader
{
    protected static int lastScale;
    protected static int lastScaleWidth;
    protected static int lastScaleHeight;
    private static Framebuffer framebuffer;
    protected float red;
    protected float green;
    protected float blue;
    protected float alpha;
    protected float radius;
    protected float quality;
    private boolean entityShadows;
    
    public FramebufferShader(final String fragmentShader) {
        super(fragmentShader);
        this.alpha = 1.0f;
        this.radius = 2.0f;
        this.quality = 1.0f;
    }
    
    public void startDraw(final float partialTicks) {
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        (FramebufferShader.framebuffer = this.setupFrameBuffer(FramebufferShader.framebuffer)).bindFramebuffer(true);
        this.entityShadows = Ruby.mc.gameSettings.entityShadows;
        Ruby.mc.gameSettings.entityShadows = false;
        ((IEntityRenderer)Ruby.mc.entityRenderer).invokeSetupCameraTransform(partialTicks, 0);
    }
    
    public void stopDraw(final Color color, final float radius, final float quality) {
        Ruby.mc.gameSettings.entityShadows = this.entityShadows;
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 771);
        Ruby.mc.getFramebuffer().bindFramebuffer(true);
        this.red = color.getRed() / 255.0f;
        this.green = color.getGreen() / 255.0f;
        this.blue = color.getBlue() / 255.0f;
        this.alpha = color.getAlpha() / 255.0f;
        this.radius = radius;
        this.quality = quality;
        Ruby.mc.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        this.startShader();
        Ruby.mc.entityRenderer.setupOverlayRendering();
        this.drawFramebuffer(FramebufferShader.framebuffer);
        this.stopShader();
        Ruby.mc.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    
    public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
        if (Display.isActive() || Display.isVisible()) {
            if (frameBuffer != null) {
                frameBuffer.framebufferClear();
                final ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
                final int factor = scale.getScaleFactor();
                final int factor2 = scale.getScaledWidth();
                final int factor3 = scale.getScaledHeight();
                if (FramebufferShader.lastScale != factor || FramebufferShader.lastScaleWidth != factor2 || FramebufferShader.lastScaleHeight != factor3) {
                    frameBuffer.deleteFramebuffer();
                    frameBuffer = new Framebuffer(Ruby.mc.displayWidth, Ruby.mc.displayHeight, true);
                    frameBuffer.framebufferClear();
                }
                FramebufferShader.lastScale = factor;
                FramebufferShader.lastScaleWidth = factor2;
                FramebufferShader.lastScaleHeight = factor3;
            }
            else {
                frameBuffer = new Framebuffer(Ruby.mc.displayWidth, Ruby.mc.displayHeight, true);
            }
        }
        else if (frameBuffer == null) {
            frameBuffer = new Framebuffer(Ruby.mc.displayWidth, Ruby.mc.displayHeight, true);
        }
        return frameBuffer;
    }
    
    public void drawFramebuffer(final Framebuffer framebuffer) {
        final ScaledResolution scaledResolution = new ScaledResolution(Ruby.mc);
        GL11.glBindTexture(3553, framebuffer.framebufferTexture);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, (double)scaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth(), 0.0);
        GL11.glEnd();
        GL20.glUseProgram(0);
    }
}
