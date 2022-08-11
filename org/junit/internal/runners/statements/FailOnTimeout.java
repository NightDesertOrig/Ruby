//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.runners.statements;

import org.junit.runners.model.*;

public class FailOnTimeout extends Statement
{
    private final Statement fOriginalStatement;
    private final long fTimeout;
    
    public FailOnTimeout(final Statement originalStatement, final long timeout) {
        this.fOriginalStatement = originalStatement;
        this.fTimeout = timeout;
    }
    
    @Override
    public void evaluate() throws Throwable {
        final StatementThread thread = this.evaluateStatement();
        if (!thread.fFinished) {
            this.throwExceptionForUnfinishedThread(thread);
        }
    }
    
    private StatementThread evaluateStatement() throws InterruptedException {
        final StatementThread thread = new StatementThread(this.fOriginalStatement);
        thread.start();
        thread.join(this.fTimeout);
        thread.interrupt();
        return thread;
    }
    
    private void throwExceptionForUnfinishedThread(final StatementThread thread) throws Throwable {
        if (thread.fExceptionThrownByOriginalStatement != null) {
            throw thread.fExceptionThrownByOriginalStatement;
        }
        this.throwTimeoutException(thread);
    }
    
    private void throwTimeoutException(final StatementThread thread) throws Exception {
        final Exception exception = new Exception(String.format("test timed out after %d milliseconds", this.fTimeout));
        exception.setStackTrace(thread.getStackTrace());
        throw exception;
    }
    
    private static class StatementThread extends Thread
    {
        private final Statement fStatement;
        private boolean fFinished;
        private Throwable fExceptionThrownByOriginalStatement;
        
        public StatementThread(final Statement statement) {
            this.fFinished = false;
            this.fExceptionThrownByOriginalStatement = null;
            this.fStatement = statement;
        }
        
        @Override
        public void run() {
            try {
                this.fStatement.evaluate();
                this.fFinished = true;
            }
            catch (InterruptedException e2) {}
            catch (Throwable e) {
                this.fExceptionThrownByOriginalStatement = e;
            }
        }
    }
}
