//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.settings.impl;

import dev.zprestige.ruby.settings.*;
import java.util.*;

public class Parent extends Setting
{
    protected final List<Setting> children;
    protected boolean value;
    
    public Parent(final String name) {
        this.children = new ArrayList<Setting>();
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
    
    public boolean GetParent() {
        return this.value;
    }
    
    public List<Setting> getChildren() {
        return this.children;
    }
}
