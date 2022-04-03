package atm.model;

import java.math.BigInteger;

public class Banknote implements Cloneable{

    private BanknoteType type;
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

    public void setType(BanknoteType type) {
        this.type = type;
    }

    public Banknote(BanknoteType type, BigInteger amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public Banknote clone() {
        try {
            Banknote clone = (Banknote) super.clone();
            clone.setAmount(this.getAmount());
            clone.setType(this.getType());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
