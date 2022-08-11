//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.theories.internal;

import org.junit.runners.model.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.experimental.theories.*;

public class Assignments
{
    private List<PotentialAssignment> fAssigned;
    private final List<ParameterSignature> fUnassigned;
    private final TestClass fClass;
    
    private Assignments(final List<PotentialAssignment> assigned, final List<ParameterSignature> unassigned, final TestClass testClass) {
        this.fUnassigned = unassigned;
        this.fAssigned = assigned;
        this.fClass = testClass;
    }
    
    public static Assignments allUnassigned(final Method testMethod, final TestClass testClass) throws Exception {
        final List<ParameterSignature> signatures = ParameterSignature.signatures(testClass.getOnlyConstructor());
        signatures.addAll(ParameterSignature.signatures(testMethod));
        return new Assignments(new ArrayList<PotentialAssignment>(), signatures, testClass);
    }
    
    public boolean isComplete() {
        return this.fUnassigned.size() == 0;
    }
    
    public ParameterSignature nextUnassigned() {
        return this.fUnassigned.get(0);
    }
    
    public Assignments assignNext(final PotentialAssignment source) {
        final List<PotentialAssignment> assigned = new ArrayList<PotentialAssignment>(this.fAssigned);
        assigned.add(source);
        return new Assignments(assigned, this.fUnassigned.subList(1, this.fUnassigned.size()), this.fClass);
    }
    
    public Object[] getActualValues(final int start, final int stop, final boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
        final Object[] values = new Object[stop - start];
        for (int i = start; i < stop; ++i) {
            final Object value = this.fAssigned.get(i).getValue();
            if (value == null && !nullsOk) {
                throw new PotentialAssignment.CouldNotGenerateValueException();
            }
            values[i - start] = value;
        }
        return values;
    }
    
    public List<PotentialAssignment> potentialsForNextUnassigned() throws InstantiationException, IllegalAccessException {
        final ParameterSignature unassigned = this.nextUnassigned();
        return this.getSupplier(unassigned).getValueSources(unassigned);
    }
    
    public ParameterSupplier getSupplier(final ParameterSignature unassigned) throws InstantiationException, IllegalAccessException {
        final ParameterSupplier supplier = this.getAnnotatedSupplier(unassigned);
        if (supplier != null) {
            return supplier;
        }
        return (ParameterSupplier)new AllMembersSupplier(this.fClass);
    }
    
    public ParameterSupplier getAnnotatedSupplier(final ParameterSignature unassigned) throws InstantiationException, IllegalAccessException {
        final ParametersSuppliedBy annotation = unassigned.findDeepAnnotation(ParametersSuppliedBy.class);
        if (annotation == null) {
            return null;
        }
        return (ParameterSupplier)annotation.value().newInstance();
    }
    
    public Object[] getConstructorArguments(final boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
        return this.getActualValues(0, this.getConstructorParameterCount(), nullsOk);
    }
    
    public Object[] getMethodArguments(final boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
        return this.getActualValues(this.getConstructorParameterCount(), this.fAssigned.size(), nullsOk);
    }
    
    public Object[] getAllArguments(final boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
        return this.getActualValues(0, this.fAssigned.size(), nullsOk);
    }
    
    private int getConstructorParameterCount() {
        final List<ParameterSignature> signatures = ParameterSignature.signatures(this.fClass.getOnlyConstructor());
        final int constructorParameterCount = signatures.size();
        return constructorParameterCount;
    }
    
    public Object[] getArgumentStrings(final boolean nullsOk) throws PotentialAssignment.CouldNotGenerateValueException {
        final Object[] values = new Object[this.fAssigned.size()];
        for (int i = 0; i < values.length; ++i) {
            values[i] = this.fAssigned.get(i).getDescription();
        }
        return values;
    }
}
