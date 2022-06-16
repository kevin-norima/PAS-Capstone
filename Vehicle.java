import java.util.ArrayList;
import java.sql.*;
import java.text.DecimalFormat;

public class Vehicle extends PASHelper {
    
    private String make,model,type,fuelType,color;
    private int year;
    private double purchasePrice,premiumCharge,total = 0;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    Policy p;
    PolicyHolder ph;

    ArrayList <Vehicle> arrVehicle = new ArrayList<Vehicle>();

    RatingEngine RatingEngObj;
    

    public Vehicle(String make, String model, String type, String fuelType, String color, int year,
            double purchasePrice, double premiumCharge) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.fuelType = fuelType;
        this.color = color;
        this.year = year;
        this.purchasePrice = purchasePrice;
        this.premiumCharge = premiumCharge;
    }

    public void load(Policy policy,PolicyHolder policyHolder){

        this.p = policy;
        this.ph = policyHolder;

        RatingEngObj = new RatingEngine();
        

        System.out.println("\nVehicle Details");
        //System.out.print("How many vehicle?: ");
        int vehicleNum = checkNumber("How many vehicle?: ");
        //input.nextLine();
        int i = 0;
        do{
            RatingEngObj.doDlx(policyHolder);
            int vehicleCount = i+1;
            System.out.println("\nVehicle Number "+vehicleCount);
            System.out.print("Enter Make: ");
            make = input.nextLine();
            System.out.print("Enter Model: ");
            model = input.nextLine();
                do {
                    year = checkNumber("Enter Year YYYY: ");
                } while(year < 1000 || year > 9999);
            System.out.print("Enter Type: ");
            type = input.nextLine();
            System.out.print("Enter Fuel Type : ");
            fuelType = input.nextLine();
            purchasePrice = checkPrice("Enter Purchase Price: ");
            System.out.print("Enter Color: ");
            color = input.nextLine();
            RatingEngObj.getVehicleAge(year);
            RatingEngObj.getVehiclePurchasePrice(purchasePrice);
            premiumCharge = Double.valueOf(df.format(RatingEngObj.calculatePremium()));
            System.out.println("Premium charged: "+premiumCharge);
            
            this.total = total + premiumCharge;
            arrVehicle.add(new Vehicle(make, model, type, fuelType, color, year, purchasePrice, premiumCharge));
            i++;
        } while(i<vehicleNum);
        showDetails();
    }

    public void showDetails(){
        clrScreen();
        System.out.println("\nPolicy Details");
        System.out.format( "%-16s %-18s %10s %n", "Account Number","Effective Date","Expiry Date");
        System.out.format( "%8s %20s %16s %n", p.getAccountNumber(),p.getEffectiveDate(),p.getExpiryDate());
        System.out.println("");
        System.out.println("Policy Holder Details");
        System.out.println("Full Name: "+ph.getFname()+" "+ph.getLname());
        System.out.println("Date of Birth: "+ph.getDob());
        System.out.println("Address: "+ph.getAddress());
        System.out.println("Driver's License: "+ph.getDriverlicense());
        System.out.println("Driver's License issued date: "+ph.getDateissuedlicense());
        System.out.println("Total premium of each vehicle: "+total);
        System.out.println("");
        System.out.format( "%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n","Vehicle","Make","Model","Year","Type","Fuel-Type","Purchase Price","Color","Premium Charge");
        int vehicleCount=0;
        for (Vehicle vehicle : arrVehicle) {
            System.out.format( "%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", vehicleCount = vehicleCount+1,vehicle.make,vehicle.model,vehicle.year,vehicle.type,vehicle.fuelType,vehicle.purchasePrice,vehicle.color,vehicle.premiumCharge);
        }
        System.out.format( "%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n","--------------------","---------------","---------------","---------------","---------------","---------------","---------------","TOTAL PREMIUM: ",total);
        buyPolicy();
    }

    public void buyPolicy(){
        int select = checkNumber("Do you want to buy? YES - 1 / NO - 0: ");
        if(select == 1){
            storePolicy();
            storePolicyHolder();
            storeVehicle();
            System.out.println("");
            returnMenu();
        }
        else{
            System.out.println("Policy Cancelled");
            returnMenu();
        }
    }
    
    public void storePolicy(){
        try {
            String sql = "INSERT INTO tbl_policy (policy_effectivedate, policy_expirydate,account_number,premium_charge) VALUES" 
            +"('"+p.getEffectiveDate()+"','"+p.getExpiryDate()+"','"+p.getAccountNumber()+"','"+total+"')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
            returnMenu();
        }
    }

    public void storePolicyHolder(){
        int policyNum = getPolicyNum();
        try {
            String sql = "INSERT INTO tbl_policyholder (fname,lname,dob,address,driverlicense,driverlicenseissued,policy_number,account_number) VALUES" 
            +"('"+ph.getFname()+"','"+ph.getLname()+"','"+ph.getDob()+"','"+ph.getAddress()+"','"+ph.getDriverlicense()+"','"+ph.getDateissuedlicense()+"','"+policyNum+"','"+p.getAccountNumber()+"')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();  
        } catch (Exception e) {
            System.out.println(e.toString());
            returnMenu();
        }
    }

    public void storeVehicle(){
        int policyNum = getPolicyNum();
        try {
            for (Vehicle vehicle : arrVehicle) {
            String sql = "INSERT INTO tbl_vehicle (make,model,year,type,fuel_type,purchase_price,color,vehicle_premium,policy_number) VALUES" 
            +"('"+vehicle.make+"','"+vehicle.model+"','"+vehicle.year+"','"+vehicle.type+"','"+vehicle.fuelType+"','"+vehicle.purchasePrice+"','"+vehicle.color+"','"+vehicle.premiumCharge+"','"+policyNum+"')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            }
            System.out.println("Generated Policy Number: "+getPolicyNum());
        } catch (Exception e) {
            System.out.println(e.toString());
            returnMenu();
        }
    }

    public void displayVehicleDetails(int policyNum){
        try {
            // Step 2: Construct a 'Statement' object called 'stmt' inside the Connection created
            String sql = "SELECT * FROM tbl_vehicle WHERE policy_number ='"+policyNum+"'";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery();
                int vehicleCount = 1;
                System.out.format( "%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n","Vehicle","Make","Model","Year","Type","Fuel-Type","Purchase Price","Color","Premium Charge");
                while(resultSet.next()){
                    String resmake = resultSet.getString("tbl_vehicle.make");
                    String resmodel = resultSet.getString("tbl_vehicle.model");
                    String resyear = resultSet.getString("tbl_vehicle.year");
                    String restype = resultSet.getString("tbl_vehicle.type");
                    String resfuelType = resultSet.getString("tbl_vehicle.fuel_type");
                    double respurchase_price = resultSet.getDouble("tbl_vehicle.purchase_price");
                    String rescolor = resultSet.getString("tbl_vehicle.color");
                    double resvehicle_premium = resultSet.getDouble("tbl_vehicle.vehicle_premium");
                    System.out.format( "%-20s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %n", vehicleCount,resmake,resmodel,resyear,restype,resfuelType,respurchase_price,rescolor,resvehicle_premium);
                    vehicleCount++;
                }
        } catch (Exception e) {
            System.out.println(e.toString());
            returnMenu();
        }
    }
}
