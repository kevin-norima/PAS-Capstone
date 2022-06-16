import java.time.LocalDate;
import java.sql.*;
public class PolicyHolder extends PASHelper {

    private String fname,lname,address,driverlicense;
    private LocalDate dob,dateissuedlicense;

    RatingEngine RatingEngObj;

    public void load(Policy policy){
            RatingEngObj = new RatingEngine();
            System.out.println("\nPolicy Holder Details\n");
            System.out.print("Enter First Name: ");
            fname = input.nextLine();
            System.out.print("Enter Last Name: ");
            lname = input.nextLine();
            dob = checkDate("Enter date of birth: ");
            System.out.print("Enter Address: ");
            address = input.nextLine();
            System.out.print("Enter Driver's license number: ");
            driverlicense = input.nextLine();
            dateissuedlicense = checkDate("Enter Date on which driver's license was first issued(YYYY-MM-DD): ");
    }

    public void displayPolicyHolderDetails(int policynum){
        try {
            // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
            String sql = "SELECT * FROM tbl_policyholder WHERE policy_number ='"+policynum+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            String respolicyHolderFname = resultSet.getString("tbl_policyholder.fname");
            String respolicyHolderLname = resultSet.getString("tbl_policyholder.lname");
            String respolicyHolderDob = resultSet.getString("tbl_policyholder.dob");
            String respolicyHolderAdd = resultSet.getString("tbl_policyholder.address");
            String respolicyHolderDriverLicense = resultSet.getString("tbl_policyholder.driverlicense");
            String respolicyHolderDriverLicenseIssued = resultSet.getString("tbl_policyholder.driverlicenseissued");
            System.out.println("Policy Holder Details");
            System.out.println("Full Name: "+respolicyHolderFname+" "+respolicyHolderLname);
            System.out.println("Date of Birth: "+respolicyHolderDob);
            System.out.println("Address: "+respolicyHolderAdd);
            System.out.println("Driver's License: "+respolicyHolderDriverLicense);
            System.out.println("Driver's License issued date: "+respolicyHolderDriverLicenseIssued);
            System.out.println("");
        } catch (Exception e) {
            System.out.println(e.toString());
            returnMenu();
        }
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getDriverlicense() {
        return driverlicense;
    }

    public LocalDate getDateissuedlicense() {
        return dateissuedlicense;
    }

    public RatingEngine getRatingEngObj() {
        return RatingEngObj;
    }
    
}