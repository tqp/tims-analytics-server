package com.timsanalytics.utils;

import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class GenerateUuidService {

    public static void main(String[] args) {
        for(int i=1; i<=5; i++) {
            GenerateUuidService generateUuidService = new GenerateUuidService();
            System.out.println(generateUuidService.GenerateUuid());
        }
    }

    public String GenerateUuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
