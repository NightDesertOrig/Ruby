//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest;

public interface Description
{
    Description appendText(final String p0);
    
    Description appendDescriptionOf(final SelfDescribing p0);
    
    Description appendValue(final Object p0);
    
     <T> Description appendValueList(final String p0, final String p1, final String p2, final T... p3);
    
     <T> Description appendValueList(final String p0, final String p1, final String p2, final Iterable<T> p3);
    
    Description appendList(final String p0, final String p1, final String p2, final Iterable<? extends SelfDescribing> p3);
}
