//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.textui;

import java.io.*;
import junit.framework.*;
import junit.runner.*;

public class TestRunner extends BaseTestRunner
{
    private ResultPrinter fPrinter;
    public static final int SUCCESS_EXIT = 0;
    public static final int FAILURE_EXIT = 1;
    public static final int EXCEPTION_EXIT = 2;
    
    public TestRunner() {
        this(System.out);
    }
    
    public TestRunner(final PrintStream writer) {
        this(new ResultPrinter(writer));
    }
    
    public TestRunner(final ResultPrinter printer) {
        this.fPrinter = printer;
    }
    
    public static void run(final Class<? extends TestCase> testClass) {
        run((Test)new TestSuite((Class)testClass));
    }
    
    public static TestResult run(final Test test) {
        final TestRunner runner = new TestRunner();
        return runner.doRun(test);
    }
    
    public static void runAndWait(final Test suite) {
        final TestRunner aTestRunner = new TestRunner();
        aTestRunner.doRun(suite, true);
    }
    
    public void testFailed(final int status, final Test test, final Throwable t) {
    }
    
    public void testStarted(final String testName) {
    }
    
    public void testEnded(final String testName) {
    }
    
    protected TestResult createTestResult() {
        return new TestResult();
    }
    
    public TestResult doRun(final Test test) {
        return this.doRun(test, false);
    }
    
    public TestResult doRun(final Test suite, final boolean wait) {
        final TestResult result = this.createTestResult();
        result.addListener((TestListener)this.fPrinter);
        final long startTime = System.currentTimeMillis();
        suite.run(result);
        final long endTime = System.currentTimeMillis();
        final long runTime = endTime - startTime;
        this.fPrinter.print(result, runTime);
        this.pause(wait);
        return result;
    }
    
    protected void pause(final boolean wait) {
        if (!wait) {
            return;
        }
        this.fPrinter.printWaitPrompt();
        try {
            System.in.read();
        }
        catch (Exception ex) {}
    }
    
    public static void main(final String[] args) {
        final TestRunner aTestRunner = new TestRunner();
        try {
            final TestResult r = aTestRunner.start(args);
            if (!r.wasSuccessful()) {
                System.exit(1);
            }
            System.exit(0);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }
    
    public TestResult start(final String[] args) throws Exception {
        String testCase = "";
        String method = "";
        boolean wait = false;
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-wait")) {
                wait = true;
            }
            else if (args[i].equals("-c")) {
                testCase = this.extractClassName(args[++i]);
            }
            else if (args[i].equals("-m")) {
                final String arg = args[++i];
                final int lastIndex = arg.lastIndexOf(46);
                testCase = arg.substring(0, lastIndex);
                method = arg.substring(lastIndex + 1);
            }
            else if (args[i].equals("-v")) {
                System.err.println("JUnit " + Version.id() + " by Kent Beck and Erich Gamma");
            }
            else {
                testCase = args[i];
            }
        }
        if (testCase.equals("")) {
            throw new Exception("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");
        }
        try {
            if (!method.equals("")) {
                return this.runSingleMethod(testCase, method, wait);
            }
            final Test suite = this.getTest(testCase);
            return this.doRun(suite, wait);
        }
        catch (Exception e) {
            throw new Exception("Could not create and run test suite: " + e);
        }
    }
    
    protected TestResult runSingleMethod(final String testCase, final String method, final boolean wait) throws Exception {
        final Class<? extends TestCase> testClass = this.loadSuiteClass(testCase).asSubclass(TestCase.class);
        final Test test = TestSuite.createTest((Class)testClass, method);
        return this.doRun(test, wait);
    }
    
    protected void runFailed(final String message) {
        System.err.println(message);
        System.exit(1);
    }
    
    public void setPrinter(final ResultPrinter printer) {
        this.fPrinter = printer;
    }
}
