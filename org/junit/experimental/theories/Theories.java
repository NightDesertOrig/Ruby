//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.theories;

import org.junit.runners.*;
import java.lang.annotation.*;
import org.junit.runners.model.*;
import org.junit.internal.*;
import java.util.*;
import org.junit.*;
import java.lang.reflect.*;
import org.junit.experimental.theories.internal.*;

public class Theories extends BlockJUnit4ClassRunner
{
    public Theories(final Class<?> klass) throws InitializationError {
        super(klass);
    }
    
    @Override
    protected void collectInitializationErrors(final List<Throwable> errors) {
        super.collectInitializationErrors(errors);
        this.validateDataPointFields(errors);
    }
    
    private void validateDataPointFields(final List<Throwable> errors) {
        final Field[] arr$;
        final Field[] fields = arr$ = this.getTestClass().getJavaClass().getDeclaredFields();
        for (final Field each : arr$) {
            if (each.getAnnotation(DataPoint.class) != null && !Modifier.isStatic(each.getModifiers())) {
                errors.add(new Error("DataPoint field " + each.getName() + " must be static"));
            }
        }
    }
    
    @Override
    protected void validateConstructor(final List<Throwable> errors) {
        this.validateOnlyOneConstructor(errors);
    }
    
    @Override
    protected void validateTestMethods(final List<Throwable> errors) {
        for (final FrameworkMethod each : this.computeTestMethods()) {
            if (each.getAnnotation(Theory.class) != null) {
                each.validatePublicVoid(false, errors);
            }
            else {
                each.validatePublicVoidNoArg(false, errors);
            }
        }
    }
    
    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        final List<FrameworkMethod> testMethods = super.computeTestMethods();
        final List<FrameworkMethod> theoryMethods = this.getTestClass().getAnnotatedMethods(Theory.class);
        testMethods.removeAll(theoryMethods);
        testMethods.addAll(theoryMethods);
        return testMethods;
    }
    
    public Statement methodBlock(final FrameworkMethod method) {
        return new TheoryAnchor(method, this.getTestClass());
    }
    
    public static class TheoryAnchor extends Statement
    {
        private int successes;
        private FrameworkMethod fTestMethod;
        private TestClass fTestClass;
        private List<AssumptionViolatedException> fInvalidParameters;
        
        public TheoryAnchor(final FrameworkMethod method, final TestClass testClass) {
            this.successes = 0;
            this.fInvalidParameters = new ArrayList<AssumptionViolatedException>();
            this.fTestMethod = method;
            this.fTestClass = testClass;
        }
        
        private TestClass getTestClass() {
            return this.fTestClass;
        }
        
        @Override
        public void evaluate() throws Throwable {
            this.runWithAssignment(Assignments.allUnassigned(this.fTestMethod.getMethod(), this.getTestClass()));
            if (this.successes == 0) {
                Assert.fail("Never found parameters that satisfied method assumptions.  Violated assumptions: " + this.fInvalidParameters);
            }
        }
        
        protected void runWithAssignment(final Assignments parameterAssignment) throws Throwable {
            if (!parameterAssignment.isComplete()) {
                this.runWithIncompleteAssignment(parameterAssignment);
            }
            else {
                this.runWithCompleteAssignment(parameterAssignment);
            }
        }
        
        protected void runWithIncompleteAssignment(final Assignments incomplete) throws InstantiationException, IllegalAccessException, Throwable {
            for (final PotentialAssignment source : incomplete.potentialsForNextUnassigned()) {
                this.runWithAssignment(incomplete.assignNext(source));
            }
        }
        
        protected void runWithCompleteAssignment(final Assignments complete) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Throwable {
            new BlockJUnit4ClassRunner(this.getTestClass().getJavaClass()) {
                @Override
                protected void collectInitializationErrors(final List<Throwable> errors) {
                }
                
                public Statement methodBlock(final FrameworkMethod method) {
                    final Statement statement = super.methodBlock(method);
                    return new Statement() {
                        @Override
                        public void evaluate() throws Throwable {
                            try {
                                statement.evaluate();
                                TheoryAnchor.this.handleDataPointSuccess();
                            }
                            catch (AssumptionViolatedException e) {
                                TheoryAnchor.this.handleAssumptionViolation(e);
                            }
                            catch (Throwable e2) {
                                TheoryAnchor.this.reportParameterizedError(e2, complete.getArgumentStrings(TheoryAnchor.this.nullsOk()));
                            }
                        }
                    };
                }
                
                @Override
                protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
                    return TheoryAnchor.this.methodCompletesWithParameters(method, complete, test);
                }
                
                public Object createTest() throws Exception {
                    return this.getTestClass().getOnlyConstructor().newInstance(complete.getConstructorArguments(TheoryAnchor.this.nullsOk()));
                }
            }.methodBlock(this.fTestMethod).evaluate();
        }
        
        private Statement methodCompletesWithParameters(final FrameworkMethod method, final Assignments complete, final Object freshInstance) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    try {
                        final Object[] values = complete.getMethodArguments(TheoryAnchor.this.nullsOk());
                        method.invokeExplosively(freshInstance, values);
                    }
                    catch (PotentialAssignment.CouldNotGenerateValueException ex) {}
                }
            };
        }
        
        protected void handleAssumptionViolation(final AssumptionViolatedException e) {
            this.fInvalidParameters.add(e);
        }
        
        protected void reportParameterizedError(final Throwable e, final Object... params) throws Throwable {
            if (params.length == 0) {
                throw e;
            }
            throw new ParameterizedAssertionError(e, this.fTestMethod.getName(), params);
        }
        
        private boolean nullsOk() {
            final Theory annotation = this.fTestMethod.getMethod().getAnnotation(Theory.class);
            return annotation != null && annotation.nullsAccepted();
        }
        
        protected void handleDataPointSuccess() {
            ++this.successes;
        }
    }
}
