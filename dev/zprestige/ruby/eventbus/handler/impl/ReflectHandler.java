//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.eventbus.handler.impl;

import dev.zprestige.ruby.eventbus.handler.*;
import java.lang.reflect.*;
import dev.zprestige.ruby.eventbus.event.*;

public class ReflectHandler extends Handler
{
    protected final Method listener;
    
    public ReflectHandler(final Method listener, final Object subscriber) {
        super(listener, subscriber);
        this.listener = listener;
    }
    
    public void invoke(final Event event) {
        try {
            this.listener.invoke(this.subscriber, event);
        }
        catch (Exception ex) {}
    }
}
