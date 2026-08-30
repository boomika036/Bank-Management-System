package service;

import model.Account;
import model.Transaction;
import index.CustomerIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BankService {

    private final HashMap<Integer, Account> accounts = new HashMap<>();
    private final HashMap<Integer, List<Transaction>> history = new HashMap<>();
    private final CustomerIndex customerIndex = new CustomerIndex();
    private final TransferService transferService = new TransferService();

    // Create account
    public boolean createAccount(int id, String name, double balance) {

        if (accounts.containsKey(id) || balance < 0) {
            return false;
        }

        Account account = new Account(id, name, balance);

        accounts.put(id, account);
        history.put(id, new ArrayList<>());
        customerIndex.add(name, id);

        return true;
    }

    // Deposit
    public boolean deposit(int id, double amount) {

        Account account = accounts.get(id);

        if (account == null || amount <= 0) {
            return false;
        }

        account.deposit(amount);

        history.get(id).add(
                new Transaction("DEPOSIT", amount, -1)
        );

        return true;
    }

    // Withdraw
    public boolean withdraw(int id, double amount) {

        Account account = accounts.get(id);

        if (account == null) {
            return false;
        }

        if (!account.withdraw(amount)) {
            return false;
        }

        history.get(id).add(
                new Transaction("WITHDRAW", amount, -1)
        );

        return true;
    }

    // Transfer
    public boolean transfer(int fromId, int toId, double amount) {

        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);

        // Check both accounts BEFORE changing balance
        if (from == null || to == null) {
            return false;
        }

        double oldFromBalance = from.getBalance();
        double oldToBalance = to.getBalance();

        boolean success = transferService.transfer(from, to, amount);

        if (!success) {
            return false;
        }

        try {
            history.get(fromId).add(
                    new Transaction("TRANSFER_OUT", amount, toId)
            );

            history.get(toId).add(
                    new Transaction("TRANSFER_IN", amount, fromId)
            );

            return true;

        } catch (Exception e) {

            // Complete rollback
            while (from.getBalance() != oldFromBalance) {
                if (from.getBalance() < oldFromBalance) {
                    from.deposit(oldFromBalance - from.getBalance());
                } else {
                    from.withdraw(from.getBalance() - oldFromBalance);
                }
            }

            while (to.getBalance() != oldToBalance) {
                if (to.getBalance() < oldToBalance) {
                    to.deposit(oldToBalance - to.getBalance());
                } else {
                    to.withdraw(to.getBalance() - oldToBalance);
                }
            }

            return false;
        }
    }

    // Reverse last transaction
    public boolean reverseLastTransaction(int id) {

        Account account = accounts.get(id);
        List<Transaction> list = history.get(id);

        if (account == null || list == null || list.isEmpty()) {
            return false;
        }

        Transaction transaction = list.remove(list.size() - 1);

        switch (transaction.getType()) {

            case "DEPOSIT":
                if (account.getBalance() < transaction.getAmount()) {
                    list.add(transaction);
                    return false;
                }

                account.withdraw(transaction.getAmount());
                return true;

            case "WITHDRAW":
                account.deposit(transaction.getAmount());
                return true;

            case "TRANSFER_OUT":

                Account target = accounts.get(
                        transaction.getOtherAccountId()
                );

                if (target == null ||
                    account.getBalance() < transaction.getAmount()) {

                    list.add(transaction);
                    return false;
                }

                account.withdraw(transaction.getAmount());
                target.deposit(transaction.getAmount());

                removeLastMatchingTransfer(
                        transaction.getOtherAccountId(),
                        id
                );

                return true;

            case "TRANSFER_IN":

                Account source = accounts.get(
                        transaction.getOtherAccountId()
                );

                if (source == null ||
                    account.getBalance() < transaction.getAmount()) {

                    list.add(transaction);
                    return false;
                }

                account.withdraw(transaction.getAmount());
                source.deposit(transaction.getAmount());

                removeLastMatchingTransfer(
                        transaction.getOtherAccountId(),
                        id
                );

                return true;

            default:
                list.add(transaction);
                return false;
        }
    }

    private void removeLastMatchingTransfer(int accountId, int otherId) {

        List<Transaction> list = history.get(accountId);

        if (list == null || list.isEmpty()) {
            return;
        }

        for (int i = list.size() - 1; i >= 0; i--) {

            Transaction t = list.get(i);

            if ((t.getType().equals("TRANSFER_OUT")
                    || t.getType().equals("TRANSFER_IN"))
                    && t.getOtherAccountId() == otherId) {

                list.remove(i);
                return;
            }
        }
    }

    // Display account
    public void displayAccount(int id) {

        Account account = accounts.get(id);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("\n----- ACCOUNT DETAILS -----");
        System.out.println("Account ID : " + account.getId());
        System.out.println("Name       : " + account.getCustomerName());
        System.out.println("Balance    : ₹" + account.getBalance());
    }

    // Find all accounts of customer
    public void findCustomer(String name) {

        List<Integer> ids = customerIndex.find(name);

        if (ids.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            System.out.println("Accounts of " + name + " : " + ids);
        }
    }
}