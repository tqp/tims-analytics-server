package com.timsanalytics.main.services;

import com.timsanalytics.main.beans.KeyValueString;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticsService {

    public KeyValueString exampleGetRequest() {
        return new KeyValueString("Diagnostic", "exampleGetRequest");
    }

    public KeyValueString examplePostRequest() {
        return new KeyValueString("Diagnostic", "examplePostRequest");
    }

    public KeyValueString examplePostRequestWithPathVariable() {
        return new KeyValueString("Diagnostic", "examplePostRequestWithPathVariable");
    }

    public KeyValueString examplePostRequestWithRequestBody() {
        return new KeyValueString("Diagnostic", "examplePostRequestWithRequestBody");
    }
}
