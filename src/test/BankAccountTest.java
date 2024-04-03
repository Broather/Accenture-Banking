import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;

class BankAccountTest {

    BankAccount wealthyIndividual;
    BankAccount emptyIndividual;
    @BeforeEach
    void setUp(){
        wealthyIndividual = new BankAccount(1000, 10000f);
        emptyIndividual = new BankAccount(2000);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 21, 1})
    void depositWithNonNegativeIntegers(int amount) {
        float balanceBeforeDeposit = emptyIndividual.getBalance();
        Assertions.assertTrue(emptyIndividual.deposit(amount));
        Assertions.assertEquals(balanceBeforeDeposit + amount, emptyIndividual.getBalance());
    }
    @ParameterizedTest
    @ValueSource(floats = {5.05f, 10.21f, 43.01f})
    void depositWithNonNegativeFloats(float amount) {
        float balanceBeforeDeposit = emptyIndividual.getBalance();
        Assertions.assertTrue(emptyIndividual.deposit(amount));
        Assertions.assertEquals(balanceBeforeDeposit + amount, emptyIndividual.getBalance());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 21, 1})
    void withdrawWithNonNegativeIntegers(int amount) {
        float balanceBeforeWithdraw = wealthyIndividual.getBalance();
        Assertions.assertTrue(wealthyIndividual.withdraw(amount));
        Assertions.assertEquals(balanceBeforeWithdraw - amount, wealthyIndividual.getBalance());
    }
    @ParameterizedTest
    @ValueSource(floats = {5.05f, 10.21f, 43.01f})
    void withdrawWithNonNegativeFloats(float amount) {
        float balanceBeforeWithdraw = wealthyIndividual.getBalance();
        Assertions.assertTrue(wealthyIndividual.withdraw(amount));
        Assertions.assertEquals(balanceBeforeWithdraw - amount, wealthyIndividual.getBalance());
    }

    @Test
    void transferAmount() {
        float onesBalanceBeforeTransfer = wealthyIndividual.getBalance();
        float othersBalanceBeforeTransfer = emptyIndividual.getBalance();

        float amountToTransfer = 20f;
        wealthyIndividual.transferAmount(amountToTransfer, emptyIndividual);
        assertAll(() -> Assertions.assertEquals(onesBalanceBeforeTransfer - amountToTransfer, wealthyIndividual.getBalance()),
                () -> Assertions.assertEquals(othersBalanceBeforeTransfer + amountToTransfer, emptyIndividual.getBalance()));
    }
}