//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.eventbus.handler;

import java.lang.reflect.*;
import dev.zprestige.ruby.eventbus.event.*;

public abstract class Handler
{
    protected final Object subscriber;
    
    public Handler(final Method listener, final Object subscriber) {
        listener.setAccessible(true);
        this.subscriber = subscriber;
    }
    
    public abstract void invoke(final Event p0);
    
    public boolean isSubscriber(final Object object) {
        return this.subscriber.equals(object);
    }
}
