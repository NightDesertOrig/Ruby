//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners;

import org.junit.runner.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.runner.notification.*;
import org.junit.runners.model.*;
import java.lang.annotation.*;

public class Parameterized extends Suite
{
    private final ArrayList<Runner> runners;
    
    public Parameterized(final Class<?> klass) throws Throwable {
        super(klass, Collections.emptyList());
        this.runners = new ArrayList<Runner>();
        final List<Object[]> parametersList = this.getParametersList(this.getTestClass());
        for (int i = 0; i < parametersList.size(); ++i) {
            this.runners.add((Runner)new TestClassRunnerForParameters(this.getTestClass().getJavaClass(), parametersList, i));
        }
    }
    
    @Override
    protected List<Runner> getChildren() {
        return this.runners;
    }
    
    private List<Object[]> getParametersList(final TestClass klass) throws Throwable {
        return (List<Object[]>)this.getParametersMethod(klass).invokeExplosively((Object)null, new Object[0]);
    }
    
    private FrameworkMethod getParametersMethod(final TestClass testClass) throws Exception {
        final List<FrameworkMethod> methods = (List<FrameworkMethod>)testClass.getAnnotatedMethods((Class)Parameters.class);
        for (final FrameworkMethod each : methods) {
            final int modifiers = each.getMethod().getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                return each;
            }
        }
        throw new Exception("No public static parameters method on class " + testClass.getName());
    }
    
    private class TestClassRunnerForParameters extends BlockJUnit4ClassRunner
    {
        private final int fParameterSetNumber;
        private final List<Object[]> fParameterList;
        
        TestClassRunnerForParameters(final Class<?> type, final List<Object[]> parameterList, final int i) throws InitializationError {
            super((Class)type);
            this.fParameterList = parameterList;
            this.fParameterSetNumber = i;
        }
        
        public Object createTest() throws Exception {
            return this.getTestClass().getOnlyConstructor().newInstance(this.computeParams());
        }
        
        private Object[] computeParams() throws Exception {
            try {
                return this.fParameterList.get(this.fParameterSetNumber);
            }
            catch (ClassCastException e) {
                throw new Exception(String.format("%s.%s() must return a Collection of arrays.", this.getTestClass().getName(), Parameterized.this.getParametersMethod(this.getTestClass()).getName()));
            }
        }
        
        protected String getName() {
            return String.format("[%s]", this.fParameterSetNumber);
        }
        
        protected String testName(final FrameworkMethod method) {
            return String.format("%s[%s]", method.getName(), this.fParameterSetNumber);
        }
        
        protected void validateConstructor(final List<Throwable> errors) {
            this.validateOnlyOneConstructor((List)errors);
        }
        
        protected Statement classBlock(final RunNotifier notifier) {
            return this.childrenInvoker(notifier);
        }
        
        protected Annotation[] getRunnerAnnotations() {
            return new Annotation[0];
        }
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD })
    public @interface Parameters {
    }
}
