package ru.netology.service.data;

import lombok.Value;
import org.junit.jupiter.api.DisplayName;

@DisplayName(" 5. логин пароль" +
        "код верификации" +
        "номера карт")

public class DataHelper {
    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "qwerty123");
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static VerificationCode getOtherVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("13254");
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static CardInfo getOtherCard() {
        return new CardInfo("5559 0000 0000 0003", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class CardInfo {
        String cardnumber;
        String testId;
    }
}




