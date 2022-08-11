//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util;

import java.util.*;

public class GraphUtil
{
    public Queue<Long> crystals;
    
    public GraphUtil() {
        this.crystals = new LinkedList<Long>();
    }
    
    public int getCount() {
        final long currentTimeMillis = System.currentTimeMillis();
        try {
            while (!this.crystals.isEmpty() && this.crystals.peek() < currentTimeMillis) {
                this.crystals.remove();
            }
        }
        catch (Exception ex) {}
        return this.crystals.size();
    }
    
    public void addItem() {
        this.crystals.add(System.currentTimeMillis() + 1000L);
    }
}
