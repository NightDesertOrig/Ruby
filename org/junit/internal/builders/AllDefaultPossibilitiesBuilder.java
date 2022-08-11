//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.builders;

import org.junit.runners.model.*;
import org.junit.runner.*;
import java.util.*;

public class AllDefaultPossibilitiesBuilder extends RunnerBuilder
{
    private final boolean fCanUseSuiteMethod;
    
    public AllDefaultPossibilitiesBuilder(final boolean canUseSuiteMethod) {
        this.fCanUseSuiteMethod = canUseSuiteMethod;
    }
    
    @Override
    public Runner runnerForClass(final Class<?> testClass) throws Throwable {
        final List<RunnerBuilder> builders = Arrays.asList(this.ignoredBuilder(), this.annotatedBuilder(), this.suiteMethodBuilder(), this.junit3Builder(), this.junit4Builder());
        for (final RunnerBuilder each : builders) {
            final Runner runner = each.safeRunnerForClass(testClass);
            if (runner != null) {
                return runner;
            }
        }
        return null;
    }
    
    protected JUnit4Builder junit4Builder() {
        return new JUnit4Builder();
    }
    
    protected JUnit3Builder junit3Builder() {
        return new JUnit3Builder();
    }
    
    protected AnnotatedBuilder annotatedBuilder() {
        return new AnnotatedBuilder(this);
    }
    
    protected IgnoredBuilder ignoredBuilder() {
        return new IgnoredBuilder();
    }
    
    protected RunnerBuilder suiteMethodBuilder() {
        if (this.fCanUseSuiteMethod) {
            return new SuiteMethodBuilder();
        }
        return new NullBuilder();
    }
}
