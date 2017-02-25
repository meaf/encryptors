package com.meafs.Back;

import java.io.*;

/**
 * Created by meaf on 25.02.17.
 */
public final class FileUtil {
    static public String readFromFile(String fileName) throws FileNotFoundException{
        File file = new File(fileName);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(
                    (new FileReader(
                            file.getAbsoluteFile()
                    ))
            );
            String s;
            try{while (((s = in.readLine()) != null)) {
                sb.append(s);
            }
            in.close();
            } catch (IOException e){
                e.printStackTrace();
            }

        } catch (FileNotFoundException e){
            throw new FileNotFoundException(fileName+" not found");}
        return sb.toString();
    }
}


