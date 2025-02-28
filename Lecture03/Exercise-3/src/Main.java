public class Main {
    private int counter;

    public synchronized void increment() {
//        for (int i = 0; i < 1000; i++) {
//            counter++;
//        }
        counter++;
    }

    public static void main(String[] args) {
        Main main = new Main();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("Thread 1: " + main.counter);
                main.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("Thread 2: " + main.counter);
                main.increment();
            }
        });

        thread1.start();
        thread2.start();

        System.out.println("Final counter value: " + main.counter);
    }
}