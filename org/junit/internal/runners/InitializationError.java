//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners;

import java.util.*;

@Deprecated
public class InitializationError extends Exception
{
    private static final long serialVersionUID = 1L;
    private final List<Throwable> fErrors;
    
    public InitializationError(final List<Throwable> errors) {
        this.fErrors = errors;
    }
    
    public InitializationError(final Throwable... errors) {
        this(Arrays.asList(errors));
    }
    
    public InitializationError(final String string) {
        this(new Throwable[] { new Exception(string) });
    }
    
    public List<Throwable> getCauses() {
        return this.fErrors;
    }
}
