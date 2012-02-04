/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.nova.v1_1;

import static org.testng.Assert.assertEquals;

import java.net.URI;
import java.util.Properties;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties;
import org.jclouds.openstack.nova.v1_1.internal.BaseNovaRestClientExpectTest;
import org.jclouds.openstack.nova.v1_1.parse.ParseServerListTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

/**
 * 
 * @see KeystoneProperties#CREDENTIAL_TYPE
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "PasswordAuthenticationExpectTest")
public class PasswordAuthenticationExpectTest extends BaseNovaRestClientExpectTest {

   /**
    * this reflects the properties that a user would pass to createContext
    */
   @Override
   protected Properties setupProperties() {
      Properties contextProperties = super.setupProperties();
      contextProperties.setProperty("jclouds.keystone.credential-type", "passwordCredentials");
      return contextProperties;
   }

   public void testListServersWhenResponseIs2xx() throws Exception {
      HttpRequest listServers = HttpRequest.builder().method("GET").endpoint(
               URI.create("https://compute.north.host/v1.1/3456/servers")).headers(
               ImmutableMultimap.<String, String> builder().put("Accept", "application/json").put("X-Auth-Token",
                        authToken).build()).build();

      HttpResponse listServersResponse = HttpResponse.builder().statusCode(200).payload(
               payloadFromResource("/server_list.json")).build();

      NovaClient clientWhenServersExist = requestsSendResponses(keystoneAuthWithUsernameAndPassword,
               responseWithKeystoneAccess, listServers, listServersResponse);
      
      assertEquals(clientWhenServersExist.getConfiguredRegions(), ImmutableSet.of("North"));
      
      assertEquals(clientWhenServersExist.getServerClientForRegion("North").listServers().toString(), new ParseServerListTest().expected().toString());
   }

}
