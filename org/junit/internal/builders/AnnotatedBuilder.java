//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.builders;

import org.junit.runner.*;
import org.junit.runners.model.*;

public class AnnotatedBuilder extends RunnerBuilder
{
    private static final String CONSTRUCTOR_ERROR_FORMAT = "Custom runner class %s should have a public constructor with signature %s(Class testClass)";
    private RunnerBuilder fSuiteBuilder;
    
    public AnnotatedBuilder(final RunnerBuilder suiteBuilder) {
        this.fSuiteBuilder = suiteBuilder;
    }
    
    @Override
    public Runner runnerForClass(final Class<?> testClass) throws Exception {
        final RunWith annotation = testClass.getAnnotation(RunWith.class);
        if (annotation != null) {
            return this.buildRunner(annotation.value(), testClass);
        }
        return null;
    }
    
    public Runner buildRunner(final Class<? extends Runner> runnerClass, final Class<?> testClass) throws Exception {
        try {
            return (Runner)runnerClass.getConstructor(Class.class).newInstance(testClass);
        }
        catch (NoSuchMethodException e) {
            try {
                return (Runner)runnerClass.getConstructor(Class.class, RunnerBuilder.class).newInstance(testClass, this.fSuiteBuilder);
            }
            catch (NoSuchMethodException e2) {
                final String simpleName = runnerClass.getSimpleName();
                throw new InitializationError(String.format("Custom runner class %s should have a public constructor with signature %s(Class testClass)", simpleName, simpleName));
            }
        }
    }
}
