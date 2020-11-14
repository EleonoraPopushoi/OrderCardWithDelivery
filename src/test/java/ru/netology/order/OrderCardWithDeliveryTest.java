package ru.netology.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.generator.DataGenerator.*;

public class OrderCardWithDeliveryTest {
    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    String makeDate = makeDate(3);
    String makeName = makeName();
    String makePhone = makePhone();
    String makeCity = makeCity();
    String randomDate = randomDate();

    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {

        $("[data-test-id=city] input").setValue(makeCity);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=date] input").setValue(formatter.format(newDate));
        $("[data-test-id=name] input").setValue(makeName);
        $("[data-test-id=phone] input").setValue(makePhone);
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='notification'] .notification__content").waitUntil(exist,15000).shouldHave(exactText("Встреча успешно запланирована на " + formatter.format(newDate)));
        $("[data-test-id=date] input").sendKeys(randomDate);
        $("[data-test-id=date] input").setValue(randomDate);
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(exist,15000).shouldHave(exactText("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible);
        $("[data-test-id='replan-notification'] .button__text").click();
        $(withText("[data-test-id='notification'] .notification__content")).waitUntil(exist,15000).shouldHave(exactText("Встреча успешно запланирована на " + formatter.format(newDate)));
    }

    @Test
    void shouldNotSubmitWithoutCity() {
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue(makeName);
        $("[data-test-id=phone] input").setValue(makePhone);
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithoutName() {
        $("[data-test-id=city] input").setValue(makeCity);
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=phone] input").setValue(makePhone);
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithInvalidName() {
        $("[data-test-id=city] input").setValue(makeCity);
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Vasiliy Ivanov");
        $("[data-test-id=phone] input").setValue(makePhone);
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitWithoutPhone() {
        $("[data-test-id=city] input").setValue(makeCity);
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue(makeName);
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void shouldNotSendOverMaxPhoneNumber() {   //баг , при вводе некорректного номера телефона, приходит тодтвкрждение об успешном процессе.
        $("[data-test-id=city] input").setValue(makeCity);
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue(makeName);
        $("[data-test-id=phone] input").setValue("+7901234567890008");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldNotSendAboveMinPhoneNumber() {   //баг , при вводе некорректного номера телефона, приходит тодтвкрждение об успешном процессе.
        $("[data-test-id=city] input").setValue(makeCity);
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue(makeName);;
        $("[data-test-id=phone] input").setValue("7901234");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldNotSubmitWithoutCheckbox() {
        $("[data-test-id=city] input").setValue(makeCity);
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue(makeName);
        $("[data-test-id=phone] input").setValue(makePhone);
        $(".button__text").click();
        $(".checkbox_size_m.input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
