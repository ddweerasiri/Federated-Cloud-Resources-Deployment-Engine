package au.edu.unsw.cse.soc.federatedcloud.user;

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

import au.edu.unsw.cse.soc.federatedcloud.user.app.datamodel.App;
import au.edu.unsw.cse.soc.federatedcloud.user.app.datamodel.Resource;
import au.edu.unsw.cse.soc.federatedcloud.user.app.datamodel.ResourceNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class CloudBaseUserInterface {
    private static final Logger log = LoggerFactory.getLogger(CloudBaseUserInterface.class);

    public static void main(String[] args) {
        CloudBaseUserInterface interfaze = new CloudBaseUserInterface();

        String command;
        try {
            command = args[0];
            if ("init".equals(command)) {
                String appName = args[1];
                try {
                    String appAttributes = args[2];
                    interfaze.initCloudBase(appName,appAttributes);
                }
                catch (ArrayIndexOutOfBoundsException aie) {
                    log.warn("No attributes specified.");
                    interfaze.initCloudBase(appName);
                }

            } else if ("deploy".equals(command)) {
                String resourceName = args[1];
                String resourceMetaData = args[2];
                interfaze.deployResource(resourceName, resourceMetaData);
            } else if ("undeploy".equals(command)) {
                String resourceName = args[1];
                interfaze.undeployResource(resourceName);
            } else {
                System.err.println("Command: " + command + " is not specified.");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("please specify a command.");
            log.error(ex.getMessage(), ex);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    /**
     * Init an App.
     * Sample command: init aws-s3
     * @param appName
     */
    private void initCloudBase(String appName) {
        initCloudBase(appName, "{}");
    }

    /**
     * Init an App.
     * Sample command: init aws-s3
     * @param appName
     * @param appAttributes
     */
    private void initCloudBase(String appName, String appAttributes) {
        //check if app.json exist in the current directory
        File jsonFile = new File("./" + Constants.FILE_NAME);
        //if app.json does not exist
        if(!jsonFile.exists()) {
            //create an app.json
            try {
                boolean isCreated = jsonFile.createNewFile();

                if (isCreated) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_FILE_NAME);
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(inputStream, writer);
                    String fileContent = writer.toString();

                    App app = gson.fromJson(fileContent, App.class);

                    //Set name
                    app.setName(appName);

                    //set attributes
                    Map<String,String> map=new HashMap<String,String>();
                    app.setAttributes((Map<String,String>) gson.fromJson(appAttributes, map.getClass()));

                    transformAppToFile(app, jsonFile);

                    System.out.println("App:" +app.getName() + " was created");
                } else {
                    System.err.println("File was not created");
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            System.out.println("File:" + jsonFile.getAbsolutePath() + " already exists.");
        }
    }

    /**
     * Deploy a resource and update the app.json
     * Sample command : "deploy aws-s3 {\"bucket-name\":\"ddw-testbucket\"}"
     * @param resourceName
     * @param resourceMetadata
     * @throws IOException
     */
    private void deployResource(String resourceName, String resourceMetadata) throws IOException {
        //check if app.json exist in the current directory
        File jsonFile = new File("./" + Constants.FILE_NAME);
        //if app.json exist
        if(jsonFile.exists()) {
            //check if the resource is already exists
            try {
                App app = transformJsonFileToApp(jsonFile);
                Resource resourceToBeUploaded = app.getResourceByName(resourceName);

                //do nothing, send an message to user
                System.err.println("Resource is already exists.");
            }  catch (ResourceNotFoundException e) {
                //send an deployment request
                int resourceID = DeploymentEngineClient.deploy(resourceName, resourceMetadata);

                //add an new resource entry in the app.json
                App app = transformJsonFileToApp(jsonFile);
                Resource newResource = new Resource();
                newResource.setName(resourceName);
                newResource.setAttributes(generateAttributesFromJson(resourceMetadata));

                //store the resource id with in that new entry
                newResource.getAttributes().put("id", String.valueOf(resourceID));

                app.getResources().add(newResource);

                //persist the file
                transformAppToFile(app, jsonFile);
                System.out.println("Resource:" + newResource.getName() + " was deployed.");
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            log.error("Please initialize an App first using \"init\" command.");
        }
    }

    /**
     * Undeploy an resource from the App
     * Sample command : undeploy aws-s3
     * @param resourceName
     */
    private void undeployResource(String resourceName) {
        //check if app.json exist in the current directory
        File jsonFile = new File("./" + Constants.FILE_NAME);
        //if app.json exist
        if(jsonFile.exists()) {
            //check if the resource is already exists
            try {
                App app = transformJsonFileToApp(jsonFile);
                Resource resourceToBeUndeployed = app.getResourceByName(resourceName);
                //get the resource id
                int resourceId = resourceToBeUndeployed.getID();

                //send an request to undeploy the resource
                DeploymentEngineClient.undeploy(resourceId);

                //remove the resource entry from app.json
                app.removeResourceFromApp(resourceToBeUndeployed);
                transformAppToFile(app, jsonFile);

                System.out.println("Resource:" + resourceToBeUndeployed.getName() + " undeployed.");
            } catch (ResourceNotFoundException e) {
                //do nothing, send an message to user
                System.err.println(e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (Exception e) {
                System.err.println("FATAL" + e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            //do nothing, send an message to user
        }

    }

    private App transformJsonFileToApp(File jsonFile) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String fileContent = FileUtils.readFileToString(jsonFile);

        App app = gson.fromJson(fileContent, App.class);

        return app;
    }

    private File transformAppToFile(App app, File jsonFile) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String appJSON = gson.toJson(app);

        FileUtils.writeStringToFile(jsonFile, appJSON);

        return jsonFile;
    }

    private Map<String,String> generateAttributesFromJson(String resourceMetadata) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String,String> map=new HashMap<String,String>();
        map = (Map<String,String>) gson.fromJson(resourceMetadata, map.getClass());
        return map;
    }


}
