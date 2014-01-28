package au.edu.unsw.cse.soc.federatedcloud.bpmn.servicetask.aws.ec2;

import au.edu.unsw.cse.soc.federatedcloud.CloudResourceBaseDeploymentEngine;
import au.edu.unsw.cse.soc.federatedcloud.DataModelUtil;
import au.edu.unsw.cse.soc.federatedcloud.datamodel.CloudResourceDescription;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class AWSEC2Connecter implements JavaDelegate {

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		System.out.println("AWS-EC2 Deployer");

        int descriptionID = 1;                 //This should be point to the correct description
        CloudResourceDescription desc = DataModelUtil.buildCouldResourceDescriptionFromJSON(descriptionID);

        //CloudResourceBaseDeploymentEngine engine = new CloudResourceBaseDeploymentEngine();
        //engine.deployCloudResourceDescription(desc);


	}

}
