//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners.statements;

import org.junit.runners.model.*;
import org.junit.internal.*;

public class ExpectException extends Statement
{
    private Statement fNext;
    private final Class<? extends Throwable> fExpected;
    
    public ExpectException(final Statement next, final Class<? extends Throwable> expected) {
        this.fNext = next;
        this.fExpected = expected;
    }
    
    @Override
    public void evaluate() throws Exception {
        boolean complete = false;
        try {
            this.fNext.evaluate();
            complete = true;
        }
        catch (AssumptionViolatedException e) {
            throw e;
        }
        catch (Throwable e2) {
            if (!this.fExpected.isAssignableFrom(e2.getClass())) {
                final String message = "Unexpected exception, expected<" + this.fExpected.getName() + "> but was<" + e2.getClass().getName() + ">";
                throw new Exception(message, e2);
            }
        }
        if (complete) {
            throw new AssertionError((Object)("Expected exception: " + this.fExpected.getName()));
        }
    }
}
