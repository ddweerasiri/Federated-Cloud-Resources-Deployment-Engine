package au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.cli;
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
import org.apache.commons.cli.*;

/**
 * User: denis
 * TODO: Include the class description here
 */
public class Cli {
    private static final Logger log = LoggerFactory.getLogger(Cli.class);
    private String[] args = null;
    private Options options = new Options();

    public Cli(String[] args) {
        this.args = args;
        options.addOption("init", true, "init a resource description.");
        options.addOption("tool", true, "specify the native orchestration tool.");
        options.addOption("h", "help", false, "show help.");



    }

    public void parse() {
        String resourceDescription;
        String tool = null;

        CommandLineParser parser = new BasicParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);


            if (cmd.hasOption("init")) {
                resourceDescription = cmd.getOptionValue("init");
                if (resourceDescription == null) {
                    log.error("argument value cannot be empty for:" + "init");
                }
                log.info("Init: value: " + resourceDescription);

            }
            if (cmd.hasOption("tool")) {
                tool = cmd.getOptionValue("tool");
                if (tool == null) {
                    log.error("argument value cannot be empty for:" + "tool");
                }
                log.info("Tool: value: " + tool);
            }
            if (cmd.hasOption("h")) {
                help();
            }

        } catch (ParseException e) {
            log.error("Failed to parse command line properties", e);
            help();
        }

        if (("docker").equals(tool.toLowerCase())) {
            au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.connectors.docker.TestClass.main(null);
        } else if (("juju").equals(tool.toLowerCase())) {
            au.edu.unsw.cse.soc.federatedcloud.community.based.cloudbase.connectors.juju.TestClass.main(null);
        }


    }

    private void help() {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();

        formater.printHelp("Main", options);
        System.exit(0);
    }
}
