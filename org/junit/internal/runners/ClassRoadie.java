//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import org.junit.runner.*;
import org.junit.runner.notification.*;
import java.lang.reflect.*;
import org.junit.internal.*;
import java.util.*;

@Deprecated
public class ClassRoadie
{
    private RunNotifier fNotifier;
    private TestClass fTestClass;
    private Description fDescription;
    private final Runnable fRunnable;
    
    public ClassRoadie(final RunNotifier notifier, final TestClass testClass, final Description description, final Runnable runnable) {
        this.fNotifier = notifier;
        this.fTestClass = testClass;
        this.fDescription = description;
        this.fRunnable = runnable;
    }
    
    protected void runUnprotected() {
        this.fRunnable.run();
    }
    
    protected void addFailure(final Throwable targetException) {
        this.fNotifier.fireTestFailure(new Failure(this.fDescription, targetException));
    }
    
    public void runProtected() {
        try {
            this.runBefores();
            this.runUnprotected();
        }
        catch (FailedBefore e) {}
        finally {
            this.runAfters();
        }
    }
    
    private void runBefores() throws FailedBefore {
        try {
            try {
                final List<Method> befores = this.fTestClass.getBefores();
                for (final Method before : befores) {
                    before.invoke(null, new Object[0]);
                }
            }
            catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
        catch (AssumptionViolatedException e3) {
            throw new FailedBefore();
        }
        catch (Throwable e2) {
            this.addFailure(e2);
            throw new FailedBefore();
        }
    }
    
    private void runAfters() {
        final List<Method> afters = this.fTestClass.getAfters();
        for (final Method after : afters) {
            try {
                after.invoke(null, new Object[0]);
            }
            catch (InvocationTargetException e) {
                this.addFailure(e.getTargetException());
            }
            catch (Throwable e2) {
                this.addFailure(e2);
            }
        }
    }
}
