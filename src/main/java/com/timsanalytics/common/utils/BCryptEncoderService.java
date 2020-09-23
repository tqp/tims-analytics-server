package com.timsanalytics.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class BCryptEncoderService {

    public static void main(String[] args) throws Exception {
        BCryptEncoderService bCryptEncoderService = new BCryptEncoderService();

        BufferedReader br;
        System.out.println("Do you want to encode (e) or verify (v)?: ");
        br = new BufferedReader(new InputStreamReader(System.in));
        String response = br.readLine();
        if (response.equalsIgnoreCase("e")) {
            System.out.println("Enter the value to encrypt: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            String text = br.readLine();
            String cipher = bCryptEncoderService.encode(text);
            System.out.println("Cipher: " + cipher);
        } else {
            System.out.println("Enter the decoded text: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            String decoded = br.readLine();

            System.out.println("Enter the encoded text: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            String encoded = br.readLine();

            Boolean result = bCryptEncoderService.verify(decoded, encoded);
            System.out.println("Matches: " + result);
        }
    }

    public String encode(String text) {
        //System.out.println("Encoding: " + text);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(text);
    }

    public Boolean verify(String text, String encoded) {
        //System.out.println("Verify: " + text + " = " + encoded);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(text, encoded);
    }
}
