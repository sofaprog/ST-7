package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {

    private static final String WEATHER_URL =
            "https://api.open-meteo.com/v1/forecast" +
            "?latitude=56&longitude=44" +
            "&hourly=temperature_2m,rain" +
            "&current=cloud_cover" +
            "&timezone=Europe%2FMoscow" +
            "&forecast_days=1" +
            "&wind_speed_unit=ms";

    private static final String OUTPUT_FILE = "result/forecast.txt";
    private static final String LINE = "-".repeat(65);

    public static void fetchWeather(WebDriver driver) {
        try {
            driver.get(WEATHER_URL);

            WebElement preTag = driver.findElement(By.tagName("pre"));
            String rawJson = preTag.getText();

            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(rawJson);

            JSONObject hourlyData = (JSONObject) root.get("hourly");
            JSONArray timeList   = (JSONArray) hourlyData.get("time");
            JSONArray tempList   = (JSONArray) hourlyData.get("temperature_2m");
            JSONArray rainList   = (JSONArray) hourlyData.get("rain");

            printTable(timeList, tempList, rainList);
            saveToFile(timeList, tempList, rainList);

        } catch (Exception ex) {
            System.err.println("Ошибка в задании 3: " + ex.getMessage());
        }
    }

    private static void printTable(JSONArray times, JSONArray temps, JSONArray rains) {
        System.out.println("Прогноз погоды — Нижний Новгород (почасовой)");
        System.out.println(LINE);
        System.out.printf("%-4s | %-18s | %-16s | %-10s%n",
                "№", "Дата/время", "Температура (°C)", "Осадки (мм)");
        System.out.println(LINE);

        for (int i = 0; i < times.size(); i++) {
            System.out.printf("%-4d | %-18s | %-16.1f | %-10.2f%n",
                    i + 1,
                    times.get(i),
                    (Double) temps.get(i),
                    (Double) rains.get(i));
        }

        System.out.println(LINE);
    }

    private static void saveToFile(JSONArray times, JSONArray temps, JSONArray rains) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            pw.println("Прогноз погоды — Нижний Новгород (почасовой)");
            pw.println(LINE);
            pw.printf("%-4s | %-18s | %-16s | %-10s%n",
                    "№", "Дата/время", "Температура (°C)", "Осадки (мм)");
            pw.println(LINE);

            for (int i = 0; i < times.size(); i++) {
                pw.printf("%-4d | %-18s | %-16.1f | %-10.2f%n",
                        i + 1,
                        times.get(i),
                        (Double) temps.get(i),
                        (Double) rains.get(i));
            }

            pw.println(LINE);
            System.out.println("Таблица сохранена: " + OUTPUT_FILE);

        } catch (Exception ex) {
            System.err.println("Не удалось сохранить файл: " + ex.getMessage());
        }
    }
}
