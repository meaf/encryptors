package com.meafs.Back.l4;

import com.meafs.Back.Charset;
import com.meafs.Back.Encryption;
import com.vaadin.sass.internal.util.StringUtil;

import javax.el.MethodNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by meaf on 09.04.17.
 */
public class Stirl implements Encryption {

    private String text;
    private Random random;


    public String getText() {
        return text;
    }

    public void setText(String charset) {
        this.text = charset;
    }

    public Stirl(){
        this("123\n456\n789\n0 ");
    }

    public Stirl(String text){this(text, 0);}

    public Stirl(String text, int seed){
        random = new Random(0L);
        this.text = text;
    }

    @Override
    public String encrypt(String str, int... key) throws IllegalArgumentException {

        Encryption.validation(str, text);
        setSeed(key[0]);
        StringBuilder encrypted = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            int splitPoint;
            int CC, SS;
            int offset;
            do {
                do {
                    do {
                        splitPoint = random.nextInt(text.length());
                        offset = text.indexOf(str.charAt(i), splitPoint);
                    } while (offset == -1);
                    String prefix = text.substring(0, offset);
                    SS = org.springframework.util.StringUtils.countOccurrencesOf(prefix, "\n")+1;
                } while (SS > 100);
                int lineStart = text.substring(0, offset).lastIndexOf("\n");
                CC = offset - lineStart;
            }while (CC > 100);


            encrypted.append(String.format("%1$02d"+"%2$02d ", CC, SS));
        }
        return encrypted.toString();
    }

    public void setSeed(int seed){
        random.setSeed((long) seed);
    }

    @Override
    public String decrypt(String str, int... key) throws IllegalArgumentException, IndexOutOfBoundsException {
        StringBuilder decrypted = new StringBuilder();
        try {
            Arrays.stream(str.split(" "))
                  .forEach((code) -> {
                      int CC = Integer.parseInt(code) / 100;
                      int SS = Integer.parseInt(code) % 100;
                      int line=0, absolutePosition;
                      for (int i=1; i<SS; i++){
                          line = text.indexOf("\n", ++line);
                      }
                      absolutePosition = line + CC - (line==0 ? 1 : 0);
                      decrypted.append(text.charAt(absolutePosition));
            });
            }catch (IndexOutOfBoundsException e){
                throw e;
            }
        return decrypted.toString();
    }

    @Override
    public String brute(String str) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

}
