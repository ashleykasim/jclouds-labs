/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jclouds.vsphere;

//import com.vmware.vim25.mo.ServiceInstance;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.jclouds.apis.BaseApiLiveTest;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.ssh.SshClient;
import org.jclouds.vsphere.compute.config.VSphereComputeServiceAdapter;
import org.testng.annotations.Test;

import java.util.Properties;


@Test(groups = "live")
public class BaseVSphereLiveTest  extends BaseApiLiveTest<VSphereApi> {
   public BaseVSphereLiveTest() {
      provider = "vsphere";
   }

   private VSphereComputeServiceAdapter adapter;
   private TemplateBuilder templateBuilder;
   private SshClient.Factory sshFactory;

   @Override
   protected VSphereApi create(Properties props, Iterable<Module> modules) {
      Injector injector = newBuilder().modules(modules).overrides(props).buildInjector();
      adapter = injector.getInstance(VSphereComputeServiceAdapter.class);
      templateBuilder = injector.getInstance(TemplateBuilder.class);
      sshFactory = injector.getInstance(SshClient.Factory.class);
      return injector.getInstance(VSphereApi.class);
   }

   //@Test
//   public void testTestUploadFile() throws Exception {
//      ServiceInstance si = new ServiceInstance(new URL("https://10.45.7.70/sdk"), "root", "vmware", true);
//      //si.getFileManager().makeDirectory();
//   }


}
