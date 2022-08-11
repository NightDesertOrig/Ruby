//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import java.lang.annotation.*;
import java.util.*;
import org.junit.*;
import java.lang.reflect.*;

@Deprecated
public class TestClass
{
    private final Class<?> fClass;
    
    public TestClass(final Class<?> klass) {
        this.fClass = klass;
    }
    
    public List<Method> getTestMethods() {
        return this.getAnnotatedMethods(Test.class);
    }
    
    List<Method> getBefores() {
        return this.getAnnotatedMethods((Class<? extends Annotation>)BeforeClass.class);
    }
    
    List<Method> getAfters() {
        return this.getAnnotatedMethods((Class<? extends Annotation>)AfterClass.class);
    }
    
    public List<Method> getAnnotatedMethods(final Class<? extends Annotation> annotationClass) {
        final List<Method> results = new ArrayList<Method>();
        for (final Class<?> eachClass : this.getSuperClasses(this.fClass)) {
            final Method[] arr$;
            final Method[] methods = arr$ = eachClass.getDeclaredMethods();
            for (final Method eachMethod : arr$) {
                final Annotation annotation = eachMethod.getAnnotation(annotationClass);
                if (annotation != null && !this.isShadowed(eachMethod, results)) {
                    results.add(eachMethod);
                }
            }
        }
        if (this.runsTopToBottom(annotationClass)) {
            Collections.reverse(results);
        }
        return results;
    }
    
    private boolean runsTopToBottom(final Class<? extends Annotation> annotation) {
        return annotation.equals(Before.class) || annotation.equals(BeforeClass.class);
    }
    
    private boolean isShadowed(final Method method, final List<Method> results) {
        for (final Method each : results) {
            if (this.isShadowed(method, each)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isShadowed(final Method current, final Method previous) {
        if (!previous.getName().equals(current.getName())) {
            return false;
        }
        if (previous.getParameterTypes().length != current.getParameterTypes().length) {
            return false;
        }
        for (int i = 0; i < previous.getParameterTypes().length; ++i) {
            if (!previous.getParameterTypes()[i].equals(current.getParameterTypes()[i])) {
                return false;
            }
        }
        return true;
    }
    
    private List<Class<?>> getSuperClasses(final Class<?> testClass) {
        final ArrayList<Class<?>> results = new ArrayList<Class<?>>();
        for (Class<?> current = testClass; current != null; current = current.getSuperclass()) {
            results.add(current);
        }
        return results;
    }
    
    public Constructor<?> getConstructor() throws SecurityException, NoSuchMethodException {
        return this.fClass.getConstructor((Class<?>[])new Class[0]);
    }
    
    public Class<?> getJavaClass() {
        return this.fClass;
    }
    
    public String getName() {
        return this.fClass.getName();
    }
}
