//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners.statements;

import org.junit.runners.model.*;
import java.util.*;

public class RunBefores extends Statement
{
    private final Statement fNext;
    private final Object fTarget;
    private final List<FrameworkMethod> fBefores;
    
    public RunBefores(final Statement next, final List<FrameworkMethod> befores, final Object target) {
        this.fNext = next;
        this.fBefores = befores;
        this.fTarget = target;
    }
    
    @Override
    public void evaluate() throws Throwable {
        for (final FrameworkMethod before : this.fBefores) {
            before.invokeExplosively(this.fTarget, new Object[0]);
        }
        this.fNext.evaluate();
    }
}
