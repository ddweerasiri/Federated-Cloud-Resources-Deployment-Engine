package au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.util;
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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: denis
 * Generate a Linked Object Model from the JSON based resource description
 */
public class LinkedObjectModelFactory {
    private static final Logger log = LoggerFactory.getLogger(LinkedObjectModelFactory.class);

    public static JSONObject generateObjectFromFile(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        JSONObject jsonObject = null;
        jsonObject = (JSONObject) parser.parse(new FileReader(file));
        return jsonObject;
    }


}
