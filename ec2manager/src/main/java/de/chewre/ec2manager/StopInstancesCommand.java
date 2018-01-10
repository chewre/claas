package de.chewre.ec2manager;

import java.util.Collections;
import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;

/**
 * 
 * @author C. Henning Wrede
 * @version 0.0.1
 * @category EC2
 */
public class StopInstancesCommand {

	private final AmazonEC2 ec2Client;
	private final List<String> instances;

	public StopInstancesCommand(String region, List<String> instances) {
		this.instances = Collections.unmodifiableList(instances);
		ec2Client = AmazonEC2ClientBuilder
				.standard()
				.withRegion(region)
				.build();
	}

	public StopInstancesResult execute() {
		StopInstancesRequest stopInstances = new StopInstancesRequest(instances);

		return ec2Client.stopInstances(stopInstances);
	}
}
