package com.meafs.Back.l2;

import com.meafs.Back.Charset;
import com.meafs.Back.Encryption;

/**
 * Created by meaf on 25.02.17.
 */
public class Trithemius implements Encryption{

    private Charset charset;

    public Trithemius(Charset charset) {
        this.charset = charset;
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
        for (int key, t, pos, i = 0; i < str.length(); i++) {
            t=i+1;
            key = (keys[0]*t*t + keys[1]*t+keys[2])%c.length; // At^2+Bt+C
            pos = charset.sameCharPosition(str, i);
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
        for (int key, pos, i = 0; i < str.length(); i++) {
            key = charset.sameCharPosition(str, i%passPhrase.length())+1;
            pos = charset.sameCharPosition(str, i);
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
        for (int key, i = 0; i < passPhrase.length(); i++) {
            key = (2*charset.length()-charset.sameCharPosition(passPhrase, i)-2)%charset.length();
            invertedPassPhrase.append(charset.charAt(key));
        }

        return encrypt(str, invertedPassPhrase.toString());
    }

    @Override
    public String brute(String str) throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
