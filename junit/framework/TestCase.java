//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.framework;

import java.lang.reflect.*;

public abstract class TestCase extends Assert implements Test
{
    private String fName;
    
    public TestCase() {
        this.fName = null;
    }
    
    public TestCase(final String name) {
        this.fName = name;
    }
    
    public int countTestCases() {
        return 1;
    }
    
    protected TestResult createResult() {
        return new TestResult();
    }
    
    public TestResult run() {
        final TestResult result = this.createResult();
        this.run(result);
        return result;
    }
    
    public void run(final TestResult result) {
        result.run(this);
    }
    
    public void runBare() throws Throwable {
        Throwable exception = null;
        this.setUp();
        try {
            this.runTest();
        }
        catch (Throwable running) {
            exception = running;
            try {
                this.tearDown();
            }
            catch (Throwable tearingDown) {
                if (exception == null) {
                    exception = tearingDown;
                }
            }
        }
        finally {
            try {
                this.tearDown();
            }
            catch (Throwable tearingDown2) {
                if (exception == null) {
                    exception = tearingDown2;
                }
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
    
    protected void runTest() throws Throwable {
        assertNotNull("TestCase.fName cannot be null", (Object)this.fName);
        Method runMethod = null;
        try {
            runMethod = this.getClass().getMethod(this.fName, (Class<?>[])null);
        }
        catch (NoSuchMethodException e3) {
            fail("Method \"" + this.fName + "\" not found");
        }
        if (!Modifier.isPublic(runMethod.getModifiers())) {
            fail("Method \"" + this.fName + "\" should be public");
        }
        try {
            runMethod.invoke(this, new Object[0]);
        }
        catch (InvocationTargetException e) {
            e.fillInStackTrace();
            throw e.getTargetException();
        }
        catch (IllegalAccessException e2) {
            e2.fillInStackTrace();
            throw e2;
        }
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public String toString() {
        return this.getName() + "(" + this.getClass().getName() + ")";
    }
    
    public String getName() {
        return this.fName;
    }
    
    public void setName(final String name) {
        this.fName = name;
    }
}
