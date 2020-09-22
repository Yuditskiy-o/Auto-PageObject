package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement card1 = $("[data-test-id=92df3f1c-a033-48e6-8390-206f6b1f56c0] .button");
    private SelenideElement card2 = $("[data-test-id=0f3f5c2a-249e-4c3d-8287-09f7a039391d] .button");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public TransferPage transferToCard1() {
        card1.click();
        return new TransferPage();
    }

    public TransferPage transferToCard2() {
        card2.click();
        return new TransferPage();
    }

    public int getCurrentBalanceOfCard1() {
        String balance = $(".list__item [data-test-id=92df3f1c-a033-48e6-8390-206f6b1f56c0]").getText();
        String[] substring = balance.split(",");
        String getArraysLength = substring[substring.length - 1];
        String getBalance1WithRegex = getArraysLength.replaceAll("\\d+", "");
        return Integer.parseInt(getBalance1WithRegex);
    }

    public int getCurrentBalanceOfCard2() {
        String balance = $(".list__item [data-test-id=0f3f5c2a-249e-4c3d-8287-09f7a039391d]").getText();
        String[] substring = balance.split(",");
        String getArraysLength = substring[substring.length - 1];
        String getBalance2WithRegex = getArraysLength.replaceAll("\\d+", "");
        return Integer.parseInt(getBalance2WithRegex);
    }

    public int getExpectedBalanceOfCard1(int transfer) {
        int currentBalance = getCurrentBalanceOfCard1();
        return currentBalance + transfer;
    }

    public int getExpectedBalanceOfCard2(int transfer) {
        int currentBalance = getCurrentBalanceOfCard2();
        return currentBalance + transfer;
    }
}
