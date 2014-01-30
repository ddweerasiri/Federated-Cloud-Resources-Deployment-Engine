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

import java.util.Arrays;
import java.util.HashMap;

/**
 * User: denis
 * Test class to deploy json descriptions in file system to the DB
 */
public class DBClient {
    private static final Logger log = LoggerFactory.getLogger(DBClient.class);

    public static void main(String[] args) throws RqlDriverException {
        RqlConnection r = RqlConnection.connect("localhost", 28015);
        // Any use of db set the default db
        r.db("test1").table_create("characters");

        RqlTopLevelQuery.DbList list = r.db_list();
        RqlCursor cursor = r.run(list);

        // A simple Insert
        r.db("test1").table("characters").insert(Arrays.asList(
                new HashMap() {{
                    put("name", "Worf");
                    put("show", "Star Trek TNG");
                }},
                new HashMap() {{
                    put("name", "Data");
                    put("show", "Star Trek TNG");
                }},
                new HashMap() {{
                    put("name", "William Adama");
                    put("show", "Battlestar Galactica");
                }},
                new HashMap() {{
                    put("name", "Homer Simpson");
                    put("show", "The Simpsons");
                }}
        ));

        // Then a Simple Query
        r.table("tv_shows").filter(new HashMap() {{
            put("name", "Star Trek TNG");
        }});
        // Returns Array(HashMap( ("name","Worf") , ("show","Star Trek TNG") ),HashMap( ("name","Data") , ("show","Star Trek TNG") ))

        r.close();
    }
}
