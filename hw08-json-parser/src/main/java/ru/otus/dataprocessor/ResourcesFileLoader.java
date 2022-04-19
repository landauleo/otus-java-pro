package ru.otus.dataprocessor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;


public class ResourcesFileLoader implements Loader {
    private String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        Type listType = new TypeToken<ArrayList<Measurement>>() {}.getType(); //хакнули protected access
        String jsonString = null;
        try {
            jsonString = new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(jsonString, listType);
    }

}
