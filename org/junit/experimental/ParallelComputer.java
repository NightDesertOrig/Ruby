//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental;

import org.junit.runner.*;
import org.junit.runners.*;
import java.util.concurrent.*;
import java.util.*;
import org.junit.runners.model.*;

public class ParallelComputer extends Computer
{
    private final boolean fClasses;
    private final boolean fMethods;
    
    public ParallelComputer(final boolean classes, final boolean methods) {
        this.fClasses = classes;
        this.fMethods = methods;
    }
    
    public static Computer classes() {
        return new ParallelComputer(true, false);
    }
    
    public static Computer methods() {
        return new ParallelComputer(false, true);
    }
    
    private static <T> Runner parallelize(final Runner runner) {
        if (runner instanceof ParentRunner) {
            ((ParentRunner)runner).setScheduler(new RunnerScheduler() {
                private final List<Future<Object>> fResults = new ArrayList<Future<Object>>();
                private final ExecutorService fService = Executors.newCachedThreadPool();
                
                public void schedule(final Runnable childStatement) {
                    this.fResults.add(this.fService.submit((Callable<Object>)new Callable<Object>() {
                        public Object call() throws Exception {
                            childStatement.run();
                            return null;
                        }
                    }));
                }
                
                public void finished() {
                    for (final Future<Object> each : this.fResults) {
                        try {
                            each.get();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        return runner;
    }
    
    @Override
    public Runner getSuite(final RunnerBuilder builder, final Class<?>[] classes) throws InitializationError {
        final Runner suite = super.getSuite(builder, classes);
        return this.fClasses ? parallelize(suite) : suite;
    }
    
    @Override
    protected Runner getRunner(final RunnerBuilder builder, final Class<?> testClass) throws Throwable {
        final Runner runner = super.getRunner(builder, testClass);
        return this.fMethods ? parallelize(runner) : runner;
    }
}
