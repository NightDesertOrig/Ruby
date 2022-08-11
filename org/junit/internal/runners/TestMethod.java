//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import java.util.*;
import org.junit.*;
import java.lang.reflect.*;

@Deprecated
public class TestMethod
{
    private final Method fMethod;
    private TestClass fTestClass;
    
    public TestMethod(final Method method, final TestClass testClass) {
        this.fMethod = method;
        this.fTestClass = testClass;
    }
    
    public boolean isIgnored() {
        return this.fMethod.getAnnotation(Ignore.class) != null;
    }
    
    public long getTimeout() {
        final Test annotation = this.fMethod.getAnnotation(Test.class);
        if (annotation == null) {
            return 0L;
        }
        final long timeout = annotation.timeout();
        return timeout;
    }
    
    protected Class<? extends Throwable> getExpectedException() {
        final Test annotation = this.fMethod.getAnnotation(Test.class);
        if (annotation == null || annotation.expected() == Test.None.class) {
            return null;
        }
        return annotation.expected();
    }
    
    boolean isUnexpected(final Throwable exception) {
        return !this.getExpectedException().isAssignableFrom(exception.getClass());
    }
    
    boolean expectsException() {
        return this.getExpectedException() != null;
    }
    
    List<Method> getBefores() {
        return (List<Method>)this.fTestClass.getAnnotatedMethods((Class)Before.class);
    }
    
    List<Method> getAfters() {
        return (List<Method>)this.fTestClass.getAnnotatedMethods((Class)After.class);
    }
    
    public void invoke(final Object test) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        this.fMethod.invoke(test, new Object[0]);
    }
}
