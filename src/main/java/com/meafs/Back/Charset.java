package com.meafs.Back;

import java.io.Serializable;

/**
 * Created by meaf on 22.02.17.
 */
public class Charset implements Serializable {
    private String charset, name;

    public Charset(){};

    public Charset(String charset) {
        this.charset = charset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int length(){
        return charset.length();
    }

    public void merge(String anotherCharset){
        charset = charset.concat(anotherCharset);
    }

    public int indexOf(char character){
        return charset.indexOf(character);
    }

    public char charAt(int index){
        return charset.charAt(index);
    }

    public char[] toCharArray(){
        return charset.toCharArray();
    }
}
