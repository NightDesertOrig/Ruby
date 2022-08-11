//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.framework;

import org.junit.runner.*;
import org.junit.runner.notification.*;
import java.util.*;

public class JUnit4TestAdapterCache extends HashMap<Description, Test>
{
    private static final long serialVersionUID = 1L;
    private static final JUnit4TestAdapterCache fInstance;
    
    public static JUnit4TestAdapterCache getDefault() {
        return JUnit4TestAdapterCache.fInstance;
    }
    
    public Test asTest(final Description description) {
        if (description.isSuite()) {
            return this.createTest(description);
        }
        if (!this.containsKey(description)) {
            this.put(description, this.createTest(description));
        }
        return ((HashMap<K, Test>)this).get(description);
    }
    
    Test createTest(final Description description) {
        if (description.isTest()) {
            return new JUnit4TestCaseFacade(description);
        }
        final TestSuite suite = new TestSuite(description.getDisplayName());
        for (final Description child : description.getChildren()) {
            suite.addTest(this.asTest(child));
        }
        return suite;
    }
    
    public RunNotifier getNotifier(final TestResult result, final JUnit4TestAdapter adapter) {
        final RunNotifier notifier = new RunNotifier();
        notifier.addListener((RunListener)new RunListener() {
            public void testFailure(final Failure failure) throws Exception {
                result.addError(JUnit4TestAdapterCache.this.asTest(failure.getDescription()), failure.getException());
            }
            
            public void testFinished(final Description description) throws Exception {
                result.endTest(JUnit4TestAdapterCache.this.asTest(description));
            }
            
            public void testStarted(final Description description) throws Exception {
                result.startTest(JUnit4TestAdapterCache.this.asTest(description));
            }
        });
        return notifier;
    }
    
    public List<Test> asTestList(final Description description) {
        if (description.isTest()) {
            return Arrays.asList(this.asTest(description));
        }
        final List<Test> returnThis = new ArrayList<Test>();
        for (final Description child : description.getChildren()) {
            returnThis.add(this.asTest(child));
        }
        return returnThis;
    }
    
    static {
        fInstance = new JUnit4TestAdapterCache();
    }
}
