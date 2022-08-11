//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import java.util.*;
import org.hamcrest.*;

public class AnyOf<T> extends BaseMatcher<T>
{
    private final Iterable<Matcher<? extends T>> matchers;
    
    public AnyOf(final Iterable<Matcher<? extends T>> matchers) {
        this.matchers = matchers;
    }
    
    public boolean matches(final Object o) {
        for (final Matcher<? extends T> matcher : this.matchers) {
            if (matcher.matches(o)) {
                return true;
            }
        }
        return false;
    }
    
    public void describeTo(final Description description) {
        description.appendList("(", " or ", ")", this.matchers);
    }
    
    @Factory
    public static <T> Matcher<T> anyOf(final Matcher<? extends T>... matchers) {
        return anyOf((Iterable<Matcher<? extends T>>)Arrays.asList(matchers));
    }
    
    @Factory
    public static <T> Matcher<T> anyOf(final Iterable<Matcher<? extends T>> matchers) {
        return new AnyOf<T>(matchers);
    }
}
