package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor() {
        return new VerificationCode("12345");
    }

    @Value
    public static class TransferInfo {
        String amount;
        String card;
    }

    public static TransferInfo getTestCardInfo(String amount, String card) {
        return new TransferInfo(amount, card);
    }

    public static TransferInfo getDefaultFirstCardInfo(String amount) {
        return new TransferInfo(amount, "5559000000000001");
    }

    public static TransferInfo getDefaultSecondCardInfo(String amount) {
        return new TransferInfo(amount, "5559000000000002");
    }

    public static int getExpectedBalanceIfBalanceIncreased(int balance, int amount) {
        return balance + amount;
    }

    public static int getExpectedBalanceIfBalanceDecreased(int balance, int amount) {
        return balance - amount;
    }
}
