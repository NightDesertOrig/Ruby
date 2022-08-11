//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners;

import java.util.*;
import org.junit.runners.model.*;
import org.junit.internal.builders.*;
import org.junit.runner.*;
import org.junit.runner.notification.*;
import java.lang.annotation.*;

public class Suite extends ParentRunner<Runner>
{
    private final List<Runner> fRunners;
    
    public static Runner emptySuite() {
        try {
            return (Runner)new Suite((Class<?>)null, new Class[0]);
        }
        catch (InitializationError e) {
            throw new RuntimeException("This shouldn't be possible");
        }
    }
    
    private static Class<?>[] getAnnotatedClasses(final Class<?> klass) throws InitializationError {
        final SuiteClasses annotation = klass.getAnnotation(SuiteClasses.class);
        if (annotation == null) {
            throw new InitializationError(String.format("class '%s' must have a SuiteClasses annotation", klass.getName()));
        }
        return annotation.value();
    }
    
    public Suite(final Class<?> klass, final RunnerBuilder builder) throws InitializationError {
        this(builder, klass, getAnnotatedClasses(klass));
    }
    
    public Suite(final RunnerBuilder builder, final Class<?>[] classes) throws InitializationError {
        this(null, builder.runners((Class)null, (Class[])classes));
    }
    
    protected Suite(final Class<?> klass, final Class<?>[] suiteClasses) throws InitializationError {
        this((RunnerBuilder)new AllDefaultPossibilitiesBuilder(true), klass, suiteClasses);
    }
    
    protected Suite(final RunnerBuilder builder, final Class<?> klass, final Class<?>[] suiteClasses) throws InitializationError {
        this(klass, builder.runners((Class)klass, (Class[])suiteClasses));
    }
    
    protected Suite(final Class<?> klass, final List<Runner> runners) throws InitializationError {
        super((Class)klass);
        this.fRunners = runners;
    }
    
    protected List<Runner> getChildren() {
        return this.fRunners;
    }
    
    protected Description describeChild(final Runner child) {
        return child.getDescription();
    }
    
    protected void runChild(final Runner runner, final RunNotifier notifier) {
        runner.run(notifier);
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    @Inherited
    public @interface SuiteClasses {
        Class<?>[] value();
    }
}
