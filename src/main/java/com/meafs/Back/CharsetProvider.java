package com.meafs.Back;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by meaf on 06.03.17.
 */
public class CharsetProvider {
    private static volatile CharsetProvider instance;
    private CharsetStore charsetStore;
    private CharsetProvider(){
        configure();
    }

    public static CharsetProvider getInstance() {
        if (instance != null)
            return instance;
            else instance = new CharsetProvider();
        return instance;
    }

    private void configure(){
        try{
            final CharsetStore newCharsetStore = readJSON();
            for (final CharsetStore.Entry<String, Charset> entry : newCharsetStore.entrySet())
                entry.getValue().setName(entry.getKey());
            charsetStore = newCharsetStore;
            if (charsetStore.isEmpty())
                System.out.println("charset not configured");
        } catch (FileNotFoundException|URISyntaxException e){e.printStackTrace();}
    }

    private CharsetStore readJSON() throws FileNotFoundException, URISyntaxException {
//        final URL url = CharsetProvider.class.getClassLoader().getResource("charsets.json");

//        TODO: Fix jar
        File file = new File("/home/meaf/IdeaProjects/Projects/Git/encryptors/target/classes/charsets.json");
        final ObjectMapper mapper = new ObjectMapper();
        try{
            return (CharsetStore)mapper.readValue(file, CharsetStore.class);
        }catch (IOException e1){
            try{
                System.out.println("new instance");
                return CharsetStore.class.newInstance();
            }catch (InstantiationException|IllegalAccessException e2){
                throw new IllegalStateException(e2);
            }
        }

    }

    public LinkedList<Charset> getAll() {
        LinkedList<Charset> csList = new LinkedList<Charset>();
        csList.addAll(charsetStore.values());
        System.out.println(csList.toString());
        return csList;
    }
}
