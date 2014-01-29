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
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.regex.Pattern;

/**
 * User: denis
 * Util methods to manipulate Cloud resource descriptions
 */
public class DataModelUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataModelUtil.class);

    /**
     * Process an file and returns the CloudResourceDescription
     *
     * @param aFile input file to be processed (must be a JSON file)
     */
    private static CloudResourceDescription processFile(File aFile) throws Exception {
        String fileContent = FileUtils.readFileToString(aFile);
        return readJSON(fileContent);
    }

    /**
     * Build a {@code CloudResourceDescription} for a given ID of a cloud resource description
     *
     * @param cloudResourceID ID of a cloud resource description
     * @return an object of {@code CloudResourceDescription}
     * @throws Exception
     */
    public static CloudResourceDescription buildCouldResourceDescriptionFromJSON(int cloudResourceID) throws Exception {
        return searchFolder("./cloud-resource-base", Pattern.compile("(?i).*\\.json$"), cloudResourceID);
    }

    /**
     * Search for all the files in a particular path and process them one by one
     *
     * @param searchPath  file location to be searched
     * @param filePattern search criteria
     */
    private static CloudResourceDescription searchFolder(String searchPath, Pattern filePattern, int cloudResourceDescriptionID) throws Exception {
        File dir = new File(searchPath);
        System.out.println(dir.getAbsolutePath());
        for (File item : dir.listFiles()) {
            if (item.isDirectory()) {
                //recursively search subdirectories
                return searchFolder(item.getAbsolutePath(), filePattern, cloudResourceDescriptionID);
            } else if (item.isFile() && filePattern.matcher(item.getName()).matches()) {
                CloudResourceDescription description = processFile(item);
                boolean isEqual = description.isCorrectCloudResourceDescription(cloudResourceDescriptionID);
                if (isEqual) {
                    return description;
                }
            } else {
                String errorMsg = "Expected cloud resource description with \"id\": " + cloudResourceDescriptionID +
                        " was not found.";
                RuntimeException ex = new RuntimeException(errorMsg);
                logger.error(errorMsg, ex);
                throw ex;
            }
        }
        throw new RuntimeException("No files found on the specified directory:" + searchPath);
    }

    private static CloudResourceDescription readJSON(String json) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(json, CloudResourceDescription.class);
    }

    /**
     * Build a {@code CloudResourceDescription} from the JSON file
     *
     * @param file the JSON model of the cloud resource description
     * @return an object of {@code CloudResourceDescription}
     * @throws Exception
     */
    public static CloudResourceDescription buildCouldResourceDescriptionFromJSON(File file) throws Exception {
        return processFile(file);
    }
}
