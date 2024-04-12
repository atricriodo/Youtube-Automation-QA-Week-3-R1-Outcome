package demo.utils;

import java.time.Duration;

import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.*;

public class Utilities {

    public static void goToUrlAndWait(WebDriver driver, String url) {
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

    public static void sendKeysWrapper(WebDriver driver, By locator, String textToSend) {
        System.out.println("Sending Keys");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement textInput = driver.findElement(locator);
            textInput.clear();
            textInput.sendKeys(textToSend);
            textInput.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Exception Occured! " + e.getMessage());
        }
    }

    public static void clickTillUnclickable(WebDriver driver, By locator, Integer maxIterations) throws InterruptedException{
        Integer numIter = 0;
        while(numIter<maxIterations){
            try{
                findElementAndClick(driver, locator);
            }
            catch(TimeoutException e){
                System.out.println("Stopping - "+e.getMessage());
                break;
            }
        }
    }
    
    public static void findElementAndClick(WebDriver driver, By locator) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for the element to be clickable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        
        // Wait until element is visible after scrolling
        wait.until(ExpectedConditions.visibilityOf(element));
        
        // Click the element
        element.click();
        Thread.sleep(1000);
    }
    public static void findElementAndClickWE(WebDriver driver, WebElement we, By locator) {
        WebElement element = we.findElement(locator);
        // Scroll to the element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        
        
        // Click the element
        element.click();
    }

    public static String findElementAndPrint(WebDriver driver, By locator, int elementNo){

        WebElement we = driver.findElements(locator).get(elementNo);
        // Return the result
        String txt = we.getText();
        return txt;
    }

    public static String findElementAndPrintWE(WebDriver driver, By locator, WebElement we, int elementNo){
        WebElement element = we.findElements(locator).get(elementNo);

        String txt = element.getText();
        return txt;
    }

    public static long convertToNumericValue(String value) {
        // Trim the string to remove any leading or trailing spaces
        value = value.trim().toUpperCase();

        // Check if the last character is non-numeric and determine the multiplier
        char lastChar = value.charAt(value.length() - 1);
        int multiplier = 1;
        switch (lastChar) {
            case 'K':
                multiplier = 1000;
                break;
            case 'M':
                multiplier = 1000000;
                break;
            case 'B':
                multiplier = 1000000000;
                break;
            default:
                // If the last character is numeric, parse the entire string
                if (Character.isDigit(lastChar)) {
                    return Long.parseLong(value);
                }
                throw new IllegalArgumentException("Invalid format: " + value);
        }

        // Extract the numeric part before the last character
        String numericPart = value.substring(0, value.length() - 1);
        double number = Double.parseDouble(numericPart);

        // Calculate the final value
        return (long) (number * multiplier);
    }
}
