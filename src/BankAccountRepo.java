import java.util.ArrayList;

public class BankAccountRepo {
    private ArrayList<BankAccount> allAccounts;

    BankAccountRepo() {
        allAccounts = new ArrayList<>();
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
    private void add(BankAccount bankAccount) {
        this.allAccounts.add(bankAccount);
    }

    public static void main(String[] args) {
//        TODO: Input/Output, e.g. if yesterday didn't use Input from user then today please add interaction
        BankAccountRepo repo = new BankAccountRepo();

        repo.add(new BankAccount(1234, 100f));
        repo.add(new BankAccount(4321, 0f));

        repo.get(1234).transferAmount(50f, repo.get(4321));
        repo.get(1234).printBalance();
        repo.get(4321).printBalance();
    }

}