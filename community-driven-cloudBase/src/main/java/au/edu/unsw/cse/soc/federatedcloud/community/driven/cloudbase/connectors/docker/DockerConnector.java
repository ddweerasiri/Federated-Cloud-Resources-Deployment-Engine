package au.edu.unsw.cse.soc.federatedcloud.community.driven.cloudbase.connectors.docker;
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

import au.edu.unsw.cse.soc.federatedcloud.community.driven.cloudbase.connectors.Connector;
import au.edu.unsw.cse.soc.federatedcloud.community.driven.cloudbase.connectors.Result;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Random;

/**
 * User: denis
 * Docker Connector
 */
public class DockerConnector implements Connector{
    private static final Logger log = LoggerFactory.getLogger(DockerConnector.class);

    @Override
    public int init(JSONObject resourceDescription) {
        DockerResourceDescription desc = populateDockerSpecificDescription(resourceDescription);

        DockerContainerDescription container = desc.getContainer();
        DockerImageDescription imageDesc = container.getImage();
        DockerImage image = prepareImage(imageDesc);

        String deploymentShellScriptContent = prepareDeploymentShellScript(imageDesc, desc);
        int id = persistDeploymentShellScript(deploymentShellScriptContent);
        log.info("Resource description created with id:" + id);
        return id;
    }

    private File writeStringToFile(String fileName, String fileContent) {
        File file = new File(fileName);
        boolean dirCreated = file.getParentFile().mkdirs();
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        out.println(fileContent);
        out.flush();
        out.close();
        return file;
    }

    private int persistDeploymentShellScript(String deploymentShellScriptContent) {
        Random rand = new Random();    //referred from http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
        int randomNum = rand.nextInt((1000 - 1) + 1) + 1;

        File deploymentShellScript = writeStringToFile("./tmp/"+randomNum+".sh", deploymentShellScriptContent);

        return randomNum;
    }

    private DockerImage prepareImage(DockerImageDescription image) {
        if (image.hasScript()) {
            File dockerfile = prepareDockerfile(image);
            File concreteImageFile = createImageFromDockerfile(dockerfile);
            return new DockerImage(concreteImageFile);
        } else {  //if the Image has not defined and scripts, it means, the Image can be found from the Registry
            return new DockerImage(image.getName());
        }
    }

    private File createImageFromDockerfile(File dockerfile) {
        //run "sudo docker build -t $image_name" OR https://docs.docker.com/reference/api/docker_remote_api_v1.15/#build-an-image-from-dockerfile-via-stdin
        throw new UnsupportedOperationException("This method is still not implemented. So Dockerfile based image creating is still not supported");
    }

    private String prepareDeploymentShellScript(DockerImageDescription image, DockerResourceDescription desc) {
        String vMIP = desc.getContainer().getVirtualMachine().getIp();
        String vMPort = desc.getContainer().getVirtualMachine().getPort();

        String shellScript = "#!/bin/sh \n";
        shellScript += "sudo docker run " + desc.getContainer().getName() + " -d -t " + image.getName() + "\n";
        //or FOLLOWING CURL command can be used to create a new Container.
        // curl -i -H "Accept: application/json" -H "Content-Type: application/json" -d '{"Hostname": "denis-docker-boulem", "Image": "ddweerasiri/sample-webapp-in-tomcat7"}' -X POST http://129.94.175.240:4243/containers/create

        shellScript += "curl -i -H \"Accept: application/json\" -H \"Content-Type: application/json\" -d '{\"Domainname\": \"" +
                desc.getContainer().getName() + "\", \"Image\": \"" + image.getName() +"\"}' -X POST http://" +
                vMIP + ":" + vMPort + "/containers/create" + "\n";
        //above command returns an $id, that is used to start the container
        // curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST http://129.94.175.240:4243/containers/($id)/start
        return shellScript;
    }

    private File prepareDockerfile(DockerImageDescription image) {
        DockerImageDescription baseImage = image.getBaseImage();
        String script = image.getScript();

        String baseImageName = baseImage.getName();

        String dockerFileContent = "FROM " + baseImageName + "\n" + script ;


        File dockerFile = writeStringToFile("Dockerfile", dockerFileContent);

        return dockerFile;
    }

    private DockerResourceDescription populateDockerSpecificDescription(JSONObject resourceDescription) {
        DockerResourceDescriptionFactory fac = new DockerResourceDescriptionFactory();
        //for the moment we create a dummy resource description

        DockerResourceDescription desc = fac.buildDummyDescription();
        return desc;
    }

    @Override
    public Result deploy(int resourceID) {
        String scriptFileLocation = "./tmp/" + resourceID + ".sh";
        //run the deployment script
        ProcessBuilder pb = new ProcessBuilder(scriptFileLocation);
        Map<String, String> env = pb.environment();
        pb.directory(new File("."));
        try {
            Process p = pb.start();

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));
            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                log.info(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                log.info(s);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
