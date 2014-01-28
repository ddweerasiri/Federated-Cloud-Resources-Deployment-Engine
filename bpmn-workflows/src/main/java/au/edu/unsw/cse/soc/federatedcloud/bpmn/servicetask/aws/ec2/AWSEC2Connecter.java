package au.edu.unsw.cse.soc.federatedcloud.bpmn.servicetask.aws.ec2;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class AWSEC2Connecter implements JavaDelegate {

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		System.out.println("AWS-EC2 Deployer");
		//Build CloudResourceDescription
		//Submit it to the Deployer
		
	}

}
