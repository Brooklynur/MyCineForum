package com.example.lore_depa.provalistview;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by SSBS_SELECT on 24/11/2017.
 */

public class IoCrypto {

    private final String key;

    public IoCrypto() {
        key = "MyCineForum";
    }

    public String encrypt (String value) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = generateKey(key);
        Cipher chiper = Cipher.getInstance("AES");
        chiper.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] returnValue = chiper.doFinal(value.getBytes());
        return Base64.encodeToString(returnValue, Base64.DEFAULT);
    }

    public String decypt (String value) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = generateKey(key);
        Cipher chiper = Cipher.getInstance("AES");
        chiper.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decodedValue = Base64.decode(value, Base64.DEFAULT);
        byte[] decValue = chiper.doFinal(decodedValue);
        return new String(decValue);
    }


    public SecretKeySpec generateKey (String key)throws UnsupportedEncodingException, NoSuchAlgorithmException{

        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = key.getBytes("UTF-8");
        messageDigest.update(bytes, 0 , bytes.length);
        byte[] digest = messageDigest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(digest, "AES");
        return secretKeySpec;

    }

}
