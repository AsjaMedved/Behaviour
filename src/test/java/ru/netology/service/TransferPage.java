package ru.netology.service;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@DisplayName(" 4. страница перевода" +
        "перевод баланса между картами" +
        "сообщния об ошибке")

public class TransferPage {

    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement error = $("[data-test-id='error-notification'] .notification__content");

    public TransferPage() {
        amountField.shouldBe(visible);
    }

    public DashboardPage makeValidTransfer(String amount, String fromCardNumber) {
        amountField.setValue(amount);
        fromField.setValue(fromCardNumber);
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage campoMappaVuotoDaDove(String amount) {
        amountField.setValue(amount);
        fromField.setValue("");
        transferButton.click();
        error.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
        return new DashboardPage();
    }
}
