//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner;

import java.io.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import org.junit.runner.notification.*;

public class Result implements Serializable
{
    private static final long serialVersionUID = 1L;
    private AtomicInteger fCount;
    private AtomicInteger fIgnoreCount;
    private final List<Failure> fFailures;
    private long fRunTime;
    private long fStartTime;
    
    public Result() {
        this.fCount = new AtomicInteger();
        this.fIgnoreCount = new AtomicInteger();
        this.fFailures = Collections.synchronizedList(new ArrayList<Failure>());
        this.fRunTime = 0L;
    }
    
    public int getRunCount() {
        return this.fCount.get();
    }
    
    public int getFailureCount() {
        return this.fFailures.size();
    }
    
    public long getRunTime() {
        return this.fRunTime;
    }
    
    public List<Failure> getFailures() {
        return this.fFailures;
    }
    
    public int getIgnoreCount() {
        return this.fIgnoreCount.get();
    }
    
    public boolean wasSuccessful() {
        return this.getFailureCount() == 0;
    }
    
    public RunListener createListener() {
        return new Listener();
    }
    
    private class Listener extends RunListener
    {
        public void testRunStarted(final Description description) throws Exception {
            Result.this.fStartTime = System.currentTimeMillis();
        }
        
        public void testRunFinished(final Result result) throws Exception {
            final long endTime = System.currentTimeMillis();
            Result.this.fRunTime += endTime - Result.this.fStartTime;
        }
        
        public void testFinished(final Description description) throws Exception {
            Result.this.fCount.getAndIncrement();
        }
        
        public void testFailure(final Failure failure) throws Exception {
            Result.this.fFailures.add(failure);
        }
        
        public void testIgnored(final Description description) throws Exception {
            Result.this.fIgnoreCount.getAndIncrement();
        }
        
        public void testAssumptionFailure(final Failure failure) {
        }
    }
}
