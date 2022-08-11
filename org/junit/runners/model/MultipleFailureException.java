//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners.model;

import java.util.*;

public class MultipleFailureException extends Exception
{
    private static final long serialVersionUID = 1L;
    private final List<Throwable> fErrors;
    
    public MultipleFailureException(final List<Throwable> errors) {
        this.fErrors = new ArrayList<Throwable>(errors);
    }
    
    public List<Throwable> getFailures() {
        return Collections.unmodifiableList((List<? extends Throwable>)this.fErrors);
    }
    
    @Override
    public String getMessage() {
        final StringBuilder sb = new StringBuilder(String.format("There were %d errors:", this.fErrors.size()));
        for (final Throwable e : this.fErrors) {
            sb.append(String.format("\n  %s(%s)", e.getClass().getName(), e.getMessage()));
        }
        return sb.toString();
    }
    
    public static void assertEmpty(final List<Throwable> errors) throws Throwable {
        if (errors.isEmpty()) {
            return;
        }
        if (errors.size() == 1) {
            throw errors.get(0);
        }
        throw new org.junit.internal.runners.model.MultipleFailureException((List)errors);
    }
}
