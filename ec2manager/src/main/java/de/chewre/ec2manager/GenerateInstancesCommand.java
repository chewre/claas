package de.chewre.ec2manager;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;

/**
 * 
 * @author C. Henning Wrede
 * @version 0.0.1
 * @category EC2
 */
public class GenerateInstancesCommand {

    private final AmazonEC2 ec2Client;
    private final RunInstancesRequest runInstancesRequest;

    public GenerateInstancesCommand(String region/*, String imageId, String instanceType*/) {
		ec2Client = AmazonEC2ClientBuilder
				.standard()
				.withRegion(region)
				.build();

        runInstancesRequest = new RunInstancesRequest();

        throw new UnsupportedOperationException("GenerateInstancesCommand()");
        /*
        runInstancesRequest.withImageId(imageId)
                .withInstanceType(instanceType)
                .withAdditionalInfo()
                .withBlockDeviceMappings()
                .withClientToken()
                .withDisableApiTermination()
                .withEbsOptimized()
                .withIamInstanceProfile()
                .withInstanceInitiatedShutdownBehavior()
                .withKernelId()
                .withKeyName()
                .withMaxCount()
                .withMinCount()
                .withMonitoring()
                .withNetworkInterfaces()
                .withPlacement()
                .withPrivateIpAddress()
                .withRamdiskId()
                .withSecurityGroups()
                .withSubnetId()
                .withUserData()
                .withGeneralProgressListener()
                .withRequestMetricCollector();
                */
    }

    public RunInstancesResult execute() {
        return ec2Client.runInstances(runInstancesRequest);
    }
}
