//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit;

import org.hamcrest.*;
import java.util.*;
import org.junit.internal.matchers.*;
import org.junit.internal.*;

public class Assume
{
    public static void assumeTrue(final boolean b) {
        assumeThat(b, (Matcher<Boolean>)CoreMatchers.is((T)true));
    }
    
    public static void assumeNotNull(final Object... objects) {
        assumeThat(Arrays.asList(objects), Each.each(CoreMatchers.notNullValue()));
    }
    
    public static <T> void assumeThat(final T actual, final Matcher<T> matcher) {
        if (!matcher.matches(actual)) {
            throw new AssumptionViolatedException(actual, matcher);
        }
    }
    
    public static void assumeNoException(final Throwable t) {
        assumeThat(t, CoreMatchers.nullValue());
    }
}
