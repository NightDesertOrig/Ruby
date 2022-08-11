//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import net.minecraft.client.*;
import dev.zprestige.ruby.ui.font.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.module.client.*;
import java.awt.*;
import java.io.*;

public class FontManager
{
    protected final Minecraft mc;
    protected FontRenderer customFont;
    protected int size;
    
    public FontManager() {
        this.mc = Ruby.mc;
        this.loadFont(this.size = 17);
    }
    
    public void loadFont(final int size) {
        this.size = size;
        this.customFont = new FontRenderer(this.getFont((float)size));
    }
    
    public void drawStringWithShadow(final String text, final float x, final float y, final int color) {
        if (CustomFont.Instance.isEnabled()) {
            this.customFont.drawStringWithShadow(text, x, y, color);
        }
        else {
            this.mc.fontRenderer.drawStringWithShadow(text, x, y, color);
        }
    }
    
    public int getStringWidth(final String text) {
        if (CustomFont.Instance.isEnabled()) {
            return this.customFont.getStringWidth(text);
        }
        return this.mc.fontRenderer.getStringWidth(text);
    }
    
    public float getFontHeight() {
        return (CustomFont.Instance.isEnabled() ? this.customFont.getHeight() : ((float)this.mc.fontRenderer.FONT_HEIGHT)) / 2.0f;
    }
    
    private Font getFont(final float size) {
        final Font plain = new Font("default", 0, (int)size);
        try {
            final InputStream inputStream = FontManager.class.getResourceAsStream("/assets/minecraft/textures/ruby/font/Font.ttf");
            if (inputStream != null) {
                Font awtClientFont = Font.createFont(0, inputStream);
                awtClientFont = awtClientFont.deriveFont(0, size);
                inputStream.close();
                return awtClientFont;
            }
            return plain;
        }
        catch (Exception exception) {
            return plain;
        }
    }
    
    public int getSize() {
        return this.size;
    }
}
