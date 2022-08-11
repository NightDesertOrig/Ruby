//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.requests;

import org.junit.runner.*;
import org.junit.internal.builders.*;

public class ClassRequest extends Request
{
    private final Class<?> fTestClass;
    private boolean fCanUseSuiteMethod;
    
    public ClassRequest(final Class<?> testClass, final boolean canUseSuiteMethod) {
        this.fTestClass = testClass;
        this.fCanUseSuiteMethod = canUseSuiteMethod;
    }
    
    public ClassRequest(final Class<?> testClass) {
        this(testClass, true);
    }
    
    @Override
    public Runner getRunner() {
        return new AllDefaultPossibilitiesBuilder(this.fCanUseSuiteMethod).safeRunnerForClass((Class)this.fTestClass);
    }
}
