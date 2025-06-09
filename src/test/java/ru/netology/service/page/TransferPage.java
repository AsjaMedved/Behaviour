package ru.netology.service.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@DisplayName(" 4. страница перевода" +
        "перевод баланса между картами" +
        "сообщния об ошибке")

public class TransferPage {

    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement error = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement cancelButton = $$("button").findBy(Condition.text("Отмена"));

    public TransferPage() {
        amountField.shouldBe(visible);
    }

    public void transfer(String amount, String fromCardNumber) {
        amountField.setValue(amount);
        fromField.setValue(fromCardNumber);
    }

    private void transferButton () {
                transferButton.click();
    }

    public void cancelButton () {
                cancelButton.click();
    }

    public void checkErrorIsVisible() {
        error.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
    }

    public DashboardPage makeValidTransfer(String amount, String fromCardNumber) {
        transfer(amount, fromCardNumber);
        transferButton();
        return new DashboardPage();
    }



//
//    public TransferPage makeTransferExpectingError(String amount, String fromCardNumber) {
//        transfer(amount, fromCardNumber);
//        transferButton();
//        checkErrorIsVisible();
//        return this;
//    }
//
//    public DashboardPage cancelTransfer(String amount, String fromCardNumber) {
//        transfer(amount, fromCardNumber);
//        cancelButton();
//        return new DashboardPage();
//    }
}
