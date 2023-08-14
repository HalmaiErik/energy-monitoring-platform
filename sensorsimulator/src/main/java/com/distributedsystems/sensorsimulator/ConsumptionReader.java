package com.distributedsystems.sensorsimulator;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsumptionReader {

    @Value("${file.consumptions.name}")
    private String FILE_NAME;

    public List<Double> readValues() {
        List<Double> values = new ArrayList<>();
        try {
            File sensorFile = new File(FILE_NAME);
            Scanner scanner = new Scanner(sensorFile);

            while (scanner.hasNextLine()) {
                Double value = Double.parseDouble(scanner.nextLine());
                values.add(value);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return values;
    }
}
