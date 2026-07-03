package org.momento.lycoris.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;

public class JavaAgent {

    public static final String PID = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

    private static Instrumentation ins;

    public static Instrumentation load() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine vm = VirtualMachine.attach(PID);
        vm.loadAgent(JavaAgent.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        vm.detach();
        if (ins == null)
            throw new IllegalStateException("JavaAgent not initialized");
        return ins;
    }

    public static void agentmain(final String args, final Instrumentation instrumentation) {
        ins = instrumentation;
    }
}
