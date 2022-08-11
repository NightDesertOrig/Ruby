//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import java.lang.reflect.*;
import org.hamcrest.*;

public class IsEqual<T> extends BaseMatcher<T>
{
    private final Object object;
    
    public IsEqual(final T equalArg) {
        this.object = equalArg;
    }
    
    public boolean matches(final Object arg) {
        return areEqual(this.object, arg);
    }
    
    public void describeTo(final Description description) {
        description.appendValue(this.object);
    }
    
    private static boolean areEqual(final Object o1, final Object o2) {
        if (o1 == null || o2 == null) {
            return o1 == null && o2 == null;
        }
        if (isArray(o1)) {
            return isArray(o2) && areArraysEqual(o1, o2);
        }
        return o1.equals(o2);
    }
    
    private static boolean areArraysEqual(final Object o1, final Object o2) {
        return areArrayLengthsEqual(o1, o2) && areArrayElementsEqual(o1, o2);
    }
    
    private static boolean areArrayLengthsEqual(final Object o1, final Object o2) {
        return Array.getLength(o1) == Array.getLength(o2);
    }
    
    private static boolean areArrayElementsEqual(final Object o1, final Object o2) {
        for (int i = 0; i < Array.getLength(o1); ++i) {
            if (!areEqual(Array.get(o1, i), Array.get(o2, i))) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isArray(final Object o) {
        return o.getClass().isArray();
    }
    
    @Factory
    public static <T> Matcher<T> equalTo(final T operand) {
        return new IsEqual<T>(operand);
    }
}
