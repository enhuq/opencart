package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseTest;

public class AccountRegistrationTest extends BaseTest {

    public Faker faker;

    @Test(groups = {"Regression", "Master"})
    public void verify_account_registration() {
        logger.info("**** Starting verify_account_registration() test ****");

        HomePage home = new HomePage(driver);
        faker = new Faker();
        home.clickMyAccount();
        home.clickRegister();

        AccountRegistrationPage registration = new AccountRegistrationPage(driver);

        logger.info("Providing customer details...");
        registration.setFirstName(faker.name().firstName());
        registration.setLastName(faker.name().lastName());
        registration.setEmail(faker.internet().safeEmailAddress());
        registration.setTelephone(faker.phoneNumber().cellPhone());

        String password = faker.internet().password(8, 10);
        registration.setPassword(password);
        registration.setConfirmPassword(password);

        registration.setPrivacyPolicy();
        registration.clickContinue();

        logger.info("Validating expected message...");
        String confMsg = registration.getConfirmationMsg();
        Assert.assertEquals(confMsg, "Your Account Has Been Created!");
    }

}
