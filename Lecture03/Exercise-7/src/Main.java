public class Main {
    public static void main(String[] args) {
        System.out.println("Thread Synchronization Demonstration - Bank Account");
        System.out.println("==================================================");

        BankAccount account = new BankAccount(1000);

        Thread depositThread1 = new Thread(new DepositTask(account, "Deposit-1", 200));
        Thread depositThread2 = new Thread(new DepositTask(account, "Deposit-2", 300));
        Thread withdrawThread1 = new Thread(new WithdrawTask(account, "Withdraw-1", 400));
        Thread withdrawThread2 = new Thread(new WithdrawTask(account, "Withdraw-2", 150));

        depositThread1.start();
        withdrawThread1.start();
        depositThread2.start();
        withdrawThread2.start();

        try {
            depositThread1.join();
            depositThread2.join();
            withdrawThread1.join();
            withdrawThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nAll transactions completed!");
        System.out.println("Final account balance: $" + account.getBalance());
    }
}