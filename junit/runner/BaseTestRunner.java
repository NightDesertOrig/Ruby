//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.runner;

import java.util.*;
import junit.framework.*;
import java.lang.reflect.*;
import java.text.*;
import java.io.*;

public abstract class BaseTestRunner implements TestListener
{
    public static final String SUITE_METHODNAME = "suite";
    private static Properties fPreferences;
    static int fgMaxMessageLength;
    static boolean fgFilterStack;
    boolean fLoading;
    
    public BaseTestRunner() {
        this.fLoading = true;
    }
    
    public synchronized void startTest(final Test test) {
        this.testStarted(test.toString());
    }
    
    protected static void setPreferences(final Properties preferences) {
        BaseTestRunner.fPreferences = preferences;
    }
    
    protected static Properties getPreferences() {
        if (BaseTestRunner.fPreferences == null) {
            (BaseTestRunner.fPreferences = new Properties()).put("loading", "true");
            BaseTestRunner.fPreferences.put("filterstack", "true");
            readPreferences();
        }
        return BaseTestRunner.fPreferences;
    }
    
    public static void savePreferences() throws IOException {
        final FileOutputStream fos = new FileOutputStream(getPreferencesFile());
        try {
            getPreferences().store(fos, "");
        }
        finally {
            fos.close();
        }
    }
    
    public static void setPreference(final String key, final String value) {
        getPreferences().put(key, value);
    }
    
    public synchronized void endTest(final Test test) {
        this.testEnded(test.toString());
    }
    
    public synchronized void addError(final Test test, final Throwable t) {
        this.testFailed(1, test, t);
    }
    
    public synchronized void addFailure(final Test test, final AssertionFailedError t) {
        this.testFailed(2, test, (Throwable)t);
    }
    
    public abstract void testStarted(final String p0);
    
    public abstract void testEnded(final String p0);
    
    public abstract void testFailed(final int p0, final Test p1, final Throwable p2);
    
    public Test getTest(final String suiteClassName) {
        if (suiteClassName.length() <= 0) {
            this.clearStatus();
            return null;
        }
        Class<?> testClass = null;
        try {
            testClass = this.loadSuiteClass(suiteClassName);
        }
        catch (ClassNotFoundException e) {
            String clazz = e.getMessage();
            if (clazz == null) {
                clazz = suiteClassName;
            }
            this.runFailed("Class not found \"" + clazz + "\"");
            return null;
        }
        catch (Exception e2) {
            this.runFailed("Error: " + e2.toString());
            return null;
        }
        Method suiteMethod = null;
        try {
            suiteMethod = testClass.getMethod("suite", (Class<?>[])new Class[0]);
        }
        catch (Exception e5) {
            this.clearStatus();
            return (Test)new TestSuite((Class)testClass);
        }
        if (!Modifier.isStatic(suiteMethod.getModifiers())) {
            this.runFailed("Suite() method must be static");
            return null;
        }
        Test test = null;
        try {
            test = (Test)suiteMethod.invoke(null, (Object[])new Class[0]);
            if (test == null) {
                return test;
            }
        }
        catch (InvocationTargetException e3) {
            this.runFailed("Failed to invoke suite():" + e3.getTargetException().toString());
            return null;
        }
        catch (IllegalAccessException e4) {
            this.runFailed("Failed to invoke suite():" + e4.toString());
            return null;
        }
        this.clearStatus();
        return test;
    }
    
    public String elapsedTimeAsString(final long runTime) {
        return NumberFormat.getInstance().format(runTime / 1000.0);
    }
    
    protected String processArguments(final String[] args) {
        String suiteName = null;
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-noloading")) {
                this.setLoading(false);
            }
            else if (args[i].equals("-nofilterstack")) {
                BaseTestRunner.fgFilterStack = false;
            }
            else if (args[i].equals("-c")) {
                if (args.length > i + 1) {
                    suiteName = this.extractClassName(args[i + 1]);
                }
                else {
                    System.out.println("Missing Test class name");
                }
                ++i;
            }
            else {
                suiteName = args[i];
            }
        }
        return suiteName;
    }
    
    public void setLoading(final boolean enable) {
        this.fLoading = enable;
    }
    
    public String extractClassName(final String className) {
        if (className.startsWith("Default package for")) {
            return className.substring(className.lastIndexOf(".") + 1);
        }
        return className;
    }
    
    public static String truncate(String s) {
        if (BaseTestRunner.fgMaxMessageLength != -1 && s.length() > BaseTestRunner.fgMaxMessageLength) {
            s = s.substring(0, BaseTestRunner.fgMaxMessageLength) + "...";
        }
        return s;
    }
    
    protected abstract void runFailed(final String p0);
    
    protected Class<?> loadSuiteClass(final String suiteClassName) throws ClassNotFoundException {
        return Class.forName(suiteClassName);
    }
    
    protected void clearStatus() {
    }
    
    protected boolean useReloadingTestSuiteLoader() {
        return getPreference("loading").equals("true") && this.fLoading;
    }
    
    private static File getPreferencesFile() {
        final String home = System.getProperty("user.home");
        return new File(home, "junit.properties");
    }
    
    private static void readPreferences() {
        InputStream is = null;
        try {
            is = new FileInputStream(getPreferencesFile());
            setPreferences(new Properties(getPreferences()));
            getPreferences().load(is);
        }
        catch (IOException e) {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException ex) {}
        }
    }
    
    public static String getPreference(final String key) {
        return getPreferences().getProperty(key);
    }
    
    public static int getPreference(final String key, final int dflt) {
        final String value = getPreference(key);
        int intValue = dflt;
        if (value == null) {
            return intValue;
        }
        try {
            intValue = Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {}
        return intValue;
    }
    
    public static String getFilteredTrace(final Throwable t) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        final StringBuffer buffer = stringWriter.getBuffer();
        final String trace = buffer.toString();
        return getFilteredTrace(trace);
    }
    
    public static String getFilteredTrace(final String stack) {
        if (showStackRaw()) {
            return stack;
        }
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        final StringReader sr = new StringReader(stack);
        final BufferedReader br = new BufferedReader(sr);
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (!filterLine(line)) {
                    pw.println(line);
                }
            }
        }
        catch (Exception IOException) {
            return stack;
        }
        return sw.toString();
    }
    
    protected static boolean showStackRaw() {
        return !getPreference("filterstack").equals("true") || !BaseTestRunner.fgFilterStack;
    }
    
    static boolean filterLine(final String line) {
        final String[] patterns = { "junit.framework.TestCase", "junit.framework.TestResult", "junit.framework.TestSuite", "junit.framework.Assert.", "junit.swingui.TestRunner", "junit.awtui.TestRunner", "junit.textui.TestRunner", "java.lang.reflect.Method.invoke(" };
        for (int i = 0; i < patterns.length; ++i) {
            if (line.indexOf(patterns[i]) > 0) {
                return true;
            }
        }
        return false;
    }
    
    static {
        BaseTestRunner.fgMaxMessageLength = 500;
        BaseTestRunner.fgFilterStack = true;
        BaseTestRunner.fgMaxMessageLength = getPreference("maxmessage", BaseTestRunner.fgMaxMessageLength);
    }
}
