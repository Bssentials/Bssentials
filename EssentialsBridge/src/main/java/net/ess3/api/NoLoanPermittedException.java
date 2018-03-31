package net.ess3.api;

public class NoLoanPermittedException extends Exception {
    private static final long serialVersionUID = 1L; // Bssentials

    public NoLoanPermittedException() {
        super("negativeBalanceError");
    }
}
