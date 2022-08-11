//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.events;

import dev.zprestige.ruby.eventbus.event.*;
import dev.zprestige.ruby.module.*;

public class ModuleToggleEvent extends Event
{
    public static class Enable extends ModuleToggleEvent
    {
        Module module;
        
        public Enable(final Module module) {
            this.module = module;
        }
        
        public Module getModule() {
            return this.module;
        }
    }
    
    public static class Disable extends ModuleToggleEvent
    {
        Module module;
        
        public Disable(final Module module) {
            this.module = module;
        }
        
        public Module getModule() {
            return this.module;
        }
    }
}
