package com.meafs.Back.l3;

import com.meafs.Back.Charset;
import com.meafs.Back.Encryption;

/**
 * Created by meaf on 15.03.17.
 */
public class Gamma implements Encryption {
    private Charset charset;

    public Gamma(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String encrypt(String str, int... key) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();

        Encryption.validation(str, charset.getCharset());
        for (int xorResult, i = 0; i < str.length(); i++)
        {
            xorResult = (key[i]^charset.sameCharPosition(str, i));
            sb.append(charset.charAt(xorResult));
        }

        return sb.toString();
    }

    @Override
    public String decrypt(String str, int... key) throws IllegalArgumentException {
        return encrypt(str, key);
    }

    @Override
    public String brute(String str) throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
