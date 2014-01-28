package au.edu.unsw.cse.soc.federatedcloud.bpmn.servicetask;

import au.edu.unsw.cse.soc.federatedcloud.CloudResourceBaseDeploymentEngine;
import au.edu.unsw.cse.soc.federatedcloud.DataModelUtil;
import au.edu.unsw.cse.soc.federatedcloud.datamodel.CloudResourceDescription;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class CloudBaseJobDispatcher implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(CloudBaseJobDispatcher.class);

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
        log.info("Deployment Request received from the BPMN workflow.");

        int descriptionID = 1;
        CloudResourceDescription desc = DataModelUtil.buildCouldResourceDescriptionFromJSON(descriptionID);

        Client client = Client.create();
        WebResource webResource = client.resource("http://localhost:8080/deployment-engine-1.0-SNAPSHOT/engine/deploy");
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output = response.getEntity(String.class);

        log.info("Output from Server .... \n");
        log.info(output);
	}
}
