//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import junit.framework.*;
import java.lang.reflect.*;

public class SuiteMethod extends JUnit38ClassRunner
{
    public SuiteMethod(final Class<?> klass) throws Throwable {
        super(testFromSuiteMethod(klass));
    }
    
    public static Test testFromSuiteMethod(final Class<?> klass) throws Throwable {
        Method suiteMethod = null;
        Test suite = null;
        try {
            suiteMethod = klass.getMethod("suite", (Class<?>[])new Class[0]);
            if (!Modifier.isStatic(suiteMethod.getModifiers())) {
                throw new Exception(klass.getName() + ".suite() must be static");
            }
            suite = (Test)suiteMethod.invoke(null, new Object[0]);
        }
        catch (InvocationTargetException e) {
            throw e.getCause();
        }
        return suite;
    }
}
