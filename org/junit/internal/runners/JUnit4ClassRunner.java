//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import org.junit.runner.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import org.junit.runner.notification.*;
import org.junit.runner.manipulation.*;
import java.util.*;

@Deprecated
public class JUnit4ClassRunner extends Runner implements Filterable, Sortable
{
    private final List<Method> fTestMethods;
    private TestClass fTestClass;
    
    public JUnit4ClassRunner(final Class<?> klass) throws InitializationError {
        this.fTestClass = new TestClass(klass);
        this.fTestMethods = this.getTestMethods();
        this.validate();
    }
    
    protected List<Method> getTestMethods() {
        return this.fTestClass.getTestMethods();
    }
    
    protected void validate() throws InitializationError {
        final MethodValidator methodValidator = new MethodValidator(this.fTestClass);
        methodValidator.validateMethodsForDefaultRunner();
        methodValidator.assertValid();
    }
    
    @Override
    public void run(final RunNotifier notifier) {
        new ClassRoadie(notifier, this.fTestClass, this.getDescription(), (Runnable)new Runnable() {
            public void run() {
                JUnit4ClassRunner.this.runMethods(notifier);
            }
        }).runProtected();
    }
    
    protected void runMethods(final RunNotifier notifier) {
        for (final Method method : this.fTestMethods) {
            this.invokeTestMethod(method, notifier);
        }
    }
    
    @Override
    public Description getDescription() {
        final Description spec = Description.createSuiteDescription(this.getName(), this.classAnnotations());
        final List<Method> testMethods = this.fTestMethods;
        for (final Method method : testMethods) {
            spec.addChild(this.methodDescription(method));
        }
        return spec;
    }
    
    protected Annotation[] classAnnotations() {
        return this.fTestClass.getJavaClass().getAnnotations();
    }
    
    protected String getName() {
        return this.getTestClass().getName();
    }
    
    protected Object createTest() throws Exception {
        return this.getTestClass().getConstructor().newInstance(new Object[0]);
    }
    
    protected void invokeTestMethod(final Method method, final RunNotifier notifier) {
        final Description description = this.methodDescription(method);
        Object test;
        try {
            test = this.createTest();
        }
        catch (InvocationTargetException e) {
            this.testAborted(notifier, description, e.getCause());
            return;
        }
        catch (Exception e2) {
            this.testAborted(notifier, description, e2);
            return;
        }
        final TestMethod testMethod = this.wrapMethod(method);
        new MethodRoadie(test, testMethod, notifier, description).run();
    }
    
    private void testAborted(final RunNotifier notifier, final Description description, final Throwable e) {
        notifier.fireTestStarted(description);
        notifier.fireTestFailure(new Failure(description, e));
        notifier.fireTestFinished(description);
    }
    
    protected TestMethod wrapMethod(final Method method) {
        return new TestMethod(method, this.fTestClass);
    }
    
    protected String testName(final Method method) {
        return method.getName();
    }
    
    protected Description methodDescription(final Method method) {
        return Description.createTestDescription(this.getTestClass().getJavaClass(), this.testName(method), this.testAnnotations(method));
    }
    
    protected Annotation[] testAnnotations(final Method method) {
        return method.getAnnotations();
    }
    
    public void filter(final Filter filter) throws NoTestsRemainException {
        final Iterator<Method> iter = this.fTestMethods.iterator();
        while (iter.hasNext()) {
            final Method method = iter.next();
            if (!filter.shouldRun(this.methodDescription(method))) {
                iter.remove();
            }
        }
        if (this.fTestMethods.isEmpty()) {
            throw new NoTestsRemainException();
        }
    }
    
    public void sort(final Sorter sorter) {
        Collections.sort(this.fTestMethods, new Comparator<Method>() {
            public int compare(final Method o1, final Method o2) {
                return sorter.compare(JUnit4ClassRunner.this.methodDescription(o1), JUnit4ClassRunner.this.methodDescription(o2));
            }
        });
    }
    
    protected TestClass getTestClass() {
        return this.fTestClass;
    }
}
