//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.eventbus;

import dev.zprestige.ruby.eventbus.handler.*;
import dev.zprestige.ruby.eventbus.event.*;
import java.util.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import java.lang.annotation.*;
import java.util.concurrent.*;
import java.lang.reflect.*;
import dev.zprestige.ruby.eventbus.handler.impl.*;

public class EventBus
{
    protected final Set<Object> subscribers;
    protected final Map<Class<?>, List<Handler>> handlerMap;
    protected final Class<? extends Handler> handlerType;
    
    public EventBus() {
        this.subscribers = Collections.synchronizedSet(new HashSet<Object>());
        this.handlerMap = new ConcurrentHashMap<Class<?>, List<Handler>>();
        this.handlerType = LambdaHandler.class;
    }
    
    public void register(final Object subscriber) {
        if (subscriber == null || this.subscribers.contains(subscriber)) {
            return;
        }
        this.subscribers.add(subscriber);
        this.addHandlers(subscriber);
    }
    
    public boolean post(final Event event) {
        if (event == null) {
            return false;
        }
        final List<Handler> handlers = this.handlerMap.get(event.getClass());
        if (handlers == null) {
            return false;
        }
        handlers.stream().filter(handler -> !event.isCancelled()).forEach(handler -> handler.invoke(event));
        return event.isCancelled();
    }
    
    public void unregister(final Object subscriber) {
        if (subscriber == null || !this.subscribers.contains(subscriber)) {
            return;
        }
        this.subscribers.remove(subscriber);
        this.handlerMap.values().forEach(handlers -> handlers.removeIf(handler -> handler.isSubscriber(subscriber)));
        this.handlerMap.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
    
    protected void addHandlers(final Object subscriber) {
        final boolean isClass = subscriber instanceof Class;
        final Class<?>[] parameters;
        List<Handler> handlers;
        Arrays.stream(((Class)(isClass ? subscriber : subscriber.getClass())).getMethods()).filter(method -> method.isAnnotationPresent((Class<? extends Annotation>)RegisterListener.class)).filter(method -> isClass == Modifier.isStatic(method.getModifiers())).forEach(method -> {
            parameters = method.getParameterTypes();
            if (method.getReturnType() == Void.TYPE) {
                if (parameters.length == 1 && Event.class.isAssignableFrom(parameters[0])) {
                    handlers = this.handlerMap.computeIfAbsent(parameters[0], v -> new CopyOnWriteArrayList());
                    handlers.add(this.createHandler(method, subscriber));
                }
            }
        });
    }
    
    protected Handler createHandler(final Method method, final Object object) {
        try {
            return (Handler)this.handlerType.getDeclaredConstructor(Method.class, Object.class).newInstance(method, object);
        }
        catch (Exception ignored) {
            return new ReflectHandler(method, object);
        }
    }
}
