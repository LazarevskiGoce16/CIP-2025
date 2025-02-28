public class TickTack implements Runnable {
    private String name;
    private int rate;

    public TickTack(String name, int rate) {
        this.name = name;
        this.rate = rate;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(name + " Tick");
            try {
                Thread.sleep(rate);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " Tack");
            try {
                Thread.sleep(rate);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
