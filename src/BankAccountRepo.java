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
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLUE = "\u001B[34m";

        BankAccountRepo repo = new BankAccountRepo();
        repo.add(new BankAccount(1234, 100f));
        repo.add(4321);
        repo.add(1212);
        repo.add(3434);

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("Log in to your bank account with your account number: ");
            int accountNumber = scanner.nextInt();
            if (repo.get(accountNumber) == null) {
                System.out.printf("There is no account with account number %d, would you like to create one [Y/N]: ", accountNumber);
                boolean _continue = scanner.next().equalsIgnoreCase("Y");
                if (_continue && repo.add(accountNumber)) {
                    System.out.println("Account successfully created!");
                }
            }
            BankAccount myAccount;
            myAccount = repo.get(accountNumber);
            boolean logged_in = true;
            do {
                System.out.println(ANSI_BLUE + "1: check balance" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "2: deposit funds" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "3: withdraw funds" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "4: transfer funds" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "5: log out" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "6: log out and quit" + ANSI_RESET);

                int choice = scanner.nextInt();
                int amount;
                switch (choice) {
                    case 1:
                        myAccount.printBalance();
                        break;
//                    TODO: add case for checking transaction history
//                    TODO: TransactionRepo.loadTransactions("transactions.txt") would add one Transaction object for each line in the text file
//                    TODO: TransactionRepo.loadTransactions creates an empty "transactions.txt" if it doesn't exists in the project root
//                    TODO: Transaction would have String sender, String recipient, float amount, DateTime createdAt
//                    TODO: TransactionRepo.printTransactions(accountNumber) prints sender, createdAt, "+"amount if account is the recipient in the transaction or prints recipient, createdAt, "-"amount if account is sender in a transaction
//                    TODO: Transactions from a deposit would print as ATM, createdAt, +amount
//                    TODO: Transactions from a withdraw would print as ATM, createdAt, -amount
//                    TODO: Somehow "attach" a transactionRepo.add function call (given an appropriate Transaction object as an argument) to each BankAccount.deposit/withdraw/transferAmount call
                    case 2:
                        System.out.println("Enter amount to deposit: ");
                        amount = scanner.nextInt();
                        if (!myAccount.deposit(amount)) {
                            System.out.println("Cannot deposit funds (could be because of incorrect amount)");
                        }
                        break;
                    case 3:
                        System.out.println("Enter amount to withdraw: ");
                        amount = scanner.nextInt();
                        if (!myAccount.withdraw(amount)) {
                            System.out.println("Cannot withdraw funds (could be because of insufficient funds)");
                        }
                        break;
                    case 4:
                        ArrayList<BankAccount> otherAccounts = new ArrayList<>(repo.getAllAccounts());
                        otherAccounts.removeIf(acc -> acc.equals(myAccount));

                        boolean transaction_successful = false;
                        do {
                            System.out.println("Choose an account to transfer to:");
                            for (int i = 0; i < otherAccounts.size(); i++) {
                                System.out.printf("%s%d: account %s%n%s", ANSI_BLUE, i + 1, otherAccounts.get(i).getAccountNumber(), ANSI_RESET);
                            }
                            choice = scanner.nextInt();
                            try {
                                BankAccount other = otherAccounts.get(choice - 1);
                                System.out.print("Enter amount to transfer: ");
                                amount = scanner.nextInt();
                                transaction_successful = myAccount.transferAmount(amount, other);
                                if (!transaction_successful) {
                                    System.out.println("Cannot transfer funds (could be because of insufficient funds)");
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Chosen number is out of bounds, pick again");
                            }
                        } while (!transaction_successful);
                        break;
                    case 5:
                        logged_in = false;
                        break;
                    case 6:
                        scanner.close();
                        return;

                    default:
                        System.out.printf("Unrecognised choice: %d", choice);

                }
            } while (logged_in);
        } while (true);
    }

    private ArrayList<BankAccount> getAllAccounts() {
        return this.allAccounts;
    }

}