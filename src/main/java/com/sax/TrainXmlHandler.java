package com.sax;

import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TrainXmlHandler extends DefaultHandler {
    private final List<Train> trains = new ArrayList<>();
    private Train currentTrain;
    private final StringBuilder currentCharacters =new StringBuilder();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentCharacters.setLength(0);
        // предотвращаем NullPointerException
        if ("train".equalsIgnoreCase(qName)) {
            currentTrain = new Train();
            String id = attributes.getValue("id");
            currentTrain.setId(id);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        currentCharacters.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String content = currentCharacters.toString().trim();

        switch (qName.toLowerCase()) {
            case "train" -> trains.add(currentTrain);
            case "departurestation" -> currentTrain.setDepartureStation(content);
            case "arrivalstation" -> currentTrain.setArrivalStation(content);
            case "departuretime" -> currentTrain.setDepartureTime(LocalTime.parse(content));
            case "arrivaltime" -> currentTrain.setArrivalTime(LocalTime.parse(content));
        }
    }
}
