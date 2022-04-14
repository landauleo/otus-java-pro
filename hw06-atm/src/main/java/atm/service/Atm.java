package atm.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import atm.infrastructure.exception.InsufficientBanknoteAmountException;
import atm.infrastructure.exception.UnacceptableInputAmountException;
import atm.model.Banknote;
import atm.model.BanknoteType;

public class Atm {

    private Banknote tens;
    private Banknote fifties;
    private Banknote hundreds;
    private Banknote twoHundreds;
    private Banknote fiveHundreds;
    private Banknote thousands;
    private Banknote twoThousands;
    private Banknote fiveThousands;
    private List<Banknote> banknoteList;

    public Atm() {
        this.tens = new Banknote(BanknoteType.TEN, BigInteger.ZERO);
        this.fifties = new Banknote(BanknoteType.FIFTY, BigInteger.ZERO);
        this.hundreds = new Banknote(BanknoteType.HUNDRED, BigInteger.ZERO);
        this.twoHundreds = new Banknote(BanknoteType.TWO_HUNDRED, BigInteger.ZERO);
        this.fiveHundreds = new Banknote(BanknoteType.FIVE_HUNDRED, BigInteger.ZERO);
        this.thousands = new Banknote(BanknoteType.THOUSAND, BigInteger.ZERO);
        this.twoThousands = new Banknote(BanknoteType.TWO_THOUSAND, BigInteger.ZERO);
        this.fiveThousands = new Banknote(BanknoteType.FIVE_THOUSAND, BigInteger.ZERO);
        this.banknoteList = List.of(fiveThousands, twoThousands, thousands, fiveHundreds, twoHundreds, hundreds, fifties, tens);
    }

    public List<Banknote> getBanknoteList() {
        return banknoteList;
    }

    public void acceptBanknotes(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            switch (banknote.getType()) {
                case TEN -> tens.setNumber(tens.getNumber().add(banknote.getNumber()));
                case FIFTY -> fifties.setNumber(fifties.getNumber().add(banknote.getNumber()));
                case HUNDRED -> hundreds.setNumber(hundreds.getNumber().add(banknote.getNumber()));
                case TWO_HUNDRED -> twoHundreds.setNumber(twoHundreds.getNumber().add(banknote.getNumber()));
                case FIVE_HUNDRED -> fiveHundreds.setNumber(fiveHundreds.getNumber().add(banknote.getNumber()));
                case THOUSAND -> thousands.setNumber(thousands.getNumber().add(banknote.getNumber()));
                case TWO_THOUSAND -> twoThousands.setNumber(twoThousands.getNumber().add(banknote.getNumber()));
                case FIVE_THOUSAND -> fiveThousands.setNumber(fiveThousands.getNumber().add(banknote.getNumber()));
                default -> throw new IllegalArgumentException("Unknown banknote type " + banknote.getType());
            }
        }
    }

    public synchronized List<Banknote> getBanknotes(BigInteger amount) {
        validateInputAmount(amount);
        checkBanknoteSufficiency(amount);

        List<Banknote> resultList = new ArrayList<>();
        for (int i = 0; i < banknoteList.size() && amount.compareTo(BigInteger.ZERO) != 0; i++) {
            Banknote banknote = banknoteList.get(i);
            while (amount.compareTo(banknote.getType().getDenomination()) >= 0 && banknote.getNumber().compareTo(BigInteger.ZERO) > 0) {
                amount = amount.subtract(banknote.getType().getDenomination());
                banknote.setNumber(banknote.getNumber().subtract(BigInteger.ONE));
                if (!resultList.isEmpty() && resultList.get(resultList.size() - 1).getType() == banknote.getType()) {
                    Banknote banknoteWithNeededType = resultList.stream().filter(item -> item.getType() == banknote.getType()).findFirst().get();
                    banknoteWithNeededType.setNumber(banknoteWithNeededType.getNumber().add(BigInteger.ONE));
                } else {
                    resultList.add(new Banknote(banknote.getType(), BigInteger.ONE));
                }
            }
        }
        return resultList;
    }

    public BigInteger getAccountBalance() {
        BigInteger sum = BigInteger.ZERO;
        for (Banknote banknote : banknoteList) {
            Banknote cloned = banknote.clone(); //not shallow, but deep copy
            while (cloned.getNumber().compareTo(BigInteger.ZERO) > 0) {
                sum = sum.add(cloned.getType().getDenomination());
                cloned.setNumber(cloned.getNumber().subtract(BigInteger.ONE));
            }
        }
        return sum;
    }

    private void validateInputAmount(BigInteger amount) {
        if (amount.compareTo(BigInteger.ZERO) == 0) {
            throw new UnacceptableInputAmountException("٩(ఠ益ఠ)۶ Amount should be greater that zero ٩(ఠ益ఠ)۶");
        }
        if (amount.remainder(BigInteger.valueOf(10)).compareTo(BigInteger.ZERO) != 0) {
            throw new NumberFormatException("(ಡ‸ಡ) Decimal value is required (ಡ‸ಡ)");
        }
    }

    private void checkBanknoteSufficiency(BigInteger amount) {
        BigInteger totalBalance = getAccountBalance();

        if (totalBalance.compareTo(amount) < 0) {
            throw new InsufficientBanknoteAmountException("[ ± _ ± ] Sorry, we're running out of money, max sum that may be provided is " + totalBalance + " [ ± _ ± ]");
        }

        BigInteger copiedAmount = amount;
        Banknote cloned;
        for (int i = 0; i < banknoteList.size() && copiedAmount.compareTo(BigInteger.ZERO) != 0; i++) {
            cloned = banknoteList.get(i).clone(); //not shallow, but deep copy
            while (copiedAmount.compareTo(cloned.getType().getDenomination()) >= 0 && cloned.getNumber().compareTo(BigInteger.ZERO) > 0) {
                copiedAmount = copiedAmount.subtract(cloned.getType().getDenomination());//10
                cloned.setNumber(cloned.getNumber().subtract(BigInteger.ONE));
            }
        }

        if(copiedAmount.compareTo(BigInteger.ZERO) != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            banknoteList.forEach(item -> {
                stringBuilder.append("banknote denomination: ").append(item.getType().getDenomination());
                stringBuilder.append(" current denomination amount: ").append(item.getNumber()).append("\n");
            });
            throw new InsufficientBanknoteAmountException("(」°ロ°)」 Sorry, we have not enough banknotes for your money request, " +
                    "try to change your request based on the information below: (」°ロ°)」\n" + stringBuilder);
        }
    }

}
