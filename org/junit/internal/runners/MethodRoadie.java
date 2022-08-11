//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import org.junit.runner.*;
import java.util.concurrent.*;
import org.junit.internal.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.runner.notification.*;

@Deprecated
public class MethodRoadie
{
    private final Object fTest;
    private final RunNotifier fNotifier;
    private final Description fDescription;
    private TestMethod fTestMethod;
    
    public MethodRoadie(final Object test, final TestMethod method, final RunNotifier notifier, final Description description) {
        this.fTest = test;
        this.fNotifier = notifier;
        this.fDescription = description;
        this.fTestMethod = method;
    }
    
    public void run() {
        if (this.fTestMethod.isIgnored()) {
            this.fNotifier.fireTestIgnored(this.fDescription);
            return;
        }
        this.fNotifier.fireTestStarted(this.fDescription);
        try {
            final long timeout = this.fTestMethod.getTimeout();
            if (timeout > 0L) {
                this.runWithTimeout(timeout);
            }
            else {
                this.runTest();
            }
        }
        finally {
            this.fNotifier.fireTestFinished(this.fDescription);
        }
    }
    
    private void runWithTimeout(final long timeout) {
        this.runBeforesThenTestThenAfters(new Runnable() {
            public void run() {
                final ExecutorService service = Executors.newSingleThreadExecutor();
                final Callable<Object> callable = new Callable<Object>() {
                    public Object call() throws Exception {
                        MethodRoadie.this.runTestMethod();
                        return null;
                    }
                };
                final Future<Object> result = service.submit(callable);
                service.shutdown();
                try {
                    final boolean terminated = service.awaitTermination(timeout, TimeUnit.MILLISECONDS);
                    if (!terminated) {
                        service.shutdownNow();
                    }
                    result.get(0L, TimeUnit.MILLISECONDS);
                }
                catch (TimeoutException e2) {
                    MethodRoadie.this.addFailure(new Exception(String.format("test timed out after %d milliseconds", timeout)));
                }
                catch (Exception e) {
                    MethodRoadie.this.addFailure(e);
                }
            }
        });
    }
    
    public void runTest() {
        this.runBeforesThenTestThenAfters(new Runnable() {
            public void run() {
                MethodRoadie.this.runTestMethod();
            }
        });
    }
    
    public void runBeforesThenTestThenAfters(final Runnable test) {
        try {
            this.runBefores();
            test.run();
        }
        catch (FailedBefore e) {}
        catch (Exception e2) {
            throw new RuntimeException("test should never throw an exception to this level");
        }
        finally {
            this.runAfters();
        }
    }
    
    protected void runTestMethod() {
        try {
            this.fTestMethod.invoke(this.fTest);
            if (this.fTestMethod.expectsException()) {
                this.addFailure(new AssertionError((Object)("Expected exception: " + this.fTestMethod.getExpectedException().getName())));
            }
        }
        catch (InvocationTargetException e) {
            final Throwable actual = e.getTargetException();
            if (actual instanceof AssumptionViolatedException) {
                return;
            }
            if (!this.fTestMethod.expectsException()) {
                this.addFailure(actual);
            }
            else if (this.fTestMethod.isUnexpected(actual)) {
                final String message = "Unexpected exception, expected<" + this.fTestMethod.getExpectedException().getName() + "> but was<" + actual.getClass().getName() + ">";
                this.addFailure(new Exception(message, actual));
            }
        }
        catch (Throwable e2) {
            this.addFailure(e2);
        }
    }
    
    private void runBefores() throws FailedBefore {
        try {
            try {
                final List<Method> befores = this.fTestMethod.getBefores();
                for (final Method before : befores) {
                    before.invoke(this.fTest, new Object[0]);
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
        final List<Method> afters = this.fTestMethod.getAfters();
        for (final Method after : afters) {
            try {
                after.invoke(this.fTest, new Object[0]);
            }
            catch (InvocationTargetException e) {
                this.addFailure(e.getTargetException());
            }
            catch (Throwable e2) {
                this.addFailure(e2);
            }
        }
    }
    
    protected void addFailure(final Throwable e) {
        this.fNotifier.fireTestFailure(new Failure(this.fDescription, e));
    }
}
