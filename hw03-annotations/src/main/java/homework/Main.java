package homework;

public class Main {
    public static void main(String... args) {
        TestRunner runner = new TestRunner();
        runner.run(DummyService.class.getName());
    }
}
