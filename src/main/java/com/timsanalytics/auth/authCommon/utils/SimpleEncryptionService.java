package com.timsanalytics.auth.authCommon.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;

@Service
public class SimpleEncryptionService {
    private final Environment environment;

    @Autowired
    public SimpleEncryptionService(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) throws Exception {
        SimpleEncryptionService simpleEncryptionService = new SimpleEncryptionService(null);

        BufferedReader br;
        System.out.println("Do you want to encrypt (e) or decrypt (d)?: ");
        br = new BufferedReader(new InputStreamReader(System.in));
        String response = br.readLine();
        if (response.equalsIgnoreCase("e")) {
            System.out.println("Enter the value to encrypt: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            String text = br.readLine();
            String cipher = simpleEncryptionService.encrypt(text);
            System.out.println("Cipher: '" + cipher + "'");
        } else {
            System.out.println("Enter the value to decrypt: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            String cipher = br.readLine();
            String text = simpleEncryptionService.decrypt(cipher);
            System.out.println("Text: '" + text + "'");
        }
    }

    private String crypt(int mode, String text) throws Exception {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        String encryptionKey = environment.getProperty("simple-encryption-key");
        SecretKey secretKey = secretKeyFactory.generateSecret(new PBEKeySpec(encryptionKey.toCharArray()));
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(mode, secretKey, new PBEParameterSpec(environment.getProperty("simple-encryption-salt").getBytes(), 20));

        switch (mode) {
            case Cipher.ENCRYPT_MODE:
                return Base64.getEncoder().encodeToString(text.getBytes());
            case Cipher.DECRYPT_MODE:
                return new String(Base64.getDecoder().decode(text));
            default:
                throw new IllegalArgumentException("Unknown Mode: " + mode);
        }
    }

    private String encrypt(String text) throws Exception {
        return crypt(Cipher.ENCRYPT_MODE, text);
    }

    public String decrypt(String cipher) throws Exception {
        return crypt(Cipher.DECRYPT_MODE, cipher);
    }

}
