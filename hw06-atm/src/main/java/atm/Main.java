package atm;

import atm.model.Banknote;
import atm.model.BanknoteType;
import atm.service.Atm;

import java.math.BigInteger;
import java.util.List;

/**
 * Домашнее задание
 * Эмулятор банкомата
 * Цель:
 * Применить на практике принципы SOLID.
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Написать эмулятор АТМ (банкомата).
 * Объект класса АТМ должен уметь:
 * - принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
 * - выдавать запрошенную сумму минимальным количеством банкнот или ошибку, если сумму нельзя выдать.
 * - выдавать сумму остатка денежных средств В этом задании больше думайте об архитектуре приложения. Не отвлекайтесь на создание таких объектов как: пользователь, авторизация, клавиатура, дисплей, UI (консольный, Web, Swing), валюта, счет, карта, т.д. Все это не только не нужно, но и вредно!
 */
public class Main {

    public static void main(String[] args) {
        Atm atm = new Atm();
        atm.acceptBanknotes(List.of(new Banknote(BanknoteType.TEN, BigInteger.TEN)));
        atm.getBanknotes(BigInteger.TEN);
    }
}
