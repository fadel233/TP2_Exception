public class InsufficientFundsException extends BankingException {
    private final double balance;
    private final double requested;

    public InsufficientFundsException(double balance, double requested) {
        super("Fonds insuffisants : solde = " + balance + " €, montant demandé = " + requested + " €");
        this.balance = balance;
        this.requested = requested;
    }

    public double getBalance()   { return balance; }
    public double getRequested() { return requested; }
    public double getShortfall() { return requested - balance; }
}