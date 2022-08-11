//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util;

public class Timer
{
    long startTime;
    boolean paused;
    long delay;
    private long time;
    
    public Timer() {
        this.time = -1L;
        this.startTime = System.currentTimeMillis();
        this.paused = false;
        this.delay = 0L;
    }
    
    public boolean getTime(final long ms) {
        return this.getMs(System.nanoTime() - this.time) >= ms;
    }
    
    public long getCurrentTime() {
        return System.nanoTime() - this.time;
    }
    
    public boolean getTimeSub(final long ms) {
        return this.getMs(System.nanoTime() - this.time) <= ms;
    }
    
    public void setTime(final int time) {
        this.time = System.nanoTime();
    }
    
    public long getMs(final long time) {
        return time / 1000000L;
    }
    
    public boolean isPassed() {
        return !this.paused && System.currentTimeMillis() - this.startTime >= this.delay;
    }
    
    public void setDelay(final long delay) {
        this.delay = delay;
    }
    
    public boolean isPaused() {
        return this.paused;
    }
    
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }
}
