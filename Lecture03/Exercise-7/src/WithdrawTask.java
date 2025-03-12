public class WithdrawTask implements Runnable {
    private BankAccount account;
    private String name;
    private double amount;

    public WithdrawTask(BankAccount account, String name, double amount) {
        this.account = account;
        this.name = name;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount, name);
    }
}
