//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click.setting.impl;

import dev.zprestige.ruby.ui.click.setting.*;
import dev.zprestige.ruby.settings.impl.*;
import dev.zprestige.ruby.settings.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;
import java.awt.*;
import java.io.*;
import java.awt.datatransfer.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;

public class ColorSwitchButton extends Button
{
    protected static Tessellator tessellator;
    protected static BufferBuilder builder;
    protected ColorSwitch color;
    protected Color finalColor;
    protected boolean pickingColor;
    protected boolean pickingHue;
    protected boolean pickingAlpha;
    protected boolean opened;
    protected boolean dragging;
    protected int hoverAnimWidth;
    protected int panelX;
    protected int panelY;
    protected int dragX;
    protected int dragY;
    
    public ColorSwitchButton(final ColorSwitch setting) {
        super((Setting)setting);
        this.pickingColor = false;
        this.pickingHue = false;
        this.pickingAlpha = false;
        this.opened = false;
        this.dragging = false;
        this.color = setting;
        this.finalColor = setting.GetColor();
        this.hoverAnimWidth = 0;
    }
    
    public static boolean mouseOver(final int minX, final int minY, final int maxX, final int maxY, final int mX, final int mY) {
        return mX >= minX && mY >= minY && mX <= maxX && mY <= maxY;
    }
    
    public static Color getColor(final Color color, final float alpha) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        return new Color(red, green, blue, alpha);
    }
    
    public static void drawPickerBase(final int pickerX, final int pickerY, final int pickerWidth, final int pickerHeight, final float red, final float green, final float blue) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glBegin(9);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glVertex2f((float)pickerX, (float)pickerY);
        GL11.glVertex2f((float)pickerX, (float)(pickerY + pickerHeight));
        GL11.glColor4f(red, green, blue, 255.0f);
        GL11.glVertex2f((float)(pickerX + pickerWidth), (float)(pickerY + pickerHeight));
        GL11.glVertex2f((float)(pickerX + pickerWidth), (float)pickerY);
        GL11.glEnd();
        GL11.glDisable(3008);
        GL11.glBegin(9);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glVertex2f((float)pickerX, (float)pickerY);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glVertex2f((float)pickerX, (float)(pickerY + pickerHeight));
        GL11.glVertex2f((float)(pickerX + pickerWidth), (float)(pickerY + pickerHeight));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glVertex2f((float)(pickerX + pickerWidth), (float)pickerY);
        GL11.glEnd();
        GL11.glEnable(3008);
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawGradientRect(final double leftpos, final double top, final double right, final double bottom, final int col1, final int col2) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        final float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        final float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        final float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        final float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(leftpos, top);
        GL11.glVertex2d(leftpos, bottom);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void drawLeftGradientRect(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        ColorSwitchButton.builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        ColorSwitchButton.builder.pos((double)right, (double)top, 0.0).color((endColor >> 24 & 0xFF) / 255.0f, (endColor >> 16 & 0xFF) / 255.0f, (endColor >> 8 & 0xFF) / 255.0f, (endColor >> 24 & 0xFF) / 255.0f).endVertex();
        ColorSwitchButton.builder.pos((double)left, (double)top, 0.0).color((startColor >> 16 & 0xFF) / 255.0f, (startColor >> 8 & 0xFF) / 255.0f, (startColor & 0xFF) / 255.0f, (startColor >> 24 & 0xFF) / 255.0f).endVertex();
        ColorSwitchButton.builder.pos((double)left, (double)bottom, 0.0).color((startColor >> 16 & 0xFF) / 255.0f, (startColor >> 8 & 0xFF) / 255.0f, (startColor & 0xFF) / 255.0f, (startColor >> 24 & 0xFF) / 255.0f).endVertex();
        ColorSwitchButton.builder.pos((double)right, (double)bottom, 0.0).color((endColor >> 24 & 0xFF) / 255.0f, (endColor >> 16 & 0xFF) / 255.0f, (endColor >> 8 & 0xFF) / 255.0f, (endColor >> 24 & 0xFF) / 255.0f).endVertex();
        ColorSwitchButton.tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void gradient(final int minX, final int minY, final int maxX, final int maxY, final int startColor, final int endColor, final boolean left) {
        if (left) {
            final float startA = (startColor >> 24 & 0xFF) / 255.0f;
            final float startR = (startColor >> 16 & 0xFF) / 255.0f;
            final float startG = (startColor >> 8 & 0xFF) / 255.0f;
            final float startB = (startColor & 0xFF) / 255.0f;
            final float endA = (endColor >> 24 & 0xFF) / 255.0f;
            final float endR = (endColor >> 16 & 0xFF) / 255.0f;
            final float endG = (endColor >> 8 & 0xFF) / 255.0f;
            final float endB = (endColor & 0xFF) / 255.0f;
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glShadeModel(7425);
            GL11.glBegin(9);
            GL11.glColor4f(startR, startG, startB, startA);
            GL11.glVertex2f((float)minX, (float)minY);
            GL11.glVertex2f((float)minX, (float)maxY);
            GL11.glColor4f(endR, endG, endB, endA);
            GL11.glVertex2f((float)maxX, (float)maxY);
            GL11.glVertex2f((float)maxX, (float)minY);
            GL11.glEnd();
            GL11.glShadeModel(7424);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
        else {
            drawGradientRect(minX, minY, maxX, maxY, startColor, endColor);
        }
    }
    
    public static int gradientColor(final int color, final int percentage) {
        final int r = ((color & 0xFF0000) >> 16) * (100 + percentage) / 100;
        final int g = ((color & 0xFF00) >> 8) * (100 + percentage) / 100;
        final int b = (color & 0xFF) * (100 + percentage) / 100;
        return new Color(r, g, b).hashCode();
    }
    
    public static void drawGradientRect(final float left, final float top, final float right, final float bottom, int startColor, int endColor, final boolean hovered) {
        if (hovered) {
            startColor = gradientColor(startColor, -20);
            endColor = gradientColor(endColor, -20);
        }
        final float c = (startColor >> 24 & 0xFF) / 255.0f;
        final float c2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float c3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float c4 = (startColor & 0xFF) / 255.0f;
        final float c5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float c6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float c7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float c8 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 0.0).color(c2, c3, c4, c).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0).color(c2, c3, c4, c).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 0.0).color(c6, c7, c8, c5).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0).color(c6, c7, c8, c5).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public void render(final int mouseX, final int mouseY) {
        super.render(mouseX, mouseY);
        RenderUtil.drawOutlineRect(this.x + this.width - 11, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, ClickGui.Instance.color.GetColor(), 1.0f);
        if (this.color.GetSwitch()) {
            RenderUtil.drawRect((float)(this.x + this.width - 10), (float)(this.y + 3), (float)(this.x + this.width - 3), (float)(this.y + this.height - 3), ClickGui.Instance.color.GetColor().getRGB());
            RenderUtil.drawOutlineRect(this.x + this.width - 10, this.y + 3, this.x + this.width - 3, this.y + this.height - 3, ClickGui.Instance.backgroundColor.GetColor(), 1.0f);
        }
        if (this.color.GetSwitch()) {
            RenderUtil.drawOutlineRect(this.x + this.width - 23, this.y + 2, this.x + this.width - 14, this.y + this.height - 2, ClickGui.Instance.color.GetColor(), 1.0f);
            RenderUtil.drawRect((float)(this.x + this.width - 22), (float)(this.y + 3), (float)(this.x + this.width - 15), (float)(this.y + this.height - 3), this.color.GetColor().getRGB());
            RenderUtil.drawOutlineRect(this.x + this.width - 22, this.y + 3, this.x + this.width - 15, this.y + this.height - 3, ClickGui.Instance.color.GetColor(), 1.0f);
        }
        Ruby.fontManager.drawStringWithShadow(this.color.getName(), (float)this.x, this.getStringMiddle(this.color.getName()), -1);
        if (this.opened) {
            GL11.glPushMatrix();
            GL11.glPushAttrib(524288);
            RenderUtil.scissor(0, 0, 2000, 2000);
            GL11.glEnable(3089);
            this.dragScreen(mouseX, mouseY);
            RenderUtil.drawRect((float)(this.panelX - 1), (float)(this.panelY - 14), (float)(this.panelX + this.width - 1), (float)(this.panelY + 109), ClickGui.Instance.backgroundColor.GetColor().getRGB());
            Ruby.fontManager.drawStringWithShadow(this.color.getName(), this.panelX + this.width / 2.0f - Ruby.fontManager.getStringWidth(this.color.getName()) / 2.0f, this.panelY - 7.5f - Ruby.fontManager.getFontHeight() / 2.0f, -1);
            RenderUtil.drawOutlineRect(this.panelX - 1, this.panelY - 14, this.panelX + this.width - 1, this.panelY - 1, ClickGui.Instance.color.GetColor(), 1.0f);
            RenderUtil.drawOutlineRect(this.panelX - 1, this.panelY - 1, this.panelX + this.width - 1, this.panelY + 109, ClickGui.Instance.color.GetColor(), 1.0f);
            RenderUtil.prepareScissor(this.panelX + 1, 0, this.width - 4, 1000);
            this.drawPicker(this.color, this.panelX + 1, this.panelY, this.panelX, this.panelY + 90, this.panelX, this.panelY + 80, mouseX, mouseY);
            RenderUtil.releaseScissor();
            RenderUtil.drawOutlineRect(this.panelX + 1, this.panelY, this.panelX + this.width - 3, this.panelY + 78, ClickGui.Instance.color.GetColor(), 1.0f);
            RenderUtil.drawOutlineRect(this.panelX + 1, this.panelY + 80, this.panelX + this.width - 3, this.panelY + 87, ClickGui.Instance.color.GetColor(), 1.0f);
            RenderUtil.drawOutlineRect(this.panelX + 1, this.panelY + 90, this.panelX + this.width - 3, this.panelY + 97, ClickGui.Instance.color.GetColor(), 1.0f);
            RenderUtil.drawRect((float)(this.panelX + 1), (float)(this.panelY + 98), (float)(this.panelX + this.width - 3), (float)(this.panelY + 108), ClickGui.Instance.backgroundColor.GetColor().getRGB());
            RenderUtil.drawOutlineRect(this.panelX + 1, this.panelY + 98, this.panelX + this.width - 3, this.panelY + 108, ClickGui.Instance.color.GetColor(), 1.0f);
            final String hex = String.format("#%06x", this.color.GetColor().getRGB() & 0xFFFFFF);
            Ruby.fontManager.drawStringWithShadow(hex, (float)(this.panelX + 2), this.panelY + 103 - Ruby.fontManager.getFontHeight() / 2.0f, -1);
            this.color.setColorValue(this.finalColor);
            GL11.glDisable(3089);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    }
    
    protected boolean isInsideHex(final int mouseX, final int mouseY) {
        return mouseX > this.panelX + 1 && mouseX < this.panelX + this.width - 3 && mouseY > this.panelY + 98 && mouseY < this.panelY + 108;
    }
    
    protected boolean insideBox(final int mouseX, final int mouseY) {
        return mouseX > this.x + this.width - 28 && mouseX < this.x + this.width - 14 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
    }
    
    protected boolean insideSwitch(final int mouseX, final int mouseY) {
        return mouseX > this.x + this.width - 12 && mouseX < this.x + this.width - 2 && mouseY > this.y + 2 && mouseY < this.y + this.height - 2;
    }
    
    protected boolean isInsideTop(final int mouseX, final int mouseY) {
        return mouseX > this.panelX - 1 && mouseX < this.panelX + this.width - 1 && mouseY > this.panelY - 14 && mouseY < this.panelY - 1;
    }
    
    public void click(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.insideSwitch(mouseX, mouseY)) {
            this.color.setSwitchValue(!this.color.GetSwitch());
        }
        if (this.color.GetSwitch()) {
            if (mouseButton == 0 && this.opened && this.isInsideTop(mouseX, mouseY)) {
                this.dragX = this.panelX - mouseX;
                this.dragY = this.panelY - mouseY;
                this.dragging = true;
            }
            if (mouseButton == 0 && this.insideBox(mouseX, mouseY)) {
                if (!this.opened) {
                    this.panelX = mouseX;
                    this.panelY = mouseY;
                }
                this.opened = !this.opened;
            }
            if (mouseButton == 1 && this.isInsideHex(mouseX, mouseY) && this.opened) {
                this.copyClipBoard();
            }
            if (mouseButton == 0 && this.isInsideHex(mouseX, mouseY) && this.opened) {
                this.pasteClipBoard();
            }
        }
        else if (this.opened) {
            this.opened = false;
        }
    }
    
    public void copyClipBoard() {
        final String hex = this.finalColor.getRed() + "-" + this.finalColor.getGreen() + "-" + this.finalColor.getBlue() + "-" + this.finalColor.getAlpha();
        final StringSelection selection = new StringSelection(hex);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        ClickGui.Instance.mainScreen.copyPasteMap.put(255.0f, "Color has been successfully copied to clipboard.");
    }
    
    public void pasteClipBoard() {
        String string;
        Exception exception = null;
        try {
            string = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        }
        catch (IOException | UnsupportedFlavorException ex2) {
            final Exception ex;
            exception = ex;
            return;
        }
        try {
            final String[] color1 = string.split("-");
            this.color.setColorValue(new Color(Integer.parseInt(color1[0]), Integer.parseInt(color1[1]), Integer.parseInt(color1[2]), Integer.parseInt(color1[3])));
            ClickGui.Instance.mainScreen.copyPasteMap.put(255.0f, "Color has been successfully pasted from clipboard.");
        }
        catch (Exception exception) {
            ClickGui.Instance.mainScreen.copyPasteMap.put(255.0f, "Wrong color format" + exception.getLocalizedMessage().replace("\"", ""));
        }
    }
    
    public void dragScreen(final int mouseX, final int mouseY) {
        if (this.dragging) {
            this.panelX = this.dragX + mouseX;
            this.panelY = this.dragY + mouseY;
        }
    }
    
    public void release(final int mouseX, final int mouseY, final int releaseButton) {
        this.dragging = false;
        final boolean pickingColor = false;
        this.pickingAlpha = pickingColor;
        this.pickingHue = pickingColor;
        this.pickingColor = pickingColor;
    }
    
    public void drawPicker(final ColorSwitch setting, final int pickerX, final int pickerY, final int hueSliderX, final int hueSliderY, final int alphaSliderX, final int alphaSliderY, final int mouseX, final int mouseY) {
        float[] color = { 0.0f, 0.0f, 0.0f, 0.0f };
        try {
            color = new float[] { Color.RGBtoHSB(setting.GetColor().getRed(), setting.GetColor().getGreen(), setting.GetColor().getBlue(), null)[0], Color.RGBtoHSB(setting.GetColor().getRed(), setting.GetColor().getGreen(), setting.GetColor().getBlue(), null)[1], Color.RGBtoHSB(setting.GetColor().getRed(), setting.GetColor().getGreen(), setting.GetColor().getBlue(), null)[2], setting.GetColor().getAlpha() / 255.0f };
        }
        catch (Exception ex) {}
        final int pickerWidth = this.width - 4;
        final int pickerHeight = 78;
        final int hueSliderWidth = pickerWidth + 8;
        final int hueSliderHeight = 7;
        final int alphaSliderHeight = 7;
        if (this.pickingColor && (!Mouse.isButtonDown(0) || !mouseOver(pickerX, pickerY, pickerX + pickerWidth, pickerY + 78, mouseX, mouseY))) {
            this.pickingColor = false;
        }
        if (this.pickingHue && (!Mouse.isButtonDown(0) || !mouseOver(hueSliderX, hueSliderY, hueSliderX + hueSliderWidth, hueSliderY + 7, mouseX, mouseY))) {
            this.pickingHue = false;
        }
        if (this.pickingAlpha && (!Mouse.isButtonDown(0) || !mouseOver(alphaSliderX, alphaSliderY, alphaSliderX + pickerWidth, alphaSliderY + 7, mouseX, mouseY))) {
            this.pickingAlpha = false;
        }
        if (Mouse.isButtonDown(0) && mouseOver(pickerX, pickerY, pickerX + pickerWidth, pickerY + 78, mouseX, mouseY)) {
            this.pickingColor = true;
        }
        if (Mouse.isButtonDown(0) && mouseOver(hueSliderX, hueSliderY, hueSliderX + hueSliderWidth, hueSliderY + 7, mouseX, mouseY)) {
            this.pickingHue = true;
        }
        if (Mouse.isButtonDown(0) && mouseOver(alphaSliderX, alphaSliderY, alphaSliderX + pickerWidth, alphaSliderY + 7, mouseX, mouseY)) {
            this.pickingAlpha = true;
        }
        if (this.pickingHue) {
            final float restrictedX = (float)Math.min(Math.max(hueSliderX, mouseX), hueSliderX + hueSliderWidth - 7);
            color[0] = (restrictedX - hueSliderX) / hueSliderWidth;
        }
        if (this.pickingAlpha) {
            final float restrictedX = (float)Math.min(Math.max(alphaSliderX, mouseX), alphaSliderX + pickerWidth);
            color[3] = 1.0f - (restrictedX - alphaSliderX) / pickerWidth;
        }
        if (this.pickingColor) {
            final float restrictedX = (float)Math.min(Math.max(pickerX, mouseX), pickerX + pickerWidth);
            final float restrictedY = (float)Math.min(Math.max(pickerY, mouseY), pickerY + 78);
            color[1] = (restrictedX - pickerX) / pickerWidth;
            color[2] = 1.0f - (restrictedY - pickerY) / 78.0f;
        }
        final int selectedColor = Color.HSBtoRGB(color[0], 1.0f, 1.0f);
        final float selectedRed = (selectedColor >> 16 & 0xFF) / 255.0f;
        final float selectedGreen = (selectedColor >> 8 & 0xFF) / 255.0f;
        final float selectedBlue = (selectedColor & 0xFF) / 255.0f;
        drawPickerBase(pickerX, pickerY, pickerWidth, 78, selectedRed, selectedGreen, selectedBlue);
        this.drawHueSlider(hueSliderX, hueSliderY, hueSliderWidth - 2, 7, color[0]);
        final int cursorX = (int)(pickerX + color[1] * pickerWidth);
        final int cursorY = (int)(pickerY + 78 - color[2] * 78.0f);
        Gui.drawRect(cursorX - 1, cursorY - 1, cursorX + 1, cursorY + 1, -1);
        RenderUtil.drawOutlineRect(cursorX - 1, cursorY - 1, cursorX + 1, cursorY + 1, ClickGui.Instance.color.GetColor(), 1.0f);
        this.drawAlphaSlider(alphaSliderX, alphaSliderY, pickerWidth, 7, selectedRed, selectedGreen, selectedBlue, color[3]);
        this.finalColor = getColor(new Color(Color.HSBtoRGB(color[0], color[1], color[2])), color[3]);
    }
    
    public void drawHueSlider(final int x, int y, final int width, final int height, final float hue) {
        int step = 0;
        if (height > width) {
            RenderUtil.drawRect((float)x, (float)y, (float)(x + width), (float)(y + 4), -65536);
            y += 4;
            for (int colorIndex = 0; colorIndex < 6; ++colorIndex) {
                final int previousStep = Color.HSBtoRGB(step / 6.0f, 1.0f, 1.0f);
                final int nextStep = Color.HSBtoRGB((step + 1) / 6.0f, 1.0f, 1.0f);
                drawGradientRect((float)x, y + step * (height / 6.0f), (float)(x + width), y + (step + 1) * (height / 6.0f), previousStep, nextStep, false);
                ++step;
            }
            final int sliderMinY = (int)(y + height * hue) - 4;
            RenderUtil.drawRect((float)x, (float)(sliderMinY - 1), (float)(x + width), (float)(sliderMinY + 1), -1);
            RenderUtil.drawOutlineRect(x, sliderMinY - 1, x + width, sliderMinY + 1, Color.BLACK, 1.0f);
            RenderUtil.drawOutlineRect(x, sliderMinY - 1, x + width, sliderMinY + 1, ClickGui.Instance.color.GetColor(), 1.0f);
        }
        else {
            for (int colorIndex = 0; colorIndex < 6; ++colorIndex) {
                final int previousStep = Color.HSBtoRGB(step / 6.0f, 1.0f, 1.0f);
                final int nextStep = Color.HSBtoRGB((step + 1) / 6.0f, 1.0f, 1.0f);
                gradient(x + step * (width / 6), y, x + (step + 1) * (width / 6), y + height, previousStep, nextStep, true);
                ++step;
            }
            final int sliderMinX = (int)(x + width * hue);
            RenderUtil.drawRect((float)(sliderMinX - 1), (float)y, (float)(sliderMinX + 1), (float)(y + height), -1);
            RenderUtil.drawOutlineRect(sliderMinX - 1, y, sliderMinX + 1, y + height, Color.BLACK, 1.0f);
            RenderUtil.drawOutlineRect(sliderMinX - 1, y, sliderMinX + 1, y + height, ClickGui.Instance.color.GetColor(), 1.0f);
        }
    }
    
    public void drawAlphaSlider(final int x, final int y, final int width, final int height, final float red, final float green, final float blue, final float alpha) {
        boolean left = true;
        for (int checkerBoardSquareSize = height / 2, squareIndex = -checkerBoardSquareSize; squareIndex < width; squareIndex += checkerBoardSquareSize) {
            if (!left) {
                RenderUtil.drawRect((float)(x + squareIndex), (float)y, (float)(x + squareIndex + checkerBoardSquareSize), (float)(y + height), -1);
                RenderUtil.drawRect((float)(x + squareIndex), (float)(y + checkerBoardSquareSize), (float)(x + squareIndex + checkerBoardSquareSize), (float)(y + height), -7303024);
                if (squareIndex < width - checkerBoardSquareSize) {
                    final int minX = x + squareIndex + checkerBoardSquareSize;
                    final int maxX = Math.min(x + width, x + squareIndex + checkerBoardSquareSize * 2);
                    RenderUtil.drawRect((float)minX, (float)y, (float)maxX, (float)(y + height), -7303024);
                    RenderUtil.drawRect((float)minX, (float)(y + checkerBoardSquareSize), (float)maxX, (float)(y + height), -1);
                }
            }
            left = !left;
        }
        drawLeftGradientRect(x, y, x + width + 13, y + height, new Color(red, green, blue, 1.0f).getRGB(), 0);
        final int sliderMinX = (int)(x + width - width * alpha);
        RenderUtil.drawRect((float)(sliderMinX - 1), (float)y, (float)(sliderMinX + 1), (float)(y + height), -1);
        RenderUtil.drawOutlineRect(sliderMinX - 1, y, sliderMinX + 1, y + height, Color.BLACK, 1.0f);
        RenderUtil.drawOutlineRect(sliderMinX - 1, y, sliderMinX + 1, y + height, ClickGui.Instance.color.GetColor(), 1.0f);
    }
    
    static {
        ColorSwitchButton.tessellator = Tessellator.getInstance();
        ColorSwitchButton.builder = ColorSwitchButton.tessellator.getBuffer();
    }
}
