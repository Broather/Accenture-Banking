import java.util.ArrayList;
import java.util.Scanner;

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

    private boolean add(int accountNumber) {
        for (BankAccount account : this.allAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                return false;
            }
        }
        BankAccount newAccount = new BankAccount(accountNumber);
        this.allAccounts.add(newAccount);
        return true;
    }

    private void add(BankAccount account) {
        this.allAccounts.add(account);
    }

    public static void main(String[] args) {
//        TODO: Input/Output, e.g. if yesterday didn't use Input from user then today please add interaction
        BankAccountRepo repo = new BankAccountRepo();
        repo.add(new BankAccount(1234, 100f));
        repo.add(new BankAccount(4321, 0f));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Log in to your bank account with your account number: ");
        int accountNumber = scanner.nextInt();
        boolean _continue;
        if (repo.get(accountNumber) == null) {
            System.out.printf("There is no account with account number %d, would you like to create one [Y/N]: ", accountNumber);
            _continue = scanner.next().equalsIgnoreCase("Y");
            if (_continue && repo.add(accountNumber)) {
                System.out.println("Account successfully created!");
            }
        }
        BankAccount myAccount;
        if ((myAccount = repo.get(accountNumber)) == null) {
            return;
        }
        do {
            System.out.println("1: check balance");
            System.out.println("2: deposit funds");
            System.out.println("3: withdraw funds");

            int choice = scanner.nextInt();
            int amount;
            switch (choice) {
                case 1:
                    myAccount.printBalance();
                    break;
                case 2:
                    System.out.println("Enter amount to deposit: ");
                    amount = scanner.nextInt();
                    myAccount.deposit(amount);
                    break;
                case 3:
                    System.out.println("Enter amount to withdraw: ");
                    amount = scanner.nextInt();
                    if (!myAccount.withdraw(amount)) {
                        System.out.println("Cannot withdraw funds (could be because of insufficient funds)");
                    }
                    break;
//                    TODO: implement case 4 for transferring funds between accounts
//                    TODO: use allAccounts.retainAll(Arrays.asList(BankAccount)) to list all other accounts to transfer to
//                    TODO: and then use choice to select one and ask how much to transfer

                default:
                    System.out.printf("Unrecognised choice: %d", choice);

            }

            System.out.println("Continue? [Y/N]");
            _continue = scanner.next().equalsIgnoreCase("Y");
        } while (_continue);
    }

}