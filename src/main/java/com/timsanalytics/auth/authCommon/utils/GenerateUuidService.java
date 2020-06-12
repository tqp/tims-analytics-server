package com.timsanalytics.auth.authCommon.utils;

import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class GenerateUuidService {

    public static void main(String[] args) {
        GenerateUuidService generateUuidService = new GenerateUuidService();
        System.out.println(generateUuidService.GenerateUuid());

    }

    public String GenerateUuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
