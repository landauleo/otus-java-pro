package ru.otus.dataprocessor;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ru.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        if (data == null || data.isEmpty()) {
            throw new FileProcessException("List of measurements should not be null or empty");
        }
        return data.stream().collect(Collectors.toMap(Measurement::getName, Measurement::getValue, Double::sum, TreeMap::new));
    }

}
