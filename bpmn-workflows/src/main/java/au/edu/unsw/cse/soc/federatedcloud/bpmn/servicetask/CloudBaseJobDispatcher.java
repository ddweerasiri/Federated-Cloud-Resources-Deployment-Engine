package au.edu.unsw.cse.soc.federatedcloud.bpmn.servicetask;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URLEncoder;

public class CloudBaseJobDispatcher implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(CloudBaseJobDispatcher.class);
    private org.activiti.engine.delegate.Expression resourceID;
    private org.activiti.engine.delegate.Expression resourceDescription;

    @Override
    public void execute(DelegateExecution execution) throws Exception {



        Client client = Client.create();
        WebResource webResource = client.resource("http://localhost:8080/deployment-engine-1.0-SNAPSHOT/engine/deploy");


        String resourceDescriptionID = (String) resourceID.getValue(execution);
        log.info("Deployment Request received from the BPMN workflow for resource id: " + resourceDescriptionID);

        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add("description_id", resourceDescriptionID);
        try {
            String resourceDescription1 = (String) resourceDescription.getValue(execution);
            queryParams.add("description_json", URLEncoder.encode(resourceDescription1, "UTF-8"));
        } catch (NullPointerException npe) {
            log.warn("Resource Description is empty");
        }

        ClientResponse response = webResource.queryParams(queryParams).accept(MediaType.TEXT_PLAIN).post(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output = response.getEntity(String.class);

        log.info("Output from Server .... \n");
        log.info(output);
    }

}
