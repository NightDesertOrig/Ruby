//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners;

import org.junit.runner.notification.*;
import org.junit.runner.*;
import java.lang.annotation.*;
import org.junit.internal.runners.rules.*;
import org.junit.runners.model.*;
import org.junit.internal.runners.model.*;
import org.junit.internal.runners.statements.*;
import java.util.*;
import org.junit.*;
import org.junit.rules.*;

public class BlockJUnit4ClassRunner extends ParentRunner<FrameworkMethod>
{
    public BlockJUnit4ClassRunner(final Class<?> klass) throws InitializationError {
        super(klass);
    }
    
    @Override
    protected void runChild(final FrameworkMethod method, final RunNotifier notifier) {
        final Description description = this.describeChild(method);
        if (method.getAnnotation(Ignore.class) != null) {
            notifier.fireTestIgnored(description);
        }
        else {
            this.runLeaf(this.methodBlock(method), description, notifier);
        }
    }
    
    @Override
    protected Description describeChild(final FrameworkMethod method) {
        return Description.createTestDescription((Class)this.getTestClass().getJavaClass(), this.testName(method), method.getAnnotations());
    }
    
    @Override
    protected List<FrameworkMethod> getChildren() {
        return this.computeTestMethods();
    }
    
    protected List<FrameworkMethod> computeTestMethods() {
        return this.getTestClass().getAnnotatedMethods(Test.class);
    }
    
    @Override
    protected void collectInitializationErrors(final List<Throwable> errors) {
        super.collectInitializationErrors(errors);
        this.validateNoNonStaticInnerClass(errors);
        this.validateConstructor(errors);
        this.validateInstanceMethods(errors);
        this.validateFields(errors);
    }
    
    protected void validateNoNonStaticInnerClass(final List<Throwable> errors) {
        if (this.getTestClass().isANonStaticInnerClass()) {
            final String gripe = "The inner class " + this.getTestClass().getName() + " is not static.";
            errors.add(new Exception(gripe));
        }
    }
    
    protected void validateConstructor(final List<Throwable> errors) {
        this.validateOnlyOneConstructor(errors);
        this.validateZeroArgConstructor(errors);
    }
    
    protected void validateOnlyOneConstructor(final List<Throwable> errors) {
        if (!this.hasOneConstructor()) {
            final String gripe = "Test class should have exactly one public constructor";
            errors.add(new Exception(gripe));
        }
    }
    
    protected void validateZeroArgConstructor(final List<Throwable> errors) {
        if (!this.getTestClass().isANonStaticInnerClass() && this.hasOneConstructor() && this.getTestClass().getOnlyConstructor().getParameterTypes().length != 0) {
            final String gripe = "Test class should have exactly one public zero-argument constructor";
            errors.add(new Exception(gripe));
        }
    }
    
    private boolean hasOneConstructor() {
        return this.getTestClass().getJavaClass().getConstructors().length == 1;
    }
    
    @Deprecated
    protected void validateInstanceMethods(final List<Throwable> errors) {
        this.validatePublicVoidNoArgMethods((Class<? extends Annotation>)After.class, false, errors);
        this.validatePublicVoidNoArgMethods((Class<? extends Annotation>)Before.class, false, errors);
        this.validateTestMethods(errors);
        if (this.computeTestMethods().size() == 0) {
            errors.add(new Exception("No runnable methods"));
        }
    }
    
    private void validateFields(final List<Throwable> errors) {
        RuleFieldValidator.RULE_VALIDATOR.validate(this.getTestClass(), (List)errors);
    }
    
    protected void validateTestMethods(final List<Throwable> errors) {
        this.validatePublicVoidNoArgMethods(Test.class, false, errors);
    }
    
    protected Object createTest() throws Exception {
        return this.getTestClass().getOnlyConstructor().newInstance(new Object[0]);
    }
    
    protected String testName(final FrameworkMethod method) {
        return method.getName();
    }
    
    protected Statement methodBlock(final FrameworkMethod method) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                protected Object runReflectiveCall() throws Throwable {
                    return BlockJUnit4ClassRunner.this.createTest();
                }
            }.run();
        }
        catch (Throwable e) {
            return (Statement)new Fail(e);
        }
        Statement statement = this.methodInvoker(method, test);
        statement = this.possiblyExpectingExceptions(method, test, statement);
        statement = this.withPotentialTimeout(method, test, statement);
        statement = this.withBefores(method, test, statement);
        statement = this.withAfters(method, test, statement);
        statement = this.withRules(method, test, statement);
        return statement;
    }
    
    protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
        return (Statement)new InvokeMethod(method, test);
    }
    
    @Deprecated
    protected Statement possiblyExpectingExceptions(final FrameworkMethod method, final Object test, final Statement next) {
        final Test annotation = method.getAnnotation(Test.class);
        return (Statement)(this.expectsException(annotation) ? new ExpectException(next, (Class)this.getExpectedException(annotation)) : next);
    }
    
    @Deprecated
    protected Statement withPotentialTimeout(final FrameworkMethod method, final Object test, final Statement next) {
        final long timeout = this.getTimeout(method.getAnnotation(Test.class));
        return (Statement)((timeout > 0L) ? new FailOnTimeout(next, timeout) : next);
    }
    
    @Deprecated
    protected Statement withBefores(final FrameworkMethod method, final Object target, final Statement statement) {
        final List<FrameworkMethod> befores = this.getTestClass().getAnnotatedMethods((Class<? extends Annotation>)Before.class);
        return (Statement)(befores.isEmpty() ? statement : new RunBefores(statement, (List)befores, target));
    }
    
    @Deprecated
    protected Statement withAfters(final FrameworkMethod method, final Object target, final Statement statement) {
        final List<FrameworkMethod> afters = this.getTestClass().getAnnotatedMethods((Class<? extends Annotation>)After.class);
        return (Statement)(afters.isEmpty() ? statement : new RunAfters(statement, (List)afters, target));
    }
    
    private Statement withRules(final FrameworkMethod method, final Object target, final Statement statement) {
        Statement result = statement;
        result = this.withMethodRules(method, target, result);
        result = this.withTestRules(method, target, result);
        return result;
    }
    
    private Statement withMethodRules(final FrameworkMethod method, final Object target, Statement result) {
        final List<TestRule> testRules = this.getTestRules(target);
        for (final MethodRule each : this.getMethodRules(target)) {
            if (!testRules.contains(each)) {
                result = each.apply(result, method, target);
            }
        }
        return result;
    }
    
    private List<MethodRule> getMethodRules(final Object target) {
        return this.rules(target);
    }
    
    @Deprecated
    protected List<MethodRule> rules(final Object target) {
        return this.getTestClass().getAnnotatedFieldValues(target, (Class<? extends Annotation>)Rule.class, MethodRule.class);
    }
    
    private Statement withTestRules(final FrameworkMethod method, final Object target, final Statement statement) {
        final List<TestRule> testRules = this.getTestRules(target);
        return (Statement)(testRules.isEmpty() ? statement : new RunRules(statement, (Iterable)testRules, this.describeChild(method)));
    }
    
    protected List<TestRule> getTestRules(final Object target) {
        return this.getTestClass().getAnnotatedFieldValues(target, (Class<? extends Annotation>)Rule.class, TestRule.class);
    }
    
    private Class<? extends Throwable> getExpectedException(final Test annotation) {
        if (annotation == null || annotation.expected() == Test.None.class) {
            return null;
        }
        return annotation.expected();
    }
    
    private boolean expectsException(final Test annotation) {
        return this.getExpectedException(annotation) != null;
    }
    
    private long getTimeout(final Test annotation) {
        if (annotation == null) {
            return 0L;
        }
        return annotation.timeout();
    }
}
