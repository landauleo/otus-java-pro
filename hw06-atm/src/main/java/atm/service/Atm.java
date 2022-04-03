package atm.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import atm.infrastructure.exception.InsufficientBanknoteAmountException;
import atm.infrastructure.exception.UnacceptableInputAmountException;
import atm.model.Banknote;
import atm.model.BanknoteType;

public class Atm {

    private volatile Banknote tens = new Banknote(BanknoteType.TEN, BigInteger.ZERO);
    private volatile Banknote fifties = new Banknote(BanknoteType.FIFTY, BigInteger.ZERO);
    private volatile Banknote hundreds = new Banknote(BanknoteType.HUNDRED, BigInteger.ZERO);
    private volatile Banknote twoHundreds = new Banknote(BanknoteType.TWO_HUNDRED, BigInteger.ZERO);
    private volatile Banknote fiveHundreds = new Banknote(BanknoteType.FIVE_HUNDRED, BigInteger.ZERO);
    private volatile Banknote thousands = new Banknote(BanknoteType.THOUSAND, BigInteger.ZERO);
    private volatile Banknote twoThousands = new Banknote(BanknoteType.TWO_THOUSAND, BigInteger.ZERO);
    private volatile Banknote fiveThousands = new Banknote(BanknoteType.FIVE_THOUSAND, BigInteger.ZERO);
    private volatile List<Banknote> banknoteList = List.of(fiveThousands, twoThousands, thousands, fiveHundreds, twoHundreds, hundreds, fifties, tens);

    public List<Banknote> getBanknoteList() {
        return banknoteList;
    }

    public void acceptBanknotes(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            switch (banknote.getType()) {
                case TEN -> tens.setAmount(tens.getAmount().add(banknote.getAmount()));
                case FIFTY -> fifties.setAmount(fifties.getAmount().add(banknote.getAmount()));
                case HUNDRED -> hundreds.setAmount(hundreds.getAmount().add(banknote.getAmount()));
                case TWO_HUNDRED -> twoHundreds.setAmount(twoHundreds.getAmount().add(banknote.getAmount()));
                case FIVE_HUNDRED -> fiveHundreds.setAmount(fiveHundreds.getAmount().add(banknote.getAmount()));
                case THOUSAND -> thousands.setAmount(thousands.getAmount().add(banknote.getAmount()));
                case TWO_THOUSAND -> twoThousands.setAmount(twoThousands.getAmount().add(banknote.getAmount()));
                case FIVE_THOUSAND -> fiveThousands.setAmount(fiveThousands.getAmount().add(banknote.getAmount()));
                default -> throw new IllegalArgumentException("Unknown banknote type " + banknote.getType());
            }
        }
    }

    public synchronized List<Banknote> getBanknotes(BigInteger amount) {
        validateInputAmount(amount);
        checkBanknoteSufficiency(amount);

        List<Banknote> resultList = new ArrayList<>();
        for (int i = 0; i < banknoteList.size() || banknoteList.get(0).getAmount().compareTo(BigInteger.ZERO) != 0; i++) {
            if (amount.compareTo(banknoteList.get(0).getType().getDenomination()) > 0 && banknoteList.get(0).getAmount().compareTo(BigInteger.ZERO) > 0) {
                amount = amount.subtract(banknoteList.get(0).getType().getDenomination());
                banknoteList.get(0).setAmount(banknoteList.get(0).getAmount().subtract(BigInteger.ONE));
                resultList.add(banknoteList.get(0));
            }
        }
        return resultList;
    }

    public BigInteger getAccountBalance() {
        BigInteger sum = BigInteger.ZERO;
        for (Banknote banknote : banknoteList) {
            Banknote cloned = banknote.clone(); //not shallow, but deep copy
            while (cloned.getAmount().compareTo(BigInteger.ZERO) > 0) {
                sum = sum.add(cloned.getType().getDenomination());
                cloned.setAmount(cloned.getAmount().subtract(BigInteger.ONE));
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
            throw new InsufficientBanknoteAmountException("[ ± _ ± ] Sorry, we're running out of money, max sum that may be provided is " + totalBalance + "[ ± _ ± ]");
        }

        BigInteger copiedAmount = amount;
        Banknote cloned;
        for (int i = 0; i < banknoteList.size() || copiedAmount.compareTo(BigInteger.ZERO) != 0; i++) {
            cloned = banknoteList.get(i).clone(); //not shallow, but deep copy
            while (copiedAmount.compareTo(cloned.getType().getDenomination()) >= 0 && cloned.getAmount().compareTo(BigInteger.ZERO) > 0) {
                copiedAmount = copiedAmount.subtract(cloned.getType().getDenomination());//10
                cloned.setAmount(cloned.getAmount().subtract(BigInteger.ONE));
            }
        }

        if(copiedAmount.compareTo(BigInteger.ZERO) != 0) {
            throw new InsufficientBanknoteAmountException("(」°ロ°)」 Sorry, we have not enough banknotes for your money request, try to change your request based on the information below: (」°ロ°)」\n" +
                    banknoteList.toString());
        }
    }

}
