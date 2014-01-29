package au.edu.unsw.cse.soc.federatedcloud.deployers.rackspace;
/*
 * Copyright (c) 2014, Denis Weerasiri All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import au.edu.unsw.cse.soc.federatedcloud.datamodel.CloudResourceDescription;
import au.edu.unsw.cse.soc.federatedcloud.deployers.CloudResourceDeployer;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.examples.rackspace.cloudservers.CreateServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * User: denis
 * Creates an Ubuntu 12.04 server with 1024 MB of RAM on the Rackspace Cloud.
 */
public class RackspaceDeployer implements CloudResourceDeployer {
    private static final Logger log = LoggerFactory.getLogger(RackspaceDeployer.class);

    public void deploy(CloudResourceDescription description) throws Exception {
        //Reading the credentials
        Properties properties = new Properties();
        properties.load(new FileInputStream("Rackspace-Deployers/src/main/resources/RackspaceCredentials.properties"));
        String username = properties.getProperty("username");
        String apiKey = properties.getProperty("apiKey");

        CreateServer createServer = new CreateServer(username, apiKey);


        String memory = description.getAttributes().get("memory");
        String os = description.getAttributes().get("operating-system");

        try {
            //createServer.getHardware();
            NodeMetadata metaData = createServer.createServer(memory, os);

            String publicAddress = metaData.getPublicAddresses().iterator().next();

            System.out.format("  %s%n", metaData);
            System.out.format("  Login: ssh %s@%s%n", metaData.getCredentials().getUser(), publicAddress);
            System.out.format("  Password: %s%n", metaData.getCredentials().getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            createServer.close();
        }
    }
}
