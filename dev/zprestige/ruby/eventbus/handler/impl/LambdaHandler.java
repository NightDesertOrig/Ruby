//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.eventbus.handler.impl;

import java.util.concurrent.*;
import dev.zprestige.ruby.eventbus.handler.*;
import java.lang.reflect.*;
import dev.zprestige.ruby.eventbus.event.*;
import java.lang.invoke.*;

public class LambdaHandler extends Handler
{
    private static final ConcurrentHashMap<Method, DynamicHandler> handlerCache;
    private DynamicHandler dynamicHandler;
    
    public LambdaHandler(final Method listener, final Object subscriber) {
        super(listener, subscriber);
        if (LambdaHandler.handlerCache.containsKey(listener)) {
            this.dynamicHandler = LambdaHandler.handlerCache.get(listener);
        }
        else {
            try {
                final MethodHandles.Lookup lookup = MethodHandles.lookup();
                final boolean isStatic = Modifier.isStatic(listener.getModifiers());
                final MethodType targetSignature = MethodType.methodType(DynamicHandler.class);
                final CallSite callSite = LambdaMetafactory.metafactory(lookup, "invoke", isStatic ? targetSignature : targetSignature.appendParameterTypes(subscriber.getClass()), MethodType.methodType(Void.TYPE, Event.class), lookup.unreflect(listener), MethodType.methodType(Void.TYPE, listener.getParameterTypes()[0]));
                final MethodHandle target = callSite.getTarget();
                this.dynamicHandler = (DynamicHandler)(isStatic ? target.invoke() : target.invoke(subscriber));
                LambdaHandler.handlerCache.put(listener, this.dynamicHandler);
            }
            catch (Throwable t) {}
        }
    }
    
    public void invoke(final Event event) {
        this.dynamicHandler.invoke(event);
    }
    
    static {
        handlerCache = new ConcurrentHashMap<Method, DynamicHandler>();
    }
}
