//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import java.util.regex.*;
import org.hamcrest.*;

public class DescribedAs<T> extends BaseMatcher<T>
{
    private final String descriptionTemplate;
    private final Matcher<T> matcher;
    private final Object[] values;
    private static final Pattern ARG_PATTERN;
    
    public DescribedAs(final String descriptionTemplate, final Matcher<T> matcher, final Object[] values) {
        this.descriptionTemplate = descriptionTemplate;
        this.matcher = matcher;
        this.values = values.clone();
    }
    
    public boolean matches(final Object o) {
        return this.matcher.matches(o);
    }
    
    public void describeTo(final Description description) {
        final java.util.regex.Matcher arg = DescribedAs.ARG_PATTERN.matcher(this.descriptionTemplate);
        int textStart = 0;
        while (arg.find()) {
            description.appendText(this.descriptionTemplate.substring(textStart, arg.start()));
            final int argIndex = Integer.parseInt(arg.group(1));
            description.appendValue(this.values[argIndex]);
            textStart = arg.end();
        }
        if (textStart < this.descriptionTemplate.length()) {
            description.appendText(this.descriptionTemplate.substring(textStart));
        }
    }
    
    @Factory
    public static <T> Matcher<T> describedAs(final String description, final Matcher<T> matcher, final Object... values) {
        return new DescribedAs<T>(description, matcher, values);
    }
    
    static {
        ARG_PATTERN = Pattern.compile("%([0-9]+)");
    }
}
