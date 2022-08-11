//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.theories;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class ParameterSignature
{
    private final Class<?> type;
    private final Annotation[] annotations;
    
    public static ArrayList<ParameterSignature> signatures(final Method method) {
        return signatures(method.getParameterTypes(), method.getParameterAnnotations());
    }
    
    public static List<ParameterSignature> signatures(final Constructor<?> constructor) {
        return signatures(constructor.getParameterTypes(), constructor.getParameterAnnotations());
    }
    
    private static ArrayList<ParameterSignature> signatures(final Class<?>[] parameterTypes, final Annotation[][] parameterAnnotations) {
        final ArrayList<ParameterSignature> sigs = new ArrayList<ParameterSignature>();
        for (int i = 0; i < parameterTypes.length; ++i) {
            sigs.add(new ParameterSignature(parameterTypes[i], parameterAnnotations[i]));
        }
        return sigs;
    }
    
    private ParameterSignature(final Class<?> type, final Annotation[] annotations) {
        this.type = type;
        this.annotations = annotations;
    }
    
    public boolean canAcceptType(final Class<?> candidate) {
        return this.type.isAssignableFrom(candidate);
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public List<Annotation> getAnnotations() {
        return Arrays.asList(this.annotations);
    }
    
    public boolean canAcceptArrayType(final Class<?> type) {
        return type.isArray() && this.canAcceptType(type.getComponentType());
    }
    
    public boolean hasAnnotation(final Class<? extends Annotation> type) {
        return this.getAnnotation(type) != null;
    }
    
    public <T extends Annotation> T findDeepAnnotation(final Class<T> annotationType) {
        final Annotation[] annotations2 = this.annotations;
        return this.findDeepAnnotation(annotations2, annotationType, 3);
    }
    
    private <T extends Annotation> T findDeepAnnotation(final Annotation[] annotations, final Class<T> annotationType, final int depth) {
        if (depth == 0) {
            return null;
        }
        for (final Annotation each : annotations) {
            if (annotationType.isInstance(each)) {
                return annotationType.cast(each);
            }
            final Annotation candidate = this.findDeepAnnotation(each.annotationType().getAnnotations(), (Class<Annotation>)annotationType, depth - 1);
            if (candidate != null) {
                return annotationType.cast(candidate);
            }
        }
        return null;
    }
    
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        for (final Annotation each : this.getAnnotations()) {
            if (annotationType.isInstance(each)) {
                return annotationType.cast(each);
            }
        }
        return null;
    }
}
