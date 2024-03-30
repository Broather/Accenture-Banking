import java.util.ArrayList;

public class BankAccountRepo {
    private ArrayList<BankAccount> allAccounts;

    BankAccountRepo() {
        allAccounts = new ArrayList<>();
    }
    public ArrayList<BankAccount> getAllAccounts() {
        return this.allAccounts;
    }

    public BankAccount get(int accountNumber) {
        if (allAccounts.isEmpty()) {
            return null;
        }
        for (BankAccount account : allAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public boolean add(int accountNumber) {
        for (BankAccount account : this.allAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                return false;
            }
        }
        BankAccount newAccount = new BankAccount(accountNumber);
        this.allAccounts.add(newAccount);
        return true;
    }

    public void add(BankAccount account) {
        this.allAccounts.add(account);
    }


}