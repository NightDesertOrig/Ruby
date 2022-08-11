//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.extensions;

import junit.framework.*;

public class RepeatedTest extends TestDecorator
{
    private int fTimesRepeat;
    
    public RepeatedTest(final Test test, final int repeat) {
        super(test);
        if (repeat < 0) {
            throw new IllegalArgumentException("Repetition count must be >= 0");
        }
        this.fTimesRepeat = repeat;
    }
    
    @Override
    public int countTestCases() {
        return super.countTestCases() * this.fTimesRepeat;
    }
    
    @Override
    public void run(final TestResult result) {
        for (int i = 0; i < this.fTimesRepeat && !result.shouldStop(); ++i) {
            super.run(result);
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + "(repeated)";
    }
}
