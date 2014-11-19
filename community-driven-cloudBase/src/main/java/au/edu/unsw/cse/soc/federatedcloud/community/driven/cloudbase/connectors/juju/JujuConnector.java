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

import au.edu.unsw.cse.soc.federatedcloud.community.driven.cloudbase.connectors.Connector;
import au.edu.unsw.cse.soc.federatedcloud.community.driven.cloudbase.connectors.Result;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipUtil;

import java.io.*;
import java.util.Random;

/**
 * User: denis
 * Juju Connector
 */
public class JujuConnector implements Connector {
    private static final Logger log = LoggerFactory.getLogger(JujuConnector.class);

    @Override
    public int init(JSONObject resourceDescription) {
        JujuResourceDescription desc = populateJujuSpecificDescription(resourceDescription);

        JujuService service = desc.getService();


        //prepare charm
        //prepare environment
        //return the unique key
        File charmDir = prepareCharm(desc);
        File deploymentShellScript = prepareCharmDeploymentShellScript(desc);
        int id = packageFinalResourceDescription(charmDir, deploymentShellScript);
        log.info("Resource description created with id:" + id);
        return id;
    }

    private int packageFinalResourceDescription(File charmDir, File deploymentShellScript) {
        Random rand = new Random();    //referred from http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
        int randomNum = rand.nextInt((1000 - 1) + 1) + 1;

        File outputFile = new File(charmDir.getParent() + File.separator + randomNum + ".zip");
        ZipUtil.pack(charmDir, outputFile);
        return randomNum;
    }

    private File prepareCharmDeploymentShellScript(JujuResourceDescription desc) {
        //TODO: include the real implementation and remove dummyDeplScriptGenerator()
        File deplScript = dummyDeplScriptGenerator(desc);

        return deplScript;
    }

    private File dummyDeplScriptGenerator(JujuResourceDescription desc) {
        String scriptContent = "juju deploy " + desc.getService().getCharm().getName();

        File scriptFile = writeStringToFile("./tmp/sample-tomcat-charm-archive/run.sh", scriptContent);
        return scriptFile;
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

    private File prepareCharm(JujuResourceDescription desc) {
        //File metadatayaml = prepareMetadataYaml(desc);
        //File configFile = prepareConfigFile(desc);
        //File hooksDir = prepareHooks(desc);

        //File charmDir = perpareCharmDir(metadatayaml, configFile, hooksDir);

        //TODO: include the real implementation and remove dummyCharmGenerator()
        File charmDir = dummyCharmGenerator();

        return charmDir;
    }

    private File dummyCharmGenerator() {
        File INPUT_ZIP_FILE = new File("./community-driven-cloudBase/src/main/resources/juju/sample1/sample-tomcat-charm-archive.zip");
        File OUTPUT_FOLDER = new File("./tmp/sample-tomcat-charm-archive");

        ZipUtil.unpack(INPUT_ZIP_FILE, OUTPUT_FOLDER);

        return OUTPUT_FOLDER;
    }

    private JujuResourceDescription populateJujuSpecificDescription(JSONObject resourceDescription) {
        JujuResourceDescriptionFactory fac = new JujuResourceDescriptionFactory();
        //for the moment we create a dummy resource description

        JujuResourceDescription desc = fac.buildDummyDescription();
        return desc;
    }

    @Override
    public Result deploy(int resourceID) {
        //extract the package
        //copy the charm to the relevant place
        //run the deploymentShellScript
        return null;
    }
}
