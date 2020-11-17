package ru.geekbrains;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Test {
    private static WebDriver driver;
    private static final String LOGIN_PAGE_URL = "https://crm.geekbrains.space/user/login";
    private static final String STUDENT_LOGIN = "Applanatest";
    private static final String STUDENT_PASSWORD = "Student2020!";
    private static final String expensesMenu = "//ul[@class='nav nav-multilevel main-menu']/li[@class='dropdown']/a/span[contains(., 'Расходы')]";
    private static final String expensesSubMenu = "//span[@class='title' and text()='Заявки на расходы']";
    private static final String button = "div[class='pull-left btn-group icons-holder']";
    private static final String calendar = ".//div[preceding-sibling::div[child::label[@class='required']]]//input[@class='datepicker-input  hasDatepicker']";
    private static final String saveButton = "button[class='btn btn-success action-button']";
    private static final String projectMenu = "//ul[@class='nav nav-multilevel main-menu']/li[@class='dropdown']/a/span[contains(., 'Проекты')]";
    private static final String projectSubMenu = "//span[@class='title' and text()='Мои проекты']";
    private static final String projectAddButton = "//a[@title='Создать проект']";
    private static final String inputName = "//input[@data-name='field__name']";

    public static void main(String[] args) {
//        lesson3_online();
        crm_create_project();
    }

    private static void crm_create_project() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        //Добавляем неявное ожидание (вот без этой строчки не работает!)
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        System.out.println("Логин");
        login();

        //click on Проекты / Мои проекты / Создать проект
        System.out.println("Проекты");
        driver.findElement(By.xpath(projectMenu)).click();
        System.out.println("Мои проекты");
        driver.findElement(By.xpath(projectSubMenu)).click();
        System.out.println("Создать проект");
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(projectAddButton))));
        driver.findElement(By.xpath(projectAddButton)).click();

        System.out.println("Наименование проекта");
        driver.findElement(By.xpath(inputName)).sendKeys("Name-of-project");

        System.out.println("DropDown Организация");
        //Select businessUnitDropDown = new Select(driver.findElement(By.xpath("//select[@class='select2-chosen' and text()='Укажите организацию']")));
        //Select businessUnitDropDown = new Select(driver.findElement(By.xpath("//select[contains(.,'Укажите организацию']")));
        //Select businessUnitDropDown = new Select(driver.findElement(By.xpath("//div[@class='company-container']/div/a")));
        // businessUnitDropDown.selectByValue("1");


    }

    private static void lesson3_online() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        //Добавляем неявное ожидание (вот без этой строчки не работает!)
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        login();

        //click on Расходы menu
        driver.findElement(By.xpath(expensesMenu)).click();
        driver.findElement(By.xpath(expensesSubMenu)).click();
       // Thread.sleep(5000);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(button))));
        driver.findElement(By.cssSelector(button)).click();

        new WebDriverWait(driver, 5).until(ExpectedConditions.urlContains("/create"));
      //  Thread.sleep(5000);
        driver.findElement(By.xpath("//textarea")).sendKeys("test");
        Select businessUnitDropDown = new Select(driver.findElement(By.name("crm_expense_request[businessUnit]")));
        businessUnitDropDown.selectByValue("1");
        //статья расходов
        Select expenditureDropDown = new Select(driver.findElement(By.name("crm_expense_request[expenditure]")));
        expenditureDropDown.selectByValue("87");

        WebElement sumPlan = driver.findElement(By.name("crm_expense_request[sumPlan]"));
        sumPlan.clear();
        sumPlan.sendKeys("1488");

        WebElement approvalNeededCheckbox = driver.findElement(By.name("crm_expense_request[approvalNeeded]"));
        System.out.println("Before click: " + approvalNeededCheckbox.isSelected());
        approvalNeededCheckbox.click();
        System.out.println("After click: " + approvalNeededCheckbox.isSelected());

        driver.findElement(By.xpath(calendar)).click();
        driver.findElement(By.xpath("//a[text()='20']")).click();

        driver.findElement(By.cssSelector(saveButton)).click();

        tearDown();
    }

    private static void login() {
        driver.get(LOGIN_PAGE_URL);

        WebElement loginTextInput = driver.findElement(By.name("_username"));
        loginTextInput.sendKeys(STUDENT_LOGIN);

        WebElement passwordTextInput = driver.findElement(By.name("_password"));
        passwordTextInput.sendKeys(STUDENT_PASSWORD);

        WebElement loginButton = driver.findElement(By.xpath(".//button[@name='_submit']"));
        loginButton.click();
    }

    private static void tearDown() {
        System.out.println("Test is completed!");
        driver.quit();
    }
}
