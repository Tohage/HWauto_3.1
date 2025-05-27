import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardApplicationTests {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://0.0.0.0:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void correctRequestSubmission() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement reltElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(reltElement.isDisplayed());
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", reltElement.getText());

    }

    @Test
    void inputNameWithHyphen() {
        WebElement form = driver.findElement(By.cssSelector("form"));
        List<WebElement> inputs = form.findElements(By.cssSelector("input"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов-Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        WebElement reltElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(reltElement.isDisplayed());
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", reltElement.getText());

    }

}