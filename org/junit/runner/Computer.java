//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner;

import org.junit.runners.*;
import org.junit.runners.model.*;

public class Computer
{
    public static Computer serial() {
        return new Computer();
    }
    
    public Runner getSuite(final RunnerBuilder builder, final Class<?>[] classes) throws InitializationError {
        return new Suite(new RunnerBuilder() {
            @Override
            public Runner runnerForClass(final Class<?> testClass) throws Throwable {
                return Computer.this.getRunner(builder, testClass);
            }
        }, classes);
    }
    
    protected Runner getRunner(final RunnerBuilder builder, final Class<?> testClass) throws Throwable {
        return builder.runnerForClass(testClass);
    }
}
