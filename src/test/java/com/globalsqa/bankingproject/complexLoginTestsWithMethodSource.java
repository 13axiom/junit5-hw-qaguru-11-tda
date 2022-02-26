package com.globalsqa.bankingproject;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Параметрезированный тест на логин разными юзерами c несколькими проверками")
public class complexLoginTestsWithMethodSource {

    @BeforeEach
    void precondition() {
        Selenide.open("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    static Stream<Arguments> customerLoginPlusDeposit() {
        return Stream.of(
                Arguments.of("Harry Potter", "1004", 200),
                Arguments.of("Albus Dumbledore", "1010",100)
        );
    }

    @MethodSource("customerLoginPlusDeposit")
    @ParameterizedTest(name = "Сумма на балансе соответсвует сумме депозита = {2} у customer user: {0}")
    void customerLoginPlusDeposit(String customerName, String expectedBalanceId, int depSumm) {
        // нажать на "Customer Login"
        $(".btn-primary").shouldHave(text("Customer Login")).click();
        $("[name=myForm]").shouldHave(text("Your Name :"), Duration.ofSeconds(15));
        String currURL = getWebDriver().getCurrentUrl();
        assertTrue(currURL.equals("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/customer"));

        // Выбрать "Your Name"
        //Нажать кнопку "Login"
        $("#userSelect").selectOption(customerName);
        $$("button").findBy(text("Login")).shouldBe(visible).click();

        //ER: successful login
        $$(".borderM.box.padT20.ng-scope").first().$("strong").shouldHave(text("Welcome "+ customerName + " !!"));

        //Проверка ID баланса по-умолчанию и начального баланса = 0
        $$(".borderM.box.padT20.ng-scope").findBy(text("Account Number")).shouldHave(text(expectedBalanceId));
        $$(".borderM.box.padT20.ng-scope").findBy(text("Balance")).shouldHave(text("Balance : 0"));

        //делаем деп
        $("[ng-class=btnClass2]").click();
        $("[name=myForm]").shouldHave(text("Amount to be Deposited :"));
        $("[placeholder=amount]").setValue(String.valueOf(depSumm));
        $(".btn-default").click();

        //ASSERT: Deposit Successful (RED)
        $("[ng-show=message]").shouldHave(text("Deposit Successful"))
                .shouldHave(cssValue("color","rgba(255, 0, 0, 1)"));

        //ER: Проверяем изменение баланса
        $$(".borderM.box.padT20.ng-scope").findBy(text("Balance")).shouldHave(text("Balance : "+depSumm));

    }

}
