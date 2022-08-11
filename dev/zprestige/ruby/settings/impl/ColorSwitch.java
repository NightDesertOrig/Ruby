//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.settings.impl;

import dev.zprestige.ruby.settings.*;
import java.awt.*;

public class ColorSwitch extends Setting
{
    protected boolean booleanValue;
    protected Color colorValue;
    
    public ColorSwitch(final String name) {
        this.name = name;
        this.booleanValue = false;
        this.colorValue = Color.RED;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public void setBool(final boolean value) {
        this.booleanValue = value;
    }
    
    public void setColor(final Color value) {
        this.colorValue = value;
    }
    
    public boolean GetSwitch() {
        return this.booleanValue;
    }
    
    public Color GetColor() {
        return this.colorValue;
    }
    
    public void setSwitchValue(final boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
    
    public void setColorValue(final Color colorValue) {
        this.colorValue = colorValue;
    }
    
    public ColorSwitch parent(final Parent parent) {
        this.setHasParent(true);
        this.setParent(parent);
        return this;
    }
}
