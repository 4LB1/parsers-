package com.gson.core;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gson.model.Lesson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class ReadExample {
    private static final String FILE_NAME = "lessons.json";

    public static void main(String[] args) {
        // Создаем Gson с конвертером LocalTime
        Gson gson = Converters.registerLocalTime(new GsonBuilder()).create();

        // TypeToken нужен, чтобы Gson понял тип List<Lesson> из-за Type Erasure
        Type lessonListType = new TypeToken<List<Lesson>>() {}.getType();

        try (Reader reader = new FileReader(FILE_NAME)) {
            List<Lesson> lessons = gson.fromJson(reader, lessonListType);

            System.out.println("=== Успешно прочитано уроков: " + lessons.size() + " ===");
            for (int i = 0; i < lessons.size(); i++) {
                Lesson l = lessons.get(i);
                System.out.println((i + 1) + ". Предмет: " + l.getSubject());
                System.out.println("   Длительность: " + l.getDuration());
                System.out.println("   Темы: " + String.join(", ", l.getTopics()));
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}