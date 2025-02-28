public class Main {
    public static void main(String[] args) {
        Thread clock1 = new Thread(new TickTack("clock1", 600));
        Thread clock2 = new Thread(new TickTack("clock2", 800));

        clock1.start();
        clock2.start();
    }
}