package atm.model;

import java.math.BigInteger;

public class Banknote {

    private final BanknoteType type;
    private BigInteger amount;

    public BanknoteType getType() {
        return type;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public Banknote(BanknoteType type, BigInteger amount) {
        this.type = type;
        this.amount = amount;
    }

}
