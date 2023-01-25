package com.github.joaovictorjs;

import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Jexpimp {
    /**
     * Returns an object from a JSON file.
     *
     * @param path the path where JSON file is located
     * @param type the type of the object to be converted
     * @return the return depends on the type you passed and the object that is expecting the return
     */
    public static <T extends Object> T importJSON(String path, Class<T> type) throws NotJSONFileException, IOException {
        path = FilenameUtils.separatorsToSystem(path);

        if (!path.endsWith(".json")) throw new NotJSONFileException(path);

        File src = new File(path);

        if (!src.exists()) return null;

        FileInputStream fis = new FileInputStream(src);
        byte[] bytes = new byte[(int) src.length()];
        fis.read(bytes);
        String raw = new String(bytes);
        fis.close();

        return new Gson().fromJson(raw, type);
    }

    /**
     * exports a JSON file containing the data you passed
     *
     * @param path                  the path where the JSON file will be saved
     * @param data                  the data that will be stored in the file
     * @param excludeWhenDuplicated deletes the file if it already exists
     * @return returns true if everything went well and false if something went wrong
     */
    public static <T extends Object> boolean exportJSON(String path, T data, boolean excludeWhenDuplicated) throws NotJSONFileException, IOException {
        path = FilenameUtils.separatorsToSystem(path);

        if (!path.endsWith(".json")) throw new NotJSONFileException(path);

        String parent = new File(path).getParent();

        if (parent == null || parent.equals("") || parent.equals(".") || parent.equals("." + File.separator)) {

        } else {
            if (!new File(parent).mkdirs()) {
                return false;
            }
        }

        File target = new File(path);

        if (target.exists()) {
            if (excludeWhenDuplicated) {
                if (!target.delete()) {
                    return false;
                }
            } else {
                return false;
            }
        }

        if (!target.createNewFile()) {
            return false;
        }

        String raw = new Gson().toJson(data);

        FileOutputStream fos = new FileOutputStream(target);
        fos.write(raw.getBytes());
        fos.flush();
        fos.close();

        return target.exists() && target.length() == raw.getBytes().length;
    }
}
