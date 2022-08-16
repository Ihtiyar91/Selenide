package ru.netology.del;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class ComplexElementsCardTests {
    @BeforeEach
    void open() {
        Selenide.open("http://localhost:9999/");
    }

    String limitDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouildSuccesComplexElementsCardDelivery() {
        $("[data-test-id=city] input").setValue("Ка");
        $$x("//div//span[@class='menu-item__control']").get(4).click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(BACK_SPACE);
        int dayDelivery = LocalDate.now().plusDays(7).getDayOfMonth();
        if (dayDelivery > 7) {
            $$("tr td").findBy(text(String.valueOf( dayDelivery))).click();
        }
            else {
                $("[data-step='1']").click();
            $$("tr td").findBy(text(String.valueOf( dayDelivery))).click();
            }
            $("[data-test-id=name] input").setValue("Иван Иванов-Сидоров");
            $("[data-test-id=phone] input").setValue("+79453219968");
            $("[data-test-id=agreement]").click();
            $(byText("Забронировать")).click();
            $("[data-test-id='notification'] .notification__title")
                    .shouldBe(Condition.appear, Duration.ofSeconds(15))
                    .shouldHave((text("Успешно!")));
            $("[data-test-id='notification'] .notification__content")
                    .shouldBe(Condition.visible)
                    .shouldHave(text("Встреча успешно забронирована на " + limitDate(7)));
        }
    }
