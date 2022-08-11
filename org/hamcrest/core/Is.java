//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import org.hamcrest.*;

public class Is<T> extends BaseMatcher<T>
{
    private final Matcher<T> matcher;
    
    public Is(final Matcher<T> matcher) {
        this.matcher = matcher;
    }
    
    public boolean matches(final Object arg) {
        return this.matcher.matches(arg);
    }
    
    public void describeTo(final Description description) {
        description.appendText("is ").appendDescriptionOf(this.matcher);
    }
    
    @Factory
    public static <T> Matcher<T> is(final Matcher<T> matcher) {
        return new Is<T>(matcher);
    }
    
    @Factory
    public static <T> Matcher<T> is(final T value) {
        return is((Matcher<T>)IsEqual.equalTo((T)value));
    }
    
    @Factory
    public static Matcher<Object> is(final Class<?> type) {
        return is(IsInstanceOf.instanceOf(type));
    }
}
