//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.visual;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import dev.zprestige.ruby.*;
import java.awt.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

public class Interactions extends Module
{
    public final Slider range;
    public final ColorBox color;
    public final ColorBox outlineColor;
    
    public Interactions() {
        this.range = this.Menu.Slider("Range", 0.1f, 300.0f);
        this.color = this.Menu.Color("Color");
        this.outlineColor = this.Menu.Color("Outline Color");
    }
    
    public void onFrame(final float partialTicks) {
        this.mc.renderGlobal.damagedBlocks.forEach((integer, destroyBlockProgress) -> this.renderDestroyProgress(destroyBlockProgress));
    }
    
    private void renderDestroyProgress(final DestroyBlockProgress destroyBlockProgress) {
        if (destroyBlockProgress != null) {
            final BlockPos pos = destroyBlockProgress.getPosition();
            if ((this.mc.playerController.getIsHittingBlock() && this.mc.playerController.currentBlock.equals((Object)pos)) || this.mc.player.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > this.range.GetSlider()) {
                return;
            }
            final float progress = Math.min(1.0f, destroyBlockProgress.getPartialBlockDamage() / 8.0f);
            this.renderProgress(pos, progress);
        }
    }
    
    private void renderProgress(final BlockPos pos, final float progress) {
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        final double x = pos.getX() + 0.5;
        final double y = pos.getY() + 0.5;
        final double z = pos.getZ() + 0.5;
        float scale = 0.015833333f;
        GlStateManager.translate(x - this.mc.renderManager.renderPosX, y - this.mc.renderManager.renderPosY, z - this.mc.renderManager.renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.mc.player.rotationPitch, (this.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        final int distance = (int)this.mc.player.getDistance(x, y, z);
        scale = distance / 2.0f / 3.0f;
        if (scale < 1.0f) {
            scale = 1.0f;
        }
        GlStateManager.scale(scale, scale, scale);
        final String string = progress * 100.0f + "%";
        GlStateManager.translate(-(Ruby.fontManager.getStringWidth(string) / 2.0), 0.0, 0.0);
        RenderUtil.drawUnfilledCircle(Ruby.fontManager.getStringWidth(string) / 2.0f, 0.0f, 23.0f, new Color(this.outlineColor.GetColor().getRed() / 255.0f, this.outlineColor.GetColor().getGreen() / 255.0f, this.outlineColor.GetColor().getBlue() / 255.0f, 1.0f).getRGB(), 5.0f, progress * 360.0f);
        RenderUtil.drawCircle(Ruby.fontManager.getStringWidth(string) / 2.0f, 0.0f, 22.0f, new Color(this.color.GetColor().getRed() / 255.0f, this.color.GetColor().getGreen() / 255.0f, this.color.GetColor().getBlue() / 255.0f, 1.0f).getRGB());
        Ruby.fontManager.drawStringWithShadow(string, 0.0f, 6.0f, new Color(255, 255, 255).getRGB());
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/icons/pickaxe.png"));
        Gui.drawScaledCustomSizeModalRect((int)(Ruby.fontManager.getStringWidth(string) / 2.0f) - 10, -17, 0.0f, 0.0f, 12, 12, 22, 22, 12.0f, 12.0f);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
