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
        assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TEN).findFirst().get().getAmount(), BigInteger.ZERO);

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
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TEN).findFirst().get().getAmount(), BigInteger.ONE),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.FIFTY).findFirst().get().getAmount(), BigInteger.TWO),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.HUNDRED).findFirst().get().getAmount(), BigInteger.valueOf(3)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TWO_HUNDRED).findFirst().get().getAmount(), BigInteger.valueOf(4)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.FIVE_HUNDRED).findFirst().get().getAmount(), BigInteger.valueOf(5)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.THOUSAND).findFirst().get().getAmount(), BigInteger.valueOf(6)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.TWO_THOUSAND).findFirst().get().getAmount(), BigInteger.valueOf(7)),
                () -> assertEquals(atm.getBanknoteList().stream().filter(item -> item.getType() == BanknoteType.FIVE_THOUSAND).findFirst().get().getAmount(), BigInteger.valueOf(8))
        );
    }

    @Test
    @DisplayName("Чуть более удобно сконструированный (теперь никаких множественных ссёртов) тест на выдачу банкнот")
    void testGetBanknotes() {
        assertThrows(UnacceptableInputAmountException.class, () -> atm.getBanknotes(BigInteger.ZERO), "٩(ఠ益ఠ)۶ Amount should be greater that zero ٩(ఠ益ఠ)۶");
        assertThrows(NumberFormatException.class, () -> atm.getBanknotes(BigInteger.valueOf(123)), "(ಡ‸ಡ) Decimal value is required (ಡ‸ಡ)");
        assertThrows(InsufficientBanknoteAmountException.class, () -> atm.getBanknotes(BigInteger.valueOf(1230)), "[ ± _ ± ] Sorry, we're running out of money, max sum that may be provided is 0 [ ± _ ± ]");

        atm.acceptBanknotes( List.of( //15470
                new Banknote(BanknoteType.FIVE_THOUSAND, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.TWO_THOUSAND, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.THOUSAND, BigInteger.valueOf(3)),
                new Banknote(BanknoteType.TWO_HUNDRED, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.HUNDRED, BigInteger.valueOf(2)),
                new Banknote(BanknoteType.FIFTY, BigInteger.valueOf(1)),
                new Banknote(BanknoteType.TEN, BigInteger.valueOf(2))));

        assertThrows(InsufficientBanknoteAmountException.class, () -> atm.getBanknotes(BigInteger.valueOf(15480)), "[ ± _ ± ] Sorry, we're running out of money, max sum that may be provided is 15470 [ ± _ ± ]");
        assertEquals(BigInteger.valueOf(15470),atm.getAccountBalance());
        assertDoesNotThrow(() -> atm.getBanknotes(BigInteger.valueOf(1000)));
        assertEquals(BigInteger.valueOf(14470),atm.getAccountBalance());
        assertDoesNotThrow(() -> atm.getBanknotes(BigInteger.valueOf(10_000))); //15460
        //TODO: чекнуть, когда сумма достаточная, но купюр не хватает

    }

    @Test
    void testGetAccountBalance() {
    }

}