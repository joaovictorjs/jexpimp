package com.github.joaovictorjs;

public class NotJSONFileException extends Exception {
    NotJSONFileException(String path) {
        super(path + " is not a JSON file. Make sure filename ends with \".json\" extension");
    }
}
