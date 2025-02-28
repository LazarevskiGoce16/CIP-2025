public class Main extends Thread {
    private int counter = 10;
    private static int countThreads = 0;
    private int threadNum = ++countThreads;

    @Override
    public void run() {
        System.out.println("Thread: " + threadNum + "(" + counter + ")");
        counter--;
    }

    public Main() {
        System.out.println("Creating a thread: " + threadNum);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Main main = new Main();
            main.start();
            System.out.println("Thread name: " + main.getName());
            System.out.println("Thread priority: " + main.getPriority());
            System.out.println("Is the thread alive: " + main.isAlive());
            System.out.println("Thread declaration: " + main);
        }

        System.out.println("All the threads are running...");
    }
}