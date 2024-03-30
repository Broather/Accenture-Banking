/**
 * a bank account class that stores information about how much money it contains
 */
public class BankAccount {
    private final int accountNumber;
    private float balance;

    BankAccount(int accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0f;
    }

    BankAccount(int accountNumber, float balance) {
        this.accountNumber = accountNumber;
        if (balance < 0) {
            System.out.println("Can't create an account with a negative balance");
        }
        this.balance = balance;
    }
    @Override
    public String toString(){
        return String.format("%d", this.accountNumber);
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public boolean deposit(float amount) {
        if (amount < 0) {
            return false;
        }
        this.balance += amount;
        return true;
    }

    public boolean withdraw(float amount) {
        if (amount < 0 || amount > this.balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    public void printBalance() {
        System.out.println(this.balance);
    }

    public boolean transferAmount(float amount, BankAccount other) {
        if (this.withdraw(amount)) {
            other.deposit(amount);
            return true;
        }
        return false;
    }

}