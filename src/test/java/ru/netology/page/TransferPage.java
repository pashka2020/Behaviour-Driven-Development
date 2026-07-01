package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement heading = $("h1").shouldHave(Condition.text("Пополнение карты"));

    public TransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage makeTransfer(String amount, String fromCard) {
        amountField.setValue(amount);
        fromField.setValue(fromCard);
        transferButton.click();
        return new DashboardPage();
    }
}
