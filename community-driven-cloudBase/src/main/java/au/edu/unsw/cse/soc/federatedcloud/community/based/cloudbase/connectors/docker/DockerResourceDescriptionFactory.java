package au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.connectors.docker;
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
public class DockerResourceDescriptionFactory {
    private static final Logger log = LoggerFactory.getLogger(DockerResourceDescriptionFactory.class);

    public static DockerResourceDescription buildDummyDescription() {
        DockerResourceDescription desc = new DockerResourceDescription();

        //System.out.println(desc.getTestVal());

        DockerContainerDescription container = new DockerContainerDescription();
        container.setName("tomcat-instance-1");
        container.setState("run");
        container.setProtBindingRules("127.0.0.1:80:8080");
        desc.setContainer(container);

        //System.out.println(desc.getContainer().getName());

        DockerImageDescription image = new DockerImageDescription();
        image.setName("ddweerasiri/tomcat7");
        image.setVersion("7");
        //image.setScript("sudo apt-get install tomcat");
        //image.setBaseImage("tifayuki/java", "7");
        container.setImage(image);

        DockerVirtualMachineDescription vm = new DockerVirtualMachineDescription();
        vm.setIp("129.94.175.240");
        vm.setPort("4243");
        container.setVirtualMachine(vm);

        return desc;
    }
}
