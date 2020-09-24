package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @Order(1)
    void shouldTransferMoneyFromSecondToFirst() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val expectedBalanceOfFirstCard = dashboardPage.getCurrentBalanceOfFirstCard();
        val expectedBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getDefaultSecondCardInfo(String.valueOf(amount));
        transferPage.moneyTransfer(transferInfo);
        val balanceOfFirstCard = getExpectedBalanceIfBalanceIncreased(expectedBalanceOfFirstCard, amount);
        val balanceOfSecondCard = getExpectedBalanceIfBalanceDecreased(expectedBalanceOfSecondCard, amount);
        val finalBalanceOfFirstCard = dashboardPage.getCurrentBalanceOfFirstCard();
        val finalBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceOfFirstCard, finalBalanceOfFirstCard);
        assertEquals(balanceOfSecondCard, finalBalanceOfSecondCard);
    }

    @Test
    @Order(2)
    void shouldTransferMoneyFromFirstToSecond() {
        val dashboardPage = new DashboardPage();
        val amount = 600;
        val expectedBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.transferToSecondCard();
        val transferInfo = getDefaultFirstCardInfo(String.valueOf(amount));
        transferPage.moneyTransfer(transferInfo);
        val balanceOfSecondCard = getExpectedBalanceIfBalanceIncreased(expectedBalanceOfSecondCard, amount);
        val finalBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceOfSecondCard, finalBalanceOfSecondCard);

    }

    @Test
    @Order(3)
    void shouldBeErrorWhenAmountEmpty() {
        val dashboardPage = new DashboardPage();
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getTestCardInfo("", "5559000000000002");
        transferPage.moneyTransfer(transferInfo);
        transferPage.invalidMoneyTransfer();
    }

    @Test
    @Order(4)
    void shouldBeErrorWhenCardFieldEmpty() {
        val dashboardPage = new DashboardPage();
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getTestCardInfo("1000", "");
        transferPage.moneyTransfer(transferInfo);
        transferPage.invalidMoneyTransfer();
    }

    @Test
    @Order(5)
    void shouldBeErrorWhenCardIrrelevant() {
        val dashboardPage = new DashboardPage();
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getTestCardInfo("1000", "5559000000000022");
        transferPage.moneyTransfer(transferInfo);
        transferPage.invalidMoneyTransfer();
    }

    @Test
    @Order(6)
    void shouldTransferNothingWhenAmountZero() {
        val dashboardPage = new DashboardPage();
        val amount = 0;
        val expectedBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        val transferPage = dashboardPage.transferToSecondCard();
        val transferInfo = getDefaultFirstCardInfo(String.valueOf(amount));
        transferPage.moneyTransfer(transferInfo);
        val balanceOfSecondCard = getExpectedBalanceIfBalanceIncreased(expectedBalanceOfSecondCard, amount);
        val finalBalanceOfSecondCard = dashboardPage.getCurrentBalanceOfSecondCard();
        assertEquals(balanceOfSecondCard, finalBalanceOfSecondCard);
    }

    @Test
    @Order(7)
    void shouldBeErrorWhenNotEnoughMoneyForTransfer() {
        val dashboardPage = new DashboardPage();
        val amount = dashboardPage.getCurrentBalanceOfSecondCard() + 1;
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getDefaultSecondCardInfo(String.valueOf(amount));
        transferPage.moneyTransfer(transferInfo);
        transferPage.invalidMoneyTransfer();
    }
}

