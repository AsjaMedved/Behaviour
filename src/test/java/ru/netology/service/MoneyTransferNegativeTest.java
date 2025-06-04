package ru.netology.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferNegativeTest {

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
        loginPage.invalidLogin(invalidAuthInfo.getLogin(), invalidAuthInfo.getPassword());
    }

    @Test
    @DisplayName("пустое поле логин")
    void campoDiAccessoVuoto() {

        var originalAuthInfo = DataHelper.getAuthInfo();
        var invalidAuthInfo = DataHelper.getOtherAuthInfo(originalAuthInfo);
        var loginPage = new LoginPage();
        loginPage.campoVuotoLogin(invalidAuthInfo.getPassword());
    }

    @Test
    @DisplayName("пустое поле пароль")
    void campoPasswordVuoto() {

        var originalAuthInfo = DataHelper.getAuthInfo();
        var invalidAuthInfo = DataHelper.getOtherAuthInfo(originalAuthInfo);
        var loginPage = new LoginPage();
        loginPage.campoVuotoPassword(invalidAuthInfo.getLogin());
    }

    @Test
    @DisplayName("Вход с не верным кодом верификации")
    void incorrectVerificationCode() {

        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var invalidCode = DataHelper.getOtherVerificationCodeFor(authInfo);
        verificationPage.invalidVerify(invalidCode);
    }

    @Test
    @DisplayName("пустое поле верификации")
    void campoDiVerificaVuoto() {

        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var invalidCode = DataHelper.getOtherVerificationCodeFor(authInfo);
        verificationPage.submitWithEmptyCode(invalidCode);
    }
    @Test
    @DisplayName("пустое поле карты от куда")
    void campoMappaVuotoDaDoves() {

        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var validCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(validCode);

        var firstCard = DataHelper.getFirstCard();
        var transferPage = dashboardPage.selectCard(firstCard);
        dashboardPage = transferPage.campoMappaVuotoDaDove("500");
    }

}
