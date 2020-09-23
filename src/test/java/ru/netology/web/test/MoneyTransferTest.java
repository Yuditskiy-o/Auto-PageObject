package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoney() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val expectedBalance = dashboardPage.getExpectedBalanceOfCard1(amount);
        dashboardPage.transferToCard();
        val transferPage = new TransferPage();
        val transferInfo = DataHelper.getTransferInfo(String.valueOf(amount));
        transferPage.moneyTransfer(transferInfo);
        val finalBalance = dashboardPage.getCurrentBalanceOfCard1();
        assertEquals(expectedBalance, finalBalance);
    }

    @Test
    void shouldBeErrorWhenAmountEmpty() {
        val dashboardPage = new DashboardPage();
        dashboardPage.transferToCard();
        val transferPage = new TransferPage();
        val transferInfo = new DataHelper.TransferInfo("", "5559000000000002");
        transferPage.invalidMoneyTransfer(transferInfo);
    }

    @Test
    void shouldBeErrorWhenCardFieldEmpty() {
        val dashboardPage = new DashboardPage();
        dashboardPage.transferToCard();
        val transferPage = new TransferPage();
        val transferInfo = new DataHelper.TransferInfo("1500", "");
        transferPage.invalidMoneyTransfer(transferInfo);
    }

    @Test
    void shouldBeErrorWhenCardIrrelevant() {
        val dashboardPage = new DashboardPage();
        dashboardPage.transferToCard();
        val transferPage = new TransferPage();
        val transferInfo = new DataHelper.TransferInfo("1500", "5559000000000025");
        transferPage.invalidMoneyTransfer(transferInfo);
    }

    @Test
    void shouldTransferNothingWhenAmountZero() {
        val dashboardPage = new DashboardPage();
        val amount = 0;
        val expectedBalance = dashboardPage.getExpectedBalanceOfCard1(amount);
        dashboardPage.transferToCard();
        val transferPage = new TransferPage();
        val transferInfo = DataHelper.getTransferInfo(String.valueOf(amount));
        transferPage.moneyTransfer(transferInfo);
        val finalBalance = dashboardPage.getCurrentBalanceOfCard1();
        assertEquals(expectedBalance, finalBalance);
    }

    @Test
    void shouldBeErrorWhenNotEnoughMoneyForTransfer() {
        val dashboardPage = new DashboardPage();
        val amount = dashboardPage.getCurrentBalanceOfCard2() + 1;
        dashboardPage.getExpectedBalanceOfCard2(amount);
        dashboardPage.transferToCard();
        val transferPage = new TransferPage();
        val transferInfo = DataHelper.getTransferInfo(String.valueOf(amount));
        transferPage.invalidMoneyTransfer(transferInfo);
    }
}

