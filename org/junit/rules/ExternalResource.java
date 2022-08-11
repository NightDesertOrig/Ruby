//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import org.junit.runners.model.*;
import org.junit.runner.*;

public abstract class ExternalResource implements TestRule
{
    public Statement apply(final Statement base, final Description description) {
        return this.statement(base);
    }
    
    private Statement statement(final Statement base) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                ExternalResource.this.before();
                try {
                    base.evaluate();
                }
                finally {
                    ExternalResource.this.after();
                }
            }
        };
    }
    
    protected void before() throws Throwable {
    }
    
    protected void after() {
    }
}
