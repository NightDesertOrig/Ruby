//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.font;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import dev.zprestige.ruby.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.*;
import java.util.*;

@SideOnly(Side.CLIENT)
public class ImageAWT
{
    private final Minecraft mc;
    private static final ArrayList<ImageAWT> activeFontRenderers;
    private static int gcTicks;
    private final Font font;
    private int fontHeight;
    private final CharLocation[] charLocations;
    private final HashMap<String, FontCache> cachedStrings;
    private int textureID;
    private int textureWidth;
    private int textureHeight;
    
    public static void garbageCollectionTick() {
        if (ImageAWT.gcTicks++ > 600) {
            ImageAWT.activeFontRenderers.forEach(ImageAWT::collectGarbage);
            ImageAWT.gcTicks = 0;
        }
    }
    
    public ImageAWT(final Font font, final int startChar, final int stopChar) {
        this.mc = Ruby.mc;
        this.fontHeight = -1;
        this.cachedStrings = new HashMap<String, FontCache>();
        this.textureID = 0;
        this.textureWidth = 0;
        this.textureHeight = 0;
        this.font = font;
        this.charLocations = new CharLocation[stopChar];
        this.renderBitmap(startChar, stopChar);
        ImageAWT.activeFontRenderers.add(this);
    }
    
    public ImageAWT(final Font font) {
        this(font, 0, 255);
    }
    
    private void collectGarbage() {
        final long currentTime = System.currentTimeMillis();
        this.cachedStrings.entrySet().stream().filter(entry -> currentTime - entry.getValue().getLastUsage() > 30000L).forEach(entry -> {
            GL11.glDeleteLists(entry.getValue().getDisplayList(), 1);
            this.cachedStrings.remove(entry.getKey());
        });
    }
    
    public float getHeight() {
        return (this.fontHeight - 8.0f) / 2.0f;
    }
    
    public void drawString(final String text, final double x, final double y, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.25, 0.25, 0.25);
        GL11.glTranslated(x * 2.0, y * 2.0 - 2.0, 0.0);
        GlStateManager.bindTexture(this.textureID);
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
        double currX = 0.0;
        final FontCache cached = this.cachedStrings.get(text);
        if (cached != null) {
            GL11.glCallList(cached.getDisplayList());
            cached.setLastUsage(System.currentTimeMillis());
            GlStateManager.popMatrix();
            return;
        }
        final int list = -1;
        final boolean assumeNonVolatile = false;
        GL11.glBegin(7);
        for (final char ch : text.toCharArray()) {
            if (Character.getNumericValue(ch) >= this.charLocations.length) {
                GL11.glEnd();
                GlStateManager.scale(4.0, 4.0, 4.0);
                this.mc.fontRenderer.drawString(String.valueOf(ch), (float)currX * 0.25f + 1.0f, 2.0f, color, false);
                currX += this.mc.fontRenderer.getStringWidth(String.valueOf(ch)) * 4.0;
                GlStateManager.scale(0.25, 0.25, 0.25);
                GlStateManager.bindTexture(this.textureID);
                GlStateManager.color(red, green, blue, alpha);
                GL11.glBegin(7);
            }
            else if (this.charLocations.length > ch) {
                final CharLocation fontChar;
                if ((fontChar = this.charLocations[ch]) != null) {
                    this.drawChar(fontChar, (float)currX);
                    currX += fontChar.width - 8.0;
                }
            }
        }
        GL11.glEnd();
        GlStateManager.popMatrix();
    }
    
    private void drawChar(final CharLocation ch, final float x) {
        final float width = (float)ch.width;
        final float height = (float)ch.height;
        final float srcX = (float)ch.x;
        final float srcY = (float)ch.y;
        final float renderX = srcX / this.textureWidth;
        final float renderY = srcY / this.textureHeight;
        final float renderWidth = width / this.textureWidth;
        final float renderHeight = height / this.textureHeight;
        GL11.glTexCoord2f(renderX, renderY);
        GL11.glVertex2f(x, 0.0f);
        GL11.glTexCoord2f(renderX, renderY + renderHeight);
        GL11.glVertex2f(x, 0.0f + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY + renderHeight);
        GL11.glVertex2f(x + width, 0.0f + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY);
        GL11.glVertex2f(x + width, 0.0f);
    }
    
    private void renderBitmap(final int startChar, final int stopChar) {
        final BufferedImage[] fontImages = new BufferedImage[stopChar];
        int rowHeight = 0;
        int charX = 0;
        int charY = 0;
        for (int targetChar = startChar; targetChar < stopChar; ++targetChar) {
            final BufferedImage fontImage = this.drawCharToImage((char)targetChar);
            final CharLocation fontChar = new CharLocation(charX, charY, fontImage.getWidth(), fontImage.getHeight());
            if (fontChar.height > this.fontHeight) {
                this.fontHeight = fontChar.height;
            }
            if (fontChar.height > rowHeight) {
                rowHeight = fontChar.height;
            }
            if (this.charLocations.length > targetChar) {
                this.charLocations[targetChar] = fontChar;
                fontImages[targetChar] = fontImage;
                if ((charX += fontChar.width) > 2048) {
                    if (charX > this.textureWidth) {
                        this.textureWidth = charX;
                    }
                    charX = 0;
                    charY += rowHeight;
                    rowHeight = 0;
                }
            }
        }
        this.textureHeight = charY + rowHeight;
        final BufferedImage bufferedImage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
        final Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setFont(this.font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, this.textureWidth, this.textureHeight);
        graphics2D.setColor(Color.WHITE);
        for (int targetChar2 = startChar; targetChar2 < stopChar; ++targetChar2) {
            if (fontImages[targetChar2] != null) {
                if (this.charLocations[targetChar2] != null) {
                    graphics2D.drawImage(fontImages[targetChar2], this.charLocations[targetChar2].x, this.charLocations[targetChar2].y, null);
                }
            }
        }
        this.textureID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), bufferedImage, true, true);
    }
    
    private BufferedImage drawCharToImage(final char ch) {
        final Graphics2D graphics2D = (Graphics2D)new BufferedImage(1, 1, 2).getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(this.font);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int charWidth = fontMetrics.charWidth(ch) + 8;
        if (charWidth <= 8) {
            charWidth = 7;
        }
        int charHeight;
        if ((charHeight = fontMetrics.getHeight() + 3) <= 0) {
            charHeight = this.font.getSize();
        }
        final BufferedImage fontImage = new BufferedImage(charWidth, charHeight, 2);
        final Graphics2D graphics = (Graphics2D)fontImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setFont(this.font);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
        return fontImage;
    }
    
    public int getStringWidth(final String text) {
        int width = 0;
        for (final int ch : text.toCharArray()) {
            final int index = (ch < this.charLocations.length) ? ch : 3;
            final CharLocation fontChar;
            if (this.charLocations.length <= index || (fontChar = this.charLocations[index]) == null) {
                width += (int)(this.mc.fontRenderer.getStringWidth(String.valueOf(ch)) / 4.0);
            }
            else {
                width += (int)(fontChar.width - 8.0);
            }
        }
        return width / 2;
    }
    
    public Font getFont() {
        return this.font;
    }
    
    static {
        activeFontRenderers = new ArrayList<ImageAWT>();
        ImageAWT.gcTicks = 0;
    }
    
    private static class CharLocation
    {
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        
        CharLocation(final int x, final int y, final int width, final int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
