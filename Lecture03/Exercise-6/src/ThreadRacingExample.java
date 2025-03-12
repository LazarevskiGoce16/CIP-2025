public class ThreadRacingExample {
    public static void main(String[] args) {
        System.out.println("Thread Racing Demonstration - Race Track");
        System.out.println("========================================");
        System.out.println("Each runner will advance at random speeds.");
        System.out.println("Watch as they race to the finish line!\n");

        Runner runner1 = new Runner("Bolt");
        Runner runner2 = new Runner("Swift");
        Runner runner3 = new Runner("Zoom");
        Runner runner4 = new Runner("Flash");

        runner1.start();
        runner2.start();
        runner3.start();
        runner4.start();
    }
}