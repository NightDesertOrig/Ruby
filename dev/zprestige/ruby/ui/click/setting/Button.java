//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.click.setting;

import dev.zprestige.ruby.settings.*;
import dev.zprestige.ruby.module.client.*;
import dev.zprestige.ruby.util.*;
import java.awt.*;
import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.*;

public class Button
{
    protected Setting setting;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    public Button(final Setting setting) {
        this.setting = setting;
    }
    
    public void render(final int mouseX, final int mouseY) {
        RenderUtil.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), ClickGui.Instance.backgroundColor.GetColor().getRGB());
        RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, this.y + this.height, ClickGui.Instance.backgroundColor.GetColor(), 1.0f);
        if (this.isInside(mouseX, mouseY)) {
            RenderUtil.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), ClickGui.Instance.backgroundColor.GetColor().getRGB());
        }
    }
    
    public void hover(final int mouseX, final int mouseY) {
        if (this.isInside(mouseX, mouseY)) {
            RenderUtil.drawRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), new Color(0, 0, 0, 50).getRGB());
        }
    }
    
    public void click(final int mouseX, final int mouseY, final int clickedButton) {
    }
    
    public void type(final char typedChar, final int keyCode) {
    }
    
    public void release(final int mouseX, final int mouseY, final int releaseButton) {
    }
    
    public Setting getSetting() {
        return this.setting;
    }
    
    public Module getModule() {
        return this.setting.getModule();
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public boolean isInside(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    public float getStringMiddle(final String string) {
        return this.y + this.height / 2.0f - Ruby.fontManager.getFontHeight() / 2.0f;
    }
}
