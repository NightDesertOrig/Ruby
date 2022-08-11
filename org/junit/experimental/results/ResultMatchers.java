//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.results;

import org.junit.internal.matchers.*;
import org.hamcrest.*;

public class ResultMatchers
{
    public static Matcher<PrintableResult> isSuccessful() {
        return failureCountIs(0);
    }
    
    public static Matcher<PrintableResult> failureCountIs(final int count) {
        return new TypeSafeMatcher<PrintableResult>() {
            public void describeTo(final Description description) {
                description.appendText("has " + count + " failures");
            }
            
            @Override
            public boolean matchesSafely(final PrintableResult item) {
                return item.failureCount() == count;
            }
        };
    }
    
    public static Matcher<Object> hasSingleFailureContaining(final String string) {
        return new BaseMatcher<Object>() {
            public boolean matches(final Object item) {
                return item.toString().contains(string) && ResultMatchers.failureCountIs(1).matches(item);
            }
            
            public void describeTo(final Description description) {
                description.appendText("has single failure containing " + string);
            }
        };
    }
    
    public static Matcher<PrintableResult> hasFailureContaining(final String string) {
        return new BaseMatcher<PrintableResult>() {
            public boolean matches(final Object item) {
                return item.toString().contains(string);
            }
            
            public void describeTo(final Description description) {
                description.appendText("has failure containing " + string);
            }
        };
    }
}
