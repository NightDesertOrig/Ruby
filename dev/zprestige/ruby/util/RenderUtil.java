//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util;

import net.minecraft.client.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.culling.*;

public class RenderUtil
{
    public static ICamera camera;
    public static Tessellator tessellator;
    public static BufferBuilder builder;
    public static RenderItem itemRender;
    public static Minecraft mc;
    
    public static Color alphaStep(final Color color, final int index, final int count) {
        final float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((System.currentTimeMillis() % 2000L / 1000.0f + index / (float)count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
    
    public static void prepareScissor(final int x, final int y, final int width, final int height) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(524288);
        newScissor(x, y, x + width, y + height);
        GL11.glEnable(3089);
    }
    
    public static void releaseScissor() {
        GL11.glDisable(3089);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public static void newScissor(final int x, final int y, final int x2, final int y2) {
        final ScaledResolution scaledResolution = new ScaledResolution(RenderUtil.mc);
        GL11.glScissor(x * scaledResolution.getScaleFactor(), (scaledResolution.getScaledHeight() - y2) * scaledResolution.getScaleFactor(), (x2 - x) * scaledResolution.getScaleFactor(), (y2 - y) * scaledResolution.getScaleFactor());
    }
    
    public static void image(final ResourceLocation resourceLocation, final int x, final int y, final int width, final int height) {
        RenderUtil.mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float)width, (float)height);
    }
    
    public static void drawBoxWithHeight(final AxisAlignedBB bb, final Color color, final float height) {
        final AxisAlignedBB bb2 = new AxisAlignedBB(bb.minX - RenderUtil.mc.getRenderManager().viewerPosX, bb.minY - RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ - RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX - RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY - 1.0 + height - RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ - RenderUtil.mc.getRenderManager().viewerPosZ);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        RenderGlobal.renderFilledBox(bb2, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawCustomBB(final Color color, final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        final AxisAlignedBB bb1 = new AxisAlignedBB(minX - RenderUtil.mc.getRenderManager().viewerPosX, minY - RenderUtil.mc.getRenderManager().viewerPosY, minZ - RenderUtil.mc.getRenderManager().viewerPosZ, maxX - RenderUtil.mc.getRenderManager().viewerPosX, maxY - RenderUtil.mc.getRenderManager().viewerPosY, maxZ - RenderUtil.mc.getRenderManager().viewerPosZ);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        RenderGlobal.renderFilledBox(bb1, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void renderLogo() {
        GlStateManager.enableAlpha();
        RenderUtil.mc.getTextureManager().bindTexture(new ResourceLocation("textures/icons/ruby.png"));
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GuiScreen.drawScaledCustomSizeModalRect(2, 511, 0.0f, 0.0f, 68, 28, 68, 28, 68.0f, 28.0f);
        GL11.glPopMatrix();
        GlStateManager.disableAlpha();
    }
    
    public static void drawUnfilledCircle(final float x, final float y, final float radius, final int color, final float width, final float pi_neapple) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glLineWidth(width);
        GL11.glBegin(2);
        for (int i = 0; i <= pi_neapple; ++i) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawCircle(final float x, final float y, final float radius, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
        }
        GL11.glEnd();
        GlStateManager.resetColor();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static double interpolateLastTickPos(final double pos, final double lastPos) {
        return lastPos + (pos - lastPos) * RenderUtil.mc.timer.renderPartialTicks;
    }
    
    public static void renderBox(final AxisAlignedBB bb, final Color color, final Color outLineColor, final float lineWidth) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        startRender();
        drawOutline(bb, lineWidth, outLineColor);
        endRender();
        startRender();
        drawBox(bb, color);
        endRender();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public static void drawBox(final AxisAlignedBB bb, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        color(color);
        fillBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void fillBox(final AxisAlignedBB boundingBox) {
        if (boundingBox != null) {
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.maxY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
            GL11.glBegin(7);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.minZ);
            GL11.glVertex3d((double)(float)boundingBox.minX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glVertex3d((double)(float)boundingBox.maxX, (double)(float)boundingBox.minY, (double)(float)boundingBox.maxZ);
            GL11.glEnd();
        }
    }
    
    public static void drawOutline(final AxisAlignedBB bb, final float lineWidth, final Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(lineWidth);
        color(color);
        fillOutline(bb);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void fillOutline(final AxisAlignedBB bb) {
        if (bb != null) {
            GL11.glBegin(1);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
            GL11.glEnd();
        }
    }
    
    public static void startRender() {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void endRender() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static void color(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static Vec3d interpolateEntity(final Entity entity) {
        final double x = interpolateLastTickPos(entity.posX, entity.lastTickPosX) - RenderUtil.mc.getRenderManager().renderPosX;
        final double y = interpolateLastTickPos(entity.posY, entity.lastTickPosY) - RenderUtil.mc.getRenderManager().renderPosY;
        final double z = interpolateLastTickPos(entity.posZ, entity.lastTickPosZ) - RenderUtil.mc.getRenderManager().renderPosZ;
        return new Vec3d(x, y, z);
    }
    
    public static void drawNametag(final String text, final double x, final double y, final double z, final double scale, final int color) {
        final double dist = ((Entity)((RenderUtil.mc.getRenderViewEntity() == null) ? RenderUtil.mc.player : RenderUtil.mc.getRenderViewEntity())).getDistance(x + RenderUtil.mc.getRenderManager().viewerPosX, y + RenderUtil.mc.getRenderManager().viewerPosY, z + RenderUtil.mc.getRenderManager().viewerPosZ);
        final int textWidth = Ruby.fontManager.getStringWidth(text) / 2;
        final double scaling = (dist <= 8.0) ? 0.0245 : (0.0018 + scale * dist);
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate(x, y + 0.4000000059604645, z);
        GlStateManager.rotate(-RenderUtil.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(RenderUtil.mc.getRenderManager().playerViewX, (RenderUtil.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scaling, -scaling, scaling);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        RenderUtil.mc.fontRenderer.drawStringWithShadow(text, (float)(-textWidth), (float)(-(RenderUtil.mc.fontRenderer.FONT_HEIGHT - 1)), color);
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    public static Color interpolateColor(final float value, final Color start, final Color end) {
        final float sr = start.getRed() / 255.0f;
        final float sg = start.getGreen() / 255.0f;
        final float sb = start.getBlue() / 255.0f;
        final float sa = start.getAlpha() / 255.0f;
        final float er = end.getRed() / 255.0f;
        final float eg = end.getGreen() / 255.0f;
        final float eb = end.getBlue() / 255.0f;
        final float ea = end.getAlpha() / 255.0f;
        final float r = sr * value + er * (1.0f - value);
        final float g = sg * value + eg * (1.0f - value);
        final float b = sb * value + eb * (1.0f - value);
        final float a = sa * value + ea * (1.0f - value);
        return new Color(r, g, b, a);
    }
    
    public static void addBuilderVertex(final BufferBuilder bufferBuilder, final double x, final double y, final double z, final Color color) {
        bufferBuilder.pos(x, y, z).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f).endVertex();
    }
    
    public static Vec3d updateToCamera(final Vec3d vec) {
        return new Vec3d(vec.x - RenderUtil.mc.getRenderManager().viewerPosX, vec.y - RenderUtil.mc.getRenderManager().viewerPosY, vec.z - RenderUtil.mc.getRenderManager().viewerPosZ);
    }
    
    public static void scissor(final int x, final int y, final int x2, final int y2) {
        GL11.glScissor(x * new ScaledResolution(RenderUtil.mc).getScaleFactor(), (new ScaledResolution(RenderUtil.mc).getScaledHeight() - y2) * new ScaledResolution(RenderUtil.mc).getScaleFactor(), (x2 - x) * new ScaledResolution(RenderUtil.mc).getScaleFactor(), (y2 - y) * new ScaledResolution(RenderUtil.mc).getScaleFactor());
    }
    
    public static double normalize(final double value, final double max) {
        return 0.5 * ((value - 0.0) / (max - 0.0)) + 0.5;
    }
    
    public static void drawBBBox(final AxisAlignedBB BB, final Color Color, final int alpha) {
        final AxisAlignedBB bb = new AxisAlignedBB(BB.minX - RenderUtil.mc.getRenderManager().viewerPosX, BB.minY - RenderUtil.mc.getRenderManager().viewerPosY, BB.minZ - RenderUtil.mc.getRenderManager().viewerPosZ, BB.maxX - RenderUtil.mc.getRenderManager().viewerPosX, BB.maxY - RenderUtil.mc.getRenderManager().viewerPosY, BB.maxZ - RenderUtil.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtil.mc.getRenderManager().viewerPosX, bb.minY + RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, Color.getRed() / 255.0f, Color.getGreen() / 255.0f, Color.getBlue() / 255.0f, alpha / 255.0f);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawBBBoxWithHeight(final AxisAlignedBB BB, final Color Color, final int alpha, final float height) {
        final AxisAlignedBB bb = new AxisAlignedBB(BB.minX - RenderUtil.mc.getRenderManager().viewerPosX, BB.minY - RenderUtil.mc.getRenderManager().viewerPosY, BB.minZ - RenderUtil.mc.getRenderManager().viewerPosZ, BB.maxX - RenderUtil.mc.getRenderManager().viewerPosX, BB.maxY - RenderUtil.mc.getRenderManager().viewerPosY - 1.0 + height, BB.maxZ - RenderUtil.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtil.mc.getRenderManager().viewerPosX, bb.minY + RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, Color.getRed() / 255.0f, Color.getGreen() / 255.0f, Color.getBlue() / 255.0f, alpha / 255.0f);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawBBBoxWithHeightDepth(final AxisAlignedBB BB, final Color Color, final int alpha, final float height) {
        final AxisAlignedBB bb = new AxisAlignedBB(BB.minX - RenderUtil.mc.getRenderManager().viewerPosX, BB.minY - RenderUtil.mc.getRenderManager().viewerPosY, BB.minZ - RenderUtil.mc.getRenderManager().viewerPosZ, BB.maxX - RenderUtil.mc.getRenderManager().viewerPosX, BB.maxY - RenderUtil.mc.getRenderManager().viewerPosY - 1.0 + height, BB.maxZ - RenderUtil.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtil.mc.getRenderManager().viewerPosX, bb.minY + RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, Color.getRed() / 255.0f, Color.getGreen() / 255.0f, Color.getBlue() / 255.0f, alpha / 255.0f);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawBlockOutlineBBWithHeight(final AxisAlignedBB bb, final Color color, final float linewidth, final float height) {
        final Vec3d interp = interpolateEntity((Entity)RenderUtil.mc.player, RenderUtil.mc.getRenderPartialTicks());
        drawBlockOutlineWithHeight(bb.grow(0.0020000000949949026).offset(-interp.x, -interp.y, -interp.z), color, linewidth, height);
    }
    
    public static void drawBlockOutlineBB(final AxisAlignedBB bb, final Color color, final float linewidth) {
        final Vec3d interp = interpolateEntity((Entity)RenderUtil.mc.player, RenderUtil.mc.getRenderPartialTicks());
        drawBlockOutline(bb.grow(0.0020000000949949026).offset(-interp.x, -interp.y, -interp.z), color, linewidth);
    }
    
    public static void drawBoxESP(final BlockPos pos, final Color color, final boolean secondC, final Color secondColor, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final boolean air) {
        if (box) {
            drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha));
        }
        if (outline) {
            drawBlockOutline(pos, secondC ? secondColor : color, lineWidth, air);
        }
    }
    
    public static void drawFullBox(final boolean outline, final boolean box, final Color boxColor, final Color outlineColor, final float lineWidth, final BlockPos pos) {
        if (box) {
            drawBox(pos, boxColor);
        }
        if (outline) {
            drawBlockOutline(pos, outlineColor, lineWidth, true);
        }
    }
    
    public static void drawBlockOutline(final BlockPos pos, final Color color, final float linewidth, final boolean air) {
        final IBlockState iblockstate = RenderUtil.mc.world.getBlockState(pos);
        if ((air || iblockstate.getMaterial() != Material.AIR) && RenderUtil.mc.world.getWorldBorder().contains(pos)) {
            assert RenderUtil.mc.renderViewEntity != null;
            final Vec3d interp = interpolateEntity(RenderUtil.mc.renderViewEntity, RenderUtil.mc.getRenderPartialTicks());
            drawBlockOutline(iblockstate.getSelectedBoundingBox((World)RenderUtil.mc.world, pos).grow(0.0020000000949949026).offset(-interp.x, -interp.y, -interp.z), color, linewidth);
        }
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - RenderUtil.mc.getRenderManager().renderPosX, y - RenderUtil.mc.getRenderManager().renderPosY, z - RenderUtil.mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-RenderUtil.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(RenderUtil.mc.player.rotationPitch, (RenderUtil.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }
    
    public static void drawText(final BlockPos pos, final String text) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, (EntityPlayer)RenderUtil.mc.player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(Ruby.fontManager.getStringWidth(text) / 2.0), 0.0, 0.0);
        Ruby.fontManager.drawStringWithShadow(text, 0.0f, 0.0f, -1);
        GlStateManager.popMatrix();
    }
    
    public static void drawText2(final BlockPos pos, final String text) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, (EntityPlayer)RenderUtil.mc.player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(Ruby.fontManager.getStringWidth(text) / 2.0), 0.0, 0.0);
        RenderUtil.mc.fontRenderer.drawStringWithShadow(text, 0.0f, 0.0f, -1);
        GlStateManager.popMatrix();
    }
    
    public static void drawBlockOutline(final AxisAlignedBB bb, final Color color, final float linewidth) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawBlockOutlineWithHeight(final AxisAlignedBB bb, final Color color, final float linewidth, final float height) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        final float alpha = color.getAlpha() / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY - 1.0 + height, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY - 1.0 + height, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY - 1.0 + height, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY - 1.0 + height, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY - 1.0 + height, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY - 1.0 + height, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY - 1.0 + height, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY - 1.0 + height, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }
    
    public static void drawBox(final BlockPos pos, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - RenderUtil.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtil.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtil.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtil.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtil.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - RenderUtil.mc.getRenderManager().viewerPosZ);
        RenderUtil.camera.setPosition(Objects.requireNonNull(RenderUtil.mc.getRenderViewEntity()).posX, RenderUtil.mc.getRenderViewEntity().posY, RenderUtil.mc.getRenderViewEntity().posZ);
        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtil.mc.getRenderManager().viewerPosX, bb.minY + RenderUtil.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtil.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtil.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtil.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtil.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
    
    public static void drawRect(final float x, final float y, final float width, final float height, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)width, (double)height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)width, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawRect(final float x, final float y, final float width, final float height, final Color color) {
        final float alpha = (float)color.getAlpha();
        final float red = (float)color.getRed();
        final float green = (float)color.getGreen();
        final float blue = (float)color.getBlue();
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)width, (double)height, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)width, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawOutlineRect(double x, double y, double width, double height, final Color color, final float lineWidth) {
        if (x < width) {
            final double i = x;
            x = width;
            width = i;
        }
        if (y < height) {
            final double j = y;
            y = height;
            height = j;
        }
        final float f3 = (color.getRGB() >> 24 & 0xFF) / 255.0f;
        final float f4 = (color.getRGB() >> 16 & 0xFF) / 255.0f;
        final float f5 = (color.getRGB() >> 8 & 0xFF) / 255.0f;
        final float f6 = (color.getRGB() & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GL11.glPolygonMode(1032, 6913);
        GL11.glLineWidth(lineWidth);
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f4, f5, f6, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(x, height, 0.0).endVertex();
        bufferbuilder.pos(width, height, 0.0).endVertex();
        bufferbuilder.pos(width, y, 0.0).endVertex();
        bufferbuilder.pos(x, y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPolygonMode(1032, 6914);
    }
    
    static {
        RenderUtil.mc = Ruby.mc;
        RenderUtil.itemRender = RenderUtil.mc.getRenderItem();
        RenderUtil.camera = (ICamera)new Frustum();
        RenderUtil.tessellator = Tessellator.getInstance();
        RenderUtil.builder = RenderUtil.tessellator.getBuffer();
    }
}
