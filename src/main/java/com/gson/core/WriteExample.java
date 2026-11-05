package com.gson.core;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gson.model.Lesson;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WriteExample {
    private static final String FILE_NAME = "lessons.json";

    public static void main(String[] args) {
        // Настраиваем Gson: регистрируем конвертер времени и включаем читаемый отступ
        Gson gson = Converters.registerLocalTime(new GsonBuilder())
                .setPrettyPrinting()
                .create();

        // Формируем список уроков по заданию
        List<Lesson> lessons = createLessonsList();

        // Сериализуем список в строку JSON
        String jsonData = gson.toJson(lessons);

        // Записываем в файл
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(jsonData);
            System.out.println("Список уроков успешно записан в файл " + FILE_NAME);
            System.out.println("\nСгенерированный JSON:\n" + jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Lesson> createLessonsList() {
        List<Lesson> list = new ArrayList<>();

        list.add(new Lesson(
                "Математика",
                LocalTime.of(0, 45),
                List.of("Дискриминант", "Логарифмы", "Теорема Пифагора")
        ));

        list.add(new Lesson(
                "Информатика",
                LocalTime.of(1, 30),
                List.of("ООП в Java", "Парсеры XML/JSON", "Рефлексия")
        ));

        list.add(new Lesson(
                "Физика",
                LocalTime.of(0, 45),
                List.of("Законы Ньютона", "Термодинамика")
        ));

        return list;
    }
}