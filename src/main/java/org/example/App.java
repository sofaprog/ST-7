package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mi\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        try {
            System.out.println("--- Задание 1: генерация пароля ---");
            fetchPassword(driver);

            System.out.println("\n--- Задание 2: определение IP ---");
            Task2.fetchIP(driver);

            System.out.println("\n--- Задание 3: прогноз погоды ---");
            Task3.fetchWeather(driver);

        } catch (Exception ex) {
            System.err.println("Произошла ошибка: " + ex.getMessage());
        } finally {
            driver.quit();
        }
    }

    private static void fetchPassword(WebDriver driver) {
        try {
            driver.get("https://www.calculator.net/password-generator.html");

            WebElement btnGenerate = driver.findElement(By.id("generate"));
            btnGenerate.click();

            WebElement inputPassword = driver.findElement(By.id("password"));
            String pwd = inputPassword.getAttribute("value");

            System.out.println("Сгенерированный пароль: " + pwd);

        } catch (Exception ex) {
            System.err.println("Ошибка в задании 1: " + ex.getMessage());
        }
    }
}
