//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners;

import org.junit.runner.*;
import java.lang.annotation.*;
import org.junit.internal.runners.rules.*;
import org.junit.runners.model.*;
import org.junit.internal.runners.statements.*;
import org.junit.rules.*;
import org.junit.*;
import org.junit.internal.runners.model.*;
import org.junit.internal.*;
import org.junit.runner.notification.*;
import org.junit.runner.manipulation.*;
import java.util.*;

public abstract class ParentRunner<T> extends Runner implements Filterable, Sortable
{
    private final TestClass fTestClass;
    private Sorter fSorter;
    private List<T> fFilteredChildren;
    private RunnerScheduler fScheduler;
    
    protected ParentRunner(final Class<?> testClass) throws InitializationError {
        this.fSorter = Sorter.NULL;
        this.fFilteredChildren = null;
        this.fScheduler = (RunnerScheduler)new RunnerScheduler() {
            public void schedule(final Runnable childStatement) {
                childStatement.run();
            }
            
            public void finished() {
            }
        };
        this.fTestClass = new TestClass((Class)testClass);
        this.validate();
    }
    
    protected abstract List<T> getChildren();
    
    protected abstract Description describeChild(final T p0);
    
    protected abstract void runChild(final T p0, final RunNotifier p1);
    
    protected void collectInitializationErrors(final List<Throwable> errors) {
        this.validatePublicVoidNoArgMethods((Class<? extends Annotation>)BeforeClass.class, true, errors);
        this.validatePublicVoidNoArgMethods((Class<? extends Annotation>)AfterClass.class, true, errors);
        this.validateClassRules(errors);
    }
    
    protected void validatePublicVoidNoArgMethods(final Class<? extends Annotation> annotation, final boolean isStatic, final List<Throwable> errors) {
        final List<FrameworkMethod> methods = (List<FrameworkMethod>)this.getTestClass().getAnnotatedMethods((Class)annotation);
        for (final FrameworkMethod eachTestMethod : methods) {
            eachTestMethod.validatePublicVoidNoArg(isStatic, (List)errors);
        }
    }
    
    private void validateClassRules(final List<Throwable> errors) {
        RuleFieldValidator.CLASS_RULE_VALIDATOR.validate(this.getTestClass(), (List)errors);
    }
    
    protected Statement classBlock(final RunNotifier notifier) {
        Statement statement = this.childrenInvoker(notifier);
        statement = this.withBeforeClasses(statement);
        statement = this.withAfterClasses(statement);
        statement = this.withClassRules(statement);
        return statement;
    }
    
    protected Statement withBeforeClasses(final Statement statement) {
        final List<FrameworkMethod> befores = (List<FrameworkMethod>)this.fTestClass.getAnnotatedMethods((Class)BeforeClass.class);
        return (Statement)(befores.isEmpty() ? statement : new RunBefores(statement, (List)befores, (Object)null));
    }
    
    protected Statement withAfterClasses(final Statement statement) {
        final List<FrameworkMethod> afters = (List<FrameworkMethod>)this.fTestClass.getAnnotatedMethods((Class)AfterClass.class);
        return (Statement)(afters.isEmpty() ? statement : new RunAfters(statement, (List)afters, (Object)null));
    }
    
    private Statement withClassRules(final Statement statement) {
        final List<TestRule> classRules = this.classRules();
        return (Statement)(classRules.isEmpty() ? statement : new RunRules(statement, (Iterable)classRules, this.getDescription()));
    }
    
    protected List<TestRule> classRules() {
        return (List<TestRule>)this.fTestClass.getAnnotatedFieldValues((Object)null, (Class)ClassRule.class, (Class)TestRule.class);
    }
    
    protected Statement childrenInvoker(final RunNotifier notifier) {
        return new Statement() {
            public void evaluate() {
                ParentRunner.this.runChildren(notifier);
            }
        };
    }
    
    private void runChildren(final RunNotifier notifier) {
        for (final T each : this.getFilteredChildren()) {
            this.fScheduler.schedule((Runnable)new Runnable() {
                public void run() {
                    ParentRunner.this.runChild(each, notifier);
                }
            });
        }
        this.fScheduler.finished();
    }
    
    protected String getName() {
        return this.fTestClass.getName();
    }
    
    public final TestClass getTestClass() {
        return this.fTestClass;
    }
    
    protected final void runLeaf(final Statement statement, final Description description, final RunNotifier notifier) {
        final EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
        eachNotifier.fireTestStarted();
        try {
            statement.evaluate();
        }
        catch (AssumptionViolatedException e) {
            eachNotifier.addFailedAssumption(e);
        }
        catch (Throwable e2) {
            eachNotifier.addFailure(e2);
        }
        finally {
            eachNotifier.fireTestFinished();
        }
    }
    
    protected Annotation[] getRunnerAnnotations() {
        return this.fTestClass.getAnnotations();
    }
    
    public Description getDescription() {
        final Description description = Description.createSuiteDescription(this.getName(), this.getRunnerAnnotations());
        for (final T child : this.getFilteredChildren()) {
            description.addChild(this.describeChild(child));
        }
        return description;
    }
    
    public void run(final RunNotifier notifier) {
        final EachTestNotifier testNotifier = new EachTestNotifier(notifier, this.getDescription());
        try {
            final Statement statement = this.classBlock(notifier);
            statement.evaluate();
        }
        catch (AssumptionViolatedException e3) {
            testNotifier.fireTestIgnored();
        }
        catch (StoppedByUserException e) {
            throw e;
        }
        catch (Throwable e2) {
            testNotifier.addFailure(e2);
        }
    }
    
    public void filter(final Filter filter) throws NoTestsRemainException {
        final Iterator<T> iter = this.getFilteredChildren().iterator();
        while (iter.hasNext()) {
            final T each = iter.next();
            if (this.shouldRun(filter, each)) {
                try {
                    filter.apply((Object)each);
                }
                catch (NoTestsRemainException e) {
                    iter.remove();
                }
            }
            else {
                iter.remove();
            }
        }
        if (this.getFilteredChildren().isEmpty()) {
            throw new NoTestsRemainException();
        }
    }
    
    public void sort(final Sorter sorter) {
        this.fSorter = sorter;
        for (final T each : this.getFilteredChildren()) {
            this.sortChild(each);
        }
        Collections.sort(this.getFilteredChildren(), (Comparator<? super Object>)this.comparator());
    }
    
    private void validate() throws InitializationError {
        final List<Throwable> errors = new ArrayList<Throwable>();
        this.collectInitializationErrors(errors);
        if (!errors.isEmpty()) {
            throw new InitializationError((List)errors);
        }
    }
    
    private List<T> getFilteredChildren() {
        if (this.fFilteredChildren == null) {
            this.fFilteredChildren = new ArrayList<T>((Collection<? extends T>)this.getChildren());
        }
        return this.fFilteredChildren;
    }
    
    private void sortChild(final T child) {
        this.fSorter.apply((Object)child);
    }
    
    private boolean shouldRun(final Filter filter, final T each) {
        return filter.shouldRun(this.describeChild(each));
    }
    
    private Comparator<? super T> comparator() {
        return new Comparator<T>() {
            public int compare(final T o1, final T o2) {
                return ParentRunner.this.fSorter.compare(ParentRunner.this.describeChild(o1), ParentRunner.this.describeChild(o2));
            }
        };
    }
    
    public void setScheduler(final RunnerScheduler scheduler) {
        this.fScheduler = scheduler;
    }
}
