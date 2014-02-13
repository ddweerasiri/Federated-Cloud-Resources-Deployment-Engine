package au.edu.unsw.cse.soc.federatedcloud.orchestrator;
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

import au.edu.unsw.cse.soc.federatedcloud.orchestrator.datamodel.workflow.OrchestratorWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * User: denis
 * Test class to start the orchestrator engine and event generator
 */
public class TestClass {
    private static final Logger log = LoggerFactory.getLogger(TestClass.class);

    public static void main(String[] args) throws IOException {
        OrchestratorEngine engine = OrchestrationEngineHolder.getInstance().getOrchestratorEngine();
        engine.startOrchestrationEngine();

        OrchestratorWorkflow workflow = engine.buildOrchestratorWorkflow(new File("orchestration-engine/src/main/resources/sample-orchestration-workflow.json"));
        engine.deployOrchestratorWorkflow(workflow);

        engine.startEventGenerator();
    }
}
