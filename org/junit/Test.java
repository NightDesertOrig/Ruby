//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Test {
    Class<? extends Throwable> expected() default None.class;
    
    long timeout() default 0L;
    
    public static class None extends Throwable
    {
        private static final long serialVersionUID = 1L;
        
        private None() {
        }
    }
}
