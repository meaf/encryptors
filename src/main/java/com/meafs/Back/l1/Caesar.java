package com.meafs.Back.l1;

import com.meafs.Back.Charset;
import com.meafs.Back.Encryption;

/**
 * Created by meaf on 22.02.17.
 */
public class Caesar implements Encryption{
    private Charset charset;

    public Caesar(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = new Charset(charset);
    }

    @Override
    public String encrypt(String str, int... keys) throws IllegalArgumentException{
        Encryption.validation(str, charset.getCharset());
        int key = keys[0];
        Encryption.validation(str, charset.getCharset());
        char[] c = charset.toCharArray();
        char[] enc = new char[str.length()];

        for (int i = 0; i<str.length(); i++){
            int pos = charset.sameCharPosition(str, i);
            enc[i]=c[(pos+key+c.length)%c.length];
        }

        return new String(enc);
    }

    @Override
    public String decrypt(String str, int... keys) throws IllegalArgumentException{
        int key = keys[0];
        return encrypt(str, -key);
    }

    @Override
    public String brute (String str) throws IllegalArgumentException {
        StringBuilder bruteRes = new StringBuilder();
        for (int i = 0; i < charset.length(); i++) {
            bruteRes.append("\n trying key " + i + ":\n");
            bruteRes.append(encrypt(str, i).concat("\n"));
        }
        System.out.println(bruteRes);
        return bruteRes.toString();
    }

}
