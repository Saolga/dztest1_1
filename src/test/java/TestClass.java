import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

public class TestClass {
    WebDriver dr;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        dr = new ChromeDriver();
        dr.manage().window().maximize();    //полный экран
        dr.get("http://www.rgs.ru");      //заходим на сайт
    }


    @After
    public void tearDown(){
        dr.quit();
    }

    @Test
    public void test(){

        //выбираем страхование
        WebElement webStrakh = dr.findElement(By.xpath("//*[@id=\"main-navbar-collapse\"]/ol[1]/li[2]/a"));
        webStrakh.click();

        WebDriverWait wait = new WebDriverWait(dr, 10);
        wait.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath("//*[@id=\"rgs-main-menu-insurance-dropdown\"]/div[1]/div[1]/div/div[1]/div[3]/ul/li[2]/a"))));

        WebElement webDMS = dr.findElement(By.xpath("//*[@id=\"rgs-main-menu-insurance-dropdown\"]/div[1]/div[1]/div/div[1]/div[3]/ul/li[2]/a"));
        webDMS.click();

        //проверка наличия заголовка
        WebElement title = dr.findElement(By.xpath("//*[@id=\"main-content\"]/div[2]/div/span"));
        Assert.assertEquals("Заголовок верен",
                "Добровольное медицинское страхование (ДМС)", title.getText());

        WebElement knopka = dr.findElement(By.xpath("//*[@id=\"rgs-main-context-bar\"]/div/div/div/a[3]"));
        knopka.click();

        WebDriverWait wait1 = new WebDriverWait(dr, 3);
        wait1.until(ExpectedConditions.visibilityOf(dr.findElement(By.cssSelector("body > div.modals-container > div > div > div > div.modal-header > h4 > b"))));

        //проверка наличия заголовка
        WebElement title2=dr.findElement(By.cssSelector("body > div.modals-container > div > div > div > div.modal-header > h4 > b"));
        Assert.assertEquals("Заголовок верен",
                "Заявка на добровольное медицинское страхование", title2.getText());

        //заполнение полей
        WebElement familyName=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(1) > input"));
        familyName.sendKeys("Иванов");
        WebElement name=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(2) > input"));
        name.sendKeys("Иван");
        WebElement secondName=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(3) > input"));
        secondName.sendKeys("Иванович");

        WebElement region=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(4) > select"));
        Select regionS = new Select(region);
        regionS.selectByValue("77");
        WebElement phone=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(5) > input"));
        phone.sendKeys("9099995353");
        WebElement mail=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(6) > input"));
        mail.sendKeys("qwertyqwerty");
        //WebElement data=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(7) > input"));
        //data.sendKeys("22052018");
        WebElement agreed=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(9) > label > input"));
        agreed.click();
        WebElement comm=dr.findElement(By.cssSelector("#applicationForm > div.row > div:nth-child(8) > textarea"));
        comm.sendKeys("У дороги чибис");

        //проверяем введенные значения
        Assert.assertEquals("совпадает","Иванов",familyName.getAttribute("value"));
        Assert.assertEquals("совпадает","Иван",name.getAttribute("value"));
        Assert.assertEquals("совпадает","Иванович",secondName.getAttribute("value"));
        Assert.assertEquals("совпадает","77",region.getAttribute("value"));
        Assert.assertEquals("совпадает","+7 (909) 999-53-53",phone.getAttribute("value"));
        Assert.assertEquals("совпадает","qwertyqwerty",mail.getAttribute("value"));
        Assert.assertEquals("совпадает","У дороги чибис",comm.getAttribute("value"));


        //жмем кнопку отправить:
        WebElement button=dr.findElement(By.cssSelector("#button-m"));
        button.click();
        //*[@id="button-m"]

        WebDriverWait wait2 = new WebDriverWait(dr, 3);
        wait2.until(ExpectedConditions.visibilityOf(dr.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[6]/div/label/span"))));

        //проверяем наличие сообщения
        WebElement erro=dr.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[6]/div/label/span"));
        Assert.assertEquals("Есть сообщение",
                "Введите адрес электронной почты", erro.getText());

    }
}
