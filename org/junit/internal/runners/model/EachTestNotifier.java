//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners.model;

import org.junit.runner.*;
import org.junit.runners.model.*;
import org.junit.runner.notification.*;
import java.util.*;
import org.junit.internal.*;

public class EachTestNotifier
{
    private final RunNotifier fNotifier;
    private final Description fDescription;
    
    public EachTestNotifier(final RunNotifier notifier, final Description description) {
        this.fNotifier = notifier;
        this.fDescription = description;
    }
    
    public void addFailure(final Throwable targetException) {
        if (targetException instanceof MultipleFailureException) {
            this.addMultipleFailureException((MultipleFailureException)targetException);
        }
        else {
            this.fNotifier.fireTestFailure(new Failure(this.fDescription, targetException));
        }
    }
    
    private void addMultipleFailureException(final MultipleFailureException mfe) {
        for (final Throwable each : mfe.getFailures()) {
            this.addFailure(each);
        }
    }
    
    public void addFailedAssumption(final AssumptionViolatedException e) {
        this.fNotifier.fireTestAssumptionFailed(new Failure(this.fDescription, (Throwable)e));
    }
    
    public void fireTestFinished() {
        this.fNotifier.fireTestFinished(this.fDescription);
    }
    
    public void fireTestStarted() {
        this.fNotifier.fireTestStarted(this.fDescription);
    }
    
    public void fireTestIgnored() {
        this.fNotifier.fireTestIgnored(this.fDescription);
    }
}
