package testCases;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;
import utilities.DataProviders;

public class LoginTest extends BaseTest {

    public Faker faker;

    @Test(groups = {"Sanity", "Master"})
    public void verify_login() {
        logger.info("**** Starting verify_login() test ****");

        HomePage home = new HomePage(driver);
        home.clickMyAccount();
        home.clickLogin();

        LoginPage login = new LoginPage(driver);
        login.setEmail(properties.getProperty("email"));
        login.setPassword(properties.getProperty("password"));
        login.clickButtonLogin();

        MyAccountPage myAccountPage = new MyAccountPage(driver);
        boolean targetPageExists = myAccountPage.isMyAccountPageExists();
        Assert.assertTrue(targetPageExists);
        myAccountPage.clickLogout();

        logger.info("***Finished verify_login() test ***");
    }

    @Test (dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = {"DataDriven", "Master"})
    public void verify_login_data_provider(String email, String password, String expectedResult) {
        logger.info("**** Starting verify_login_data_provider() test ****");

        HomePage home = new HomePage(driver);
        home.clickMyAccount();
        home.clickLogin();

        LoginPage login = new LoginPage(driver);
        login.setEmail(email);
        login.setPassword(password);
        login.clickButtonLogin();

        MyAccountPage myAccountPage = new MyAccountPage(driver);
        boolean targetPageExists = myAccountPage.isMyAccountPageExists();

        /* response/expectedResult from Excel sheet is 'Valid' -  and login success > test pass > logout
                                                               -  but login fail    > test fail
           response/expectedResult from Excel sheet is 'Invalid' - but login success > test fail > logout
                                                                 - and login failed  > test pass */
        if (expectedResult.equalsIgnoreCase("Valid")) {
            if (targetPageExists) {
                myAccountPage.clickLogout();
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        }

        if (expectedResult.equalsIgnoreCase("Invalid")) {
            if (targetPageExists) {
                myAccountPage.clickLogout();
                Assert.fail();
            } else {
                Assert.assertTrue(true);
            }
        }

        logger.info("*** Finished verify_login_data_provider() test ***");
    }

}
