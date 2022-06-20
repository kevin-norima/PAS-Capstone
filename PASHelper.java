/**
 * Java Course 4, Module 3
 * 
 * Norima Java Developer Course Capstone Project
 *
 * @author Mc Kevin Aranda
 */
import java.sql.*;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PASHelper {
    static Connection conn = null;
    private LocalDate parseDob;
    private int num;
    private double deci;
    CustomerAccount customerAccountObj;
    Policy policyObj;
    PolicyHolder policyHolder;
    Vehicle vehicleObj;
    Claim claimObj;

    Scanner input = new Scanner(System.in);


    public void menuScreen(){

            customerAccountObj = new CustomerAccount();
            policyObj = new Policy();
            policyHolder = new PolicyHolder();
            vehicleObj = new Vehicle("", "", "", "", "", 0, 0, 0);
            claimObj = new Claim();

                    System.out.println("|************PAS SYSTEM************|");
                    System.out.println("|----------------------------------|");
                    System.out.println("|1 - Register Customer Account.    |");
                    System.out.println("|2 - Policy Buy & Quote.           |");
                    System.out.println("|3 - Cancel Policy.                |");
                    System.out.println("|4 - File Claim.                   |");
                    System.out.println("|5 - Search Customer Account.      |");
                    System.out.println("|6 - Search Policy.                |");
                    System.out.println("|7 - Search Claim.                 |");
                    System.out.println("|8 - Exit.                         |");
                    System.out.println("************************************");
                    boolean invalidInput;
                    int choice = 0;
                    do {
                        invalidInput = false;
                        try {
                            System.out.print("Enter Selection: ");
                            choice = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid selection (1-8).");
                            invalidInput = true;  // This is what will get the program to loop back
                            input.nextLine();
                        }
                    } while (invalidInput);

                    switch(choice){
                        case 1:
                        clrScreen();
                        customerAccountObj.load();
                        break; 
                        case 2:
                        clrScreen();
                        policyObj.load();
                        policyHolder.load(policyObj);
                        vehicleObj.load(policyObj,policyHolder);
                        break;
                        case 3:
                        clrScreen();
                        policyObj.doCancel();
                        break;
                        case 4:
                        clrScreen();
                        claimObj.load();
                        break;
                        case 5:
                        clrScreen();
                        customerAccountObj.searchAccount();
                        break;
                        case 6:
                        clrScreen();
                        policyObj.searchPolicy();
                        break;
                        case 7:
                        clrScreen();
                        claimObj.displayClaimDetails();
                        break;
                        case 8:
                        System.exit(0);
                        break;
                        default:
                        clrScreen();
                        System.out.println("Out of Selection");
                        System.out.println("Enter correct choice 1-8");
                        menuScreen();
                        break;
                    }
    }

    public Connection getDBConnection(){ // db connection test
        try {
            if (conn==null){
                conn = DriverManager.getConnection // Step 1: Construct a database 'Connection' object called 'conn'
                ("jdbc:mysql://localhost:3306/pasdb", "root", "root");
            }
        } catch(Exception ex){
            ex.printStackTrace();
            // Close conn and stmt - Done automatically by try-with-resources (JDK 7)
        }   
        return conn;
    }

    public void returnMenu(){
        int selection = checkNumber("Return to menu? YES - 1 or EXIT - 0: "); //call checkNumber method for validation
        if(selection == 1){
            clrScreen();
            menuScreen();
        }
        else{
            System.exit(0);
        }
    }

    public void clrScreen(){ // clear previous text
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public boolean checkAccountPolicy(int number, String table, String fields){
        try {
            // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
            String sql = "SELECT * FROM "+table+" WHERE "+fields+" ='"+number+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
                 int accountNum = resultSet.getInt(fields); // retrieve a 'String'-cell in the row
                if (accountNum == number){
                    return true;
                }
                return false;
                
        } catch (Exception e) {
            clrScreen();
            return false;
        }
    }

    public int getPolicyNum(){
        try {
            String sql = "SELECT * FROM tbl_policy ORDER BY policy_number DESC LIMIT 1"; // retrieve the newest record in database
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            int policyNum = resultSet.getInt("policy_number"); 
            return policyNum;
                
        } catch (Exception e) {
            clrScreen();
            return 0;
        }
    }

    public LocalDate checkDate(String label){
        try {
            System.out.print(label);
            LocalDate parseDob = LocalDate.parse(input.nextLine()); // accept string and convert to LocalDate to validate the right format
            return this.parseDob = parseDob; // assigning value to global variable
        } catch (Exception e) {
            System.out.println("Invalid date format (YYYY-MM-DD)");
            checkDate(label);
        }
        return this.parseDob;
    }

    public int checkNumber(String label){
        try {
            System.out.print(label);
            int num = Integer.parseInt(input.nextLine());
            return this.num = num;
        } catch (Exception e) {
            System.out.println("Enter Valid Value!");
            checkNumber(label);
        }
        return this.num;
    }

    public double checkPrice(String label){
        try {
            System.out.print(label);
            double num = Double.parseDouble(input.nextLine());
            return this.deci = num;
        } catch (Exception e) {
            System.out.println("Enter Valid Value!");
            checkPrice(label);
        }
        return this.deci;
    }
}
