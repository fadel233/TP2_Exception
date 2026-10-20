public class TransferLimitExceededException extends BankingException {
    private final double limit;
    private final double requested;

    public TransferLimitExceededException(double limit, double requested) {
        super("Plafond de " + limit + " € dépassé : montant demandé = " + requested + " €");
        this.limit = limit;
        this.requested = requested;
    }

    public double getLimit()     { return limit; }
    public double getRequested() { return requested; }
}