package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();

        int firstCardBalance = dashboardPage.getCardBalance(firstCardInfo.getCardNumber());
        int secondCardBalance = dashboardPage.getCardBalance(secondCardInfo.getCardNumber());

        int amount = 1000;

        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo.getCardNumber());
        dashboardPage = transferPage.makeTransfer(String.valueOf(amount), firstCardInfo.getCardNumber());

        int expectedFirstCardBalance = firstCardBalance - amount;
        int expectedSecondCardBalance = secondCardBalance + amount;

        int actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo.getCardNumber());
        int actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo.getCardNumber());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }
}
