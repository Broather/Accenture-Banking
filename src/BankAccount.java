/**
 * a bank account class that stores information about how much money it contains
 */
public class BankAccount {
    private float balance;

    BankAccount() {
        this.balance = 0f;
    }

    BankAccount(float balance) {
        if (balance < 0) {
            System.out.println("Can't create an account with a negative balance");
        }
        this.balance = balance;
    }

    public boolean deposit(float amount) {
        if (amount < 0) {
            return false;
        }
        this.balance += amount;
        return true;
    }

    public boolean withdraw(float amount) {
        if (amount < 0 && amount <= this.balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    public void printBalance() {
        System.out.println(this.balance);
    }

    public void transferAmount(float amount, BankAccount other) {
        if (this.withdraw(amount)) {
            other.deposit(amount);
        }
    }

    public static void main(String[] args) {
        BankAccount b1 = new BankAccount(100f);
        BankAccount b2 = new BankAccount(0f);
        b1.transferAmount(50f, b2);
        b1.printBalance();
        b2.printBalance();
    }
}