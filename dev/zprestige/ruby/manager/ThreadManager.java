//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import java.util.concurrent.*;

public class ThreadManager
{
    protected ExecutorService executorService;
    
    public ThreadManager() {
        this.executorService = Executors.newFixedThreadPool(2);
    }
    
    public void run(final Runnable command) {
        try {
            this.executorService.execute(command);
        }
        catch (Exception ex) {}
    }
    
    public void setExecutorService(final ExecutorService executorService) {
        this.executorService = executorService;
    }
}
