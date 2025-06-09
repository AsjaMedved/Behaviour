package ru.netology.service.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import ru.netology.service.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@DisplayName(" 2. верификация проверочного кода")

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement continueButton = $$("button").findBy(Condition.text("Продолжить"));
    private SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement codeFieldError = $("[data-test-id='code'].input_invalid .input__sub");

    public VerificationPage() {
        codeField.shouldBe(Condition.visible);
    }

    public void verify(String code) {
        codeField.setValue(code);
        continueButton.click();
    }

    public DashboardPage validVerify(DataHelper.VerificationCode code) {
        verify(code.getCode());
        return new DashboardPage();
    }

    public void incorrectVerificationCode() {
        errorNotification.shouldBe(Condition.visible).shouldHave(Condition.text("Ошибка! Неверно указан код! Попробуйте ещё раз"));
    }

    public void emptyVerificationField() {
        codeFieldError.shouldBe(Condition.visible).shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}
