//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.settings.impl;

import dev.zprestige.ruby.settings.*;

public class Switch extends Setting
{
    protected boolean value;
    
    public Switch(final String name) {
        this.name = name;
        this.value = false;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public void setValue(final boolean value) {
        this.value = value;
    }
    
    public boolean GetSwitch() {
        return this.value;
    }
    
    public Switch parent(final Parent parent) {
        this.setHasParent(true);
        this.setParent(parent);
        return this;
    }
}
