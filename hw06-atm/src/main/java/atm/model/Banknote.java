package atm.model;

import java.math.BigInteger;

public class Banknote implements Cloneable{

    private BanknoteType type;
    private BigInteger number;

    public BanknoteType getType() {
        return type;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public void setType(BanknoteType type) {
        this.type = type;
    }

    public Banknote(BanknoteType type, BigInteger number) {
        this.type = type;
        this.number = number;
    }

    @Override
    public Banknote clone() {
        try {
            Banknote clone = (Banknote) super.clone();
            clone.setNumber(this.getNumber());
            clone.setType(this.getType());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
