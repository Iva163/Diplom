package ru.netology.test;

import org.junit.jupiter.api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PageTravel;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.*;

public class TestPay {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @AfterEach
    public void cleanData() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Card number with status APPROVED for payment" )
    void shouldSuccessfulPayWithAPPROVEDCard() {

        String status = "APPROVED";
        var page = new PageTravel();
        int price = page.getPriceInKops();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationOk();
        assertEquals(status, SQLHelper.getPaymentCardStatus());
        String id = getPaymentIdFromOrder();
        assertEquals(1,checkIdOrder(id));
        assertEquals(price, getAmount());

    }

    @Test
    @DisplayName("Card number with status APPROVED for credit")
    void shouldSuccessfulBuyInCreditWithAPPROVEDCard() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationOk();
        assertEquals(status, SQLHelper.getCreditCardStatus());
        String id = getCreditIdFromOrder();
        assertEquals(1,checkIdOrder(id));

    }

    @Test
    @DisplayName("Card number with status DECLINED for payment" )
    void shouldErrorPayWithDECLINEDCard() {

        String status = "DECLINED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationError();
        assertEquals(status, SQLHelper.getPaymentCardStatus());
        String id = getPaymentIdFromOrder();
        assertEquals(1,checkIdOrder(id));

    }

    @Test
    @DisplayName("Card number with status DECLINED for credit")
    void shouldErrorBuyInCreditWithDECLINEDCard() {

        String status = "DECLINED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationError();
        assertEquals(status, SQLHelper.getCreditCardStatus());
        String id = getCreditIdFromOrder();
        assertEquals(1,checkIdOrder(id));

    }

    @Test
    @DisplayName("Empty form for payment" )
    void shouldMessageFilInFieldInPay() {

        var page = new PageTravel();
        page.buy();
        page.clickContinue();
        page.notificationMessageFieldInFill(1);
        page.notificationMessageFieldInFill(2);
        page.notificationMessageFieldInFill(3);
        page.notificationMessageFieldInFill(4);
        page.notificationMessageFieldInFill(5);

    }

    @Test
    @DisplayName("Empty form for credit" )
    void shouldMessageFilInFieldInCredit() {

        var page = new PageTravel();
        page.buyInCredit();
        page.clickContinue();
        page.notificationMessageFieldInFill(1);
        page.notificationMessageFieldInFill(2);
        page.notificationMessageFieldInFill(3);
        page.notificationMessageFieldInFill(4);
        page.notificationMessageFieldInFill(5);

    }

    @Test
    @DisplayName("Card number with status INVALID for payment" )
    void shouldErrorPayWithINVALIDCard() {

        String status = "INVALID";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationError();
        assertEquals(0, getOrderCount());

    }

    @Test
    @DisplayName("Card number with status INVALID for credit" )
    void shouldErrorCreditWithINVALIDCard() {

        String status = "INVALID";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationError();
        assertEquals(0, getOrderCount());

    }

    @Test
    @DisplayName("Card number with status ZERO for payment" )
    void shouldErrorPayWithZEROCard() {

        String status = "ZERO";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationError();
        assertEquals(0, getOrderCount());

    }

    @Test
    @DisplayName("Card number with status ZERO for credit" )
    void shouldErrorCreditWithZEROCard() {

        String status = "ZERO";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationError();
        assertEquals(0, getOrderCount());

    }

    @Test
    @DisplayName("Card number with status FIFTEEN for payment" )
    void shouldErrorPayWithFIFTEENCard() {

        String status = "FIFTEEN";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(1);

    }

    @Test
    @DisplayName("Card number with status FIFTEEN for credit" )
    void shouldErrorCreditWithFIFTEENCard() {

        String status = "FIFTEEN";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(1);

    }

    @Test
    @DisplayName("Month ZERO for payment" )
    void shouldErrorZeroMonthForPay() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.getZero());
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCartDate(2);

    }

    @Test
    @DisplayName("Month ZERO for credit" )
    void shouldErrorZeroMonthForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.getZero());
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCartDate(2);

    }

    @Test
    @DisplayName("Month Over for payment" )
    void shouldErrorOverMonthForPay() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.getMonthOver());
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCartDate(2);

    }

    @Test
    @DisplayName("Month Over for credit" )
    void shouldErrorOverMonthForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.getMonthOver());
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCartDate(2);

    }

    @Test
    @DisplayName("Month One Digit for payment" )
    void shouldErrorOneDigitMonthForPay() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.getMonthOneDig());
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(2);

    }

    @Test
    @DisplayName("Month One Digit for credit" )
    void shouldErrorOneDigitMonthForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.getMonthOneDig());
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(2);

    }

    @Test
    @DisplayName("Year ZERO for payment" )
    void shouldErrorZeroYearForPay() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.getZero());
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCardDateExpired(3);

    }

    @Test
    @DisplayName("Year ZERO for credit" )
    void shouldErrorZeroYearForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.getZero());
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCardDateExpired(3);

    }

    @Test
    @DisplayName("Year More for payment" )
    void shouldErrorMoreYearForPay() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(10));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCartDate(3);

    }

    @Test
    @DisplayName("Year More for credit" )
    void shouldErrorMoreYearForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(10));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCartDate(3);

    }

    @Test
    @DisplayName("Year Less for payment" )
    void shouldErrorLessYearForPay() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearMinus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCardDateExpired(3);

    }

    @Test
    @DisplayName("Year Less for credit" )
    void shouldErrorLessYearForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearMinus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageErrorCardDateExpired(3);;

    }

    @Test
    @DisplayName("Cyrillic Name  for payment" )
    void shouldErrorCyrillicNameForPayment() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolderCyrillic());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(4);

    }

    @Test
    @DisplayName("Cyrillic Name for credit")
    void shouldErrorCyrillicNameForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolderCyrillic());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(4);

    }

    @Test
    @DisplayName("Number Name for payment" )
    void shouldErrorNumberNameForPayment() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolderNumeric());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(4);

    }

    @Test
    @DisplayName("Number Name for credit")
    void shouldErrorNumberNameForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolderNumeric());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(4);

    }

    @Test
    @DisplayName("One letter Name for payment" )
    void shouldErrorOneLetterNameForPayment() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolderOneSymbol());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(4);

    }

    @Test
    @DisplayName("One letter Name for credit")
    void shouldErrorOneLetterNameForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolderOneSymbol());
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(4);

    }

    @Test
    @DisplayName("Special characters Name for payment" )
    void shouldErrorSpecCharNameForPayment() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolderSpecChar(5));
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(4);

    }

    @Test
    @DisplayName("Special characters Name for credit")
    void shouldErrorSpecCharNameForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolderSpecChar(5));
        page.inputCVC(3);
        page.clickContinue();
        page.notificationMessageWrongFormat(4);

    }

    @Test
    @DisplayName("Two digits CVC for payment" )
    void shouldErrorTwoDigCVCForPayment() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(2);
        page.clickContinue();
        page.notificationMessageWrongFormat(5);

    }

    @Test
    @DisplayName("Two digits CVC for credit")
    void shouldErrorTwoDigCVCForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(2);
        page.clickContinue();
        page.notificationMessageWrongFormat(5);

    }

    @Test
    @DisplayName("One digits CVC for payment" )
    void shouldErrorOneDigCVCForPayment() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buy();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(1);
        page.clickContinue();
        page.notificationMessageWrongFormat(5);

    }

    @Test
    @DisplayName("One digits CVC for credit")
    void shouldErrorOneDigCVCForCredit() {

        String status = "APPROVED";
        var page = new PageTravel();
        page.buyInCredit();
        page.inputNumberCard(status);
        page.inputMonth(DataHelper.generateMonthPlus(0));
        page.inputYear(DataHelper.generateYearPlus(1));
        page.inputOwner(DataHelper.generateHolder());
        page.inputCVC(1);
        page.clickContinue();
        page.notificationMessageWrongFormat(5);

    }




}
