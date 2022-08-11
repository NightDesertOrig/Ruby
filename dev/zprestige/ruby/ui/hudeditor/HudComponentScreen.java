//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.hudeditor;

import dev.zprestige.ruby.ui.hudeditor.components.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import dev.zprestige.ruby.*;

public class HudComponentScreen
{
    protected final HudComponent hudComponent;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    
    public HudComponentScreen(final HudComponent hudComponent, final float x, final float y, final float width, final float height) {
        this.hudComponent = hudComponent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void release(final int button) {
        if (this.check()) {
            this.hudComponent.release(button);
        }
    }
    
    public void click(final int mouseX, final int mouseY, final int button) {
        if (this.check()) {
            this.hudComponent.click(mouseX, mouseY, button);
        }
        if (this.inside(mouseX, mouseY) && button == 0) {
            this.hudComponent.setEnabled(!this.hudComponent.isEnabled());
        }
    }
    
    public void draw(final int mouseX, final int mouseY) {
        if (this.check()) {
            this.hudComponent.update(mouseX, mouseY);
        }
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.hudComponent.isEnabled() ? ClickGui.Instance.color.GetColor().getRGB() : ClickGui.Instance.backgroundColor.GetColor().getRGB());
        RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.backgroundColor.GetColor(), 1.0f);
        if (this.inside(mouseX, mouseY)) {
            RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.backgroundColor.GetColor().getRGB());
        }
        Ruby.fontManager.drawStringWithShadow(this.hudComponent.getName(), this.x + 2.0f, this.y + this.height - Ruby.fontManager.getFontHeight() * 2.0f, -1);
    }
    
    protected boolean inside(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    protected boolean check() {
        return this.hudComponent.isEnabled();
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
}
