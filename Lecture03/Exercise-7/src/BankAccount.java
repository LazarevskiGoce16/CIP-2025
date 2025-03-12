public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        System.out.println("Created account with initial balance: $" + initialBalance);
    }

    public synchronized void deposit(double amount, String transactionName) {
        System.out.println(transactionName + ": Starting deposit of $" + amount);
        System.out.println(transactionName + ": Balance before deposit: $" + balance);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double newBalance = balance + amount;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        balance = newBalance;
        System.out.println(transactionName + ": Deposit completed. New balance: $" + balance);
    }

    public synchronized boolean withdraw(double amount, String transactionName) {
        System.out.println(transactionName + ": Starting withdrawal of $" + amount);
        System.out.println(transactionName + ": Balance before withdrawal: $" + balance);

        if (balance < amount) {
            System.out.println(transactionName + ": Insufficient funds! Withdrawal failed.");
            return false;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double newBalance = balance - amount;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        balance = newBalance;
        System.out.println(transactionName + ": Withdrawal completed. New balance: $" + balance);
        return true;
    }

    public double getBalance() {
        return balance;
    }
}
