package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999/");
    }

    public String dateGenerate(int days) {
        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    @Test
    void successTest() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Успешно!\nВстреча успешно забронирована на " + date));
        $x("//button[contains(@class,'notification__closer')]").click();
    }

    @Test
    void emptyCity() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Томас Джефферсон");
        form.$("[data-test-id='phone'] input").setValue("+79876543223");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void validCompositeCity() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Южно-Сахалинск");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Томас Джефферсон");
        form.$("[data-test-id='phone'] input").setValue("+79876543223");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!\nВстреча успешно забронирована на " + date));
        $x("//button[contains(@class,'notification__closer')]").click();
    }

    @Test
    void cityWithoutDelivery() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Улукуламск");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void invalidCityNameFromValidNameWithoutHyphen() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Южно Сахалинск");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void validCityWithSpaceAtFirst() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue(" Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void validCityLatin() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Moscow");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void invalidCityDigits() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("80401");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void invalidCitySpecialSymbols() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("<>");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyDate() {
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void invalidZeroDate() {
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue("00.00.0000");
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Неверно введена дата"));
    }

    @Disabled
    @Test
    void invalidDateZeroYear() {
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue("11.05.0000");
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void invalidPastDate() {
        String date = LocalDate.now().plusDays(5).minusMonths(1).format(DateTimeFormatter.
                ofPattern("dd.MM.yyyy"));
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidCurrentDate() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void emptyName() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidNameOnlySpace() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue(" ");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidNameLatin() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Andy Dyfrayn");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                        "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void validNameCompositeFamily() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Иван Тарасов-Гаевский");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно!\nВстреча успешно забронирована на " + date));
        $x("//button[contains(@class,'notification__closer')]").click();
    }

    @Test
    void invalidNameSpecialSymbolOnly() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("@");
        form.$("[data-test-id='phone'] input").setValue("+79998887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                        "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void emptyPhone() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidFormatPhoneWithBrackets() {
        String date = dateGenerate(3);
        SelenideElement form = $x("//form");

        form.$("[data-test-id='city'] input").setValue("Москва");
//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue("Питер Паркер");
        form.$("[data-test-id='phone'] input").setValue("+7(999)8887766");
        form.$("[data-test-id='agreement']").click();
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void emptyForm() {
        SelenideElement form = $x("//form");

//        form.$("[data-test-id='date'] input").clear();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//button[contains(@class,'button_view_extra')]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

 }
