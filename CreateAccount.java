import java.util.ArrayList;
import java.util.Scanner;

public class CreateAccount {

    public static void create(ArrayList<Account> accounts, Scanner sc) {

        System.out.print("Enter Account ID: ");
        int accountId = sc.nextInt();
        sc.nextLine();

        for (Account account : accounts) {
            if (account.getAccountId() == accountId) {
                System.out.println("Account ID already exists!");
                return;
            }
        }

        System.out.print("Enter Account Holder Name: ");
        String accountHolderName = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        Account account = new Account(accountId, accountHolderName, balance);
        accounts.add(account);

        System.out.println("Account Created Successfully.");
    }
}