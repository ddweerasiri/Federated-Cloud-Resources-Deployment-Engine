package cloudRessourceBase;
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

import com.dkhenry.RethinkDB.RqlConnection;
import com.dkhenry.RethinkDB.RqlCursor;
import com.dkhenry.RethinkDB.RqlTopLevelQuery;
import com.dkhenry.RethinkDB.errors.RqlDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * User: denis
 * Test class to deploy json descriptions in file system to the DB
 */
public class DBClient {
    private static final Logger log = LoggerFactory.getLogger(DBClient.class);

    public static void main(String[] args) throws RqlDriverException, IOException {
        RqlConnection r = RqlConnection.connect("localhost", 28015);

        //r.run(r.db_create("cloudResourceBase"));
        //r.run(r.db("cloudResourceBase").table_create("cloudResourceDescriptions"));
        //r.run(r.db("cloudResourceBase").table_create("mappingRules"));

        insertCloudResourceDescriptions(r);
        insertMappingRules(r);


        r.close();

    }

    private static void insertMappingRules(RqlConnection r) throws IOException, RqlDriverException {
        File dir = new File("/Users/denis/Dropbox/Documents/UNSW/Projects/github/Federated-Cloud-Resources-Deployment-Engine/cloud-resource-base/rules");
        instertFilesToDB(dir, r);
    }

    private static void insertCloudResourceDescriptions(RqlConnection r) throws RqlDriverException, IOException {
        File dir = new File("/Users/denis/Dropbox/Documents/UNSW/Projects/github/Federated-Cloud-Resources-Deployment-Engine/cloud-resource-base/cloud-resource-descriptions");
        instertFilesToDB(dir, r);
    }

    private static void instertFilesToDB(File directory, RqlConnection r) throws IOException, RqlDriverException {
        Pattern filePattern = Pattern.compile("(?i).*\\.json$");
        for (File item : directory.listFiles()) {
            if (item.isFile() && filePattern.matcher(item.getName()).matches()) {
                String json = DataUtil.fileToString(item);
                HashMap map = (HashMap) DataUtil.convertJSONToMap(json);
                r.run(r.db("cloudResourceBase").table("cloudResourceDescriptions").insert(map));
            }
        }
    }
}
