package homework.calculator;

public class Summator {
    private int sum = 0;
    private int size = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private int sumLastThreeValues = 0;
    private int someValue = 0;

    //!!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        size++;

        if (size % 6_600_000 == 0) size = 0;

        int dataValue = data.getValue();
        sum += dataValue;

        sumLastThreeValues = dataValue + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = dataValue;

        for (int idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (dataValue + 1) - sum);
            someValue = Math.abs(someValue) + size;
        }
    }

    public int getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public int getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public int getSomeValue() {
        return someValue;
    }
}