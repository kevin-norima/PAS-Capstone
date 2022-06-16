import java.time.LocalDate;
import java.sql.*;
public class Policy extends PASHelper{

    //private String cancelDate;
    private int accountNumber;
    private LocalDate expiryDate,effectiveDate,cancelDate;

    PolicyHolder ph;
    Vehicle v;

    public void load(){
        //System.out.print("Enter account number: ");
       accountNumber = checkNumber("Enter account number: ");
        if (checkAccountPolicy(accountNumber,"tbl_customersaccount","account_number") == true){
            System.out.println("\nGet a policy quote and buy the policy.\n");
            effectiveDate = checkDate("Effective Date: ");
            expiryDate = effectiveDate.plusMonths(6);
            System.out.println("Expiry Date: "+expiryDate);
        }
        else{
            System.out.println("Account number is not Exist!");
            returnMenu();
        }
    }

    public void doCancel(){
        int number = checkNumber("Enter policy number: ");
        System.out.println("");
        if (checkAccountPolicy(number,"tbl_policy","policy_number") == true){
            System.out.println("Cancel Specific Policy");
            displayPolicyDetails(number);
            System.out.println("");
            cancelDate = checkDate("Enter cancellation date: ");
            if (expiryDate.compareTo(cancelDate)>0 && effectiveDate.compareTo(cancelDate)<0){
                try {
                    // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
                    String sql = "UPDATE tbl_policy SET policy_expirydate = ('"+cancelDate+"') WHERE policy_number ='"+number+"'";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.execute();
                    clrScreen();
                    System.out.println("Successfully update Expiry date");
                    displayPolicyDetails(number);
                    System.out.println("");
                    returnMenu();
    
                } catch (Exception e) {
                    System.out.println(e.toString());
                    menuScreen();
                }
            }else{
                System.out.println("\nDate of a policy should earlier date than the Expiry date\n");
                returnMenu();
            }
            
        }
        else{
            System.out.println("Invalid Policy Number");
            returnMenu();
        }
    }

    public void displayPolicyDetails(int policynum){
        try {
            // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
            String sql = "SELECT * FROM tbl_policy WHERE policy_number ='"+policynum+"'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            int accountNum = resultSet.getInt("account_number");
            String policyEffective = resultSet.getString("policy_effectivedate");
            this.effectiveDate = LocalDate.parse(policyEffective);
            String policyExpiry = resultSet.getString("policy_expirydate");
            this.expiryDate = LocalDate.parse(policyExpiry);
            double premiumCharge = resultSet.getDouble("premium_charge");
            System.out.println("\nPolicy Details");
            System.out.format( "%-16s %-16s %-16s %-16s %n", "Account Number","Effective Date","Expiry Date","Total Premium");
            System.out.format( "%-16s %-16s %-16s %-16s %n", accountNum,policyEffective,policyExpiry,premiumCharge);
        } catch (Exception e) {
            System.out.println(e.toString());
            returnMenu();
        }
    }

    public void searchPolicy(){
        ph = new PolicyHolder();
        v = new Vehicle("", "", "", "", "", 0, 0, 0);
        System.out.println("Search Existing Policy Number");
        int policyNum = checkNumber("Enter policy number: ");
        if (checkAccountPolicy(policyNum,"tbl_policy","policy_number") == true){
            displayPolicyDetails(policyNum);
            System.out.println("");
            ph.displayPolicyHolderDetails(policyNum);
            v.displayVehicleDetails(policyNum);
            System.out.println();
            returnMenu();
            }
        else{
            System.out.println("Invalid Policy Number");
            returnMenu();
        }
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

}
