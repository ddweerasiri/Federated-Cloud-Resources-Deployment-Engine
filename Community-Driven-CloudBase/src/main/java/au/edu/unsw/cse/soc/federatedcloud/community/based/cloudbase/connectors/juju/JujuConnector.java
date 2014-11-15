package au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.connectors.juju;
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

import au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.connectors.Connector;
import au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.connectors.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: denis
 * Juju Connector
 */
public class JujuConnector implements Connector {
    private static final Logger log = LoggerFactory.getLogger(JujuConnector.class);

    @Override
    public int init() {
        return 0;
    }

    @Override
    public Result deploy(int resourceID) {
        return null;
    }
}
