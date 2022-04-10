package atm.model;

import java.math.BigInteger;

public enum BanknoteType {
    TEN(BigInteger.valueOf(10)), FIFTY(BigInteger.valueOf(50)), HUNDRED(BigInteger.valueOf(100)), TWO_HUNDRED(BigInteger.valueOf(200)), FIVE_HUNDRED(BigInteger.valueOf(500)),
    THOUSAND(BigInteger.valueOf(1000)), TWO_THOUSAND(BigInteger.valueOf(2000)), FIVE_THOUSAND(BigInteger.valueOf(5000));

    private final BigInteger denomination;

    BanknoteType(BigInteger denomination) {
        this.denomination = denomination;
    }

    public BigInteger getDenomination() {
        return denomination;
    }
}
