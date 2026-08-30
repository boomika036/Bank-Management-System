import java.util.ArrayList;
import java.util.Scanner;

class Account {

    int accountNumber;
    String accountHolder;
    double balance;

    Account(int accountNumber, String accountHolder, double balance) {

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;

    }

    void displayAccount() {

        System.out.println("----------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Balance        : ₹" + balance);

    }

}


public class BankManagement {

    static ArrayList<Account> accounts = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {


        while(true) {


            System.out.println("\n==============================");
            System.out.println(" BANK MANAGEMENT SYSTEM");
            System.out.println("==============================");

            System.out.println("1. Create Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Exit");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();


            switch(choice) {


                case 1:

                    createAccount();
                    break;


                case 2:

                    viewAccounts();
                    break;


                case 3:

                    System.out.println("Thank You!");
                    System.exit(0);
                    break;


                default:

                    System.out.println("Invalid Choice");

            }

        }

    }



    static void createAccount() {


        System.out.print("\nEnter Account Number : ");
        int accNo = sc.nextInt();

        sc.nextLine();


        // Duplicate Account Validation

        for(Account account : accounts) {


            if(account.accountNumber == accNo) {


                System.out.println("Account Number Already Exists!");
                return;


            }

        }



        System.out.print("Enter Account Holder Name : ");
        String name = sc.nextLine();



        // Empty Name Validation

        if(name.trim().isEmpty()) {


            System.out.println("Name Cannot Be Empty!");
            return;


        }




        System.out.print("Enter Initial Balance : ");
        double balance = sc.nextDouble();



        // Negative Balance Validation

        if(balance < 0) {


            System.out.println("Balance Cannot Be Negative!");
            return;


        }



        Account newAccount = new Account(accNo, name, balance);


        accounts.add(newAccount);



        System.out.println("Account Created Successfully!");

    }




    static void viewAccounts() {


        if(accounts.size() == 0) {


            System.out.println("No Accounts Available");

            return;


        }



        System.out.println("\n======= ACCOUNT DETAILS =======");



        for(Account account : accounts) {


            account.displayAccount();


        }


    }

}