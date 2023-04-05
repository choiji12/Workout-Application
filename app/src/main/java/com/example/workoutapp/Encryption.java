package com.example.workoutapp;

import java.security.MessageDigest;
import java.util.Random;

public class Encryption {
    public static String getSalt() {
        Random random = new Random();
        byte[] salt = new byte[10];

        random.nextBytes(salt);

        StringBuffer sb = new StringBuffer();

        for(int i=0; i<salt.length; i++) {
            sb.append(String.format("%02x", salt[i]));
        }

        return sb.toString();
    }

    public static String getEncrypt(String pwd, String salt) {

        byte[] salts = salt.getBytes();
        String result = "";

        byte[] temp = pwd.getBytes();
//        byte[] bytes = new byte[temp.length + salts.length];

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(temp);
            md.update(salts);

            byte[] b = md.digest();

            StringBuffer sb = new StringBuffer();

            for(int i=0; i<b.length; i++) {
                sb.append(Integer.toString((b[i] & 0xFF) + 256, 16).substring(1));
            }

            result = sb.toString();

        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }
}
