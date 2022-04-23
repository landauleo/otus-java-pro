package ru.otus.dataprocessor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.google.gson.Gson;

public class FileSerializer implements Serializer {

    private String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (Writer writer = new FileWriter(fileName)) {
            new Gson().toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
