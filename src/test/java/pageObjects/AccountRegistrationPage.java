package pageObjects;

//import java.time.Duration;

//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage {

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement textFirstName;

    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement textLastName;

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement textEmail;

    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement textTelephone;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement textPassword;

    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement textConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']")
    WebElement checkboxPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContunue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;

    public void setFirstName(String fname) {
        textFirstName.sendKeys(fname);
    }

    public void setLastName(String lname) {
        textLastName.sendKeys(lname);
    }

    public void setEmail(String email) {
        textEmail.sendKeys(email);
    }

    public void setTelephone(String phone) {
        textTelephone.sendKeys(phone);
    }

    public void setPassword(String password) {
        textPassword.sendKeys(password);
    }

    public void setConfirmPassword(String password) {
        textConfirmPassword.sendKeys(password);
    }

    public void setPrivacyPolicy() {
        checkboxPolicy.click();
    }

    public void clickContinue() {
        btnContunue.click(); //solution 1
        //btnContunue.submit(); //solution 2
        //Actions action = new Actions(driver); //solution 4
        //action.moveToElement(btnContunue).click().perform();
        //btnContunue.sendKeys(Keys.RETURN); //solution 5
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //solution 6
        //wait.until(ExpectedConditions.elementToBeClickable(btnContunue)).click();
    }

    public String getConfirmationMsg() {
        try {
            return (msgConfirmation.getText());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
