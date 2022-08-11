//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.theories.internal;

import java.util.*;

public class ParameterizedAssertionError extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public ParameterizedAssertionError(final Throwable targetException, final String methodName, final Object... params) {
        super(String.format("%s(%s)", methodName, join(", ", params)), targetException);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return this.toString().equals(obj.toString());
    }
    
    public static String join(final String delimiter, final Object... params) {
        return join(delimiter, Arrays.asList(params));
    }
    
    public static String join(final String delimiter, final Collection<Object> values) {
        final StringBuffer buffer = new StringBuffer();
        final Iterator<Object> iter = values.iterator();
        while (iter.hasNext()) {
            final Object next = iter.next();
            buffer.append(stringValueOf(next));
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
    
    private static String stringValueOf(final Object next) {
        try {
            return String.valueOf(next);
        }
        catch (Throwable e) {
            return "[toString failed]";
        }
    }
}
