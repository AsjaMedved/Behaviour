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

    public  DashboardPage cancelTransfer (String amount, String fromCardNumber) {
        amountField.setValue(amount);
        fromField.setValue(fromCardNumber);
        cancelButton.click();
        return new DashboardPage();
    }

    private void Transfer(String amount, String fromCardNumber) {
        amountField.setValue(amount);
        fromField.setValue(fromCardNumber);
        transferButton.click();
    }

    public DashboardPage makeValidTransfer(String amount, String fromCardNumber) {
        Transfer(amount, fromCardNumber);
        return new DashboardPage();
    }

    public TransferPage transferWithEmptyFromField(String amount) {
        Transfer(amount, "");
        error.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
        return this;
    }

    public TransferPage transferWithEmptyAmount(String fromCardNumber) {
        Transfer("", fromCardNumber);
        error.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
        return this;
    }

    public TransferPage transferWithInvalidCard(String amount, String invalidCardNumber) {
        Transfer(amount, invalidCardNumber);
        error.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
        return this;
    }

}
