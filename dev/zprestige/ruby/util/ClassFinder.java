//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.util;

import java.util.*;
import net.minecraft.launchwrapper.*;
import com.google.common.reflect.*;
import java.util.function.*;
import java.util.stream.*;

public class ClassFinder
{
    public static List<Class<?>> from(final String packageName) {
        try {
            return ClassPath.from((ClassLoader)Launch.classLoader).getAllClasses().stream().filter(info -> info.getName().startsWith(packageName)).map((Function<? super Object, ?>)ClassPath.ClassInfo::load).collect((Collector<? super Object, ?, List<Class<?>>>)Collectors.toList());
        }
        catch (Exception ignored) {
            return null;
        }
    }
}
