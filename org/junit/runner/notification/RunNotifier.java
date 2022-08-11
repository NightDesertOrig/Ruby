//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner.notification;

import org.junit.runner.*;
import java.util.*;

public class RunNotifier
{
    private final List<RunListener> fListeners;
    private boolean fPleaseStop;
    
    public RunNotifier() {
        this.fListeners = Collections.synchronizedList(new ArrayList<RunListener>());
        this.fPleaseStop = false;
    }
    
    public void addListener(final RunListener listener) {
        this.fListeners.add(listener);
    }
    
    public void removeListener(final RunListener listener) {
        this.fListeners.remove(listener);
    }
    
    public void fireTestRunStarted(final Description description) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(final RunListener each) throws Exception {
                each.testRunStarted(description);
            }
        }.run();
    }
    
    public void fireTestRunFinished(final Result result) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(final RunListener each) throws Exception {
                each.testRunFinished(result);
            }
        }.run();
    }
    
    public void fireTestStarted(final Description description) throws StoppedByUserException {
        if (this.fPleaseStop) {
            throw new StoppedByUserException();
        }
        new SafeNotifier() {
            @Override
            protected void notifyListener(final RunListener each) throws Exception {
                each.testStarted(description);
            }
        }.run();
    }
    
    public void fireTestFailure(final Failure failure) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(final RunListener each) throws Exception {
                each.testFailure(failure);
            }
        }.run();
    }
    
    public void fireTestAssumptionFailed(final Failure failure) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(final RunListener each) throws Exception {
                each.testAssumptionFailure(failure);
            }
        }.run();
    }
    
    public void fireTestIgnored(final Description description) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(final RunListener each) throws Exception {
                each.testIgnored(description);
            }
        }.run();
    }
    
    public void fireTestFinished(final Description description) {
        new SafeNotifier() {
            @Override
            protected void notifyListener(final RunListener each) throws Exception {
                each.testFinished(description);
            }
        }.run();
    }
    
    public void pleaseStop() {
        this.fPleaseStop = true;
    }
    
    public void addFirstListener(final RunListener listener) {
        this.fListeners.add(0, listener);
    }
    
    private abstract class SafeNotifier
    {
        void run() {
            synchronized (RunNotifier.this.fListeners) {
                final Iterator<RunListener> all = RunNotifier.this.fListeners.iterator();
                while (all.hasNext()) {
                    try {
                        this.notifyListener(all.next());
                    }
                    catch (Exception e) {
                        all.remove();
                        RunNotifier.this.fireTestFailure(new Failure(Description.TEST_MECHANISM, (Throwable)e));
                    }
                }
            }
        }
        
        protected abstract void notifyListener(final RunListener p0) throws Exception;
    }
}
