package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {

    private static final String IP_API_URL = "https://api.ipify.org/?format=json";

    public static void fetchIP(WebDriver driver) {
        try {
            driver.get(IP_API_URL);

            WebElement body = driver.findElement(By.tagName("pre"));
            String rawJson = body.getText();

            JSONParser jsonParser = new JSONParser();
            JSONObject parsed = (JSONObject) jsonParser.parse(rawJson);

            String clientIP = (String) parsed.get("ip");
            System.out.println("IP-адрес клиента: " + clientIP);

        } catch (Exception ex) {
            System.err.println("Ошибка в задании 2: " + ex.getMessage());
        }
    }
}
