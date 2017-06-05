package com.meafs.Back.l5;

import java.util.Base64;


import javax.crypto.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import com.meafs.Back.Encryption;

/**
 * Created by meaf on 05.06.17.
 */
public class DES implements Encryption {

        private Cipher encryptCipher;
        private Cipher decryptCipher;


    public DES(SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
            this.encryptCipher = Cipher.getInstance("DES");
            this.decryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
    }

    @Override
    public String encrypt(String input, int... keys) {
        try {
            byte[] inputBytes = input.getBytes("UTF-8");
            byte[] enc = encryptCipher.doFinal(inputBytes);
            return Base64.getEncoder().encodeToString(enc);
//                return Base64.encodeToString(enc,Base64.DEFAULT);
        } catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String input, int... keys) {
        try {
            byte[] dec = Base64.getDecoder().decode(input.getBytes());
            byte[] utf8 = decryptCipher.doFinal(dec);
            return new String(utf8,"UTF8");
        } catch (IOException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String brute(String str) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    public void setKey(SecretKey secretKey){
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
        }catch(Exception e){e.printStackTrace();}
    }
}



