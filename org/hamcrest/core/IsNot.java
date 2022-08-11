//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import org.hamcrest.*;

public class IsNot<T> extends BaseMatcher<T>
{
    private final Matcher<T> matcher;
    
    public IsNot(final Matcher<T> matcher) {
        this.matcher = matcher;
    }
    
    public boolean matches(final Object arg) {
        return !this.matcher.matches(arg);
    }
    
    public void describeTo(final Description description) {
        description.appendText("not ").appendDescriptionOf(this.matcher);
    }
    
    @Factory
    public static <T> Matcher<T> not(final Matcher<T> matcher) {
        return new IsNot<T>(matcher);
    }
    
    @Factory
    public static <T> Matcher<T> not(final T value) {
        return not((Matcher<T>)IsEqual.equalTo((T)value));
    }
}
