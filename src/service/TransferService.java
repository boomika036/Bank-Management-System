package service;

import model.Account;

public class TransferService {

    public boolean transfer(Account from, Account to, double amount) {

        if (from == null || to == null) {
            return false;
        }

        if (from.getId() == to.getId()) {
            return false;
        }

        if (amount <= 0 || from.getBalance() < amount) {
            return false;
        }

        // First operation
        boolean withdrawn = from.withdraw(amount);

        if (!withdrawn) {
            return false;
        }

        try {
            // Second operation
            to.deposit(amount);
            return true;

        } catch (Exception e) {
            // Rollback
            from.deposit(amount);
            return false;
        }
    }
}