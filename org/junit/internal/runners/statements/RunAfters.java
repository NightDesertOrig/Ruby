//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners.statements;

import org.junit.runners.model.*;
import java.util.*;

public class RunAfters extends Statement
{
    private final Statement fNext;
    private final Object fTarget;
    private final List<FrameworkMethod> fAfters;
    
    public RunAfters(final Statement next, final List<FrameworkMethod> afters, final Object target) {
        this.fNext = next;
        this.fAfters = afters;
        this.fTarget = target;
    }
    
    @Override
    public void evaluate() throws Throwable {
        final List<Throwable> errors = new ArrayList<Throwable>();
        try {
            this.fNext.evaluate();
        }
        catch (Throwable e) {
            errors.add(e);
        }
        finally {
            for (final FrameworkMethod each : this.fAfters) {
                try {
                    each.invokeExplosively(this.fTarget, new Object[0]);
                }
                catch (Throwable e2) {
                    errors.add(e2);
                }
            }
        }
        MultipleFailureException.assertEmpty(errors);
    }
}
