//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import org.junit.runner.*;
import java.lang.reflect.*;
import org.junit.runners.model.*;
import java.util.*;
import org.junit.runner.notification.*;

public class ErrorReportingRunner extends Runner
{
    private final List<Throwable> fCauses;
    private final Class<?> fTestClass;
    
    public ErrorReportingRunner(final Class<?> testClass, final Throwable cause) {
        this.fTestClass = testClass;
        this.fCauses = this.getCauses(cause);
    }
    
    @Override
    public Description getDescription() {
        final Description description = Description.createSuiteDescription(this.fTestClass);
        for (final Throwable each : this.fCauses) {
            description.addChild(this.describeCause(each));
        }
        return description;
    }
    
    @Override
    public void run(final RunNotifier notifier) {
        for (final Throwable each : this.fCauses) {
            this.runCause(each, notifier);
        }
    }
    
    private List<Throwable> getCauses(final Throwable cause) {
        if (cause instanceof InvocationTargetException) {
            return this.getCauses(cause.getCause());
        }
        if (cause instanceof InitializationError) {
            return ((InitializationError)cause).getCauses();
        }
        if (cause instanceof org.junit.internal.runners.InitializationError) {
            return ((org.junit.internal.runners.InitializationError)cause).getCauses();
        }
        return Arrays.asList(cause);
    }
    
    private Description describeCause(final Throwable child) {
        return Description.createTestDescription(this.fTestClass, "initializationError");
    }
    
    private void runCause(final Throwable child, final RunNotifier notifier) {
        final Description description = this.describeCause(child);
        notifier.fireTestStarted(description);
        notifier.fireTestFailure(new Failure(description, child));
        notifier.fireTestFinished(description);
    }
}
