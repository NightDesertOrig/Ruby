//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import java.lang.annotation.*;
import org.junit.runner.*;
import junit.extensions.*;
import org.junit.runner.manipulation.*;
import org.junit.runner.notification.*;
import junit.framework.*;

public class JUnit38ClassRunner extends Runner implements Filterable, Sortable
{
    private Test fTest;
    
    public JUnit38ClassRunner(final Class<?> klass) {
        this((Test)new TestSuite((Class)klass.asSubclass(TestCase.class)));
    }
    
    public JUnit38ClassRunner(final Test test) {
        this.setTest(test);
    }
    
    @Override
    public void run(final RunNotifier notifier) {
        final TestResult result = new TestResult();
        result.addListener(this.createAdaptingListener(notifier));
        this.getTest().run(result);
    }
    
    public TestListener createAdaptingListener(final RunNotifier notifier) {
        return (TestListener)new OldTestClassAdaptingListener(notifier);
    }
    
    @Override
    public Description getDescription() {
        return makeDescription(this.getTest());
    }
    
    private static Description makeDescription(final Test test) {
        if (test instanceof TestCase) {
            final TestCase tc = (TestCase)test;
            return Description.createTestDescription(tc.getClass(), tc.getName());
        }
        if (test instanceof TestSuite) {
            final TestSuite ts = (TestSuite)test;
            final String name = (ts.getName() == null) ? createSuiteDescription(ts) : ts.getName();
            final Description description = Description.createSuiteDescription(name, new Annotation[0]);
            for (int n = ts.testCount(), i = 0; i < n; ++i) {
                final Description made = makeDescription(ts.testAt(i));
                description.addChild(made);
            }
            return description;
        }
        if (test instanceof Describable) {
            final Describable adapter = (Describable)test;
            return adapter.getDescription();
        }
        if (test instanceof TestDecorator) {
            final TestDecorator decorator = (TestDecorator)test;
            return makeDescription(decorator.getTest());
        }
        return Description.createSuiteDescription(test.getClass());
    }
    
    private static String createSuiteDescription(final TestSuite ts) {
        final int count = ts.countTestCases();
        final String example = (count == 0) ? "" : String.format(" [example: %s]", ts.testAt(0));
        return String.format("TestSuite with %s tests%s", count, example);
    }
    
    public void filter(final Filter filter) throws NoTestsRemainException {
        if (this.getTest() instanceof Filterable) {
            final Filterable adapter = (Filterable)this.getTest();
            adapter.filter(filter);
        }
        else if (this.getTest() instanceof TestSuite) {
            final TestSuite suite = (TestSuite)this.getTest();
            final TestSuite filtered = new TestSuite(suite.getName());
            for (int n = suite.testCount(), i = 0; i < n; ++i) {
                final Test test = suite.testAt(i);
                if (filter.shouldRun(makeDescription(test))) {
                    filtered.addTest(test);
                }
            }
            this.setTest((Test)filtered);
        }
    }
    
    public void sort(final Sorter sorter) {
        if (this.getTest() instanceof Sortable) {
            final Sortable adapter = (Sortable)this.getTest();
            adapter.sort(sorter);
        }
    }
    
    private void setTest(final Test test) {
        this.fTest = test;
    }
    
    private Test getTest() {
        return this.fTest;
    }
    
    private final class OldTestClassAdaptingListener implements TestListener
    {
        private final RunNotifier fNotifier;
        
        private OldTestClassAdaptingListener(final RunNotifier notifier) {
            this.fNotifier = notifier;
        }
        
        public void endTest(final Test test) {
            this.fNotifier.fireTestFinished(this.asDescription(test));
        }
        
        public void startTest(final Test test) {
            this.fNotifier.fireTestStarted(this.asDescription(test));
        }
        
        public void addError(final Test test, final Throwable t) {
            final Failure failure = new Failure(this.asDescription(test), t);
            this.fNotifier.fireTestFailure(failure);
        }
        
        private Description asDescription(final Test test) {
            if (test instanceof Describable) {
                final Describable facade = (Describable)test;
                return facade.getDescription();
            }
            return Description.createTestDescription(this.getEffectiveClass(test), this.getName(test));
        }
        
        private Class<? extends Test> getEffectiveClass(final Test test) {
            return test.getClass();
        }
        
        private String getName(final Test test) {
            if (test instanceof TestCase) {
                return ((TestCase)test).getName();
            }
            return test.toString();
        }
        
        public void addFailure(final Test test, final AssertionFailedError t) {
            this.addError(test, (Throwable)t);
        }
    }
}
