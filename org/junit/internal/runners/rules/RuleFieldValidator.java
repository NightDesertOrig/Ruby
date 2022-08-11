//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners.rules;

import java.lang.annotation.*;
import org.junit.runners.model.*;
import java.util.*;
import org.junit.rules.*;
import org.junit.*;

public enum RuleFieldValidator
{
    CLASS_RULE_VALIDATOR((Class<? extends Annotation>)ClassRule.class, true), 
    RULE_VALIDATOR((Class<? extends Annotation>)Rule.class, false);
    
    private final Class<? extends Annotation> fAnnotation;
    private final boolean fOnlyStaticFields;
    
    private RuleFieldValidator(final Class<? extends Annotation> annotation, final boolean onlyStaticFields) {
        this.fAnnotation = annotation;
        this.fOnlyStaticFields = onlyStaticFields;
    }
    
    public void validate(final TestClass target, final List<Throwable> errors) {
        final List<FrameworkField> fields = target.getAnnotatedFields(this.fAnnotation);
        for (final FrameworkField each : fields) {
            this.validateField(each, errors);
        }
    }
    
    private void validateField(final FrameworkField field, final List<Throwable> errors) {
        this.optionallyValidateStatic(field, errors);
        this.validatePublic(field, errors);
        this.validateTestRuleOrMethodRule(field, errors);
    }
    
    private void optionallyValidateStatic(final FrameworkField field, final List<Throwable> errors) {
        if (this.fOnlyStaticFields && !field.isStatic()) {
            this.addError(errors, field, "must be static.");
        }
    }
    
    private void validatePublic(final FrameworkField field, final List<Throwable> errors) {
        if (!field.isPublic()) {
            this.addError(errors, field, "must be public.");
        }
    }
    
    private void validateTestRuleOrMethodRule(final FrameworkField field, final List<Throwable> errors) {
        if (!this.isMethodRule(field) && !this.isTestRule(field)) {
            this.addError(errors, field, "must implement MethodRule or TestRule.");
        }
    }
    
    private boolean isTestRule(final FrameworkField target) {
        return TestRule.class.isAssignableFrom(target.getType());
    }
    
    private boolean isMethodRule(final FrameworkField target) {
        return MethodRule.class.isAssignableFrom(target.getType());
    }
    
    private void addError(final List<Throwable> errors, final FrameworkField field, final String suffix) {
        final String message = "The @" + this.fAnnotation.getSimpleName() + " '" + field.getName() + "' " + suffix;
        errors.add(new Exception(message));
    }
}
