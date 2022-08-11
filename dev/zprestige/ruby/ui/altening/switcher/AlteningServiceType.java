//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.altening.switcher;

public enum AlteningServiceType
{
    MOJANG("https://authserver.mojang.com/", "https://sessionserver.mojang.com/"), 
    THEALTENING("http://authserver.thealtening.com/", "http://sessionserver.thealtening.com/");
    
    private final String authServer;
    private final String sessionServer;
    
    private AlteningServiceType(final String authServer, final String sessionServer) {
        this.authServer = authServer;
        this.sessionServer = sessionServer;
    }
    
    public String getAuthServer() {
        return this.authServer;
    }
    
    public String getSessionServer() {
        return this.sessionServer;
    }
}
