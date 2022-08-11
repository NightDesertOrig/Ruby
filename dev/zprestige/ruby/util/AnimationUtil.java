//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util;

public class AnimationUtil
{
    public static Integer increaseNumber(final int input, final int target, final int delta) {
        if (input < target) {
            return input + delta;
        }
        return target;
    }
    
    public static Float increaseNumber(final float input, final float target, final float delta) {
        if (input < target) {
            return input + delta;
        }
        return target;
    }
    
    public static Double increaseNumber(final double input, final double target, final double delta) {
        if (input < target) {
            return input + delta;
        }
        return target;
    }
    
    public static Integer decreaseNumber(final int input, final int target, final int delta) {
        if (input > target) {
            return input - delta;
        }
        return target;
    }
    
    public static Float decreaseNumber(final float input, final float target, final float delta) {
        if (input > target) {
            return input - delta;
        }
        return target;
    }
}
