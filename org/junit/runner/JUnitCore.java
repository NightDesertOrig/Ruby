//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner;

import junit.runner.*;
import java.lang.annotation.*;
import org.junit.internal.*;
import org.junit.runner.notification.*;
import java.util.*;
import junit.framework.*;
import org.junit.internal.runners.*;

public class JUnitCore
{
    private RunNotifier fNotifier;
    
    public JUnitCore() {
        this.fNotifier = new RunNotifier();
    }
    
    public static void main(final String... args) {
        runMainAndExit((JUnitSystem)new RealSystem(), args);
    }
    
    public static void runMainAndExit(final JUnitSystem system, final String... args) {
        final Result result = new JUnitCore().runMain(system, args);
        system.exit((int)(result.wasSuccessful() ? 0 : 1));
    }
    
    public static Result runClasses(final Computer computer, final Class<?>... classes) {
        return new JUnitCore().run(computer, classes);
    }
    
    public static Result runClasses(final Class<?>... classes) {
        return new JUnitCore().run(defaultComputer(), classes);
    }
    
    public Result runMain(final JUnitSystem system, final String... args) {
        system.out().println("JUnit version " + Version.id());
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        final List<Failure> missingClasses = new ArrayList<Failure>();
        for (final String each : args) {
            try {
                classes.add(Class.forName(each));
            }
            catch (ClassNotFoundException e) {
                system.out().println("Could not find class: " + each);
                final Description description = Description.createSuiteDescription(each, new Annotation[0]);
                final Failure failure = new Failure(description, e);
                missingClasses.add(failure);
            }
        }
        final RunListener listener = (RunListener)new TextListener(system);
        this.addListener(listener);
        final Result result = this.run((Class<?>[])classes.toArray(new Class[0]));
        for (final Failure each2 : missingClasses) {
            result.getFailures().add(each2);
        }
        return result;
    }
    
    public String getVersion() {
        return Version.id();
    }
    
    public Result run(final Class<?>... classes) {
        return this.run(Request.classes(defaultComputer(), classes));
    }
    
    public Result run(final Computer computer, final Class<?>... classes) {
        return this.run(Request.classes(computer, classes));
    }
    
    public Result run(final Request request) {
        return this.run(request.getRunner());
    }
    
    public Result run(final Test test) {
        return this.run((Runner)new JUnit38ClassRunner(test));
    }
    
    public Result run(final Runner runner) {
        final Result result = new Result();
        final RunListener listener = result.createListener();
        this.fNotifier.addFirstListener(listener);
        try {
            this.fNotifier.fireTestRunStarted(runner.getDescription());
            runner.run(this.fNotifier);
            this.fNotifier.fireTestRunFinished(result);
        }
        finally {
            this.removeListener(listener);
        }
        return result;
    }
    
    public void addListener(final RunListener listener) {
        this.fNotifier.addListener(listener);
    }
    
    public void removeListener(final RunListener listener) {
        this.fNotifier.removeListener(listener);
    }
    
    static Computer defaultComputer() {
        return new Computer();
    }
}
