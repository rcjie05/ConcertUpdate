package concertticketing;

import java.util.Scanner;

public class Concert {

    public void cttransaction() {

        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("------------------------------------");
            System.out.println("==Concert Panel==");
            System.out.println("------------------------------------");
            System.out.println("1. ADD Concert");
            System.out.println("2. View Concert");
            System.out.println("3. Update Concert");
            System.out.println("4. Delete Concert");
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

            Concert ct = new Concert();
            switch (act) {
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

    public void addConcert() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Concert Name: ");
        String cname = sc.next();
        System.out.print("Premium Price: ");


        double premium = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid premium price (number).");
                sc.next();
            }
            premium = sc.nextDouble();
            sc.nextLine();
            if (premium <= 0) {
                System.out.println("Premium price must be a positive number. Please enter a valid premium price.");
            }
        } while (premium <= 0);

        System.out.print("Regular Price: ");


        double regular = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid regular price (number).");
                sc.next();
            }
            regular = sc.nextDouble();
            sc.nextLine();
            if (regular <= 0) {
                System.out.println("Regular price must be a positive number. Please enter a valid regular price.");
            }
        } while (regular <= 0);

        System.out.print("Premium Stocks: ");

        double cpstocks = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid number of premium stocks (number).");
                sc.next();
            }
            cpstocks = sc.nextDouble();
            sc.nextLine();
            if (cpstocks < 0) {
                System.out.println("Premium stocks cannot be negative. Please enter a valid number of premium stocks.");
            }
        } while (cpstocks < 0);

        System.out.print("Regular Stocks: ");

        double crstocks = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid number of regular stocks (number).");
                sc.next();
            }
            crstocks = sc.nextDouble();
            sc.nextLine();
            if (crstocks < 0) {
                System.out.println("Regular stocks cannot be negative. Please enter a valid number of regular stocks.");
            }
        } while (crstocks < 0);

        System.out.print("Concert Status: ");
        String cstatus = sc.next();

        String qry = "INSERT INTO tbl_Concert (ct_name, ct_premium, ct_regular, ct_pstocks, ct_rstocks, ct_status) VALUES (?, ?, ?, ?, ?, ?)";

        conf.addRecord(qry, cname, premium, regular, cpstocks, crstocks, cstatus);
    }

    public void viewConcert() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String status = "Available";

        String qry = "SELECT * FROM tbl_Concert WHERE ct_status = ? AND (ct_pstocks > 0 OR ct_rstocks > 0)";
        String[] hdrs = {"ID", "Concert Name", "Premium Price", "Regular Price", "Premium Stock", "Regular Stock", "Concert Status"};
        String[] clms = {"ct_id", "ct_name", "ct_premium", "ct_regular", "ct_pstocks", "ct_rstocks", "ct_status"};

        conf.viewRecords(qry, hdrs, clms, status);
    }

    private void updateConcert() {

        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Update: ");

        int id = 0;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid concert ID (integer).");
                sc.next();
            }
            id = sc.nextInt();
            sc.nextLine();
            if (conf.getSingleValue("SELECT ct_id FROM tbl_Concert WHERE ct_id =?", id) == 0) {
                System.out.println("Seleted ID doesn't exist!");
                System.out.println("Select Concert ID Again: ");
            }
        } while (conf.getSingleValue("SELECT ct_id FROM tbl_Concert WHERE ct_id =?", id) == 0);

        System.out.print("Enter New Concert Name: ");
        String cname = sc.next();
        System.out.print("Enter New Premium Price: ");

        double premium = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid premium price (number).");
                sc.next();
            }
            premium = sc.nextDouble();
            sc.nextLine();
            if (premium <= 0) {
                System.out.println("Premium price must be a positive number. Please enter a valid premium price.");
            }
        } while (premium <= 0);

        System.out.print("Enter New Regular Price: ");

        double regular = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid regular price (number).");
                sc.next();
            }
            regular = sc.nextDouble();
            sc.nextLine();
            if (regular <= 0) {
                System.out.println("Regular price must be a positive number. Please enter a valid regular price.");
            }
        } while (regular <= 0);

        System.out.print("Enter New Premium Stocks: ");

        double cpstocks = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid number of premium stocks (number).");
                sc.next();
            }
            cpstocks = sc.nextDouble();
            sc.nextLine();
            if (cpstocks < 0) {
                System.out.println("Premium stocks cannot be negative. Please enter a valid number of premium stocks.");
            }
        } while (cpstocks < 0);

        System.out.print("Enter New Regular Stocks: ");
        double crstocks = 0;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid number of regular stocks (number).");
                sc.next();
            }
            crstocks = sc.nextDouble();
            sc.nextLine();
            if (crstocks < 0) {
                System.out.println("Regular stocks cannot be negative. Please enter a valid number of regular stocks.");
            }
        } while (crstocks < 0);

        System.out.print("Enter New Concert Status: ");
        String cstatus = sc.next();

        String qry = "UPDATE tbl_Concert SET ct_name = ?, ct_premium = ?, ct_regular = ?, ct_pstocks = ?, ct_rstocks = ?, ct_status = ? "
                + "WHERE ct_id = ?";

        conf.updateRecord(qry, cname, premium, regular, cpstocks, crstocks, cstatus, id);
    }

    private void deleteConcert() {

        Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("Enter the ID to Delete: ");

        int id = 0;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid concert ID (integer).");
                sc.next();
            }
            id = sc.nextInt();
            sc.nextLine();
            if (conf.getSingleValue("SELECT ct_id FROM tbl_Concert WHERE ct_id =?", id) == 0) {
                System.out.println("Seleted ID doesn't exist!");
                System.out.println("Select Concert ID Again: ");
            }
        } while (conf.getSingleValue("SELECT ct_id FROM tbl_Concert WHERE ct_id =?", id) == 0);

        String qry = "DELETE FROM tbl_Concert WHERE ct_id = ?";

        conf.deleteRecord(qry, id);
    }
}