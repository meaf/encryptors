package com.meafs.Back.l2;

import com.meafs.Back.Charset;
import com.meafs.Back.Encryption;

/**
 * Created by meaf on 25.02.17.
 */
public class Trithemius implements Encryption{

    private Charset charset;

    public Trithemius(String charset) {
        this.charset = new Charset(charset);
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = new Charset(charset);
    }

    @Override
    public String encrypt(String str, int... keys) throws IllegalArgumentException {
        Encryption.validation(str, charset.getCharset());
        char[] c = charset.toCharArray();
        StringBuilder enc = new StringBuilder();
        int key, t, pos;
        for (int i = 0; i < str.length(); i++) {
            t=i+1;
            key = (keys[0]*t*t + keys[1]*t+keys[2])%c.length;
            pos = charset.indexOf(str.charAt(i));
            enc.append(c[(pos+key+c.length)%c.length]);
        }
        return enc.toString();
    }

    public String encrypt(String str, String passPhrase) throws IllegalArgumentException{
        if (passPhrase.isEmpty())
                throw new IllegalArgumentException("passphrase is empty");
        Encryption.validation(passPhrase, charset.getCharset());
        StringBuilder enc = new StringBuilder();
        char[] c = charset.toCharArray();
        int key, pos;
        for (int i = 0; i < str.length(); i++) {
            key = charset.indexOf(passPhrase.charAt(i%passPhrase.length()))+1;
            pos = charset.indexOf(str.charAt(i));
            enc.append(c[(pos+key+c.length)%c.length]);
        }
        return enc.toString();
    }

    @Override
    public String decrypt(String str, int... keys) throws IllegalArgumentException {
        return encrypt(str, -keys[0], -keys[1], -keys[2]);
    }

    public String decrypt(String str, String passPhrase) throws IllegalArgumentException {
        StringBuilder invertedPassPhrase = new StringBuilder();
        int key;
        for (int i = 0; i < passPhrase.length(); i++) {
            key = (2*charset.length()-charset.indexOf(passPhrase.charAt(i))-2)%charset.length();
            invertedPassPhrase.append(charset.charAt(key));
        }

        return encrypt(str, invertedPassPhrase.toString());
    }

    @Override
    public String brute(String str) throws IllegalArgumentException {
        return "N/A";
    }
}
