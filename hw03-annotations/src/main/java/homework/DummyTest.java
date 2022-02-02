package homework;

/**
 * Создать три аннотации - @Test, @Before, @After.
 * <p>
 * Создать класс-тест, в котором будут методы, отмеченные аннотациями.
 * <p>
 * Создать "запускалку теста". На вход она должна получать имя класса с тестами, в котором следует найти и запустить методы отмеченные аннотациями и пункта 1.
 * <p>
 * Алгоритм запуска должен быть следующий:: метод(ы) Before текущий метод Test метод(ы) After для каждой такой "тройки" надо создать СВОЙ объект класса-теста.
 * <p>
 * Исключение в одном тесте не должно прерывать весь процесс тестирования.
 * <p>
 * На основании возникших во время тестирования исключений вывести статистику выполнения тестов (сколько прошло успешно, сколько упало, сколько было всего)
 * <p>
 * "Запускалка теста" не должна иметь состояние, но при этом весь функционал должен быть разбит на приватные методы. Надо придумать, как передавать информацию между методами.
 */
public class DummyTest {

    @Before
    void beforeEveryTest() {
        System.out.println("Hi,I'm your beforeMethod");
    }

    @Test
    void testItself() {
        System.out.println("Hi,I'm your testMethod");
    }

    @Test
    void oneMoreTestItself() {
        throw new RuntimeException("Hi,I'm gonna let you down, but I won't ruin the tests flow");
    }

    @After
    void afterEveryTest() {
        System.out.println("Hi,I'm your afterMethod");
    }

}
