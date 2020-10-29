package com.timsanalytics.apps.main.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.stereotype.Service;

@Service
public class TestingService {
    public static final String AWS_ACCESS_KEY_ID = "aws.accessKeyId";
    public static final String AWS_SECRET_KEY = "aws.secretKey";

    public static void main(String[] args) {
        TestingService testingService = new TestingService();
        testingService.sendTextMessage();
    }

    public String sendTextMessage() {
//        AmazonSNS snsClient = AmazonSNSClient.builder()
//                .withRegion(Regions.US_EAST_1)
//                .withClientConfiguration("configuration")
//                    .withCredentials("credentials")
//                    .build();
//        String message = "YOUR MESSAGE";
//        String phoneNumber = "PHONE_NUMBER";  // Ex: +91XXX4374XX
//        PublishResult publishResult = sendSMSMessage(snsClient, message, phoneNumber);
//        System.out.println(publishResult); // Prints the message ID.
//        return publishResult.toString();
        return null;
    }

    public static PublishResult sendSMSMessage(AmazonSNSClient snsClient, String message, String phoneNumber) {
        return snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber));
    }
}
