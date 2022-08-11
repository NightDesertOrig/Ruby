//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import java.util.*;
import org.hamcrest.*;

public class AllOf<T> extends BaseMatcher<T>
{
    private final Iterable<Matcher<? extends T>> matchers;
    
    public AllOf(final Iterable<Matcher<? extends T>> matchers) {
        this.matchers = matchers;
    }
    
    public boolean matches(final Object o) {
        for (final Matcher<? extends T> matcher : this.matchers) {
            if (!matcher.matches(o)) {
                return false;
            }
        }
        return true;
    }
    
    public void describeTo(final Description description) {
        description.appendList("(", " and ", ")", this.matchers);
    }
    
    @Factory
    public static <T> Matcher<T> allOf(final Matcher<? extends T>... matchers) {
        return allOf((Iterable<Matcher<? extends T>>)Arrays.asList(matchers));
    }
    
    @Factory
    public static <T> Matcher<T> allOf(final Iterable<Matcher<? extends T>> matchers) {
        return new AllOf<T>(matchers);
    }
}
