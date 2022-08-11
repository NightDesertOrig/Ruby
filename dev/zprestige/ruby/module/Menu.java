//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module;

import dev.zprestige.ruby.settings.*;
import dev.zprestige.ruby.settings.impl.*;

public class Menu
{
    protected Module module;
    
    public Menu(final Module module) {
        this.module = module;
    }
    
    protected void addSetting(final Setting setting) {
        this.module.newSettings.add(setting);
    }
    
    protected void setModule(final Setting setting) {
        setting.setModule(this.module);
    }
    
    public ColorBox Color(final String name) {
        final ColorBox setting = new ColorBox(name);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
    
    public ColorSwitch ColorSwitch(final String name) {
        final ColorSwitch setting = new ColorSwitch(name);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
    
    public ComboBox ComboBox(final String name, final String[] values) {
        final ComboBox setting = new ComboBox(name, values);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
    
    public Key Key(final String name, final int key) {
        final Key setting = new Key(name, key);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
    
    public Parent Parent(final String name) {
        final Parent setting = new Parent(name);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
    
    public Slider Slider(final String name, final int min, final int max) {
        final Slider setting = new Slider(name, min, max);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
    
    public Slider Slider(final String name, final double min, final double max) {
        final Slider setting = new Slider(name, min, max);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
    
    public Slider Slider(final String name, final float min, final float max) {
        final Slider setting = new Slider(name, min, max);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
    
    public Switch Switch(final String name) {
        final Switch setting = new Switch(name);
        this.setModule(setting);
        this.addSetting(setting);
        return setting;
    }
}
