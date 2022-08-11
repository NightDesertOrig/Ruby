//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.font;

public class FontCache
{
    int displayList;
    long lastUsage;
    
    public FontCache(final int displayList, final long lastUsage) {
        this.displayList = displayList;
        this.lastUsage = lastUsage;
    }
    
    public int getDisplayList() {
        return this.displayList;
    }
    
    public long getLastUsage() {
        return this.lastUsage;
    }
    
    public void setLastUsage(final long lastUsage) {
        this.lastUsage = lastUsage;
    }
}
