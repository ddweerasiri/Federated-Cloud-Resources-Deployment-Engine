package au.edu.unsw.cse.soc.federatedcloud.deployers;
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

import au.edu.unsw.cse.soc.federatedcloud.CloudResourceBaseDeploymentEngine;
import au.edu.unsw.cse.soc.federatedcloud.datamodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;

/**
 * User: denis
 * Deployer for CloudBase
 */
public class CloudBaseDeployer implements CloudResourceDeployer {
    private static final Logger logger = LoggerFactory.getLogger(CloudBaseDeployer.class);

    public void deploy(CloudResourceDescription description) throws IOException {
        Provider provider = description.getProvider("CloudResourceBase");
        CloudResourcesComposition compositionWorkflow = provider.getDeploymentWorkflow();

        Behavior behavior = compositionWorkflow.getControlFlow().getDeploymentBehavior();
        HashSet<Integer> componentResourceIDs = compositionWorkflow.getControlFlow().getComponentResourceIDs();

        deployComponentResources(behavior, componentResourceIDs);
    }

    private void deployComponentResources(Behavior deploymentBehavior, HashSet<Integer> componentResourceIDs) {
        if (deploymentBehavior.equals(Behavior.SEQUENTIAL)) {
            for (Integer componentID : componentResourceIDs) {
                DeploymentEngineLoop engineLoop = new DeploymentEngineLoop(componentID);
                engineLoop.run();                                        // Single thread implementation
            }
        } else if (deploymentBehavior.equals(Behavior.PARALLEL)) {
            for (Integer componentID : componentResourceIDs) {
                Thread engineLoop = new Thread(new DeploymentEngineLoop(componentID));
                engineLoop.start();                                     // Multiple thread implementation
            }
        }
    }

    private class DeploymentEngineLoop implements Runnable {
        private int componentID;

        private DeploymentEngineLoop(int componentID) {
            this.componentID = componentID;
        }

        public void run() {
            CloudResourceBaseDeploymentEngine engine = new CloudResourceBaseDeploymentEngine();
            try {
                engine.deployCloudResourceDescription(componentID);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
