//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import org.junit.runner.*;

public class TestName extends TestWatcher
{
    private String fName;
    
    @Override
    protected void starting(final Description d) {
        this.fName = d.getMethodName();
    }
    
    public String getMethodName() {
        return this.fName;
    }
}
