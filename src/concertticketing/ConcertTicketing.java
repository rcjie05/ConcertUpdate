package concertticketing;

import java.util.Scanner;

public class ConcertTicketing {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = true;
        do {
            System.out.println("------------------------------------");
            System.out.println("==CONCERT TICKETING==");
            System.out.println("------------------------------------");
            System.out.println("");
            System.out.println("1. Concert");
            System.out.println("2. Customer");
            System.out.println("3. Orders");
            System.out.println("4. Reports");
            System.out.println("5. EXIT");
            System.out.println("Enter Action: ");
                        
            int action = 0;
            do {
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    sc.next();
                }
                action = sc.nextInt();
                sc.nextLine();
                if (action < 1 || action > 5) {
                    System.out.println("Invalid selection. Please enter a number between 1 and 5.");
                }
            } while (action < 1 || action > 5);

            switch (action) {
                case 1:
                    Concert ct = new Concert();
                    ct.cttransaction();
                    break;
                case 2:
                    Customer cs = new Customer();
                    cs.ctransaction();
                    break;
                case 3:
                    Orders so = new Orders();
                    so.stransaction();
                    break;
                case 4:
                    Report rp = new Report();
                    rp.rtransaction();
                    break;
                case 5:
                    System.out.println("Exit Selected...type 'yes' to continue: ");
                    String resp = sc.next();
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    break;
            }
        } while (exit);
    }
    
}

