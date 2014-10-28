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
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.SignatureException;
import java.util.Properties;

/**
 * User: denis
 * Test class to start the orchestrator engine and event generator
 */
public class TestClass {
    private static final Logger log = LoggerFactory.getLogger(TestClass.class);


    /*public static void main(String[] args) throws IOException {
        OrchestratorEngine engine = OrchestrationEngineHolder.getInstance().getOrchestratorEngine();
        engine.startOrchestrationEngine();

        OrchestratorWorkflow workflow = engine.buildOrchestratorWorkflow(new File("orchestration-engine/src/main/resources/sample-orchestration-workflow.json"));
        engine.deployOrchestratorWorkflow(workflow);

        engine.startEventGenerator();
    }*/

    public static void main(String[] args) throws Exception {
        TestClass clazz = new TestClass();
        System.out.println(clazz.putBucket("Tue, 25 Feb 2014 07:20:23 +0000"));
    }

    public String putBucket(String date) throws Exception {
        //Reading the credentials
        String AWS_ACCESS_KEY = "";
        String AWS_SECRET_KEY = "";

        String toSign = "";
        /*
        toSign = "PUT" + "\n" +
                ContentMD5 + "\n" +
                ContentType + "\n" +
                date + "\n" +
                CanonicalizedAmzHeaders +
                CanonicalizedResource;
*/

        String signed = calculateRFC2104HMAC(toSign, AWS_SECRET_KEY);

        return "AWS " + AWS_ACCESS_KEY + ":" + signed;
    }

    /**
     * Computes RFC 2104-compliant HMAC signature.
     * * @param data
     * The data to be signed.
     *
     * @param key The signing key.
     * @return The Base64-encoded RFC 2104-compliant HMAC signature.
     * @throws java.security.SignatureException
     *          when signature generation fails
     */
    public String calculateRFC2104HMAC(String data, String key)
            throws java.security.SignatureException {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String result;
        try {

            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
            BASE64Encoder encoder = new BASE64Encoder();
            result = encoder.encode(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}

