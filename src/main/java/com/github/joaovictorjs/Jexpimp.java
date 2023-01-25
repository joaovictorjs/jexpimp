package com.github.joaovictorjs;

import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Jexpimp {
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
}
