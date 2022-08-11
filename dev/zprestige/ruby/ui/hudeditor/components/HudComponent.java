//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.hudeditor.components;

import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;

public class HudComponent
{
    protected String name;
    protected boolean enabled;
    protected boolean dragging;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float dragX;
    protected float dragY;
    
    public HudComponent(final String name, final float x, final float y, final float width, final float height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.enabled = false;
    }
    
    protected void drag(final int mouseX, final int mouseY) {
        this.x = this.dragX + mouseX;
        this.y = this.dragY + mouseY;
    }
    
    public void release(final int button) {
        if (button == 0) {
            this.dragging = false;
        }
    }
    
    public void click(final int mouseX, final int mouseY, final int button) {
        if (this.inside(mouseX, mouseY) && button == 0) {
            this.dragX = this.x - mouseX;
            this.dragY = this.y - mouseY;
            this.dragging = true;
        }
    }
    
    public void update(final int mouseX, final int mouseY) {
        if (this.dragging) {
            this.drag(mouseX, mouseY);
        }
        if (this.inside(mouseX, mouseY)) {
            RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.backgroundColor.GetColor().getRGB());
        }
        RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.color.GetColor(), 1.0f);
    }
    
    public void render() {
    }
    
    protected boolean inside(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
}
