//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.matchers;

import org.hamcrest.*;
import java.lang.reflect.*;

public abstract class TypeSafeMatcher<T> extends BaseMatcher<T>
{
    private Class<?> expectedType;
    
    public abstract boolean matchesSafely(final T p0);
    
    protected TypeSafeMatcher() {
        this.expectedType = findExpectedType(this.getClass());
    }
    
    private static Class<?> findExpectedType(final Class<?> fromClass) {
        for (Class<?> c = fromClass; c != Object.class; c = c.getSuperclass()) {
            for (final Method method : c.getDeclaredMethods()) {
                if (isMatchesSafelyMethod(method)) {
                    return method.getParameterTypes()[0];
                }
            }
        }
        throw new Error("Cannot determine correct type for matchesSafely() method.");
    }
    
    private static boolean isMatchesSafelyMethod(final Method method) {
        return method.getName().equals("matchesSafely") && method.getParameterTypes().length == 1 && !method.isSynthetic();
    }
    
    protected TypeSafeMatcher(final Class<T> expectedType) {
        this.expectedType = expectedType;
    }
    
    public final boolean matches(final Object item) {
        return item != null && this.expectedType.isInstance(item) && this.matchesSafely(item);
    }
}
