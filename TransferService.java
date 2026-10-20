public class TransferService {

    public static final double MAX_TRANSFER = 10_000.0;

    public void transfer(BankAccount from, BankAccount to, double amount)
            throws InvalidAccountException,
                   TransferLimitExceededException,
                   InsufficientFundsException {

        if (from == null || to == null) {
            throw new InvalidAccountException("Compte source ou destinataire null");
        }
        if (from == to || from.getAccountNumber().equals(to.getAccountNumber())) {
            throw new InvalidAccountException(
                "Virement impossible vers le même compte : " + from.getAccountNumber());
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif : " + amount);
        }
        if (amount > MAX_TRANSFER) {
            throw new TransferLimitExceededException(MAX_TRANSFER, amount);
        }
        if (amount > from.getBalance()) {
            throw new InsufficientFundsException(from.getBalance(), amount);
        }

        from.debit(amount);
        to.credit(amount);
    }
}