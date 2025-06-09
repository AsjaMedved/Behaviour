package ru.netology.service.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@DisplayName(" 1. валидный / не валидный ввод логин пароль")

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement buttonField = $$("button").findBy(Condition.text("Продолжить"));
    private SelenideElement error = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement campoVuotosLogin = $("[data-test-id='login'].input_invalid .input__sub");
    private SelenideElement campoVuotosPassword = $("[data-test-id='password'].input_invalid .input__sub");

    public void login(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        buttonField.click();
    }

    public VerificationPage validLogin(String login, String password) {
        login(login, password);
        return new VerificationPage();
    }

    public void checkErrorIsVisibleLogin() {
        campoVuotosLogin.shouldBe(visible).shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    public void checkErrorIsVisiblePassword() {
        campoVuotosPassword.shouldBe(visible).shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    public void checkErrorIsVisibleNotification() {
        error.shouldBe(visible).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}
