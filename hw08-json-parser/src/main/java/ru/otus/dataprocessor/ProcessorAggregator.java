package ru.otus.dataprocessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ru.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> map = new TreeMap<>();
        if (data == null || data.isEmpty()) {
            return Collections.singletonMap(null, null);
        }
        data.forEach(measurement -> {
            var key = measurement.getName();
            var value = measurement.getValue();
            map.put(key, map.get(key) == null ? value : map.get(key) + value);
        });
        return map;
    }

}
