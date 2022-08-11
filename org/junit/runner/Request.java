//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner;

import org.junit.internal.builders.*;
import org.junit.runners.model.*;
import org.junit.internal.runners.*;
import org.junit.runner.manipulation.*;
import java.util.*;
import org.junit.internal.requests.*;

public abstract class Request
{
    public static Request method(final Class<?> clazz, final String methodName) {
        final Description method = Description.createTestDescription((Class)clazz, methodName);
        return aClass(clazz).filterWith(method);
    }
    
    public static Request aClass(final Class<?> clazz) {
        return (Request)new ClassRequest((Class)clazz);
    }
    
    public static Request classWithoutSuiteMethod(final Class<?> clazz) {
        return (Request)new ClassRequest((Class)clazz, false);
    }
    
    public static Request classes(final Computer computer, final Class<?>... classes) {
        try {
            final AllDefaultPossibilitiesBuilder builder = new AllDefaultPossibilitiesBuilder(true);
            final Runner suite = computer.getSuite((RunnerBuilder)builder, (Class[])classes);
            return runner(suite);
        }
        catch (InitializationError e) {
            throw new RuntimeException("Bug in saff's brain: Suite constructor, called as above, should always complete");
        }
    }
    
    public static Request classes(final Class<?>... classes) {
        return classes(JUnitCore.defaultComputer(), classes);
    }
    
    @Deprecated
    public static Request errorReport(final Class<?> klass, final Throwable cause) {
        return runner((Runner)new ErrorReportingRunner((Class)klass, cause));
    }
    
    public static Request runner(final Runner runner) {
        return new Request() {
            @Override
            public Runner getRunner() {
                return runner;
            }
        };
    }
    
    public abstract Runner getRunner();
    
    public Request filterWith(final Filter filter) {
        return (Request)new FilterRequest(this, filter);
    }
    
    public Request filterWith(final Description desiredDescription) {
        return this.filterWith(Filter.matchMethodDescription(desiredDescription));
    }
    
    public Request sortWith(final Comparator<Description> comparator) {
        return (Request)new SortingRequest(this, (Comparator)comparator);
    }
}
