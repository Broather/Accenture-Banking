/**
 * a bank account class that stores information about how much money it contains
 */
public class BankAccount {
    private float balance;
    BankAccount(){
        this.balance = 0f;
    }
    BankAccount(float balance){
        this.balance = balance;
    }
    public void deposit(float amount){
        this.balance += amount;
    }

    public void withdraw(float amount){
        this.balance -= amount;
    }
    public void printBalance(){
        System.out.println(this.balance);
    }

    public void transferAmount(float amount, BankAccount other){
        this.balance -= amount;
        other.balance += amount;
    }
    public static void main(String[] args) {
        BankAccount b1 = new BankAccount(100f);
        BankAccount b2 = new BankAccount(0f);
        b1.transferAmount(50f, b2);
        b1.printBalance();
        b2.printBalance();
    }
}