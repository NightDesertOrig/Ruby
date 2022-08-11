//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.builders;

import org.junit.runner.notification.*;
import org.junit.runner.*;

public class IgnoredClassRunner extends Runner
{
    private final Class<?> fTestClass;
    
    public IgnoredClassRunner(final Class<?> testClass) {
        this.fTestClass = testClass;
    }
    
    @Override
    public void run(final RunNotifier notifier) {
        notifier.fireTestIgnored(this.getDescription());
    }
    
    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(this.fTestClass);
    }
}
