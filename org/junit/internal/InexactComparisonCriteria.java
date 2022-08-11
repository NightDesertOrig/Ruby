//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal;

import org.junit.*;

public class InexactComparisonCriteria extends ComparisonCriteria
{
    public double fDelta;
    
    public InexactComparisonCriteria(final double delta) {
        this.fDelta = delta;
    }
    
    protected void assertElementsEqual(final Object expected, final Object actual) {
        if (expected instanceof Double) {
            Assert.assertEquals((double)expected, (double)actual, this.fDelta);
        }
        else {
            Assert.assertEquals((double)(float)expected, (double)(float)actual, this.fDelta);
        }
    }
}
