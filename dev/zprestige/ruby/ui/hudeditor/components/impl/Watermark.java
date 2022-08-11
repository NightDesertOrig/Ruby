//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.hudeditor.components.impl;

import dev.zprestige.ruby.ui.hudeditor.components.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;

public class Watermark extends HudComponent
{
    public Watermark() {
        super("Watermark", 0.0f, 0.0f, (float)Ruby.fontManager.getStringWidth("Ruby"), Ruby.fontManager.getFontHeight());
    }
    
    public void render() {
        final String text = "Ruby";
        Ruby.fontManager.drawStringWithShadow("Ruby", this.x, this.y, -1);
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.color.GetColor());
        this.setWidth((float)Ruby.fontManager.getStringWidth("Ruby"));
        this.setHeight(Ruby.fontManager.getFontHeight());
    }
}
