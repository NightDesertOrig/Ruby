//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.settings.impl;

import dev.zprestige.ruby.settings.*;

public class Slider extends Setting
{
    protected float value;
    protected float min;
    protected float max;
    
    public Slider(final String name, final int min, final int max) {
        this.name = name;
        this.value = (float)min;
        this.min = (float)min;
        this.max = (float)max;
    }
    
    public Slider(final String name, final float min, final float max) {
        this.name = name;
        this.value = min;
        this.min = min;
        this.max = max;
    }
    
    public Slider(final String name, final double min, final double max) {
        this.name = name;
        this.value = (float)min;
        this.min = (float)min;
        this.max = (float)max;
    }
    
    public void setValue(final float value) {
        this.value = value;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public float getMin() {
        return this.min;
    }
    
    public float getMax() {
        return this.max;
    }
    
    public float GetSlider() {
        return this.value;
    }
    
    public Slider parent(final Parent parent) {
        this.setHasParent(true);
        this.setParent(parent);
        return this;
    }
}
