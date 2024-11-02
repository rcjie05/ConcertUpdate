package concertticketing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Sold {
    
    public void stransaction(){
        Scanner sc = new Scanner(System.in);
        String response;
        
        do{
        System.out.println("\n------------------------------------");
        System.out.println("Sold Tickets Panel");
        System.out.println("1.ADD Sold");
        System.out.println("2.View Sold");
        System.out.println("3.Update Sold");
        System.out.println("4.Delete Sold");
        System.out.println("5.Exit");
        
        System.out.print("Enter Seletion: ");
        int act = sc.nextInt();
        Sold so = new Sold();
        switch(act){
            
            case 1:
                so.addSold();
                so.viewSold();
                break;
            case 2:
                so.viewSold();
                break;
            case 3:
                so.viewSold();
                so.updateSold();
                so.viewSold();
                break;
            case 4:
               
                break;
        }
            System.out.println("Do you want to continue?(yes/no):");
            response = sc.next();
        }while(response.equalsIgnoreCase("yes"));
    }
    
    private void addSold(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        Customer cs = new Customer();
        cs.viewCustomer();
        
        System.out.println("Enter the ID of the Customer: ");
        int cid = sc.nextInt();
        
        
        String csql = "SELECT c_id FROM tbl_Customer WHERE c_id = ?";
        while(conf.getSingleValue(csql, cid) == 0){
            System.out.println("Customer does not exist, select Again: ");
            cid = sc.nextInt();
        }
        
        Concert ct = new Concert();
        ct.viewConcert();
        
        System.out.println("Enter the ID of the Concert: ");
        int ctid = sc.nextInt();
        
        
        String ctsql = "SELECT ct_id FROM tbl_Concert WHERE ct_id = ?";
        while(conf.getSingleValue(ctsql, ctid) == 0){
            System.out.println("Concert does not exist, select Again: ");
            ctid = sc.nextInt();
        }
        
        System.out.println("Select Type of Tickets");
        System.out.println("1.Premium");
        System.out.println("2.Regular");
        System.out.println("Enter selected Tickets: ");
        int ticketType = sc.nextInt();
        
        while (ticketType != 1 && ticketType != 2) {
            System.out.println("Invalid ticket type. Please enter 1 for Premium or 2 for Regular.");
            ticketType = sc.nextInt();
        }
        
        System.out.println("Enter Quantity: ");
        double quantity = sc.nextDouble();
        
        String priceqry;
        if (ticketType == 1) {
            priceqry = "SELECT ct_premium FROM tbl_Concert WHERE ct_id = ?";
        } else {
            priceqry = "SELECT ct_regular FROM tbl_Concert WHERE ct_id = ?";
        }
        
        double price = conf.getSingleValue(priceqry, ctid);
        double due = price * quantity;
        
        System.out.println("---------------------------------------------------");
        System.out.println("Total due: "+due);
        System.out.println("---------------------------------------------------");
        
        System.out.println("Enter the Received cash: ");
        double rcash = sc.nextDouble();
        
        while(rcash < due){
            System.out.print("Insuffient amount, try again: ");
            rcash = sc.nextDouble();
        }
        
        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate.format(format);
        
        String status = "Pending";
        
        String soldqry = "INSERT INTO tbl_sold(c_id, ct_id, s_type, s_quantity, s_due, s_rcash, s_date, s_status)VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        conf.addRecord(soldqry, cid, ctid, ticketType, quantity, due, rcash, date, status);
        
    }
    
    public void viewSold() {
    String qry = "SELECT s_id, c_name, ct_name, tbl_Sold.s_due, s_date, s_status " +
               "FROM tbl_Sold " +
               "JOIN tbl_Customer ON tbl_Customer.c_id = tbl_Sold.c_id " +
               "JOIN tbl_Concert ON tbl_Concert.ct_id = tbl_Sold.ct_id";
    String[] hdrs = {"OID", "First Name", "Concert Name", "Due", "Date", "Status"};
    String[] clms = {"s_id", "c_name", "ct_name", "s_due", "s_date", "s_status"};

    config conf = new config();
    conf.viewRecords(qry, hdrs, clms);
}
    
    private void updateSold() {

        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        
        Customer cs = new Customer();
        cs.viewCustomer();
        while (conf.getSingleValue("SELECT s_id FROM tbl_Sold WHERE s_id =?", id) == 0) {
            System.out.println("Seleted ID doesn't exist!");
            System.out.println("Select ID Again: ");
            id = sc.nextInt();
        }

        System.out.println("Enter the ID of the Customer: ");
        int cid = sc.nextInt();

        String csql = "SELECT c_id FROM tbl_Customer WHERE c_id = ?";
        while (conf.getSingleValue(csql, cid) == 0) {
            System.out.println("Customer does not exist, select Again: ");
            cid = sc.nextInt();
        }

        Concert ct = new Concert();
        ct.viewConcert();

        System.out.println("Enter the ID of the Concert: ");
        int ctid = sc.nextInt();

        String ctsql = "SELECT ct_id FROM tbl_Concert WHERE ct_id = ?";
        while (conf.getSingleValue(ctsql, ctid) == 0) {
            System.out.println("Concert does not exist, select Again: ");
            ctid = sc.nextInt();
        }

        System.out.println("Select Type of Tickets");
        System.out.println("1.Premium");
        System.out.println("2.Regular");
        System.out.println("Enter selected Tickets: ");
        int ticketType = sc.nextInt();

        while (ticketType != 1 && ticketType != 2) {
            System.out.println("Invalid ticket type. Please enter 1 for Premium or 2 for Regular.");
            ticketType = sc.nextInt();
        }

        System.out.println("Enter Quantity: ");
        double quantity = sc.nextDouble();

        String priceqry;
        if (ticketType == 1) {
            priceqry = "SELECT ct_premium FROM tbl_Concert WHERE ct_id = ?";
        } else {
            priceqry = "SELECT ct_regular FROM tbl_Concert WHERE ct_id = ?";
        }

        double price = conf.getSingleValue(priceqry, ctid);
        double due = price * quantity;

        System.out.println("---------------------------------------------------");
        System.out.println("Total due: " + due);
        System.out.println("---------------------------------------------------");

        System.out.println("Enter the Received cash: ");
        double rcash = sc.nextDouble();

        while (rcash < due) {
            System.out.print("Insuffient amount, try again: ");
            rcash = sc.nextDouble();
        }

        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = currdate.format(format);

        String status = "Pending";
        String qry = "UPDATE tbl_Sold SET c_id = ?, ct_id = ?, s_type = ?, s_quantity = ?, s_due = ?, s_rcash = ?, s_status = ? WHERE s_id = ?";


        conf.updateRecord(qry, cid, ctid, ticketType, quantity, due, rcash, status, id);

    }
        
        
  
        
    
}
