package com.globalsqa.bankingproject;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Отдельные тесты на логины разными юзерами")
public class LoginTestsWithoutOptimization {

    @BeforeEach
    void precondition() {
        Selenide.open("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Логин customer user Harry Potter")
    void hpLogin() {
        // нажать на "Customer Login"
        $(".btn-primary").shouldHave(text("Customer Login")).click();
        $("[name=myForm]").shouldHave(text("Your Name :"), Duration.ofSeconds(15));
        String currURL = getWebDriver().getCurrentUrl();
        assertTrue(currURL.equals("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/customer"));

        // Выбрать "Your Name"
        //Нажать кнопку "Login"
        $("#userSelect").selectOption("Harry Potter");
        $$("button").findBy(text("Login")).shouldBe(visible).click();

        //ER: successful login
        $$(".borderM.box.padT20.ng-scope").first().$("strong").shouldHave(text("Welcome "+ "Harry Potter" + " !!"));
    }

    @Test
    @DisplayName("Логин customer user Albus Dumbledore")
    void adLogin() {
        // нажать на "Customer Login"
        $(".btn-primary").shouldHave(text("Customer Login")).click();
        $("[name=myForm]").shouldHave(text("Your Name :"), Duration.ofSeconds(15));
        String currURL = getWebDriver().getCurrentUrl();
        assertTrue(currURL.equals("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/customer"));

        // Выбрать "Your Name"
        //Нажать кнопку "Login"
        $("#userSelect").selectOption("Albus Dumbledore");
        $$("button").findBy(text("Login")).shouldBe(visible).click();

        //ER: successful login
        $$(".borderM.box.padT20.ng-scope").first().$("strong").shouldHave(text("Welcome "+ "Albus Dumbledore" + " !!"));
    }

}