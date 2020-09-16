package com.timsanalytics.testing;

import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.jar.services.MeowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final MeowService meowService;

    @Autowired
    public TestService(MeowService meowService) {
        this.meowService = meowService;
    }

    public KeyValue getTest() {
        this.meowService.Meow();
        return new KeyValue("result", "success");
    }
}
