//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import org.junit.runners.model.*;
import org.junit.runner.*;
import org.junit.internal.*;

public abstract class TestWatcher implements TestRule
{
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                TestWatcher.this.starting(description);
                try {
                    base.evaluate();
                    TestWatcher.this.succeeded(description);
                }
                catch (AssumptionViolatedException e) {
                    throw e;
                }
                catch (Throwable t) {
                    TestWatcher.this.failed(t, description);
                    throw t;
                }
                finally {
                    TestWatcher.this.finished(description);
                }
            }
        };
    }
    
    protected void succeeded(final Description description) {
    }
    
    protected void failed(final Throwable e, final Description description) {
    }
    
    protected void starting(final Description description) {
    }
    
    protected void finished(final Description description) {
    }
}
