package com.meafs.Back;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;

/**
 * Created by meaf on 06.03.17.
 */
public class CharsetProvider {
    @Autowired
    private ResourceLoader resourceLoader;
    private static volatile CharsetProvider instance;
    private CharsetStore charsetStore;
    private static final Logger LOGGER = LoggerFactory.getLogger(CharsetProvider.class);

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
                LOGGER.error("Charsets were not configured");
            else LOGGER.info("Charsets were configured");
        } catch (FileNotFoundException|URISyntaxException e){e.printStackTrace();}
    }

    private CharsetStore readJSON() throws FileNotFoundException, URISyntaxException {
        String filename = "charsets.json";

//        TODO: Fix dis
        File file = new File("/home/meaf/IdeaProjects/Projects/Git/encryptors/target/classes/charsets.json");
        final ObjectMapper mapper = new ObjectMapper();
        try{
            LOGGER.debug("mapping to "+filename);
            return (CharsetStore)mapper.readValue(file, CharsetStore.class);
        }catch (IOException e1){
            try{
                LOGGER.debug("creating new instance of charsetStore");
                return CharsetStore.class.newInstance();
            }catch (InstantiationException|IllegalAccessException e2){
                LOGGER.error("configuration failed");
                throw new IllegalStateException(e2);
            }
        }
    }

    public LinkedList<Charset> getAll() {
        LOGGER.info("fetching charsets");
        LinkedList<Charset> csList = new LinkedList<>();
        csList.addAll(charsetStore.values());
        return csList;
    }
}
