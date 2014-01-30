package au.edu.unsw.cse.soc.federatedcloud.datamodel;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * User: denis
 * Represent the Data model of a Cloud resource
 */
public class CloudResourceDescription {
    private static final Logger logger = LoggerFactory.getLogger(CloudResourceDescription.class);

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    HashMap<String, String> attributes;

    public HashSet<Deployer> getDeployers() {
        return deployers;
    }

    /**
     * Extract the Deployer with a specific deployerName from the cloud resource description
     *
     * @param deployerName
     * @return
     */
    public Deployer getDeployer(String deployerName) {
        for (Iterator<Deployer> it = deployers.iterator(); it.hasNext(); ) {
            Deployer deployer = it.next();
            if (deployer.getName().equals(deployer.getName())) {
                return deployer;
            }
        }
        throw new RuntimeException("Deployer with name:\"" + deployerName + "\" was no found.");

    }

    /**
     * Extract the Deployer from the cloud resource description. (When there are multiple deployers,
     * only the first deployer is returned)
     *
     * @return
     */
    public Deployer getDeployer() {
        for (Iterator<Deployer> it = deployers.iterator(); it.hasNext(); ) {
            Deployer deployer = it.next();
            return deployer;
        }
        throw new RuntimeException("No Deployer found.");
    }

    HashSet<Deployer> deployers;

    /**
     * Extract the ID of the cloud resource description
     *
     * @return the ID of the given cloud resource description
     */
    public int getIDOfCloudResourceDescription() {
        String id = attributes.get("id");
        if (id == null || "".equals(id)) {
            String errorMsg = "This cloud description does not have the \"id\" attribute. \n" + this;
            RuntimeException ex = new RuntimeException(errorMsg);
            logger.error(errorMsg, ex);
            throw ex;
        } else {
            return Integer.parseInt(id);
        }

    }

    /**
     * The business logic to compare cloud resource description for a given id
     *
     * @param cloudResourceDescriptionID id to be search for the input cloud resource description object
     * @return whether the ID of the input cloud resource description object is equal to the input {@code cloudResourceDescriptionID}
     */
    public boolean isCorrectCloudResourceDescription(int cloudResourceDescriptionID) {
        int id = getIDOfCloudResourceDescription();
        return id == cloudResourceDescriptionID;
    }
}
