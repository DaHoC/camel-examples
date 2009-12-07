/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.guice.jms;

import java.net.URL;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.osgi.util.BundleDelegatingClassLoader;

public class MyActivator implements BundleActivator {
    CamelContext camelContext;
   
    public void start(BundleContext context) throws Exception {

        Injector injector = Guice.createInjector(new MyOSGiModule(context));

        camelContext = injector.getInstance(CamelContext.class);

        if (camelContext instanceof DefaultCamelContext) {
            if (!((DefaultCamelContext)camelContext).isStarted()) {
                ((DefaultCamelContext)camelContext).start();
            }

        }
    }

    public void stop(BundleContext context) throws Exception {
        // stop the camel context
        if (camelContext != null) {
            camelContext.stop();
        }        
    }

}