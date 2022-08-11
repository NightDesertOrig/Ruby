//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners.statements;

import org.junit.runners.model.*;

public class InvokeMethod extends Statement
{
    private final FrameworkMethod fTestMethod;
    private Object fTarget;
    
    public InvokeMethod(final FrameworkMethod testMethod, final Object target) {
        this.fTestMethod = testMethod;
        this.fTarget = target;
    }
    
    @Override
    public void evaluate() throws Throwable {
        this.fTestMethod.invokeExplosively(this.fTarget, new Object[0]);
    }
}
