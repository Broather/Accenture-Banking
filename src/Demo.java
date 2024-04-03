import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo {
    private static final Logger logger = LogManager.getLogger(Demo.class);
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        TransactionRepo transactionRepo = new TransactionRepo("transactions");
        BankAccountRepo accountRepo = new BankAccountRepo();
        accountRepo.add(new BankAccount(1234, 100f));
        accountRepo.add(4321);
        accountRepo.add(1212);
        accountRepo.add(3434);

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Log in to your bank account with your account number: ");
            int accountNumber = scanner.nextInt();
            if (accountRepo.get(accountNumber) == null) {
                System.out.printf("There is no account with account number %d, would you like to create one [Y/N]: ", accountNumber);
                boolean _continue = scanner.next().equalsIgnoreCase("Y");
                if (_continue && accountRepo.add(accountNumber)) {
                    logger.info(String.format("%sAccount successfully created!%s%n", ANSI_GREEN, ANSI_RESET));
                }
            }
            BankAccount myAccount;
            myAccount = accountRepo.get(accountNumber);
            boolean logged_in = true;
            do {
                System.out.println(ANSI_BLUE + "1: check balance" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "2: check transaction history" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "3: deposit funds" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "4: withdraw funds" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "5: transfer funds" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "6: log out" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "7: log out and quit" + ANSI_RESET);

                int choice = scanner.nextInt();
                float amount;
                switch (choice) {
                    case 1:
                        myAccount.printBalance();
                        break;
                    case 2:
                        transactionRepo.printTransactions(myAccount);
                        break;
                    case 3:
                        System.out.println("Enter amount to deposit: ");
                        amount = scanner.nextFloat();
                        if (!myAccount.deposit(amount)) {
                            System.out.println("Cannot deposit funds (could be because of incorrect amount)");
                        }else{
                            transactionRepo.add(String.format("%s %s %.2f %s", "Cash_deposit", myAccount, amount , LocalDateTime.now().format(Transaction.dateFormatter)));
                        }
                        break;
                    case 4:
                        System.out.println("Enter amount to withdraw: ");
                        amount = scanner.nextFloat();
                        if (!myAccount.withdraw(amount)) {
                            System.out.println("Cannot withdraw funds (could be because of insufficient funds)");
                        }else{
                            transactionRepo.add(String.format("%s %s %.2f %s", myAccount ,"Cash_withdrawal", amount ,LocalDateTime.now().format(Transaction.dateFormatter)));
                        }
                        break;
                    case 5:
                        ArrayList<BankAccount> otherAccounts = new ArrayList<>(accountRepo.getAllAccounts());
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
                                amount = scanner.nextFloat();
                                transaction_successful = myAccount.transferAmount(amount, other);
                                if (!transaction_successful) {
                                    System.out.println("Cannot transfer funds (could be because of insufficient funds)");
                                }else{
                                    transactionRepo.add(String.format("%s %s %.2f %s", myAccount, other, amount ,LocalDateTime.now().format(Transaction.dateFormatter)));
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Chosen number is out of bounds, pick again");
                            }
                        } while (!transaction_successful);
                        break;
                    case 6:
                        logged_in = false;
                        break;
                    case 7:
                        scanner.close();
                        transactionRepo.saveTransactions();
                        return;

                    default:
                        logger.warn(String.format("Unrecognised choice: %d", choice));
                }
            } while (logged_in);
        } while (true);
    }
}
