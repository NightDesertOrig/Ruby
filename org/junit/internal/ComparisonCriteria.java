//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal;

import java.lang.reflect.*;
import org.junit.*;

public abstract class ComparisonCriteria
{
    public void arrayEquals(final String message, final Object expecteds, final Object actuals) throws ArrayComparisonFailure {
        if (expecteds == actuals) {
            return;
        }
        final String header = (message == null) ? "" : (message + ": ");
        for (int expectedsLength = this.assertArraysAreSameLength(expecteds, actuals, header), i = 0; i < expectedsLength; ++i) {
            final Object expected = Array.get(expecteds, i);
            final Object actual = Array.get(actuals, i);
            if (this.isArray(expected) && this.isArray(actual)) {
                try {
                    this.arrayEquals(message, expected, actual);
                    continue;
                }
                catch (ArrayComparisonFailure e) {
                    e.addDimension(i);
                    throw e;
                }
            }
            try {
                this.assertElementsEqual(expected, actual);
            }
            catch (AssertionError e2) {
                throw new ArrayComparisonFailure(header, e2, i);
            }
        }
    }
    
    private boolean isArray(final Object expected) {
        return expected != null && expected.getClass().isArray();
    }
    
    private int assertArraysAreSameLength(final Object expecteds, final Object actuals, final String header) {
        if (expecteds == null) {
            Assert.fail(header + "expected array was null");
        }
        if (actuals == null) {
            Assert.fail(header + "actual array was null");
        }
        final int actualsLength = Array.getLength(actuals);
        final int expectedsLength = Array.getLength(expecteds);
        if (actualsLength != expectedsLength) {
            Assert.fail(header + "array lengths differed, expected.length=" + expectedsLength + " actual.length=" + actualsLength);
        }
        return expectedsLength;
    }
    
    protected abstract void assertElementsEqual(final Object p0, final Object p1);
}
