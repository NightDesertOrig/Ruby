//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.max;

import java.io.*;
import java.util.*;
import org.junit.runner.notification.*;
import org.junit.runner.*;

public class MaxHistory implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final Map<String, Long> fDurations;
    private final Map<String, Long> fFailureTimestamps;
    private final File fHistoryStore;
    
    public static MaxHistory forFolder(final File file) {
        if (file.exists()) {
            try {
                return readHistory(file);
            }
            catch (CouldNotReadCoreException e) {
                e.printStackTrace();
                file.delete();
            }
        }
        return new MaxHistory(file);
    }
    
    private static MaxHistory readHistory(final File storedResults) throws CouldNotReadCoreException {
        try {
            final FileInputStream file = new FileInputStream(storedResults);
            try {
                final ObjectInputStream stream = new ObjectInputStream(file);
                try {
                    return (MaxHistory)stream.readObject();
                }
                finally {
                    stream.close();
                }
            }
            finally {
                file.close();
            }
        }
        catch (Exception e) {
            throw new CouldNotReadCoreException((Throwable)e);
        }
    }
    
    private MaxHistory(final File storedResults) {
        this.fDurations = new HashMap<String, Long>();
        this.fFailureTimestamps = new HashMap<String, Long>();
        this.fHistoryStore = storedResults;
    }
    
    private void save() throws IOException {
        final ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.fHistoryStore));
        stream.writeObject(this);
        stream.close();
    }
    
    Long getFailureTimestamp(final Description key) {
        return this.fFailureTimestamps.get(key.toString());
    }
    
    void putTestFailureTimestamp(final Description key, final long end) {
        this.fFailureTimestamps.put(key.toString(), end);
    }
    
    boolean isNewTest(final Description key) {
        return !this.fDurations.containsKey(key.toString());
    }
    
    Long getTestDuration(final Description key) {
        return this.fDurations.get(key.toString());
    }
    
    void putTestDuration(final Description description, final long duration) {
        this.fDurations.put(description.toString(), duration);
    }
    
    public RunListener listener() {
        return new RememberingListener();
    }
    
    public Comparator<Description> testComparator() {
        return new TestComparator();
    }
    
    private final class RememberingListener extends RunListener
    {
        private long overallStart;
        private Map<Description, Long> starts;
        
        private RememberingListener() {
            this.overallStart = System.currentTimeMillis();
            this.starts = new HashMap<Description, Long>();
        }
        
        @Override
        public void testStarted(final Description description) throws Exception {
            this.starts.put(description, System.nanoTime());
        }
        
        @Override
        public void testFinished(final Description description) throws Exception {
            final long end = System.nanoTime();
            final long start = this.starts.get(description);
            MaxHistory.this.putTestDuration(description, end - start);
        }
        
        @Override
        public void testFailure(final Failure failure) throws Exception {
            MaxHistory.this.putTestFailureTimestamp(failure.getDescription(), this.overallStart);
        }
        
        @Override
        public void testRunFinished(final Result result) throws Exception {
            MaxHistory.this.save();
        }
    }
    
    private class TestComparator implements Comparator<Description>
    {
        public int compare(final Description o1, final Description o2) {
            if (MaxHistory.this.isNewTest(o1)) {
                return -1;
            }
            if (MaxHistory.this.isNewTest(o2)) {
                return 1;
            }
            final int result = this.getFailure(o2).compareTo(this.getFailure(o1));
            return (result != 0) ? result : MaxHistory.this.getTestDuration(o1).compareTo(MaxHistory.this.getTestDuration(o2));
        }
        
        private Long getFailure(final Description key) {
            final Long result = MaxHistory.this.getFailureTimestamp(key);
            if (result == null) {
                return 0L;
            }
            return result;
        }
    }
}
