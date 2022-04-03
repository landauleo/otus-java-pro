package atm;

import java.math.BigInteger;
import java.util.List;

import atm.model.Banknote;
import atm.model.BanknoteType;
import atm.service.Atm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AtmTest {

    private Atm atm;

    @BeforeEach
    public void initEach() {
        atm = new Atm();
    }

    @Test
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
    void testGetBanknotes() {
    }

    @Test
    void testGetAccountBalance() {
    }

}