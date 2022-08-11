//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.extensions;

import junit.framework.*;

public class TestDecorator extends Assert implements Test
{
    protected Test fTest;
    
    public TestDecorator(final Test test) {
        this.fTest = test;
    }
    
    public void basicRun(final TestResult result) {
        this.fTest.run(result);
    }
    
    public int countTestCases() {
        return this.fTest.countTestCases();
    }
    
    public void run(final TestResult result) {
        this.basicRun(result);
    }
    
    @Override
    public String toString() {
        return this.fTest.toString();
    }
    
    public Test getTest() {
        return this.fTest;
    }
}
