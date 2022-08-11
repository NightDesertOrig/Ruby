//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import org.junit.runners.model.*;
import org.junit.runner.*;
import org.junit.internal.runners.statements.*;

public class Timeout implements TestRule
{
    private final int fMillis;
    
    public Timeout(final int millis) {
        this.fMillis = millis;
    }
    
    public Statement apply(final Statement base, final Description description) {
        return (Statement)new FailOnTimeout(base, (long)this.fMillis);
    }
}
