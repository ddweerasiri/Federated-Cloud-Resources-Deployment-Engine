package au.edu.unsw.cse.soc.federatedcloud;
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
import au.edu.unsw.cse.soc.federatedcloud.datamodel.Provider;
import au.edu.unsw.cse.soc.federatedcloud.deployers.CloudResourceDeployer;
import au.edu.unsw.cse.soc.federatedcloud.deployers.DeployerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.File;

/**
 * User: denis
 * Interpret a workflow xml and execute actions
 */
@Path("/engine")
public class CloudResourceBaseDeploymentEngine {
    private static final Logger log = LoggerFactory.getLogger(CloudResourceBaseDeploymentEngine.class);
    @Path("/deploy")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String deployCloudResourceDescription(@QueryParam("description_id")  String description_id) {
        log.info("Deployment Request Received for id: " + description_id);

        try {
            CloudResourceDescription desc = DataModelUtil.buildCouldResourceDescriptionFromJSON(Integer.parseInt(description_id));
            CloudResourceBaseDeploymentEngine engine = new CloudResourceBaseDeploymentEngine();
            engine.deployCloudResourceDescription(desc);
            log.info("Deployed Resource:" + desc.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return "Resource Deployed";
    }

    public static void main(String[] args) throws Exception {
        //File file = new File("cloud-resource-base/SENG1031.json");   // cloud resource to be deployed as the input
        //File file = new File("cloud-resource-base/computing-server.json");   // cloud resource to be deployed as the input
        //File file = new File("/Users/denis/Dropbox/Documents/UNSW/Projects/github/Federated-Cloud-Resources-Deployment-Engine/cloud-resource-base/cloud-resource-descriptions/key-value-storage-service.json");   // cloud resource to be deployed as the input

        CloudResourceBaseDeploymentEngine engine = new CloudResourceBaseDeploymentEngine();
        engine.deployCloudResourceDescription(1);

    }

    /**
     * Deploy a cloud resources configuration for a given JSON file which specifies a cloud resource description
     *
     * @param file JSON file which specifies a cloud resource description
     * @throws Exception
     */
    public void deployCloudResourceDescription(File file) throws Exception {
        CloudResourceDescription description = DataModelUtil.buildCouldResourceDescriptionFromJSON(file);

        deployCloudResourceDescription(description);
    }

    /**
     * Deploy a cloud resources configuration for a given {@code CloudResourceDescription}
     *
     * @param description object of {@code CloudResourceDescription}
     * @throws Exception
     */
    public void deployCloudResourceDescription(CloudResourceDescription description) throws Exception {
        Provider provider = description.getProvider();

        CloudResourceDeployer deployer = DeployerFactory.build(provider.getName());
        deployer.deploy(description);
    }

    /**
     * Deploy a cloud resource for a given id
     *
     * @param componentID ID of a cloud resource description
     */
    public void deployCloudResourceDescription(int componentID) throws Exception {
        CloudResourceDescription description = DataModelUtil.buildCouldResourceDescriptionFromJSON(componentID);

        deployCloudResourceDescription(description);
    }
}
