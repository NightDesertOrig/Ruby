//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.extensions;

import junit.framework.*;

public class TestSetup extends TestDecorator
{
    public TestSetup(final Test test) {
        super(test);
    }
    
    public void run(final TestResult result) {
        final Protectable p = new Protectable() {
            public void protect() throws Exception {
                TestSetup.this.setUp();
                TestSetup.this.basicRun(result);
                TestSetup.this.tearDown();
            }
        };
        result.runProtected((Test)this, p);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
}
