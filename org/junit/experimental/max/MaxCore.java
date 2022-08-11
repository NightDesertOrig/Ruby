//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.max;

import java.io.*;
import org.junit.internal.requests.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.model.*;
import java.util.*;
import org.junit.internal.runners.*;
import junit.framework.*;
import java.lang.annotation.*;

public class MaxCore
{
    private static final String MALFORMED_JUNIT_3_TEST_CLASS_PREFIX = "malformed JUnit 3 test class: ";
    private final MaxHistory fHistory;
    
    @Deprecated
    public static MaxCore forFolder(final String folderName) {
        return storedLocally(new File(folderName));
    }
    
    public static MaxCore storedLocally(final File storedResults) {
        return new MaxCore(storedResults);
    }
    
    private MaxCore(final File storedResults) {
        this.fHistory = MaxHistory.forFolder(storedResults);
    }
    
    public Result run(final Class<?> testClass) {
        return this.run(Request.aClass(testClass));
    }
    
    public Result run(final Request request) {
        return this.run(request, new JUnitCore());
    }
    
    public Result run(final Request request, final JUnitCore core) {
        core.addListener(this.fHistory.listener());
        return core.run(this.sortRequest(request).getRunner());
    }
    
    public Request sortRequest(final Request request) {
        if (request instanceof SortingRequest) {
            return request;
        }
        final List<Description> leaves = this.findLeaves(request);
        Collections.sort(leaves, this.fHistory.testComparator());
        return this.constructLeafRequest(leaves);
    }
    
    private Request constructLeafRequest(final List<Description> leaves) {
        final List<Runner> runners = new ArrayList<Runner>();
        for (final Description each : leaves) {
            runners.add(this.buildRunner(each));
        }
        return new Request() {
            @Override
            public Runner getRunner() {
                try {
                    return new Suite((Class)null, runners) {};
                }
                catch (InitializationError e) {
                    return new ErrorReportingRunner(null, e);
                }
            }
        };
    }
    
    private Runner buildRunner(final Description each) {
        if (each.toString().equals("TestSuite with 0 tests")) {
            return Suite.emptySuite();
        }
        if (each.toString().startsWith("malformed JUnit 3 test class: ")) {
            return new JUnit38ClassRunner((Test)new TestSuite((Class)this.getMalformedTestClass(each)));
        }
        final Class<?> type = each.getTestClass();
        if (type == null) {
            throw new RuntimeException("Can't build a runner from description [" + each + "]");
        }
        final String methodName = each.getMethodName();
        if (methodName == null) {
            return Request.aClass(type).getRunner();
        }
        return Request.method(type, methodName).getRunner();
    }
    
    private Class<?> getMalformedTestClass(final Description each) {
        try {
            return Class.forName(each.toString().replace("malformed JUnit 3 test class: ", ""));
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }
    
    public List<Description> sortedLeavesForTest(final Request request) {
        return this.findLeaves(this.sortRequest(request));
    }
    
    private List<Description> findLeaves(final Request request) {
        final List<Description> results = new ArrayList<Description>();
        this.findLeaves(null, request.getRunner().getDescription(), results);
        return results;
    }
    
    private void findLeaves(final Description parent, final Description description, final List<Description> results) {
        if (description.getChildren().isEmpty()) {
            if (description.toString().equals("warning(junit.framework.TestSuite$1)")) {
                results.add(Description.createSuiteDescription("malformed JUnit 3 test class: " + parent, new Annotation[0]));
            }
            else {
                results.add(description);
            }
        }
        else {
            for (final Description each : description.getChildren()) {
                this.findLeaves(description, each, results);
            }
        }
    }
}
