//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.framework;

import org.junit.runner.*;

public class JUnit4TestCaseFacade implements Test, Describable
{
    private final Description fDescription;
    
    JUnit4TestCaseFacade(final Description description) {
        this.fDescription = description;
    }
    
    @Override
    public String toString() {
        return this.getDescription().toString();
    }
    
    public int countTestCases() {
        return 1;
    }
    
    public void run(final TestResult result) {
        throw new RuntimeException("This test stub created only for informational purposes.");
    }
    
    public Description getDescription() {
        return this.fDescription;
    }
}
