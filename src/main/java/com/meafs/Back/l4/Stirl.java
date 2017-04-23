package com.meafs.Back.l4;

import com.meafs.Back.Charset;
import com.meafs.Back.Encryption;

import javax.el.MethodNotFoundException;
import java.util.Random;

/**
 * Created by meaf on 09.04.17.
 */
public class Stirl implements Encryption {

    private Charset charset;
    private Text text;
    private long seed;
    private Random random;


    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Stirl(Charset charset){
        this(new Text(charset.getCharset()), charset);
    }

    public Stirl(Text text, Charset charset){
        this.text = text;
        this.charset = charset;
    }

    @Override
    public String encrypt(String str, int... key) throws IllegalArgumentException {
        StringBuilder encryptedMessage = new StringBuilder();



//
//        text.setSeed(key[0]);
//        text.generateValidIndex();
//
//        try {
//            for (int i = 0; i < str.length(); i++) {
//                encryptedMessage.append(text.charAt(key[i]));
//            }
//        }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
//
//        return null;

    }

    @Override
    public String decrypt(String str, int... key) throws IllegalArgumentException {
        StringBuilder encryptedMessage = new StringBuilder();
        try {
            for (int i = 0; i < str.length(); i++) {
                encryptedMessage.append(text.charAt(key[i]));
            }
        }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}

        return null;
    }

    @Override
    public String brute(String str) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    public void setText(String newText) {
        text.setText(newText);
    }
}
