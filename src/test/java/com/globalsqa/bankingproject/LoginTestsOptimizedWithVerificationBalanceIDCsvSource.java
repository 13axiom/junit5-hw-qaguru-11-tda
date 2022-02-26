package com.globalsqa.bankingproject;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Параметрезированный тест на логин разными юзерами с проверкой ID баланса по-умолчанию")
public class LoginTestsOptimizedWithVerificationBalanceIDCsvSource {

    @BeforeEach
    void precondition() {
        Selenide.open("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @CsvSource({
            "Harry Potter, 1004",
            "Albus Dumbledore,1010"
    })

    @ParameterizedTest(name = "Логин customer user {0} с баланс ID:{1}")
    void customerLogin(String customerName, String expectedBalanceId) {
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

        //Проверка ID баланса по-умолчанию
        $("#accountSelect").shouldHave(text(expectedBalanceId));

    }

}
