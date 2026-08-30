package main;

import service.BankService;
import java.util.Scanner;

public class BankConsoleApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankService bank = new BankService();

        while (true) {

            System.out.println("\n===== SECURE BANK =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Display Account");
            System.out.println("6. Reverse Last Transaction");
            System.out.println("7. Find Customer Accounts");
            System.out.println("8. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Account ID: ");
                    int id = sc.nextInt();

                    System.out.print("Customer Name: ");
                    String name = sc.next();

                    System.out.print("Initial Balance: ");
                    double balance = sc.nextDouble();

                    if (bank.createAccount(id, name, balance)) {
                        System.out.println("Account created successfully.");
                    } else {
                        System.out.println("Account creation failed.");
                    }
                    break;

                case 2:
                    System.out.print("Account ID: ");
                    id = sc.nextInt();

                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();

                    if (bank.deposit(id, amount)) {
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Deposit failed.");
                    }
                    break;

                case 3:
                    System.out.print("Account ID: ");
                    id = sc.nextInt();

                    System.out.print("Amount: ");
                    amount = sc.nextDouble();

                    if (bank.withdraw(id, amount)) {
                        System.out.println("Withdraw successful.");
                    } else {
                        System.out.println("Withdraw failed.");
                    }
                    break;

                case 4:
                    System.out.print("From Account ID: ");
                    int fromId = sc.nextInt();

                    System.out.print("To Account ID: ");
                    int toId = sc.nextInt();

                    System.out.print("Amount: ");
                    amount = sc.nextDouble();

                    if (bank.transfer(fromId, toId, amount)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println(
                                "Transfer failed. Balance unchanged."
                        );
                    }
                    break;

                case 5:
                    System.out.print("Account ID: ");
                    id = sc.nextInt();

                    bank.displayAccount(id);
                    break;

                case 6:
                    System.out.print("Account ID: ");
                    id = sc.nextInt();

                    if (bank.reverseLastTransaction(id)) {
                        System.out.println("Last transaction reversed.");
                    } else {
                        System.out.println("Reversal failed.");
                    }
                    break;

                case 7:
                    System.out.print("Customer Name: ");
                    name = sc.next();

                    bank.findCustomer(name);
                    break;

                case 8:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}