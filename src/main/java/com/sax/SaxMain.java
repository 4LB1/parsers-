package com.sax;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class SaxMain {
    public static void main(String[] args) {
        File file = new File("trains.xml");
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            TrainXmlHandler handler = new TrainXmlHandler();
            parser.parse(file, handler);

            LocalTime targetTime = LocalTime.of(12,0);
            System.out.println("Поезда, отправляющиеся после " + targetTime + ":");
            handler.getTrains().stream()
                    .filter(trains -> trains.getDepartureTime()
                    .isAfter(targetTime))
                    .forEach(System.out::println);


//            System.out.println("Все поезда: ");
//            handler.getTrains().stream().forEach(System.out::println);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
