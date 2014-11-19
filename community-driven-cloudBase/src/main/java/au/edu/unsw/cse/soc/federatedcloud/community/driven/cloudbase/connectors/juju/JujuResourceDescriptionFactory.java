package au.edu.unsw.cse.soc.federatedcloud.community.driven.cloudbase.connectors.juju;
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

/**
 * User: denis
 * TODO: Include the class description here
 */
public class JujuResourceDescriptionFactory {
    private static final Logger log = LoggerFactory.getLogger(JujuResourceDescriptionFactory.class);

    public JujuResourceDescription buildDummyDescription() {
        JujuResourceDescription desc = new JujuResourceDescription();

        JujuService service = new JujuService();
        service.setIsExposed(false);

        JujuServiceUnit serviceUnit = new JujuServiceUnit();
        service.addNewServiceUnit(serviceUnit);

        JujuAWSProvider awsProvider = new JujuAWSProvider();
        awsProvider.setType("ec2");
        awsProvider.setRegion("us-east-1");
        awsProvider.setAccessKey("access-key");
        awsProvider.setSecretKey("secret-key");

        serviceUnit.setProvider(awsProvider);

        JujuCharm charm = new JujuCharm();
        charm.setName("tomcat-7");
        charm.setSummary("summary");
        charm.setDescription("description");
        charm.setMaintainer("denis");
        charm.addCategory("app-servers");
        charm.setrepository("https://api.jujucharms.com/v4/precise/tomcat7-4/archive");

        service.setCharm(charm);

        JujuCharmInterface charmInterface = new JujuCharmInterface();
        charmInterface.setName("http");

        charm.addProvidedInterface(charmInterface);

        desc.setJujuService(service);

        return desc;
    }
}
