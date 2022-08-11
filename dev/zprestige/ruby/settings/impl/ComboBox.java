//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.settings.impl;

import dev.zprestige.ruby.settings.*;

public class ComboBox extends Setting
{
    protected String value;
    protected String[] values;
    
    public ComboBox(final String name, final String[] values) {
        this.name = name;
        this.value = values[0];
        this.values = values;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public String GetCombo() {
        return this.value;
    }
    
    public String[] getValues() {
        return this.values;
    }
    
    public ComboBox parent(final Parent parent) {
        this.setHasParent(true);
        this.setParent(parent);
        return this;
    }
}
