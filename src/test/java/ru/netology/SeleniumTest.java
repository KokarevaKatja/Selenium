package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumTest {
        WebDriver driver;

        @BeforeAll
        public static void setUpAll() {
            WebDriverManager.chromedriver().setup();
        }

        @BeforeEach
        void setUp() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
            driver.get("http://localhost:9999/");
        }

        @AfterEach
        void tearDown() {
            driver.quit();
            driver = null;
        }

        @Test
        void shouldPositiveTest() {
            driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
            driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79810987867");
            driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
            driver.findElement(By.cssSelector("[type=button]")).click();
            String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
            String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
            Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldNegativeTest() {
            driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Петр");
            driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
            driver.findElement(By.cssSelector("[type=button]")).click();
            String expected = "Поле обязательно для заполнения";
            String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
            Assertions.assertEquals(expected, actual);
    }
}
