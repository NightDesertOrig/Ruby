//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.eventbus.event;

import java.lang.annotation.*;

public abstract class Event
{
    protected boolean cancelled;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        if (this.getClass().isAnnotationPresent(IsCancellable.class)) {
            this.cancelled = cancelled;
        }
    }
}
