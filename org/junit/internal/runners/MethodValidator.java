//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import java.lang.annotation.*;
import org.junit.*;
import java.lang.reflect.*;
import java.util.*;

@Deprecated
public class MethodValidator
{
    private final List<Throwable> fErrors;
    private TestClass fTestClass;
    
    public MethodValidator(final TestClass testClass) {
        this.fErrors = new ArrayList<Throwable>();
        this.fTestClass = testClass;
    }
    
    public void validateInstanceMethods() {
        this.validateTestMethods((Class<? extends Annotation>)After.class, false);
        this.validateTestMethods((Class<? extends Annotation>)Before.class, false);
        this.validateTestMethods(Test.class, false);
        final List<Method> methods = this.fTestClass.getAnnotatedMethods(Test.class);
        if (methods.size() == 0) {
            this.fErrors.add(new Exception("No runnable methods"));
        }
    }
    
    public void validateStaticMethods() {
        this.validateTestMethods((Class<? extends Annotation>)BeforeClass.class, true);
        this.validateTestMethods((Class<? extends Annotation>)AfterClass.class, true);
    }
    
    public List<Throwable> validateMethodsForDefaultRunner() {
        this.validateNoArgConstructor();
        this.validateStaticMethods();
        this.validateInstanceMethods();
        return this.fErrors;
    }
    
    public void assertValid() throws InitializationError {
        if (!this.fErrors.isEmpty()) {
            throw new InitializationError((List)this.fErrors);
        }
    }
    
    public void validateNoArgConstructor() {
        try {
            this.fTestClass.getConstructor();
        }
        catch (Exception e) {
            this.fErrors.add(new Exception("Test class should have public zero-argument constructor", e));
        }
    }
    
    private void validateTestMethods(final Class<? extends Annotation> annotation, final boolean isStatic) {
        final List<Method> methods = this.fTestClass.getAnnotatedMethods(annotation);
        for (final Method each : methods) {
            if (Modifier.isStatic(each.getModifiers()) != isStatic) {
                final String state = isStatic ? "should" : "should not";
                this.fErrors.add(new Exception("Method " + each.getName() + "() " + state + " be static"));
            }
            if (!Modifier.isPublic(each.getDeclaringClass().getModifiers())) {
                this.fErrors.add(new Exception("Class " + each.getDeclaringClass().getName() + " should be public"));
            }
            if (!Modifier.isPublic(each.getModifiers())) {
                this.fErrors.add(new Exception("Method " + each.getName() + " should be public"));
            }
            if (each.getReturnType() != Void.TYPE) {
                this.fErrors.add(new Exception("Method " + each.getName() + " should be void"));
            }
            if (each.getParameterTypes().length != 0) {
                this.fErrors.add(new Exception("Method " + each.getName() + " should have no parameters"));
            }
        }
    }
}
