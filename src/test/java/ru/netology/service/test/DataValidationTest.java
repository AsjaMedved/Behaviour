package ru.netology.service.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.service.data.DataHelper;
import ru.netology.service.page.LoginPage;
import ru.netology.service.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class DataValidationTest {

    @BeforeEach
    void setup() {
               open("http://localhost:9999");
    }

    @Test
    @DisplayName("Вход с невалидным логином и/или паролем")
    void shouldShowErrorWithInvalidLogin() {

        var originalAuthInfo = DataHelper.getAuthInfo();
        var invalidAuthInfo = DataHelper.getOtherAuthInfo(originalAuthInfo);
        var loginPage = new LoginPage();
        loginPage.login(invalidAuthInfo.getLogin(), invalidAuthInfo.getPassword());
        loginPage.checkErrorIsVisibleNotification();
    }

    @Test
    @DisplayName("пустое поле логин")
    void campoDiAccessoVuoto() {

        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        loginPage.login("", authInfo.getPassword());
        loginPage.checkErrorIsVisibleLogin();
    }

    @Test
    @DisplayName("пустое поле пароль")
    void campoPasswordVuoto() {

        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        loginPage.login(authInfo.getLogin(), "");
        loginPage.checkErrorIsVisiblePassword();
    }

    @Test
    @DisplayName("Вход с не верным кодом верификации")
    void incorrectVerificationCode() {

        var originalAuthInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(originalAuthInfo.getLogin(), originalAuthInfo.getPassword());
        var invalidCode = DataHelper.getOtherVerificationCodeFor(originalAuthInfo);
        verificationPage.verify(invalidCode.getCode());
        verificationPage.incorrectVerificationCode();

    }

    @Test
    @DisplayName("пустое поле верификации")
    void campoDiVerificaVuoto() {

        var originalAuthInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(originalAuthInfo.getLogin(), originalAuthInfo.getPassword());
        verificationPage.verify("");
        verificationPage.emptyVerificationField();
    }
}
