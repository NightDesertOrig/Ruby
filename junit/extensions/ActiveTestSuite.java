//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.extensions;

import junit.framework.*;

public class ActiveTestSuite extends TestSuite
{
    private volatile int fActiveTestDeathCount;
    
    public ActiveTestSuite() {
    }
    
    public ActiveTestSuite(final Class<? extends TestCase> theClass) {
        super(theClass);
    }
    
    public ActiveTestSuite(final String name) {
        super(name);
    }
    
    public ActiveTestSuite(final Class<? extends TestCase> theClass, final String name) {
        super(theClass, name);
    }
    
    @Override
    public void run(final TestResult result) {
        this.fActiveTestDeathCount = 0;
        super.run(result);
        this.waitUntilFinished();
    }
    
    @Override
    public void runTest(final Test test, final TestResult result) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    test.run(result);
                }
                finally {
                    ActiveTestSuite.this.runFinished();
                }
            }
        };
        t.start();
    }
    
    synchronized void waitUntilFinished() {
        while (this.fActiveTestDeathCount < this.testCount()) {
            try {
                this.wait();
                continue;
            }
            catch (InterruptedException e) {
                return;
            }
            break;
        }
    }
    
    public synchronized void runFinished() {
        ++this.fActiveTestDeathCount;
        this.notifyAll();
    }
}
