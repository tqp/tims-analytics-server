package com.timsanalytics.auth.authCommon.services;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.common.beans.KeyValueString;
import org.springframework.stereotype.Service;

@Service
public class AuthCommonTestService {
//    private SampleThingService sampleThingService;

    public KeyValue testMethod() {
        System.out.println("TestService -> testMethod: ");
        return new KeyValue("key1", "value1");
    }

    public KeyValueString exampleGetRequest() {
        return new KeyValueString("Test", "exampleGetRequest");
    }

    public KeyValueString examplePostRequest() {
        return new KeyValueString("Test", "examplePostRequest");
    }

    public KeyValueString examplePostRequestWithPathVariable() {
        return new KeyValueString("Test", "examplePostRequestWithPathVariable");
    }

    public KeyValueString examplePostRequestWithRequestBody() {
        return new KeyValueString("Test", "examplePostRequestWithRequestBody");
    }
}
