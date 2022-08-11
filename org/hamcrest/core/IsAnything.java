//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import org.hamcrest.*;

public class IsAnything<T> extends BaseMatcher<T>
{
    private final String description;
    
    public IsAnything() {
        this("ANYTHING");
    }
    
    public IsAnything(final String description) {
        this.description = description;
    }
    
    public boolean matches(final Object o) {
        return true;
    }
    
    public void describeTo(final Description description) {
        description.appendText(this.description);
    }
    
    @Factory
    public static <T> Matcher<T> anything() {
        return new IsAnything<T>();
    }
    
    @Factory
    public static <T> Matcher<T> anything(final String description) {
        return new IsAnything<T>(description);
    }
    
    @Factory
    public static <T> Matcher<T> any(final Class<T> type) {
        return new IsAnything<T>();
    }
}
