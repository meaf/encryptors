package com.meafs.Back;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by meaf on 22.02.17.
 */
public interface Encryption {

    String encrypt(String str, int key) throws IllegalArgumentException;

    String decrypt(String str, int key) throws IllegalArgumentException;

    String brute(String str) throws IllegalArgumentException;

    static String readFromFile(String fileName){
        File file = new File(fileName);
        StringBuilder sb=  new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(
                    (new FileReader(
                            file.getAbsoluteFile()
                    ))
            );
            String s;
            while (((s = in.readLine()) != null)){
                sb.append(s);
            }
            in.close();
        }
        catch(Exception e){e.printStackTrace();}
        return sb.toString();
    }

    static void validation(String str, String charset) throws IllegalArgumentException {
        StringBuilder invalidCharacters = new StringBuilder();
        for (char i:str.toCharArray()) {
            if (!charset.contains(i+"") && !invalidCharacters.toString().contains(i+"")){
                invalidCharacters.append(i);
            }
        }

        System.out.println(invalidCharacters.toString());
        System.out.println("".length());
        if (invalidCharacters.length()>0)
            throw new IllegalArgumentException("Invalid characters: \n"+invalidCharacters.toString());
    }
}
