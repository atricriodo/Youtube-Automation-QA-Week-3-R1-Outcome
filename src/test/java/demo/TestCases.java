package demo;
import demo.utils.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases extends ExcelDataProvider {
    
    WebDriver driver;

    @BeforeClass
    public void setup(){
        System.out.println("Constructor: Driver");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Successfully Created Driver");
    }

    @Test(enabled = true)
    public void testCase04() throws InterruptedException {
        System.out.println("Running Test Case 04");
        Utilities.goToUrlAndWait(driver, "https://youtube.com");
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        Utilities.findElementAndClick(driver, By.xpath("//a[@title='News']"));
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for the element to be clickable
        WebElement contentCards = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='rich-shelf-header-container' and contains(.,'Latest news posts')]//ancestor::div[1]//div[@id='contents']")));
        
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        long sumOfVotes = 0;
        for(int i = 1; i<=3; i++){
            System.out.println(Utilities.findElementAndPrintWE(driver, By.xpath("//div[@id='header']["+i+"]"), contentCards));
            System.out.println(Utilities.findElementAndPrintWE(driver, By.xpath("//div[@id='body']["+i+"]"), contentCards));
            try{
                String res = Utilities.findElementAndPrintWE(driver, By.xpath("span[@id='vote-count-middle']["+i+"]"), contentCards);
                sumOfVotes += Utilities.convertToNumericValue(res);
            }
            catch(NoSuchElementException e){
                System.out.println("Vote not present - "+e.getMessage());
            }
            System.out.println(sumOfVotes);
            Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        }
        System.out.println("Ending Test Case 04");
    }

    @Test(enabled = false, dataProvider = "excelData")
    public void testCase05(String searchWord) throws InterruptedException {
        System.out.println("Running Test Case 05 Flow for: " + searchWord);
        Utilities.goToUrlAndWait(driver, "https://youtube.com");
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        Utilities.sendKeysWrapper(driver, By.xpath("//input[@id='search']"), searchWord);
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        long tally = 0;
        int iter = 1;
        while(tally<100000000 || iter > 5){
            String res =  Utilities.findElementAndPrint(driver, By.xpath("(//div[@class='style-scope ytd-video-renderer' and @id='meta']//span[@class='inline-metadata-item style-scope ytd-video-meta-block'][1])["+iter+++"]"));
            res = res.split(" ")[0];
            tally += Utilities.convertToNumericValue(res);
            Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        }
        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        System.out.println("Ending Test Case 05");
    }


    @AfterClass
    public void tearDown(){
        System.out.println("Destroying Driver Instance");
        driver.close();
        driver.quit();
        System.out.println("Successfully Destroyed Driver");
    }
}
