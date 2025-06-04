package ru.netology.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    private DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Перевод со второй карты на первую")
    void shouldTransferMoneyFromSecondToFirstCard() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var amount = "500";

        var initialFirstBalance = dashboardPage.getCardBalance(firstCard);
        var initialSecondBalance = dashboardPage.getCardBalance(secondCard);

        var transferPage = dashboardPage.selectCard(firstCard);
        dashboardPage = transferPage.makeValidTransfer(amount, secondCard.getCardnumber());

        var expectedFirstBalance = initialFirstBalance + Integer.parseInt(amount);
        var expectedSecondBalance = initialSecondBalance - Integer.parseInt(amount);

        var actualFirstBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(expectedFirstBalance, actualFirstBalance);
        assertEquals(expectedSecondBalance, actualSecondBalance);
    }

    @Test
    @DisplayName("Перевод с первой карты на вторую")
    void shouldTransferMoneyFromFirstToSecondCard() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var amount = "1000";

        var initialFirstBalance = dashboardPage.getCardBalance(firstCard);
        var initialSecondBalance = dashboardPage.getCardBalance(secondCard);

        var transferPage = dashboardPage.selectCard(secondCard);
        dashboardPage = transferPage.makeValidTransfer(amount, firstCard.getCardnumber());

        var expectedFirstBalance = initialFirstBalance - Integer.parseInt(amount);
        var expectedSecondBalance = initialSecondBalance + Integer.parseInt(amount);

        var actualFirstBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(expectedFirstBalance, actualFirstBalance);
        assertEquals(expectedSecondBalance, actualSecondBalance);
    }

    @Test
    @DisplayName("Перевод суммы превышающий текущей суммы")
    void trasferimentoDellImportoSuperioreAlSaldo() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var amount = "100000";

        var transferPage = dashboardPage.selectCard(secondCard);
        dashboardPage = transferPage.makeValidTransfer(amount, firstCard.getCardnumber());

        var actualFirstBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(0, actualFirstBalance);
        assertEquals(0, actualSecondBalance);
    }

}