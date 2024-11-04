package concertticketing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Orders {
    
    public void stransaction(){
        Scanner sc = new Scanner(System.in);
        String response;
        
        do{
        System.out.println("\n------------------------------------");
        System.out.println("==Order Tickets Panel==");
        System.out.println("1.ADD Orders");
        System.out.println("2.View Order");
        System.out.println("3.Update Order");
        System.out.println("4.Delete Order");
        System.out.println("5.Exit");
        
        System.out.print("Enter Seletion: ");
        int act = sc.nextInt();
        Orders so = new Orders();
        switch(act){
            
            case 1:
                so.addOrder();
                so.viewOrder();
                break;
            case 2:
                so.viewOrder();
                break;
            case 3:
                so.viewOrder();
                so.updateOrder();
                so.viewOrder();
                break;
            case 4:
                so.viewOrder();
                so.deleteOrders();
                so.viewOrder();
                break;
  
        }
            System.out.println("Do you want to continue?(yes/no):");
            response = sc.next();
        }while(response.equalsIgnoreCase("yes"));
    }
    
    private void addOrder(){
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

        double premiumStock = conf.getSingleValue("SELECT ct_pstocks FROM tbl_Concert WHERE ct_id = ?", ctid);
        double regularStock = conf.getSingleValue("SELECT ct_rstocks FROM tbl_Concert WHERE ct_id = ?", ctid);
        System.out.println("Available Stocks:");
        System.out.println("Premium: " + premiumStock);
        System.out.println("Regular: " + regularStock);

        while (true) {
            System.out.println("Select Type of Tickets");
            System.out.println("1.Premium");
            System.out.println("2.Regular");
            System.out.println("Enter selected Tickets: ");
            int ticketTypeInt = sc.nextInt();
            
            String ticketType;
            if (ticketTypeInt == 1) {
                ticketType = "Premium";
            } else {
                ticketType = "Regular";
            }
            
            System.out.println("Enter Quantity: ");
            double quantity = sc.nextDouble();

            double availableStock;
            if (ticketType.equals("Premium")) {
                availableStock = premiumStock;
            } else {
                availableStock = regularStock;
            }

            if (quantity > availableStock) {
                System.out.println("Insufficient stock for " + ticketType + " tickets. Available: " + availableStock);
                System.out.println("Do you want to try again? (yes/no)");
                String tryAgain = sc.next();
                if (tryAgain.equalsIgnoreCase("no")) {
                    return;
                }
            } else {
                String priceqry;
                if (ticketType.equals("Premium")) {
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
                
                String soldqry = "INSERT INTO tbl_Orders(c_id, ct_id, o_type, o_quantity, o_due, o_rcash, o_date, o_status)VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                
                conf.addRecord(soldqry, cid, ctid, ticketType, quantity, due, rcash, date, status);
                
                if (ticketType.equals("Premium")) {
                    conf.updateRecord("UPDATE tbl_Concert SET ct_pstocks = ct_pstocks - ? WHERE ct_id = ?", quantity, ctid);
                } else {
                    conf.updateRecord("UPDATE tbl_Concert SET ct_rstocks = ct_rstocks - ? WHERE ct_id = ?", quantity, ctid);
                }
                return;
            }
        }
    }
    
   public void viewOrder() {
    String qry = "SELECT tbl_Orders.o_id, tbl_Customer.c_name, tbl_Customer.c_lname, tbl_Concert.ct_name, tbl_Orders.o_type, tbl_Orders.o_due, tbl_Orders.o_quantity, tbl_Orders.o_date, tbl_Orders.o_status " +
               "FROM tbl_Orders " +
               "JOIN tbl_Customer ON tbl_Customer.c_id = tbl_Orders.c_id " +
               "JOIN tbl_Concert ON tbl_Concert.ct_id = tbl_Orders.ct_id";
    String[] hdrs = {"OID", "First Name", "Last Name", "Concert Name","Type Ticket", "Due", "Quantity", "Date", "Status"};
    String[] clms = {"o_id", "c_name", "c_lname", "ct_name", "o_type", "o_due", "o_quantity", "o_date", "o_status"};

    config conf = new config();
    conf.viewRecords(qry, hdrs, clms);
}
    
    private void updateOrder() {

        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        
        Customer cs = new Customer();
        cs.viewCustomer();
        while (conf.getSingleValue("SELECT o_id FROM tbl_Orders WHERE o_id =?", id) == 0) {
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
        
        String ticketType;
        while (true) {
            ticketType = sc.next();
            if (ticketType.equals("1") || ticketType.equals("2")) {
                break;
            }
            System.out.println("Invalid ticket type. Please enter 1 for Premium or 2 for Regular.");
        }
        if (ticketType.equals("1")) {
            ticketType = "Premium";
        } else {
            ticketType = "Regular";
        }

        System.out.println("Enter Quantity: ");
        double quantity = sc.nextDouble();

        String priceqry;
        if (ticketType.equals("Premium")) {
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

        System.out.print("Order Status: ");
        String status = sc.next();
        String qry = "UPDATE tbl_Orders SET c_id = ?, ct_id = ?, o_type = ?, o_quantity = ?, o_due = ?, o_rcash = ?, o_status = ? WHERE o_id = ?";


        conf.updateRecord(qry, cid, ctid, ticketType, quantity, due, rcash, status, id);

    }
    
    private void deleteOrders(){
        
        Scanner sc= new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT o_id FROM tbl_Orders WHERE o_id =?",id)== 0){
            System.out.println("Seleted ID doesn't exist!");
            System.out.println("Select ID Again: ");
            id = sc.nextInt();
        }
        
        String qry = "DELETE FROM tbl_Orders WHERE o_id = ?";
        
        conf.deleteRecord(qry, id);
    
    }
}