package com.ibm.ws.logging.internal.osgi.stackjoiner;

import java.lang.instrument.Instrumentation;
import org.osgi.service.component.ComponentContext;

public class ThrowableProxyComponent {
	
	private ComponentContext componentContext;
	private Instrumentation instrumentation;
	private ThrowableProxyActivator proxyActivator;
	
    /**
     * Activation callback from the Declarative Services runtime where the
     * component is ready for activation.
     *
     * @param bundleContext the bundleContext
     */
    synchronized void activate(ComponentContext componentContext) throws Exception {
    	this.componentContext = componentContext;
        this.proxyActivator = new ThrowableProxyActivator(this.instrumentation, componentContext.getBundleContext());
        this.proxyActivator.activate();
    }
    
    /**
     * Deactivation callback from the Declarative Services runtime where the
     * component is deactivated.
     *
     * @param bundleContext the bundleContext
     */
    synchronized void deactivate() throws Exception {
        this.proxyActivator.deactivate();
    }
    
    /**
     * Inject reference to the {@link java.lang.instrument.Instrumentation} implementation.
     *
     * @param instrumentationAgent the JVM's {@code Instrumentation) reference
     */
    protected void setInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }
    
}
