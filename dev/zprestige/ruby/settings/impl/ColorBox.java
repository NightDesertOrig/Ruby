//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.settings.impl;

import dev.zprestige.ruby.settings.*;
import java.awt.*;

public class ColorBox extends Setting
{
    protected Color value;
    
    public ColorBox(final String name) {
        this.name = name;
        this.value = Color.RED;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public void setValue(final Color value) {
        this.value = value;
    }
    
    public Color GetColor() {
        return this.value;
    }
    
    public ColorBox parent(final Parent parent) {
        this.setHasParent(true);
        this.setParent(parent);
        return this;
    }
    
    public ColorBox defaultValue(final Color value) {
        this.value = value;
        return this;
    }
}
