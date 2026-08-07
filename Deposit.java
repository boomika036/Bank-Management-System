import java.util.ArrayList;
import java.util.Scanner;

public class Deposit {

    public static void deposit(ArrayList<Account> accounts, Scanner sc) {

        System.out.print("Enter Account ID: ");
        int accountId = sc.nextInt();

        Account account = null;

        for (Account acc : accounts) {
            if (acc.getAccountId() == accountId) {
                account = acc;
                break;
            }
        }

        if (account == null) {
            System.out.println("Account Not Found!");
            return;
        }

        System.out.print("Enter Deposit Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid Amount!");
            return;
        }

        account.setBalance(account.getBalance() + amount);

        System.out.println("Deposit Successful.");
        System.out.println("Current Balance: " + account.getBalance());
    }
}