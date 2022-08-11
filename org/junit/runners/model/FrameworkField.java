//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners.model;

import java.lang.annotation.*;
import java.lang.reflect.*;

public class FrameworkField extends FrameworkMember<FrameworkField>
{
    private final Field fField;
    
    FrameworkField(final Field field) {
        this.fField = field;
    }
    
    public String getName() {
        return this.getField().getName();
    }
    
    public Annotation[] getAnnotations() {
        return this.fField.getAnnotations();
    }
    
    public boolean isPublic() {
        final int modifiers = this.fField.getModifiers();
        return Modifier.isPublic(modifiers);
    }
    
    public boolean isShadowedBy(final FrameworkField otherMember) {
        return otherMember.getName().equals(this.getName());
    }
    
    public boolean isStatic() {
        final int modifiers = this.fField.getModifiers();
        return Modifier.isStatic(modifiers);
    }
    
    public Field getField() {
        return this.fField;
    }
    
    public Class<?> getType() {
        return this.fField.getType();
    }
    
    public Object get(final Object target) throws IllegalArgumentException, IllegalAccessException {
        return this.fField.get(target);
    }
}
