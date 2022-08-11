//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner;

import org.junit.runner.notification.*;

public abstract class Runner implements Describable
{
    public abstract Description getDescription();
    
    public abstract void run(final RunNotifier p0);
    
    public int testCount() {
        return this.getDescription().testCount();
    }
}
