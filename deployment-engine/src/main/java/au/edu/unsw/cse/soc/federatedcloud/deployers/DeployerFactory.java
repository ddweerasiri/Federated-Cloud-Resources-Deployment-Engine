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

import au.edu.unsw.cse.soc.federatedcloud.datamodel.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: denis
 * Factory class to generate deployers for the given Provider
 */
public class DeployerFactory {
    private static final Logger logger = LoggerFactory.getLogger(DeployerFactory.class);

    public static CloudResourceDeployer build(String provider) throws Exception {
        /*if (Constants.CLOUD_RESOURCE_BASE_PROVIDER_NAME.equals(provider)) {
            return new CloudBaseDeployer();
        } else if ("Pivotal Tracker".equals(provider)) {
            return new PivotalTrackerDeployer();
        } else if ("LucidChart".equals(provider)) {
            return new LucidChartDeployer();
        } else if ("AWS-S3".equals(provider)) {
            //return new AWSS3Deployer();
            return null;
        } else if ("Rackspace".equals(provider)) {
            //return new RackspaceDeployer();
            return null;
        } else if ("Google Cloud Storage".equals(provider)) {
            return new GoogleCloudDeployer();
        } else if ("Google-Drive".equals(provider)) {
            return new GoogleDriveDeployer();
        } else if ("Heroku".equals(provider)) {
            return new HerokuDeployer();
        } else {
            throw new Exception("Deployer class is not registered for the provider \"" + provider + "\" in DeployerFactory.");
        }*/
        return null;
    }
}
