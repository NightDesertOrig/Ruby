//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.ui.altening.switcher;

import java.lang.invoke.*;
import java.lang.reflect.*;
import java.util.*;

public final class FieldAdapter
{
    private static final MethodHandles.Lookup LOOKUP;
    private static Field MODIFIERS;
    private final HashMap<String, MethodHandle> fields;
    
    public FieldAdapter(final String parent) {
        this.fields = new HashMap<String, MethodHandle>();
        try {
            final Class cls = Class.forName(parent);
            final Field modifiers = FieldAdapter.MODIFIERS;
            for (final Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                final int accessFlags = field.getModifiers();
                if (Modifier.isFinal(accessFlags)) {
                    modifiers.setInt(field, accessFlags & 0xFFFFFFEF);
                }
                MethodHandle handler = FieldAdapter.LOOKUP.unreflectSetter(field);
                handler = handler.asType(handler.type().generic().changeReturnType((Class<?>)Void.TYPE));
                this.fields.put(field.getName(), handler);
            }
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load/find the specified class");
        }
        catch (Exception e2) {
            throw new RuntimeException("Couldn't create a method handler for the field");
        }
    }
    
    public void updateFieldIfPresent(final String name, final Object newValue) {
        Optional.ofNullable(this.fields.get(name)).ifPresent(setter -> {
            try {
                setter.invokeExact(newValue);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        });
    }
    
    static {
        try {
            (FieldAdapter.MODIFIERS = Field.class.getDeclaredField("modifiers")).setAccessible(true);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        MethodHandles.Lookup lookupObject;
        try {
            final Field lookupImplField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            lookupImplField.setAccessible(true);
            lookupObject = (MethodHandles.Lookup)lookupImplField.get(null);
        }
        catch (ReflectiveOperationException e2) {
            lookupObject = MethodHandles.lookup();
        }
        LOOKUP = lookupObject;
    }
}
