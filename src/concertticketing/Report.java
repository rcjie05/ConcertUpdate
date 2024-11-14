package concertticketing;

import java.util.Scanner;

public class Report {

    public void rtransaction() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("\n------------------------------------");
            System.out.println("==Reports Panel==");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Concerts");
            System.out.println("3. View All Orders");
            System.out.println("4. View Customer Orders");
            System.out.println("5. Exit");

            System.out.print("Enter Selection: ");
            int act = sc.nextInt();
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
            }
            System.out.println("Do you want to continue?(yes/no):");
            response = sc.next();
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
    String qry = "SELECT tbl_Orders.o_id, tbl_Customer.c_name, tbl_Orders.ct_id, tbl_Concert.ct_name, tbl_Orders.o_type, tbl_Concert.ct_premium, tbl_Concert.ct_regular, tbl_Orders.o_due, tbl_Orders.o_date " +
               "FROM tbl_Orders " +
               "JOIN tbl_Concert ON tbl_Orders.ct_id = tbl_Concert.ct_id " +
               "JOIN tbl_Customer ON tbl_Orders.c_id = tbl_Customer.c_id"; // Added the join
    String[] hdrs = {"Order ID", "Customer Name", "Concert ID", "Concert Name", "Ticket Type", "Premium Tickets", "Regular Tickets", "Total Price", "Order Date"};
    String[] clms = {"o_id", "c_name", "ct_id", "ct_name", "o_type", "ct_premium", "ct_regular", "o_due", "o_date"};

    config conf = new config();
    conf.viewRecords(qry, hdrs, clms);
}

    public void viewCustomerOrders() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    Customer cs = new Customer();

    cs.viewCustomer();
    System.out.print("Enter Customer ID: ");
    int customerId = sc.nextInt();

    if (conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id = ?", customerId) == 0) {
        System.out.println("Customer ID not found!");
        return;
    }

    String qry = "SELECT tbl_Orders.o_id, tbl_Concert.ct_name, tbl_Customer.c_name, tbl_Orders.o_quantity, tbl_Orders.o_type, tbl_Orders.o_due, tbl_Orders.o_status, tbl_Orders.o_date " +
               "FROM tbl_Orders " +
               "JOIN tbl_Concert ON tbl_Orders.ct_id = tbl_Concert.ct_id " +
               "JOIN tbl_Customer ON tbl_Orders.c_id = tbl_Customer.c_id " +
               "WHERE tbl_Orders.c_id = ?";

    String[] hdrs = {"Order ID", "Concert Name", "Customer Name", "Quantity", "Ticket Type", "Total Price", "Order Status", "Order Date"};
    String[] clms = {"o_id", "ct_name", "c_name", "o_quantity", "o_type", "o_due", "o_status", "o_date"};

    
    conf.viewRecords(qry, hdrs, clms, customerId); // Correct call to viewRecords
}
    
}