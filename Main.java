public class Main {
    public static void main(String[] args) {
        BankAccount a = new BankAccount("FR001", 500);
        BankAccount b = new BankAccount("FR002", 100);
        TransferService service = new TransferService();

        try {
            service.transfer(a, b, 800);
            System.out.println("Virement réussi");
        } catch (InsufficientFundsException e) {
            System.out.println("Manque " + e.getShortfall() + " €");
        } catch (TransferLimitExceededException | InvalidAccountException e) {
            System.out.println("Refusé : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Argument invalide : " + e.getMessage());
        } finally {
            System.out.println("Solde A = " + a.getBalance() + " | Solde B = " + b.getBalance());
        }
    }
}