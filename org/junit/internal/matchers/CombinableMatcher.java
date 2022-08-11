//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.matchers;

import org.hamcrest.*;

public class CombinableMatcher<T> extends BaseMatcher<T>
{
    private final Matcher<? extends T> fMatcher;
    
    public CombinableMatcher(final Matcher<? extends T> matcher) {
        this.fMatcher = matcher;
    }
    
    public boolean matches(final Object item) {
        return this.fMatcher.matches(item);
    }
    
    public void describeTo(final Description description) {
        description.appendDescriptionOf(this.fMatcher);
    }
    
    public CombinableMatcher<T> and(final Matcher<? extends T> matcher) {
        return new CombinableMatcher<T>(CoreMatchers.allOf(matcher, this.fMatcher));
    }
    
    public CombinableMatcher<T> or(final Matcher<? extends T> matcher) {
        return new CombinableMatcher<T>(CoreMatchers.anyOf(matcher, this.fMatcher));
    }
}
