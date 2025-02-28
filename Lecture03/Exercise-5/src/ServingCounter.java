public class ServingCounter {
    private int capacity;
    private int count = 0;
    private String[] dishes;

    public ServingCounter(int capacity) {
        this.capacity = capacity;
        dishes = new String[capacity];
    }

    public synchronized void putDish(String chef, String dish) {
        try {
            while (capacity <= count) {
                System.out.println("Chef " + chef + " is waiting - no space on the counter...");
                wait();
            }
            System.out.println("Chef " + chef + " is placing a meal on the counter");
            Thread.sleep(2000);
            count++;
            dishes[count - 1] = dish;

            System.out.println("Chef " + chef + " already placed " + dish + " on the counter");
            Thread.sleep(2000);

            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void takeDish(String waiter) {
        try {
            while (count <= 0) {
                System.out.println("Waiter " + waiter + " is waiting for a dish...");
                wait();
            }

            System.out.println("Waiter " + waiter + " is taking the meal from the counter");
            String dish = dishes[count - 1];
            dishes[count - 1] = null;
            count--;

            System.out.println("Waiter " + waiter + " is delivering " + dish + " to the customers");
            Thread.sleep(2000);

            System.out.println("Waiter " + waiter + " finished delivering the meal");
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
