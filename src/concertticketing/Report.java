package concertticketing;

import java.util.Scanner;

public class Report {

    Customer cs = new Customer();

    public void rtransaction() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("------------------------------------");
            System.out.println("==Reports Panel==");
            System.out.println("------------------------------------");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Concerts");
            System.out.println("3. View All Orders");
            System.out.println("4. View Customer Orders");
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

            Report rp = new Report();
            switch (act) {
                case 1:
                    rp.viewAllCustomers();
                    break;
                case 2:
                    rp.viewAllConcerts();
                    break;
                case 3:
                    rp.viewAllOrders();
                    break;
                case 4:
                    rp.viewCustomerOrders();
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

    public void viewAllCustomers() {
        String qry = "SELECT * FROM tbl_Customer";
        String[] hdrs = {"ID", "First Name", "Last Name", "Email", "Status"};
        String[] clms = {"c_id", "c_name", "c_lname", "c_email", "c_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    public void viewAllConcerts() {
        String qry = "SELECT * FROM tbl_Concert";
        String[] hdrs = {"ID", "Concert Name", "Premium Price", "Regular Price", "Premium Stock", "Regular Stock", "Concert Status"};
        String[] clms = {"ct_id", "ct_name", "ct_premium", "ct_regular", "ct_pstocks", "ct_rstocks", "ct_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

    public void viewAllOrders() {
        String qry = "SELECT tbl_Orders.o_id, tbl_Customer.c_name, tbl_Orders.ct_id, tbl_Concert.ct_name, tbl_Orders.o_type, tbl_Concert.ct_premium,"
                + " tbl_Concert.ct_regular, tbl_Orders.o_due, tbl_Orders.o_date " +
                   "FROM tbl_Orders " +
                   "JOIN tbl_Concert ON tbl_Orders.ct_id = tbl_Concert.ct_id " +
                   "JOIN tbl_Customer ON tbl_Orders.c_id = tbl_Customer.c_id";
        String[] hdrs = {"Order ID", "Customer Name", "Concert ID", "Concert Name", "Ticket Type", "Premium Tickets", "Regular Tickets", "Total Price", "Order Date"};
        String[] clms = {"o_id", "c_name", "ct_id", "ct_name", "o_type", "ct_premium", "ct_regular", "o_due", "o_date"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

   public void viewCustomerOrders() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        cs.viewCustomer();

        int customerId = 0;
        do {
            System.out.print("Enter Customer ID: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid customer ID (integer).");
                sc.next();
            }
            customerId = sc.nextInt();
            sc.nextLine();
            if (conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id = ?", customerId) == 0) {
                System.out.println("Customer ID not found! Please enter a valid customer ID.");
            }
        } while (conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id = ?", customerId) == 0);

        String customerFullName = conf.getCustomerNameById("SELECT c_name || ' ' || c_lname AS full_name FROM tbl_Customer WHERE c_id = ?", customerId);
        String customerEmail = conf.getCustomerNameById("SELECT c_email FROM tbl_Customer WHERE c_id = ?", customerId);
        String customerAge = conf.getCustomerNameById("SELECT c_age FROM tbl_Customer WHERE c_id = ?", customerId);
        String customerPhone = conf.getCustomerNameById("SELECT c_phone FROM tbl_Customer WHERE c_id = ?", customerId);
        String customerStatus = conf.getCustomerNameById("SELECT c_status FROM tbl_Customer WHERE c_id = ?", customerId);

        String qry = "SELECT tbl_Orders.o_id, tbl_Concert.ct_name, tbl_Orders.o_quantity, tbl_Orders.o_type, tbl_Orders.o_due, tbl_Orders.o_status, tbl_Orders.o_date " +
           "FROM tbl_Orders " +
           "JOIN tbl_Concert ON tbl_Orders.ct_id = tbl_Concert.ct_id " +
           "JOIN tbl_Customer ON tbl_Orders.c_id = tbl_Customer.c_id " +
           "WHERE tbl_Orders.c_id = ?";

        String[] hdrs = {"Order ID", "Concert Name", "Quantity", "Ticket Type", "Total Price", "Order Status", "Order Date"};
        String[] clms = {"o_id", "ct_name", "o_quantity", "o_type", "o_due", "o_status", "o_date"};

        System.out.println("\n");
        System.out.println("---------------------------------------");
        System.out.println("Customer Order");
        System.out.println("---------------------------------------");
        System.out.println("Customer Name: " + customerFullName);
        System.out.println("Customer Email: " + customerEmail); 
        System.out.println("Customer Age: " + customerAge);
        System.out.println("Customer Phone: " + customerPhone);
        System.out.println("Customer Status: " + customerStatus);

        conf.viewRecords(qry, hdrs, clms, customerId);
    }
}