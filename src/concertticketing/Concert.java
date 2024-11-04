package concertticketing;

import java.util.Scanner;

public class Concert {
    
    public void cttransaction(){
        
        Scanner sc = new Scanner(System.in);
        String response;
        
        do{
        System.out.println("\n------------------------------------");
        System.out.println("Concert Panel");
        System.out.println("1.ADD Concert");
        System.out.println("2.View Concert");
        System.out.println("3.Update Concert");
        System.out.println("4.Delete Concert");
        System.out.println("5.Exit");
        
        System.out.print("Enter Seletion: ");
        int act = sc.nextInt();
        Concert ct = new Concert();
        switch(act){
            
            case 1:
                ct.addConcert();
                ct.viewConcert();
                break;
            case 2:
                ct.viewConcert();
                break;
            case 3:
                ct.viewConcert();
                ct.updateConcert();
                ct.viewConcert();
                break;
            case 4:
                ct.deleteConcert();
                break;
        }
            System.out.println("Do you want to continue?(yes/no):");
            response = sc.next();
        }while(response.equalsIgnoreCase("yes"));
 }

public void addConcert(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Concert Name: ");
        String cname = sc.next();
        System.out.print("Premium Price: ");
        double premium = sc.nextDouble();
        System.out.print("Regular Price: ");
        double regular = sc.nextDouble();
        System.out.print("Premium Stocks: ");
        double cpstocks = sc.nextDouble();
        System.out.print("Regular Stocks: ");
        double crstocks = sc.nextDouble();
        System.out.print("Concert Status: ");
        String cstatus = sc.next();

        String qry = "INSERT INTO tbl_Concert (ct_name, ct_premium, ct_regular, ct_pstocks, ct_rstocks, ct_status) VALUES (?, ?, ?, ?, ?, ?)";

        conf.addRecord(qry, cname, premium, regular, cpstocks, crstocks, cstatus);


    }

public void viewConcert() {
        
        String qry = "SELECT * FROM tbl_Concert";
        String[] hdrs = {"ID", "Concert Name", "Premium Price", "Regular Price", "Premium Stock", "Regular Stock", "Concert Status"};
        String[] clms = {"ct_id", "ct_name", "ct_premium", "ct_regular", "ct_pstocks", "ct_rstocks", "ct_status"};

        config conf = new config();
        conf.viewRecords(qry, hdrs, clms);
    }

 private void updateConcert(){
    
        Scanner sc= new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Update: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT ct_id FROM tbl_Concert WHERE ct_id =?",id)== 0){
            System.out.println("Seleted ID doesn't exist!");
            System.out.println("Select Customer ID Again: ");
            id = sc.nextInt();
        }
        
       System.out.print("Enter New Concert Name: ");
        String cname = sc.next();
        System.out.print("Enter New Premium Price: ");
        double premium = sc.nextDouble();
        System.out.print("Enter New Regular Price: ");
        double regular = sc.nextDouble();
        System.out.print("Enter New Premium Stocks: ");
        double cpstocks = sc.nextDouble();
        System.out.print("Enter New Regular Stocks: ");
        double crstocks = sc.nextDouble();
        System.out.print("Enter New Concert Status: ");
        String cstatus = sc.next();
        
        String qry = "UPDATE tbl_Concert SET ct_name = ?, ct_premium = ?, ct_regular = ?, ct_pstocks = ?, ct_rstocks = ?, ct_status = ? WHERE ct_id = ?";
        
        conf.updateRecord(qry, cname, premium, regular, cpstocks, crstocks, cstatus, id);
        
    }
 
 private void deleteConcert(){
        
        Scanner sc= new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();
        
        while(conf.getSingleValue("SELECT ct_id FROM tbl_Concert WHERE ct_id =?",id)== 0){
            System.out.println("Seleted ID doesn't exist!");
            System.out.println("Select Customer ID Again: ");
            id = sc.nextInt();
        }
        
        String qry = "DELETE FROM tbl_Concert WHERE ct_id = ?";
        
        conf.deleteRecord(qry, id);
    
    }
 
}
    
