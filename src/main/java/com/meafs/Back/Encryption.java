package com.meafs.Back;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by meaf on 22.02.17.
 */
public interface Encryption {

    String encrypt(String str, int... key) throws IllegalArgumentException;

    String decrypt(String str, int... key) throws IllegalArgumentException;

    String brute(String str) throws IllegalArgumentException;

    static void validation(String str, String charset) throws IllegalArgumentException {
        StringBuilder invalidCharacters = new StringBuilder();
        for (char i:str.toCharArray()) {
            if (!charset.contains(i+"") && !invalidCharacters.toString().contains(i+"")){
                invalidCharacters.append(i);
            }
        }
        if (invalidCharacters.length()>0)
            throw new IllegalArgumentException("Invalid characters: \n"+invalidCharacters.toString());
    }
}
