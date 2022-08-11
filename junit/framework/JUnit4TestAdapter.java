//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.framework;

import org.junit.runner.*;
import java.util.*;
import org.junit.*;
import org.junit.runner.manipulation.*;

public class JUnit4TestAdapter implements Test, Filterable, Sortable, Describable
{
    private final Class<?> fNewTestClass;
    private final Runner fRunner;
    private final JUnit4TestAdapterCache fCache;
    
    public JUnit4TestAdapter(final Class<?> newTestClass) {
        this(newTestClass, JUnit4TestAdapterCache.getDefault());
    }
    
    public JUnit4TestAdapter(final Class<?> newTestClass, final JUnit4TestAdapterCache cache) {
        this.fCache = cache;
        this.fNewTestClass = newTestClass;
        this.fRunner = Request.classWithoutSuiteMethod((Class)newTestClass).getRunner();
    }
    
    public int countTestCases() {
        return this.fRunner.testCount();
    }
    
    public void run(final TestResult result) {
        this.fRunner.run(this.fCache.getNotifier(result, this));
    }
    
    public List<Test> getTests() {
        return this.fCache.asTestList(this.getDescription());
    }
    
    public Class<?> getTestClass() {
        return this.fNewTestClass;
    }
    
    public Description getDescription() {
        final Description description = this.fRunner.getDescription();
        return this.removeIgnored(description);
    }
    
    private Description removeIgnored(final Description description) {
        if (this.isIgnored(description)) {
            return Description.EMPTY;
        }
        final Description result = description.childlessCopy();
        for (final Description each : description.getChildren()) {
            final Description child = this.removeIgnored(each);
            if (!child.isEmpty()) {
                result.addChild(child);
            }
        }
        return result;
    }
    
    private boolean isIgnored(final Description description) {
        return description.getAnnotation((Class)Ignore.class) != null;
    }
    
    @Override
    public String toString() {
        return this.fNewTestClass.getName();
    }
    
    public void filter(final Filter filter) throws NoTestsRemainException {
        filter.apply((Object)this.fRunner);
    }
    
    public void sort(final Sorter sorter) {
        sorter.apply((Object)this.fRunner);
    }
}
