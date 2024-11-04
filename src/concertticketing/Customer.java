package concertticketing;

import java.util.Scanner;

public class Customer {
    
    public void ctransaction(){
        
        Scanner sc = new Scanner(System.in);
        String response;
        
        do{
        System.out.println("\n------------------------------------");
        System.out.println("Customer Panel");
        System.out.println("1.ADD Customer");
        System.out.println("2.View Customer");
        System.out.println("3.Update Customer");
        System.out.println("4.Delete Customer");
        System.out.println("5.Exit");
        
        System.out.print("Enter Seletion: ");
        int act = sc.nextInt();
        Customer cs = new Customer();
        switch(act){
            
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
        }
            System.out.println("Do you want to continue?(yes/no):");
            response = sc.next();
        }while(response.equalsIgnoreCase("yes"));
 }

public void addCustomer(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Customer First Name: ");
        String fname = sc.next();
        System.out.print("Customer Last Name: ");
        String lname = sc.next();
        System.out.print("Customer Email: ");
        String email = sc.next();
        System.out.print("Customer Status: ");
        String status = sc.next();

        String qry = "INSERT INTO tbl_Customer (c_name, c_lname, c_email, c_status) VALUES (?, ?, ?, ?)";

        conf.addRecord(qry, fname, lname, email, status);


    }

    public void viewCustomer() {
        
        String qry = "SELECT * FROM tbl_Customer";
        String[] hdrs = {"ID", "First Name", "Last Name", "Email", "Status"};
        String[] clms = {"c_id", "c_name", "c_lname", "c_email", "c_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

 private void updateCustomer(){
    
        Scanner sc= new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id =?",id)== 0){
            System.out.println("Seleted ID doesn't exist!");
            System.out.println("Select Customer ID Again: ");
            id = sc.nextInt();
        }
        
        System.out.print("Enter new First Name: ");
        String nname = sc.next();
        System.out.print("Enter new Last Name: ");
        String nlname = sc.next();
        System.out.print("Enter new Email: ");
        String nemail = sc.next();
        System.out.print("Enter new Status: ");
        String nstatus = sc.next();
        
        String qry = "UPDATE tbl_Customer SET c_name = ?, c_lname = ?, c_email = ?, c_status = ? WHERE c_id = ?";
        
        conf.updateRecord(qry, nname, nlname, nemail, nstatus, id);
        
    }
 
 private void deleteCustomer(){
        
        Scanner sc= new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT c_id FROM tbl_Customer WHERE c_id =?",id)== 0){
            System.out.println("Seleted ID doesn't exist!");
            System.out.println("Select Customer ID Again: ");
            id = sc.nextInt();
        }
        
        String qry = "DELETE FROM tbl_Customer WHERE c_id = ?";
        
        conf.deleteRecord(qry, id);
    
    }
    
}
