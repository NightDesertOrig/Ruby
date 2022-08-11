//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.font;

import javax.annotation.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class FontRenderer
{
    protected final ImageAWT defaultFont;
    protected final int FONT_HEIGHT;
    
    public FontRenderer(final Font font) {
        this.defaultFont = new ImageAWT(font);
        this.FONT_HEIGHT = (int)this.getHeight();
    }
    
    public float getHeight() {
        return this.defaultFont.getHeight() / 2.0f;
    }
    
    public int getSize() {
        return this.defaultFont.getFont().getSize();
    }
    
    @ParametersAreNonnullByDefault
    public int drawStringWithShadow(final String text, final float x, final float y, final int color) {
        return this.drawString(text, x, y, color, true);
    }
    
    public int drawString(final String text, final float x, final float y, final int color, final boolean dropShadow) {
        final float currY = y - 3.0f;
        if (text.contains("\n")) {
            final String[] parts = text.split("\n");
            float newY = 0.0f;
            for (final String s : parts) {
                this.drawText(s, x, currY + newY, color, dropShadow);
                newY += this.getHeight();
            }
            return 0;
        }
        if (dropShadow) {
            this.drawText(text, x + 0.4f, currY + 0.3f, new Color(0, 0, 0, 150).getRGB(), true);
        }
        return this.drawText(text, x, currY, color, false);
    }
    
    private int drawText(final String text, final float x, final float y, final int color, final boolean ignoreColor) {
        if (text == null) {
            return 0;
        }
        if (text.isEmpty()) {
            return (int)x;
        }
        GlStateManager.translate(x - 1.5, y + 0.5, 0.0);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableTexture2D();
        GL11.glEnable(2848);
        int currentColor = color;
        if ((currentColor & 0xFC000000) == 0x0) {
            currentColor |= 0xFF000000;
        }
        this.defaultFont.drawString(text, 0.0, 0.0, currentColor);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.translate(-(x - 1.5), -(y + 0.5), 0.0);
        return (int)(x + this.getStringWidth(text));
    }
    
    public int getStringWidth(final String text) {
        return this.defaultFont.getStringWidth(text) / 2;
    }
}
