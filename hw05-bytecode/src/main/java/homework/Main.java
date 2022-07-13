package homework;

import homework.proxy.Demo;

/**
 * Разработайте такой функционал:
 * метод класса можно пометить самодельной аннотацией @Log, например, так:
 * class TestLogging {
 * @Log
 * public void calculation(int param) {};
 * }
 * При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.
 * Например так.
 * class Demo {
 * public void action() {
 * new TestLogging().calculation(6);
 * }
 * }
 * В консоле дожно быть:
 * executed method: calculation, param: 6
 * Обратите внимание: явного вызова логирования быть не должно.
 * Учтите, что аннотацию можно поставить, например, на такие методы:
 * public void calculation(int param1)
 * public void calculation(int param1, int param2)
 * public void calculation(int param1, int param2, String param3)
 * P.S.
 * Выбирайте реализацию с ASM, если действительно этого хотите и уверены в своих силах.
 */

public class Main {
    public static void main(String... args) {
        Demo demo = new Demo();
        demo.action();
    }
}
