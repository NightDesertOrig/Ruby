//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.runner;

public interface TestRunListener
{
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_FAILURE = 2;
    
    void testRunStarted(final String p0, final int p1);
    
    void testRunEnded(final long p0);
    
    void testRunStopped(final long p0);
    
    void testStarted(final String p0);
    
    void testEnded(final String p0);
    
    void testFailed(final int p0, final String p1, final String p2);
}
