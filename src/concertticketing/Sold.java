package concertticketing;

import java.util.Scanner;

public class Sold {
    
    public void stransaction(){
        Scanner sc = new Scanner(System.in);
        String response;
        
        do{
        System.out.println("\n------------------------------------");
        System.out.println("Sold Tickets Panel");
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
                
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
               
                break;
        }
            System.out.println("Do you want to continue?(yes/no):");
            response = sc.next();
        }while(response.equalsIgnoreCase("yes"));
    }
    
}
