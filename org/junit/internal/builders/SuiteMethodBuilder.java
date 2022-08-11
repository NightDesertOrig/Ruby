//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.builders;

import org.junit.runners.model.*;
import org.junit.runner.*;
import org.junit.internal.runners.*;

public class SuiteMethodBuilder extends RunnerBuilder
{
    @Override
    public Runner runnerForClass(final Class<?> each) throws Throwable {
        if (this.hasSuiteMethod(each)) {
            return new SuiteMethod(each);
        }
        return null;
    }
    
    public boolean hasSuiteMethod(final Class<?> testClass) {
        try {
            testClass.getMethod("suite", (Class<?>[])new Class[0]);
        }
        catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }
}
