package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @Test
    void shouldTransferMoney() {
        open("http://localhost:9999");
        val amount = 1000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val dashboardPage = new DashboardPage();
        val expectedBalance = dashboardPage.getExpectedBalanceOfCard1(amount);
        dashboardPage.transferToCard1();
        val transferPage = new TransferPage();
        val transferInfo = DataHelper.getTransferInfo(String.valueOf(amount));
        transferPage.moneyTransfer(transferInfo);
        val finalBalance = dashboardPage.getCurrentBalanceOfCard1();
        assertEquals(expectedBalance, finalBalance);
    }

}

