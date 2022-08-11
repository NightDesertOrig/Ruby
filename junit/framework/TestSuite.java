//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.framework;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class TestSuite implements Test
{
    private String fName;
    private Vector<Test> fTests;
    
    public static Test createTest(final Class<?> theClass, final String name) {
        Constructor<?> constructor;
        try {
            constructor = getTestConstructor(theClass);
        }
        catch (NoSuchMethodException e4) {
            return warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()");
        }
        Object test;
        try {
            if (constructor.getParameterTypes().length == 0) {
                test = constructor.newInstance(new Object[0]);
                if (test instanceof TestCase) {
                    ((TestCase)test).setName(name);
                }
            }
            else {
                test = constructor.newInstance(name);
            }
        }
        catch (InstantiationException e) {
            return warning("Cannot instantiate test case: " + name + " (" + exceptionToString(e) + ")");
        }
        catch (InvocationTargetException e2) {
            return warning("Exception in constructor: " + name + " (" + exceptionToString(e2.getTargetException()) + ")");
        }
        catch (IllegalAccessException e3) {
            return warning("Cannot access test case: " + name + " (" + exceptionToString(e3) + ")");
        }
        return (Test)test;
    }
    
    public static Constructor<?> getTestConstructor(final Class<?> theClass) throws NoSuchMethodException {
        try {
            return theClass.getConstructor(String.class);
        }
        catch (NoSuchMethodException e) {
            return theClass.getConstructor((Class<?>[])new Class[0]);
        }
    }
    
    public static Test warning(final String message) {
        return (Test)new TestCase("warning") {
            protected void runTest() {
                fail(message);
            }
        };
    }
    
    private static String exceptionToString(final Throwable t) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        return stringWriter.toString();
    }
    
    public TestSuite() {
        this.fTests = new Vector<Test>(10);
    }
    
    public TestSuite(final Class<?> theClass) {
        this.fTests = new Vector<Test>(10);
        this.addTestsFromTestCase(theClass);
    }
    
    private void addTestsFromTestCase(final Class<?> theClass) {
        this.fName = theClass.getName();
        try {
            getTestConstructor(theClass);
        }
        catch (NoSuchMethodException e) {
            this.addTest(warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()"));
            return;
        }
        if (!Modifier.isPublic(theClass.getModifiers())) {
            this.addTest(warning("Class " + theClass.getName() + " is not public"));
            return;
        }
        Class<?> superClass = theClass;
        final List<String> names = new ArrayList<String>();
        while (Test.class.isAssignableFrom(superClass)) {
            for (final Method each : superClass.getDeclaredMethods()) {
                this.addTestMethod(each, names, theClass);
            }
            superClass = superClass.getSuperclass();
        }
        if (this.fTests.size() == 0) {
            this.addTest(warning("No tests found in " + theClass.getName()));
        }
    }
    
    public TestSuite(final Class<? extends TestCase> theClass, final String name) {
        this(theClass);
        this.setName(name);
    }
    
    public TestSuite(final String name) {
        this.fTests = new Vector<Test>(10);
        this.setName(name);
    }
    
    public TestSuite(final Class<?>... classes) {
        this.fTests = new Vector<Test>(10);
        for (final Class<?> each : classes) {
            this.addTest(this.testCaseForClass(each));
        }
    }
    
    private Test testCaseForClass(final Class<?> each) {
        if (TestCase.class.isAssignableFrom(each)) {
            return (Test)new TestSuite(each.asSubclass(TestCase.class));
        }
        return warning(each.getCanonicalName() + " does not extend TestCase");
    }
    
    public TestSuite(final Class<? extends TestCase>[] classes, final String name) {
        this((Class<?>[])classes);
        this.setName(name);
    }
    
    public void addTest(final Test test) {
        this.fTests.add(test);
    }
    
    public void addTestSuite(final Class<? extends TestCase> testClass) {
        this.addTest((Test)new TestSuite(testClass));
    }
    
    public int countTestCases() {
        int count = 0;
        for (final Test each : this.fTests) {
            count += each.countTestCases();
        }
        return count;
    }
    
    public String getName() {
        return this.fName;
    }
    
    public void run(final TestResult result) {
        for (final Test each : this.fTests) {
            if (result.shouldStop()) {
                break;
            }
            this.runTest(each, result);
        }
    }
    
    public void runTest(final Test test, final TestResult result) {
        test.run(result);
    }
    
    public void setName(final String name) {
        this.fName = name;
    }
    
    public Test testAt(final int index) {
        return this.fTests.get(index);
    }
    
    public int testCount() {
        return this.fTests.size();
    }
    
    public Enumeration<Test> tests() {
        return this.fTests.elements();
    }
    
    @Override
    public String toString() {
        if (this.getName() != null) {
            return this.getName();
        }
        return super.toString();
    }
    
    private void addTestMethod(final Method m, final List<String> names, final Class<?> theClass) {
        final String name = m.getName();
        if (names.contains(name)) {
            return;
        }
        if (!this.isPublicTestMethod(m)) {
            if (this.isTestMethod(m)) {
                this.addTest(warning("Test method isn't public: " + m.getName() + "(" + theClass.getCanonicalName() + ")"));
            }
            return;
        }
        names.add(name);
        this.addTest(createTest(theClass, name));
    }
    
    private boolean isPublicTestMethod(final Method m) {
        return this.isTestMethod(m) && Modifier.isPublic(m.getModifiers());
    }
    
    private boolean isTestMethod(final Method m) {
        return m.getParameterTypes().length == 0 && m.getName().startsWith("test") && m.getReturnType().equals(Void.TYPE);
    }
}
