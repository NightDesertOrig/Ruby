//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.theories.suppliers;

import org.junit.experimental.theories.*;
import java.util.*;

public class TestedOnSupplier extends ParameterSupplier
{
    public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
        final List<PotentialAssignment> list = new ArrayList<PotentialAssignment>();
        final TestedOn testedOn = (TestedOn)sig.getAnnotation((Class)TestedOn.class);
        final int[] arr$;
        final int[] ints = arr$ = testedOn.ints();
        for (final int i : arr$) {
            list.add(PotentialAssignment.forValue(Arrays.asList(new int[][] { ints }).toString(), (Object)i));
        }
        return list;
    }
}
