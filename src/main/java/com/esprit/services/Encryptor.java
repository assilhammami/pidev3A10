package com.esprit.services;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryptor {

    //Advanced Encryption Standard
    //128 bit



    byte[] IV = { 0x01, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

    public String encrypt(String input, byte[] secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(secretKey,"AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }


    public String decrypt(String cipherText, byte[] secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(secretKey,"AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV));
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }


    public byte[] stringToByteArray(String keyString){

    /*public byte[] stringToByteArray(String keyString){

        String[] keyFragments = keyString.split(" ");

        byte[] key = new byte[16];
        for (int i = 0; i < keyFragments.length; i++) {
            key[i] = Byte.parseByte(keyFragments[i]);
        }
        return key;

    }

    }*/

}
