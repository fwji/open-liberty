/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.ws.logging.internal.osgi.stackjoiner;

import java.io.PrintStream;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

public class ThrowableInfo {

    final String BASE_TRACE_SERVICE_CLASS_NAME = "com.ibm.ws.logging.internal.impl.BaseTraceService";
    final String BASE_TRACE_SERVICE_METHOD_NAME = "printStackTraceOverride";

    private Method btsMethod;

    public ThrowableInfo(Instrumentation inst) {
    	Class<?> btsClass = retrieveClass(inst, BASE_TRACE_SERVICE_CLASS_NAME);
        if (btsClass != null) {
			Method method = ReflectionHelper.getDeclaredMethod(btsClass, BASE_TRACE_SERVICE_METHOD_NAME, Throwable.class, PrintStream.class);
			setBtsMethod(method);
        }
        else {
        	System.err.println("The logging stack trace joiner could not be initialized.");
        }
    }
    
    private Class<?> retrieveClass(Instrumentation inst, String classGroup) {
        if (inst != null) {
            Class[] loadedClasses = inst.getAllLoadedClasses();
            for (int i = 0; i < loadedClasses.length; i++) {
                String name = loadedClasses[i].getName();
                if (name.equals(classGroup)) {
                    return loadedClasses[i];
                }
            }
        }
        return null;
    }

    private void setBtsMethod(Method method) {
        btsMethod = method;
    }

    public Method getBtsMethod() {
        return btsMethod;
    }
}