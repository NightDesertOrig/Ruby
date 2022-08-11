//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import java.util.*;
import org.junit.runners.model.*;
import org.hamcrest.*;
import java.util.concurrent.*;
import org.junit.*;

public class ErrorCollector extends Verifier
{
    private List<Throwable> errors;
    
    public ErrorCollector() {
        this.errors = new ArrayList<Throwable>();
    }
    
    @Override
    protected void verify() throws Throwable {
        MultipleFailureException.assertEmpty(this.errors);
    }
    
    public void addError(final Throwable error) {
        this.errors.add(error);
    }
    
    public <T> void checkThat(final T value, final Matcher<T> matcher) {
        this.checkThat("", value, matcher);
    }
    
    public <T> void checkThat(final String reason, final T value, final Matcher<T> matcher) {
        this.checkSucceeds(new Callable<Object>() {
            public Object call() throws Exception {
                Assert.assertThat(reason, value, matcher);
                return value;
            }
        });
    }
    
    public Object checkSucceeds(final Callable<Object> callable) {
        try {
            return callable.call();
        }
        catch (Throwable e) {
            this.addError(e);
            return null;
        }
    }
}
