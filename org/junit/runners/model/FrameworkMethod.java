//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners.model;

import org.junit.internal.runners.model.*;
import java.util.*;
import java.lang.reflect.*;
import java.lang.annotation.*;

public class FrameworkMethod extends FrameworkMember<FrameworkMethod>
{
    final Method fMethod;
    
    public FrameworkMethod(final Method method) {
        this.fMethod = method;
    }
    
    public Method getMethod() {
        return this.fMethod;
    }
    
    public Object invokeExplosively(final Object target, final Object... params) throws Throwable {
        return new ReflectiveCallable() {
            protected Object runReflectiveCall() throws Throwable {
                return FrameworkMethod.this.fMethod.invoke(target, params);
            }
        }.run();
    }
    
    public String getName() {
        return this.fMethod.getName();
    }
    
    public void validatePublicVoidNoArg(final boolean isStatic, final List<Throwable> errors) {
        this.validatePublicVoid(isStatic, errors);
        if (this.fMethod.getParameterTypes().length != 0) {
            errors.add(new Exception("Method " + this.fMethod.getName() + " should have no parameters"));
        }
    }
    
    public void validatePublicVoid(final boolean isStatic, final List<Throwable> errors) {
        if (Modifier.isStatic(this.fMethod.getModifiers()) != isStatic) {
            final String state = isStatic ? "should" : "should not";
            errors.add(new Exception("Method " + this.fMethod.getName() + "() " + state + " be static"));
        }
        if (!Modifier.isPublic(this.fMethod.getDeclaringClass().getModifiers())) {
            errors.add(new Exception("Class " + this.fMethod.getDeclaringClass().getName() + " should be public"));
        }
        if (!Modifier.isPublic(this.fMethod.getModifiers())) {
            errors.add(new Exception("Method " + this.fMethod.getName() + "() should be public"));
        }
        if (this.fMethod.getReturnType() != Void.TYPE) {
            errors.add(new Exception("Method " + this.fMethod.getName() + "() should be void"));
        }
    }
    
    public void validateNoTypeParametersOnArgs(final List<Throwable> errors) {
        new NoGenericTypeParametersValidator(this.fMethod).validate(errors);
    }
    
    public boolean isShadowedBy(final FrameworkMethod other) {
        if (!other.getName().equals(this.getName())) {
            return false;
        }
        if (other.getParameterTypes().length != this.getParameterTypes().length) {
            return false;
        }
        for (int i = 0; i < other.getParameterTypes().length; ++i) {
            if (!other.getParameterTypes()[i].equals(this.getParameterTypes()[i])) {
                return false;
            }
        }
        return true;
    }
    
    public boolean equals(final Object obj) {
        return FrameworkMethod.class.isInstance(obj) && ((FrameworkMethod)obj).fMethod.equals(this.fMethod);
    }
    
    public int hashCode() {
        return this.fMethod.hashCode();
    }
    
    @Deprecated
    public boolean producesType(final Type type) {
        return this.getParameterTypes().length == 0 && type instanceof Class && ((Class)type).isAssignableFrom(this.fMethod.getReturnType());
    }
    
    private Class<?>[] getParameterTypes() {
        return this.fMethod.getParameterTypes();
    }
    
    public Annotation[] getAnnotations() {
        return this.fMethod.getAnnotations();
    }
    
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return this.fMethod.getAnnotation(annotationType);
    }
}
