//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import org.hamcrest.*;

public class IsNull<T> extends BaseMatcher<T>
{
    public boolean matches(final Object o) {
        return o == null;
    }
    
    public void describeTo(final Description description) {
        description.appendText("null");
    }
    
    @Factory
    public static <T> Matcher<T> nullValue() {
        return new IsNull<T>();
    }
    
    @Factory
    public static <T> Matcher<T> notNullValue() {
        return IsNot.not((Matcher<T>)nullValue());
    }
    
    @Factory
    public static <T> Matcher<T> nullValue(final Class<T> type) {
        return nullValue();
    }
    
    @Factory
    public static <T> Matcher<T> notNullValue(final Class<T> type) {
        return notNullValue();
    }
}
