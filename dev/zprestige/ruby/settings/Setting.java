//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.settings;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;

public class Setting
{
    protected Module module;
    protected String name;
    protected boolean hasParent;
    protected Parent parent;
    
    public Setting() {
        this.hasParent = false;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(final Module module) {
        this.module = module;
    }
    
    public void setHasParent(final boolean hasParent) {
        this.hasParent = hasParent;
    }
    
    public boolean hasParent() {
        return this.hasParent;
    }
    
    public Parent getParent() {
        return this.parent;
    }
    
    public void setParent(final Parent parent) {
        parent.getChildren().add(this);
        this.parent = parent;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean openedParent() {
        return !this.hasParent || this.parent.GetParent();
    }
}
