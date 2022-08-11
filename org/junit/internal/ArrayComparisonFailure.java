//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal;

import java.util.*;

public class ArrayComparisonFailure extends AssertionError
{
    private static final long serialVersionUID = 1L;
    private List<Integer> fIndices;
    private final String fMessage;
    private final AssertionError fCause;
    
    public ArrayComparisonFailure(final String message, final AssertionError cause, final int index) {
        this.fIndices = new ArrayList<Integer>();
        this.fMessage = message;
        this.fCause = cause;
        this.addDimension(index);
    }
    
    public void addDimension(final int index) {
        this.fIndices.add(0, index);
    }
    
    @Override
    public String getMessage() {
        final StringBuilder builder = new StringBuilder();
        if (this.fMessage != null) {
            builder.append(this.fMessage);
        }
        builder.append("arrays first differed at element ");
        for (final int each : this.fIndices) {
            builder.append("[");
            builder.append(each);
            builder.append("]");
        }
        builder.append("; ");
        builder.append(this.fCause.getMessage());
        return builder.toString();
    }
    
    @Override
    public String toString() {
        return this.getMessage();
    }
}
