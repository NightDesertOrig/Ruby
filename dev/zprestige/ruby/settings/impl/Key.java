//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.settings.impl;

import dev.zprestige.ruby.settings.*;

public class Key extends Setting
{
    protected int key;
    
    public Key(final String name, final int key) {
        this.name = name;
        this.key = key;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public void setValue(final int value) {
        this.key = value;
    }
    
    public int GetKey() {
        return this.key;
    }
    
    public Key parent(final Parent parent) {
        this.setHasParent(true);
        this.setParent(parent);
        return this;
    }
}
