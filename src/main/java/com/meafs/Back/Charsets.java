package com.meafs.Back;

/**
 * Created by meaf on 22.02.17.
 */
public enum Charsets {
    ENG("abcdefghijklmnopqrstuvwxyz .,?!\n", "ENG"),
    RUS("абвгдеёжзийклмнопрстуфхцчшщъыьэюя .,?!\n", "RUS"),
    UA("абвгґдеєжзиіїйклмнопрстуфхцчшщьюя .,?!\n", "UA"),
    NUM("1234567890", "NUM");

    Charsets(String charset, String name){
        this.charset = charset;
        this.name = name;
    }

    private String charset;
    private String name;

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

}
