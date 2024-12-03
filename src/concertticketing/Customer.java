package concertticketing;

import java.util.Scanner;

public class Customer {

    public void ctransaction() {

        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("------------------------------------");
            System.out.println("==Customer Panel==");
            System.out.println("------------------------------------");
            System.out.println("1. ADD Customer");
            System.out.println("2. View Customer");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Exit");

            System.out.print("Enter Selection: ");

            int act = 0;
            do {
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    sc.next();
                }
                act = sc.nextInt();
                sc.nextLine();
                if (act < 1 || act > 5) {
                    System.out.println("Invalid selection. Please enter a number between 1 and 5.");
                }
            } while (act < 1 || act > 5);

            Customer cs = new Customer();
            switch (act) {
                case 1:
                    cs.addCustomer();
                    cs.viewCustomer();
                    break;
                case 2:
                    cs.viewCustomer();
                    break;
                case 3:
                    cs.viewCustomer();
                    cs.updateCustomer();
                    cs.viewCustomer();
                    break;
                case 4:
                    cs.deleteCustomer();
                    break;
                case 5:
                    System.out.println("Exiting.");
                    return;
            }

            do {
                System.out.println("Do you want to continue?(yes/no):");
                response = sc.nextLine();
                if (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));

        } while (response.equalsIgnoreCase("yes"));
    }

    public void addCustomer() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Customer First Name: ");
        String fname = sc.next();
        System.out.print("Customer Last Name: ");
        String lname = sc.next();
        System.out.print("Age: ");

        double age = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid age (number).");
                sc.next();
            }
            age = sc.nextDouble();
            sc.nextLine();
            if (age <= 0) {
                System.out.println("Age must be a positive number. Please enter a valid age.");
            }
        } while (age <= 0);

        System.out.print("Phone Number: ");

        double phone = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid phone number (number).");
                sc.next();
            }
            phone = sc.nextDouble();                                    
            sc.nextLine();
            if (phone <= 0) {
                System.out.println("Phone number must be a positive number. Please enter a valid phone number.");
            }
        } while (phone <= 0);

        System.out.print("Customer Email: ");
        String email = sc.next();
                                
        while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email ending with @yahoo.com, @gmail.com, or @hotmail.com");
            email = sc.next();
        }

        System.out.print("Customer Status: ");
        String status = sc.next();

        String qry = "INSERT INTO tbl_Customer (c_name, c_lname, c_age, c_phone, c_email, c_status) VALUES (?, ?, ?, ?, ?, ?)";

        conf.addRecord(qry, fname, lname, age, phone, email, status);
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("@yahoo.com") || email.endsWith("@gmail.com") || email.endsWith("@hotmail.com");
    }

    public void viewCustomer() {

        String qry = "SELECT * FROM tbl_Customer";
        String[] hdrs = {"ID", "First Name", "Last Name", "Age", "Phone Number", "Email", "Status"};
        String[] clms = {"c_id", "c_name", "c_lname", "c_age", "c_phone", "c_email", "c_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    private void updateCustomer() {

        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Update: ");

        int id = 0;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid customer ID (integer).");
                sc.next();
            }
            id = sc.nextInt();
            sc.nextLine();
            if (conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id =?", id) == 0) {
                System.out.println("Seleted ID doesn't exist!");
                System.out.println("Select Customer ID Again: ");
            }
        } while (conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id =?", id) == 0);

        System.out.print("Customer First Name: ");
        String fname = sc.next();
        System.out.print("Customer Last Name: ");
        String lname = sc.next();
        System.out.print("Age: ");

        double age = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid age (number).");
                sc.next();
            }
            age = sc.nextDouble();
            sc.nextLine();
            if (age <= 0) {
                System.out.println("Age must be a positive number. Please enter a valid age.");
            }
        } while (age <= 0);

        System.out.print("Phone Number: ");

        double phone = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid phone number (number).");
                sc.next();
            }
            phone = sc.nextDouble();
            sc.nextLine();
            if (phone <= 0) {
                System.out.println("Phone number must be a positive number. Please enter a valid phone number.");
            }
        } while (phone <= 0);

        System.out.print("Customer Email: ");
        String email = sc.next();

        while (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email ending with @yahoo.com, @gmail.com, or @hotmail.com");
            email = sc.next();
        }

        System.out.print("Customer Status: ");
        String status = sc.next();

        String qry = "UPDATE tbl_Customer SET c_name = ?, c_lname = ?, c_age = ?, c_phone = ?, c_email = ?, c_status = ? WHERE c_id = ?";

        conf.updateRecord(qry, fname, lname, age, phone, email, status, id);
    }

    private void deleteCustomer() {

        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Delete: ");

        int id = 0;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid customer ID (integer).");
                sc.next();
            }
            id = sc.nextInt();
            sc.nextLine();
            if (conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id =?", id) == 0) {
                System.out.println("Seleted ID doesn't exist!");
                System.out.println("Select Customer ID Again: ");
            }
        } while (conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id =?", id) == 0);

        String qry = "DELETE FROM tbl_Customer WHERE c_id = ?";

        conf.deleteRecord(qry, id);
    }
}
