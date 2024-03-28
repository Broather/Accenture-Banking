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
        System.out.print("Log in to your bank account with your account number: ");
        int accountNumber = scanner.nextInt();
        boolean _continue = true;
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
            System.out.println(ANSI_BLUE + "1: check balance" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "2: deposit funds" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "3: withdraw funds" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "4: transfer funds" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "5: log out and quit" + ANSI_RESET);

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
                case 4:
                    ArrayList<BankAccount> otherAccounts = new ArrayList<>(repo.getAllAccounts());
                    otherAccounts.removeIf(acc -> acc.equals(myAccount));

                    do {
                        System.out.println("Choose an account to transfer to:");
                        for (int i = 0; i < otherAccounts.size(); i++) {
//                            TODO: remove balance information (that's there for debug purposes)
                            System.out.printf("%s%d: account %s (balance: %.2f)%n%s", ANSI_BLUE, i + 1, otherAccounts.get(i).getAccountNumber(), otherAccounts.get(i).getBalance(), ANSI_RESET);
                        }
                        choice = scanner.nextInt();
                        try {
                            BankAccount other = otherAccounts.get(choice - 1);
                            System.out.print("Enter amount to transfer: ");
                            amount = scanner.nextInt();
                            if (!myAccount.transferAmount(amount, other)) {
                                System.out.println("Cannot transfer funds (could be because of insufficient funds)");
                                _continue = true;
                            } else {
                                _continue = false;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Chosen number is out of bounds, pick again");
                            _continue = true;
                        }
                    } while (_continue);
                    break;
//                TODO: add a case to log out but not exit to switch accounts
                case 5:
                    return;

                default:
                    System.out.printf("Unrecognised choice: %d", choice);

            }
        } while (true);
    }

    private ArrayList<BankAccount> getAllAccounts() {
        return this.allAccounts;
    }

}