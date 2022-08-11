//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners.model;

import java.util.*;
import java.lang.reflect.*;

class NoGenericTypeParametersValidator
{
    private final Method fMethod;
    
    NoGenericTypeParametersValidator(final Method method) {
        this.fMethod = method;
    }
    
    void validate(final List<Throwable> errors) {
        for (final Type each : this.fMethod.getGenericParameterTypes()) {
            this.validateNoTypeParameterOnType(each, errors);
        }
    }
    
    private void validateNoTypeParameterOnType(final Type type, final List<Throwable> errors) {
        if (type instanceof TypeVariable) {
            errors.add(new Exception("Method " + this.fMethod.getName() + "() contains unresolved type variable " + type));
        }
        else if (type instanceof ParameterizedType) {
            this.validateNoTypeParameterOnParameterizedType((ParameterizedType)type, errors);
        }
        else if (type instanceof WildcardType) {
            this.validateNoTypeParameterOnWildcardType((WildcardType)type, errors);
        }
        else if (type instanceof GenericArrayType) {
            this.validateNoTypeParameterOnGenericArrayType((GenericArrayType)type, errors);
        }
    }
    
    private void validateNoTypeParameterOnParameterizedType(final ParameterizedType parameterized, final List<Throwable> errors) {
        for (final Type each : parameterized.getActualTypeArguments()) {
            this.validateNoTypeParameterOnType(each, errors);
        }
    }
    
    private void validateNoTypeParameterOnWildcardType(final WildcardType wildcard, final List<Throwable> errors) {
        for (final Type each : wildcard.getUpperBounds()) {
            this.validateNoTypeParameterOnType(each, errors);
        }
        for (final Type each : wildcard.getLowerBounds()) {
            this.validateNoTypeParameterOnType(each, errors);
        }
    }
    
    private void validateNoTypeParameterOnGenericArrayType(final GenericArrayType arrayType, final List<Throwable> errors) {
        this.validateNoTypeParameterOnType(arrayType.getGenericComponentType(), errors);
    }
}
