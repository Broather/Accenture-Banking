import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TransactionRepo {
    private static final Logger logger = LogManager.getLogger(TransactionRepo.class);
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private final String fileName;
    public ArrayList<Transaction> allTransactions = new ArrayList<>();

    TransactionRepo(String fileName) {
        this.fileName = fileName;
        loadTransactions();
    }

    public void printTransactions(BankAccount account) {
        boolean didntPrintAnything = true;
        for (Transaction t: this.allTransactions){
            if (account.toString().equals(t.getSender()) && account.toString().equals(t.getRecipient())){
                throw new AssertionError("unreachable: account is sender and recipient in transaction");
            }
            if (account.toString().equals(t.getSender())){
                System.out.printf("%s \"%s\" -%.2f%n", t.getRecipient(), t.getCreatedAt(), t.getAmount());
                didntPrintAnything = false;

            }else if (account.toString().equals(t.getRecipient())){
                System.out.printf("%s \"%s\" %s+%.2f%s%n", t.getSender(), t.getCreatedAt(), ANSI_GREEN, t.getAmount(), ANSI_RESET);
                didntPrintAnything = false;
            }
        }
        if (didntPrintAnything){
            System.out.println("No transactions");
        }
    }

    public void add(String line) {
        String[] tokens = line.split(" ", 4);

        String sender = tokens[0];
        String recipient = tokens[1];
        float amount = Float.parseFloat(tokens[2]);
        LocalDateTime createdAt = LocalDateTime.parse(tokens[3], Transaction.dateFormatter);

        this.allTransactions.add(new Transaction(sender, recipient, amount, createdAt));
    }

    public void loadTransactions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileName + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                this.add(line);
            }
            logger.info(String.format("%s%s.txt successfully loaded%s%n", ANSI_GREEN, this.fileName, ANSI_RESET));
        } catch (FileNotFoundException e) {
            logger.info(String.format("Couldn't find %s.txt, creating...%n", this.fileName));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {
                writer.write("");
                logger.info(String.format("%s%s.txt successfully created%s%n", ANSI_GREEN, this.fileName, ANSI_RESET));
            } catch (IOException ex) {
                logger.error(String.format("Error creating file: " + e.getMessage()));
            }
        } catch (IOException e) {
            logger.error("Error reading file: " + e.getMessage());
        }
    }

    public void saveTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName + ".txt"))) {
            writer.write(allTransactions.stream().map(Transaction::toString).collect(Collectors.joining("\n")));
            logger.info(String.format("%s%s.txt successfully saved%s%n", ANSI_GREEN, this.fileName, ANSI_RESET));
        } catch (IOException e) {
            logger.error(String.format("Error writing to file: " + e.getMessage()));
        }

    }
}
