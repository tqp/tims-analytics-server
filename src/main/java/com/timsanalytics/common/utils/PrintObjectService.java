package com.timsanalytics.common.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PrintObjectService {

    public void PrintObject(String title, Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(title + ":\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
