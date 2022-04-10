package atm;

import java.math.BigInteger;
import java.util.List;

import atm.infrastructure.exception.InsufficientBanknoteAmountException;
import atm.infrastructure.exception.UnacceptableInputAmountException;
import atm.model.Banknote;
import atm.model.BanknoteType;
import atm.service.Atm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtmTest {

    private Atm atm;

    @BeforeEach
    public void initEach() {
        atm = new Atm();
    }

    @Test
    @DisplayName("Очень неудобно сконструированный (ради интереса -> больше так не буду) тест на приём банкнот")
    void testAcceptBanknotes() {
        assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TEN).findFirst().get().getNumber(), BigInteger.ZERO);

        assertDoesNotThrow(() ->
                atm.acceptBanknotes(List.of(new Banknote(BanknoteType.TEN, BigInteger.ONE),
                        new Banknote(BanknoteType.FIFTY, BigInteger.TWO),
                        new Banknote(BanknoteType.HUNDRED, BigInteger.valueOf(3)),
                        new Banknote(BanknoteType.TWO_HUNDRED, BigInteger.valueOf(4)),
                        new Banknote(BanknoteType.FIVE_HUNDRED, BigInteger.valueOf(5)),
                        new Banknote(BanknoteType.THOUSAND, BigInteger.valueOf(6)),
                        new Banknote(BanknoteType.TWO_THOUSAND, BigInteger.valueOf(7)),
                        new Banknote(BanknoteType.FIVE_THOUSAND, BigInteger.valueOf(8)))));

        assertAll(
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TEN).findFirst().get().getNumber(), BigInteger.ONE),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.FIFTY).findFirst().get().getNumber(), BigInteger.TWO),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.HUNDRED).findFirst().get().getNumber(), BigInteger.valueOf(3)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TWO_HUNDRED).findFirst().get().getNumber(), BigInteger.valueOf(4)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.FIVE_HUNDRED).findFirst().get().getNumber(), BigInteger.valueOf(5)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.THOUSAND).findFirst().get().getNumber(), BigInteger.valueOf(6)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TWO_THOUSAND).findFirst().get().getNumber(), BigInteger.valueOf(7)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.FIVE_THOUSAND).findFirst().get().getNumber(), BigInteger.valueOf(8))
        );
    }

    @Test
    @DisplayName("Тест на проверку валидности запрашиваемой суммы")
    void testGetBanknotesThrowsWhenInvalidRequestValue() {
        UnacceptableInputAmountException unacceptableInputAmountException = assertThrows(UnacceptableInputAmountException.class, () -> atm.getBanknotes(BigInteger.ZERO));
        assertEquals("٩(ఠ益ఠ)۶ Amount should be greater that zero ٩(ఠ益ఠ)۶", unacceptableInputAmountException.getMessage());
        NumberFormatException numberFormatException = assertThrows(NumberFormatException.class, () -> atm.getBanknotes(BigInteger.valueOf(123)));
        assertEquals("(ಡ‸ಡ) Decimal value is required (ಡ‸ಡ)", numberFormatException.getMessage());
        InsufficientBanknoteAmountException insufficientBanknoteAmountException = assertThrows(InsufficientBanknoteAmountException.class, () -> atm.getBanknotes(BigInteger.valueOf(1230)));
        assertEquals("[ ± _ ± ] Sorry, we're running out of money, max sum that may be provided is 0 [ ± _ ± ]", insufficientBanknoteAmountException.getMessage());
    }

    @Test
    @DisplayName("Тест на проверку максимальной запрашиваемой суммы")
    void testGetBanknotesMaxSum() {
        atm.acceptBanknotes( List.of(
                new Banknote(BanknoteType.FIVE_THOUSAND, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.TWO_THOUSAND, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.THOUSAND, BigInteger.valueOf(3)),
                new Banknote(BanknoteType.TWO_HUNDRED, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.HUNDRED, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.FIFTY, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.TEN, BigInteger.valueOf(2))));

        assertEquals(BigInteger.valueOf(15470),atm.getAccountBalance());
        assertThrows(InsufficientBanknoteAmountException.class, () -> atm.getBanknotes(BigInteger.valueOf(15480)), "[ ± _ ± ] Sorry, we're running out of money, max sum that may be provided is 15470 [ ± _ ± ]");
        assertEquals(BigInteger.valueOf(15470),atm.getAccountBalance());
    }

    @Test
    @DisplayName("Тест на проверку запрашиваемой суммы без ошибок")
    void testGetValidBanknotes() {
        atm.acceptBanknotes( List.of(
                new Banknote(BanknoteType.FIVE_THOUSAND, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.TWO_THOUSAND, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.THOUSAND, BigInteger.valueOf(3)),
                new Banknote(BanknoteType.TWO_HUNDRED, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.HUNDRED, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.FIFTY, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.TEN, BigInteger.valueOf(2))));

        assertEquals(BigInteger.valueOf(15470),atm.getAccountBalance());
        assertDoesNotThrow(() -> atm.getBanknotes(BigInteger.valueOf(2_000)));
        assertEquals(BigInteger.valueOf(13470),atm.getAccountBalance());
    }

    @Test
    @DisplayName("Тест на проверку запрашиваемой суммы без ошибок и получение самых крупных купюр в первую очередь")
    void testGetBanknotesWithTheBiggestDenomination() {
        atm.acceptBanknotes( List.of(
                new Banknote(BanknoteType.FIVE_THOUSAND, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.TWO_THOUSAND, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.THOUSAND, BigInteger.valueOf(3)),
                new Banknote(BanknoteType.TWO_HUNDRED, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.HUNDRED, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.FIFTY, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.TEN, BigInteger.valueOf(2))));

        assertEquals(BigInteger.valueOf(15470),atm.getAccountBalance());
        assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.FIVE_THOUSAND).findFirst().get().getNumber(), BigInteger.TWO);
        assertDoesNotThrow(() -> atm.getBanknotes(BigInteger.valueOf(7_000)));
        assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.FIVE_THOUSAND).findFirst().get().getNumber(), BigInteger.ONE);
        assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TWO_THOUSAND).findFirst().get().getNumber(), BigInteger.ZERO);
        assertEquals(BigInteger.valueOf(8470),atm.getAccountBalance());
    }

    @Test
    @DisplayName("Тест на проверку запрашиваемой суммы, когда достаточно денег на балансе")
    void testGetBanknotesWhenNotEnoughNeededDenomination() {
        atm.acceptBanknotes( List.of(
                new Banknote(BanknoteType.FIVE_THOUSAND, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.TWO_THOUSAND, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.THOUSAND, BigInteger.valueOf(3)),
                new Banknote(BanknoteType.TWO_HUNDRED, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.HUNDRED, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.FIFTY, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.TEN, BigInteger.valueOf(2))));

        assertEquals(BigInteger.valueOf(15470),atm.getAccountBalance());
        assertThrows(InsufficientBanknoteAmountException.class, () -> atm.getBanknotes(BigInteger.valueOf(530)));
    }

    @Test
    @DisplayName("Тест на получение баланса")
    void testGetAccountBalance() {
        assertEquals(BigInteger.ZERO, atm.getAccountBalance());

        atm.acceptBanknotes( List.of(
                new Banknote(BanknoteType.FIVE_HUNDRED, BigInteger.valueOf(4)),
                new Banknote(BanknoteType.FIFTY, BigInteger.valueOf(4)),
                new Banknote(BanknoteType.TEN, BigInteger.valueOf(4))));

        assertEquals(BigInteger.valueOf(2_240), atm.getAccountBalance());
    }

}