//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest;

import java.util.*;
import org.hamcrest.internal.*;

public abstract class BaseDescription implements Description
{
    public Description appendText(final String text) {
        this.append(text);
        return this;
    }
    
    public Description appendDescriptionOf(final SelfDescribing value) {
        value.describeTo(this);
        return this;
    }
    
    public Description appendValue(final Object value) {
        if (value == null) {
            this.append("null");
        }
        else if (value instanceof String) {
            this.toJavaSyntax((String)value);
        }
        else if (value instanceof Character) {
            this.append('\"');
            this.toJavaSyntax((char)value);
            this.append('\"');
        }
        else if (value instanceof Short) {
            this.append('<');
            this.append(String.valueOf(value));
            this.append("s>");
        }
        else if (value instanceof Long) {
            this.append('<');
            this.append(String.valueOf(value));
            this.append("L>");
        }
        else if (value instanceof Float) {
            this.append('<');
            this.append(String.valueOf(value));
            this.append("F>");
        }
        else if (value.getClass().isArray()) {
            this.appendValueList("[", ", ", "]", (Iterator<Object>)new ArrayIterator(value));
        }
        else {
            this.append('<');
            this.append(String.valueOf(value));
            this.append('>');
        }
        return this;
    }
    
    public <T> Description appendValueList(final String start, final String separator, final String end, final T... values) {
        return this.appendValueList(start, separator, end, Arrays.asList(values));
    }
    
    public <T> Description appendValueList(final String start, final String separator, final String end, final Iterable<T> values) {
        return this.appendValueList(start, separator, end, values.iterator());
    }
    
    private <T> Description appendValueList(final String start, final String separator, final String end, final Iterator<T> values) {
        return this.appendList(start, separator, end, new SelfDescribingValueIterator<Object>(values));
    }
    
    public Description appendList(final String start, final String separator, final String end, final Iterable<? extends SelfDescribing> values) {
        return this.appendList(start, separator, end, values.iterator());
    }
    
    private Description appendList(final String start, final String separator, final String end, final Iterator<? extends SelfDescribing> i) {
        boolean separate = false;
        this.append(start);
        while (i.hasNext()) {
            if (separate) {
                this.append(separator);
            }
            this.appendDescriptionOf((SelfDescribing)i.next());
            separate = true;
        }
        this.append(end);
        return this;
    }
    
    protected void append(final String str) {
        for (int i = 0; i < str.length(); ++i) {
            this.append(str.charAt(i));
        }
    }
    
    protected abstract void append(final char p0);
    
    private void toJavaSyntax(final String unformatted) {
        this.append('\"');
        for (int i = 0; i < unformatted.length(); ++i) {
            this.toJavaSyntax(unformatted.charAt(i));
        }
        this.append('\"');
    }
    
    private void toJavaSyntax(final char ch) {
        switch (ch) {
            case '\"': {
                this.append("\\\"");
                break;
            }
            case '\n': {
                this.append("\\n");
                break;
            }
            case '\r': {
                this.append("\\r");
                break;
            }
            case '\t': {
                this.append("\\t");
                break;
            }
            default: {
                this.append(ch);
                break;
            }
        }
    }
}
