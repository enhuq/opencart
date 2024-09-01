package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "input-email")
    WebElement textboxEmailAddress;

    @FindBy(id = "input-password")
    WebElement textboxPassword;

    @FindBy(xpath = "//input[@value='Login']")
    WebElement buttonLogin;

    public void setEmail(String email) {
        textboxEmailAddress.sendKeys(email);
    }

    public void setPassword(String password) {
        textboxPassword.sendKeys(password);
    }

    public void clickButtonLogin() {
        buttonLogin.click();
    }
}
