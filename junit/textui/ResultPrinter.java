//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.textui;

import java.io.*;
import java.util.*;
import junit.runner.*;
import java.text.*;
import junit.framework.*;

public class ResultPrinter implements TestListener
{
    PrintStream fWriter;
    int fColumn;
    
    public ResultPrinter(final PrintStream writer) {
        this.fColumn = 0;
        this.fWriter = writer;
    }
    
    synchronized void print(final TestResult result, final long runTime) {
        this.printHeader(runTime);
        this.printErrors(result);
        this.printFailures(result);
        this.printFooter(result);
    }
    
    void printWaitPrompt() {
        this.getWriter().println();
        this.getWriter().println("<RETURN> to continue");
    }
    
    protected void printHeader(final long runTime) {
        this.getWriter().println();
        this.getWriter().println("Time: " + this.elapsedTimeAsString(runTime));
    }
    
    protected void printErrors(final TestResult result) {
        this.printDefects(result.errors(), result.errorCount(), "error");
    }
    
    protected void printFailures(final TestResult result) {
        this.printDefects(result.failures(), result.failureCount(), "failure");
    }
    
    protected void printDefects(final Enumeration<TestFailure> booBoos, final int count, final String type) {
        if (count == 0) {
            return;
        }
        if (count == 1) {
            this.getWriter().println("There was " + count + " " + type + ":");
        }
        else {
            this.getWriter().println("There were " + count + " " + type + "s:");
        }
        int i = 1;
        while (booBoos.hasMoreElements()) {
            this.printDefect(booBoos.nextElement(), i);
            ++i;
        }
    }
    
    public void printDefect(final TestFailure booBoo, final int count) {
        this.printDefectHeader(booBoo, count);
        this.printDefectTrace(booBoo);
    }
    
    protected void printDefectHeader(final TestFailure booBoo, final int count) {
        this.getWriter().print(count + ") " + booBoo.failedTest());
    }
    
    protected void printDefectTrace(final TestFailure booBoo) {
        this.getWriter().print(BaseTestRunner.getFilteredTrace(booBoo.trace()));
    }
    
    protected void printFooter(final TestResult result) {
        if (result.wasSuccessful()) {
            this.getWriter().println();
            this.getWriter().print("OK");
            this.getWriter().println(" (" + result.runCount() + " test" + ((result.runCount() == 1) ? "" : "s") + ")");
        }
        else {
            this.getWriter().println();
            this.getWriter().println("FAILURES!!!");
            this.getWriter().println("Tests run: " + result.runCount() + ",  Failures: " + result.failureCount() + ",  Errors: " + result.errorCount());
        }
        this.getWriter().println();
    }
    
    protected String elapsedTimeAsString(final long runTime) {
        return NumberFormat.getInstance().format(runTime / 1000.0);
    }
    
    public PrintStream getWriter() {
        return this.fWriter;
    }
    
    public void addError(final Test test, final Throwable t) {
        this.getWriter().print("E");
    }
    
    public void addFailure(final Test test, final AssertionFailedError t) {
        this.getWriter().print("F");
    }
    
    public void endTest(final Test test) {
    }
    
    public void startTest(final Test test) {
        this.getWriter().print(".");
        if (this.fColumn++ >= 40) {
            this.getWriter().println();
            this.fColumn = 0;
        }
    }
}
