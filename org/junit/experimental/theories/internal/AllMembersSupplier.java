//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.theories.internal;

import java.lang.annotation.*;
import org.junit.runners.model.*;
import java.util.*;
import org.junit.experimental.theories.*;
import java.lang.reflect.*;

public class AllMembersSupplier extends ParameterSupplier
{
    private final TestClass fClass;
    
    public AllMembersSupplier(final TestClass type) {
        this.fClass = type;
    }
    
    @Override
    public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
        final List<PotentialAssignment> list = new ArrayList<PotentialAssignment>();
        this.addFields(sig, list);
        this.addSinglePointMethods(sig, list);
        this.addMultiPointMethods(list);
        return list;
    }
    
    private void addMultiPointMethods(final List<PotentialAssignment> list) {
        for (final FrameworkMethod dataPointsMethod : this.fClass.getAnnotatedMethods((Class<? extends Annotation>)DataPoints.class)) {
            try {
                this.addArrayValues(dataPointsMethod.getName(), list, dataPointsMethod.invokeExplosively(null, new Object[0]));
            }
            catch (Throwable t) {}
        }
    }
    
    private void addSinglePointMethods(final ParameterSignature sig, final List<PotentialAssignment> list) {
        for (final FrameworkMethod dataPointMethod : this.fClass.getAnnotatedMethods((Class<? extends Annotation>)DataPoint.class)) {
            final Class<?> type = sig.getType();
            if (dataPointMethod.producesType(type)) {
                list.add(new MethodParameterValue(dataPointMethod));
            }
        }
    }
    
    private void addFields(final ParameterSignature sig, final List<PotentialAssignment> list) {
        for (final Field field : this.fClass.getJavaClass().getFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                final Class<?> type = field.getType();
                if (sig.canAcceptArrayType(type) && field.getAnnotation(DataPoints.class) != null) {
                    this.addArrayValues(field.getName(), list, this.getStaticFieldValue(field));
                }
                else if (sig.canAcceptType(type) && field.getAnnotation(DataPoint.class) != null) {
                    list.add(PotentialAssignment.forValue(field.getName(), this.getStaticFieldValue(field)));
                }
            }
        }
    }
    
    private void addArrayValues(final String name, final List<PotentialAssignment> list, final Object array) {
        for (int i = 0; i < Array.getLength(array); ++i) {
            list.add(PotentialAssignment.forValue(name + "[" + i + "]", Array.get(array, i)));
        }
    }
    
    private Object getStaticFieldValue(final Field field) {
        try {
            return field.get(null);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("unexpected: field from getClass doesn't exist on object");
        }
        catch (IllegalAccessException e2) {
            throw new RuntimeException("unexpected: getFields returned an inaccessible field");
        }
    }
    
    static class MethodParameterValue extends PotentialAssignment
    {
        private final FrameworkMethod fMethod;
        
        private MethodParameterValue(final FrameworkMethod dataPointMethod) {
            this.fMethod = dataPointMethod;
        }
        
        @Override
        public Object getValue() throws CouldNotGenerateValueException {
            try {
                return this.fMethod.invokeExplosively(null, new Object[0]);
            }
            catch (IllegalArgumentException e) {
                throw new RuntimeException("unexpected: argument length is checked");
            }
            catch (IllegalAccessException e2) {
                throw new RuntimeException("unexpected: getMethods returned an inaccessible method");
            }
            catch (Throwable e3) {
                throw new CouldNotGenerateValueException();
            }
        }
        
        @Override
        public String getDescription() throws CouldNotGenerateValueException {
            return this.fMethod.getName();
        }
    }
}
