//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.framework;

public class AssertionFailedError extends AssertionError
{
    private static final long serialVersionUID = 1L;
    
    public AssertionFailedError() {
    }
    
    public AssertionFailedError(final String message) {
        super((Object)defaultString(message));
    }
    
    private static String defaultString(final String message) {
        return (message == null) ? "" : message;
    }
}
