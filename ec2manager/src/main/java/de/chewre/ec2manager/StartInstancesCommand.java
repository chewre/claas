package de.chewre.ec2manager;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.InstanceStatus;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesResult;

/**
 * 
 * @author C. Henning Wrede
 * @version 0.0.1
 * @category EC2
 */
public class StartInstancesCommand {

	private static final Logger LOGGER = LoggerFactory.getLogger(StartInstancesCommand.class);

	private final AmazonEC2 ec2Client;
	private final List<String> instances;

	public StartInstancesCommand(String region, List<String> instances) {
		this.instances = instances;
		ec2Client = AmazonEC2ClientBuilder
					.standard()
					.withRegion(region)
					.build();
	}

	public StartInstancesResult execute() {
		StartInstancesRequest startRequest = new StartInstancesRequest(instances);

		return ec2Client.startInstances(startRequest);
	}

	public void waitUntilInstanceIsRunning(int sleepTime) {
        DescribeInstanceStatusRequest statusRequest = new DescribeInstanceStatusRequest().withInstanceIds(instances);
        DescribeInstanceStatusResult statusResult = ec2Client.describeInstanceStatus(statusRequest);
        List<InstanceStatus> instanceStates = statusResult.getInstanceStatuses();

        while (instanceStates.size() < instances.size()) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ie) {
                LOGGER.debug(ExceptionUtils.getStackTrace(ie));
                Thread.currentThread().interrupt();
            }

            statusResult = ec2Client.describeInstanceStatus(statusRequest);
            instanceStates = statusResult.getInstanceStatuses();
        }
	}
}
