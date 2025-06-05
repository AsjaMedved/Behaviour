package ru.netology.service.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.service.page.DashboardPage;
import ru.netology.service.data.DataHelper;
import ru.netology.service.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    private DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);

        open("http://localhost:9999");

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Перевод суммы, превышающей текущий баланс")
    void trasferimentoDellImportoSuperioreAlSaldo() {

        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();

        var actualFirstBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondBalance = dashboardPage.getCardBalance(secondCard);

        var amount = String.valueOf(100_000);
        var transferPage = dashboardPage.selectCard(firstCard);
        dashboardPage = transferPage.makeValidTransfer(amount, secondCard.getCardnumber());

        var actualFirstdBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondsBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(actualSecondBalance, actualSecondsBalance);
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
    @DisplayName("пустое поле карты от куда")
    void campoMappaVuotoDaDoves() {

        var firstCard = DataHelper.getFirstCard();
        var transferPage = dashboardPage.selectCard(firstCard);
        transferPage.transferWithEmptyFromField("1000");
    }

    @Test
    @DisplayName("не верные данные карты")
    void incorrectCardInformation() {

        var firstCard = DataHelper.getOtherCard();
        var transferPage = dashboardPage.selectCard(firstCard);
        transferPage.transferWithInvalidCard("1000", firstCard.getCardnumber());
    }

    @Test
    @DisplayName("Пустое поле суммы")
    void emptyAmountField() {

        var firstCard = DataHelper.getFirstCard();
        var transferPage = dashboardPage.selectCard(firstCard);
        transferPage.transferWithEmptyAmount( firstCard.getCardnumber());
    }

    @Test
    @DisplayName("Проверка кнопки отмена")
    void checkingTheCancelButton() {

        var firstCard = DataHelper.getFirstCard();
        var transferPage = dashboardPage.selectCard(firstCard);
        transferPage.cancelTransfer("1000", firstCard.getCardnumber());
    }

}

