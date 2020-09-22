package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final SelenideElement cardButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage transferToCard() {
        cardButton.click();
        return new TransferPage();
    }

    public int getCurrentBalanceOfCard1() {
        val balance = $(".list__item [data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").getText();
        val substring = balance.split(",");
        val getArraysLength = substring[substring.length - 1];
        val value = getArraysLength.replaceAll("\\D+", "");
        return Integer.parseInt(value);
    }

    public int getCurrentBalanceOfCard2() {
        val balance = $(".list__item [data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").getText();
        val substring = balance.split(",");
        val getArraysLength = substring[substring.length - 1];
        val value = getArraysLength.replaceAll("\\D+", "");
        return Integer.parseInt(value);
    }

    public int getExpectedBalanceOfCard1(int amount) {
        int currentBalance = getCurrentBalanceOfCard1();
        return currentBalance + amount;
    }

    public int getExpectedBalanceOfCard2(int amount) {
        int currentBalance = getCurrentBalanceOfCard2();
        return currentBalance + amount;
    }
}
