//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.builders;

import org.junit.runners.model.*;
import org.junit.runner.*;
import org.junit.internal.runners.*;
import junit.framework.*;

public class JUnit3Builder extends RunnerBuilder
{
    @Override
    public Runner runnerForClass(final Class<?> testClass) throws Throwable {
        if (this.isPre4Test(testClass)) {
            return new JUnit38ClassRunner(testClass);
        }
        return null;
    }
    
    boolean isPre4Test(final Class<?> testClass) {
        return TestCase.class.isAssignableFrom(testClass);
    }
}
