//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners.model;

import java.lang.reflect.*;

public abstract class ReflectiveCallable
{
    public Object run() throws Throwable {
        try {
            return this.runReflectiveCall();
        }
        catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }
    
    protected abstract Object runReflectiveCall() throws Throwable;
}
