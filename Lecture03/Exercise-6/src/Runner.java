public class Runner extends Thread {
    private static final int TRACK_LENGTH = 100;
    private static boolean hasWinner = false;
    private String name;

    public Runner(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        int distance = 0;

        while (distance < TRACK_LENGTH && !hasWinner) {
            int step = (int) (Math.random() * 5) + 1;
            distance += step;

            printProgress(distance);

            if (distance >= TRACK_LENGTH && !hasWinner) {
                hasWinner = true;
                System.out.println("\uD83C\uDFC6" + name + " has won the race! \uD83C\uDFC6");
                System.out.println("Race is over. Other runners will stop.");
            }

            try {
                Thread.sleep((int) (Math.random() * 200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printProgress(int distance) {
        StringBuilder track = new StringBuilder();

        for (int i = 0; i < TRACK_LENGTH; i++) {
            if (i == Math.min(distance, TRACK_LENGTH - 1)) {
                track.append(name.charAt(0));
            } else {
                track.append("-");
            }
        }

        track.append("🏁");
        System.out.println(name + ": " + track.toString() + " " + distance + "%");
    }
}
