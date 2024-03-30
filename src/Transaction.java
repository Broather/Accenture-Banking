import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Transaction {
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    private final String sender;
    private final String recipient;
    private final float amount;
    private final LocalDateTime createdAt;

    Transaction(String sender, String recipient, float amount, LocalDateTime createdAt) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.createdAt = createdAt;
    }


    public String getSender() {
        return this.sender;
    }
    public String getRecipient() {
        return this.recipient;
    }
    public float getAmount() {
        return this.amount;
    }
    public String getCreatedAt() {
        return this.createdAt.format(dateFormatter);
    }
    @Override
    public String toString() {
        return String.format("%s %s %.2f %s",
                this.getSender(),
                this.getRecipient(),
                this.getAmount(),
                this.getCreatedAt());
    }



}
