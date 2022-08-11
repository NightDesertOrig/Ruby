//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import org.junit.runners.model.*;
import org.junit.internal.*;

@Deprecated
public class TestWatchman implements MethodRule
{
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                TestWatchman.this.starting(method);
                try {
                    base.evaluate();
                    TestWatchman.this.succeeded(method);
                }
                catch (AssumptionViolatedException e) {
                    throw e;
                }
                catch (Throwable t) {
                    TestWatchman.this.failed(t, method);
                    throw t;
                }
                finally {
                    TestWatchman.this.finished(method);
                }
            }
        };
    }
    
    public void succeeded(final FrameworkMethod method) {
    }
    
    public void failed(final Throwable e, final FrameworkMethod method) {
    }
    
    public void starting(final FrameworkMethod method) {
    }
    
    public void finished(final FrameworkMethod method) {
    }
}
