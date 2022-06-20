/**
 * Java Course 4, Module 3
 * 
 * Norima Java Developer Course Capstone Project
 *
 * @author Mc Kevin Aranda
 */
import java.time.LocalDate;
import java.sql.*;

public class Claim extends PASHelper {

    private LocalDate doa;
    private String address,desAccident,desDamage;
    private double estCost;

    Policy p;
    
    public void load(){
        int number = checkNumber("Enter policy number: ");
        if (checkAccountPolicy(number,"tbl_policy","policy_number") == true){
            System.out.println("\nClaims\n");
            doa = checkDate("Enter Date of accident: ");
            System.out.print("Enter Address where accident happened: ");
            address = input.nextLine();
            System.out.print("Enter Description of accident: ");
            desAccident = input.nextLine();
            System.out.print("Enter Description of damage to vehicle: ");
            desDamage = input.nextLine();
            estCost = checkPrice("Enter Estimated cost of repairs: ");
            store(number);
        }
        else{
            System.out.println("Invalid Policy Number");
            returnMenu();
        };
        }

    public void store(int number){
        try {
            // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
            String sql = "INSERT INTO tbl_Claim (doa, address, desAccident, desDamage, estCost, policy_number) VALUES" 
            +"('"+doa+"','"+address+"','"+desAccident+"','"+desDamage+"','"+estCost+"','"+number+"')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            int claimNum = getClaimNum();
            System.out.println("Claim successfully filed!\n");
            System.out.println("Generated claim number: C"+claimNum);
            System.out.println("");
            returnMenu();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public int getClaimNum(){
        try {
            // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
            String sql = "SELECT * FROM tbl_Claim ORDER BY claim_number DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            int claimNum = resultSet.getInt("claim_number"); // retrieve a 'String'-cell in the row
            return claimNum;
                
        } catch (Exception e) {
            clrScreen();
            System.out.println(e.toString());
            return 0;
        }
    }

    public void displayClaimDetails(){
        p = new Policy();
        int number = checkNumber("Enter claim number: C");
        if (checkAccountPolicy(number,"tbl_claim","claim_number") == true){
            try {
                // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
                String sql = "SELECT * FROM tbl_claim WHERE claim_number ='"+number+"'";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery();
                resultSet.next();
                String doa = resultSet.getString("doa");
                String address = resultSet.getString("address");
                String desAccident = resultSet.getString("desAccident");
                String desDamage = resultSet.getString("desDamage");
                double estCost = resultSet.getDouble("estCost");
                int policyNum = resultSet.getInt("policy_number");
                p.displayPolicyDetails(policyNum);
                System.out.println("\nClaims Details");
                System.out.printf("%-35s %-20s %n","Date of accident: ",doa);
                System.out.printf("%-35s %-20s %n","Address where accident happened: ",address);
                System.out.printf("%-35s %-20s %n","Description of accident: ",desAccident);
                System.out.printf("%-35s %-20s %n","Description of damage to vehicle: ",desDamage);
                System.out.printf("%-35s %-20s %n","Estimated cost of repairs : ",estCost);
                System.out.println("");
                returnMenu();
            } catch (Exception e) {
                System.out.println(e.toString());
                returnMenu();
            }
        }
        else{
            System.out.println("Invalid Claim Number");
            returnMenu();
        };
        }

}
