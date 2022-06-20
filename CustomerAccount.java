/**
 * Java Course 4, Module 3
 * 
 * Norima Java Developer Course Capstone Project
 *
 * @author Mc Kevin Aranda
 */
import java.sql.*;
public class CustomerAccount extends PASHelper {

    private String fname,lname,address;
    Policy pol = new Policy();

    public void userPromt(){
        System.out.print("Enter First Name: ");
        fname = input.nextLine();
        System.out.print("Enter Last Name: ");
        lname = input.nextLine();
    }
    public void load(){
        System.out.println("Create a new Customer Account \n");
        userPromt();
        System.out.print("Enter Address: ");
        address = input.nextLine();
        store();
        returnMenu();
    }
    public void store(){
        try {
            String sql = "INSERT INTO tbl_CustomersAccount (account_fname, account_lname, account_address) VALUES" 
            +"('"+fname+"','"+lname+"','"+address+"')"; // insert data inputed by user and store into database
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            int accountNum = getAccountNo(); // retrieve the newest record in database
            System.out.println("Customer successfully registered\n");
            System.out.println("Generated account number: "+accountNum);
            System.out.println("");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public int getAccountNo() {
        try {
            String sql = "SELECT * FROM tbl_customersaccount ORDER BY account_number DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();

            return resultSet.getInt("account_number");
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    public void searchAccount(){
        System.out.println("Search Existing Account");
        userPromt();
        try {
            String sql = "SELECT * FROM tbl_customersaccount WHERE account_fname ='"+fname+"' AND account_lname ='"+lname+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next() == false){
                System.out.println("Account doesn't exist!");
            }else{
                do{
                int number = resultSet.getInt("account_number");
                String fname = resultSet.getString("account_fname");
                String lname = resultSet.getString("account_lname");
                String address = resultSet.getString("account_address");
                System.out.println("\nAccount number: "+number);
                System.out.println("Full Name     : "+fname+" "+lname);
                System.out.println("Address       : "+address);
                System.out.println("");
                displayListPolicy(number);
                } while (resultSet.next());
            }
            returnMenu();
        } catch(Exception e) {
            System.out.println("Account doesn't exist!");
            returnMenu();
        }
    }
    
    public void displayListPolicy(int number){
        try {
            // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
            String sql = "SELECT * FROM tbl_policy WHERE account_number ='"+number+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            System.out.println("Policy Details List");
            System.out.format( "%-16s %-16s %-16s %-16s %-16s %n", "Policy","Policy Number","Effective Date","Expiry Date","Total Premium");
            int i = 1;
            while(resultSet.next()){
                int policyNum = resultSet.getInt("policy_number");
                String policyEffective = resultSet.getString("policy_effectivedate");
                String policyExpiry = resultSet.getString("policy_expirydate");
                double premiumCharge = resultSet.getDouble("premium_charge");
                System.out.format( "%-16s %-16s %-16s %-16s %-16s %n" , i,policyNum,policyEffective,policyExpiry,premiumCharge);
            i++;
            }      
            displayPolicyHolderDetails(number);
        } catch (Exception e) {
            returnMenu();
        }
    }

    public void displayPolicyHolderDetails(int number){
        try {
            String sql = "SELECT * FROM tbl_policyholder WHERE account_number ='"+number+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            System.out.println("");
            System.out.println("Policy Holder Details");
            System.out.println("");
            int i = 1;
            while(resultSet.next()){ // display list of revtrieve data from the query
                int policyNum = resultSet.getInt("policy_number");
                String respolicyHolderFname = resultSet.getString("fname");
                String respolicyHolderLname = resultSet.getString("lname");
                String respolicyHolderDob = resultSet.getString("dob");
                String respolicyHolderAdd = resultSet.getString("address");
                String respolicyHolderDriverLicense = resultSet.getString("driverlicense");
                String respolicyHolderDriverLicenseIssued = resultSet.getString("driverlicenseissued");
                System.out.println("Policy Holder #: "+i);
                System.out.println("Policy number: "+policyNum);
                System.out.println("Full Name: "+respolicyHolderFname+" "+respolicyHolderLname);
                System.out.println("Date of Birth: "+respolicyHolderDob);
                System.out.println("Address: "+respolicyHolderAdd);
                System.out.println("Driver's License: "+respolicyHolderDriverLicense);
                System.out.println("Driver's License issued date: "+respolicyHolderDriverLicenseIssued);
                System.out.println("");
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            returnMenu();
        }
    }
}
