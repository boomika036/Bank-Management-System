import java.util.ArrayList;
import java.util.Scanner;

public class BalanceCheck {

    public static void check(ArrayList<Account> accounts, Scanner sc) {

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

        System.out.println("\n===== ACCOUNT DETAILS =====");
        System.out.println("Account ID      : " + account.getAccountId());
        System.out.println("Account Holder  : " + account.getAccountHolderName());
        System.out.println("Current Balance : " + account.getBalance());
    }
}