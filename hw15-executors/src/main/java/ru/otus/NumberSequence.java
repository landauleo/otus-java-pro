package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Два потока печатают числа от 1 до 10, потом от 10 до 1.
 * Надо сделать так, чтобы числа чередовались, т.е. получился такой вывод:
 * Поток 1:1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3 4....
 * Поток 2: 1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3....
 * Всегда должен начинать Поток 1.
 */

public class NumberSequence {
    private static final Logger logger = LoggerFactory.getLogger(NumberSequence.class);
    private String last = "THREAD_B";

    public static void main(String[] args) {
        NumberSequence numberSequence = new NumberSequence();
        new Thread(() -> numberSequence.action("THREAD_A")).start();
        new Thread(() -> numberSequence.action("THREAD_B")).start();
    }

    private synchronized void action(String threadName) {
//        while (!Thread.currentThread().isInterrupted()) {
        for (int j = 0; j != 2; j++) { //если не хочется смотреть бесконечно
            try {
                for (int i = 1; i < 10; ++i) {
                    //spurious wakeup https://en.wikipedia.org/wiki/Spurious_wakeup -> поэтому не if
                    //https://errorprone.info/bugpattern/WaitNotInLoop
                    while (last.equals(threadName)) {
                        this.wait();
                    }

                    logger.info(threadName + ": " + i);
                    last = threadName;
                    notifyAll();
                }
                logger.debug("Iteration 1 - 10 stopped for thread " + threadName + " (੭•̀ω•́)੭̸*炎炎炎炎炎炎炎");
                for (int i = 10; i > 0; i--) {
                    while (last.equals(threadName)) {
                        this.wait();
                    }

                    logger.info(threadName + ": " + i);
                    last = threadName;
                    notifyAll();
                }
                logger.debug("Iteration 10 - 1 stopped for thread " + threadName + " (੭•̀ω•́)੭̸*炎炎炎炎炎炎炎");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}