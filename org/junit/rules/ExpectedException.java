//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import org.junit.runners.model.*;
import org.junit.runner.*;
import org.junit.matchers.*;
import org.junit.internal.matchers.*;
import org.junit.*;
import org.hamcrest.*;

public class ExpectedException implements TestRule
{
    private Matcher<Object> fMatcher;
    
    public static ExpectedException none() {
        return new ExpectedException();
    }
    
    private ExpectedException() {
        this.fMatcher = null;
    }
    
    public Statement apply(final Statement base, final Description description) {
        return new ExpectedExceptionStatement(base);
    }
    
    public void expect(final Matcher<?> matcher) {
        if (this.fMatcher == null) {
            this.fMatcher = (Matcher<Object>)matcher;
        }
        else {
            this.fMatcher = (Matcher<Object>)JUnitMatchers.both((Matcher)this.fMatcher).and((Matcher)matcher);
        }
    }
    
    public void expect(final Class<? extends Throwable> type) {
        this.expect(CoreMatchers.instanceOf(type));
    }
    
    public void expectMessage(final String substring) {
        this.expectMessage(JUnitMatchers.containsString(substring));
    }
    
    public void expectMessage(final Matcher<String> matcher) {
        this.expect(this.hasMessage(matcher));
    }
    
    private Matcher<Throwable> hasMessage(final Matcher<String> matcher) {
        return (Matcher<Throwable>)new TypeSafeMatcher<Throwable>() {
            public void describeTo(final org.hamcrest.Description description) {
                description.appendText("exception with message ");
                description.appendDescriptionOf(matcher);
            }
            
            public boolean matchesSafely(final Throwable item) {
                return matcher.matches(item.getMessage());
            }
        };
    }
    
    private class ExpectedExceptionStatement extends Statement
    {
        private final Statement fNext;
        
        public ExpectedExceptionStatement(final Statement base) {
            this.fNext = base;
        }
        
        @Override
        public void evaluate() throws Throwable {
            try {
                this.fNext.evaluate();
            }
            catch (Throwable e) {
                if (ExpectedException.this.fMatcher == null) {
                    throw e;
                }
                Assert.assertThat((Object)e, ExpectedException.this.fMatcher);
                return;
            }
            if (ExpectedException.this.fMatcher != null) {
                throw new AssertionError((Object)("Expected test to throw " + StringDescription.toString(ExpectedException.this.fMatcher)));
            }
        }
    }
}
