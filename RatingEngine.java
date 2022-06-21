/**
 * Java Course 4, Module 3
 * 
 * Norima Java Developer Course Capstone Project
 *
 * @author Mc Kevin Aranda
 */
import java.time.LocalDate;
import java.time.Period;

public class RatingEngine {

    private double vpf,vp;
    private int dlx;

    public void doDlx(PolicyHolder ph){
        LocalDate today = LocalDate.now();
        this.dlx = Period.between(ph.getDateissuedlicense(), today).getYears(); // get exactly the years of date between the Issued license and current date
    }

    public void getVehicleAge(int year){
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int vehicleAge = currentYear - year;
        if (vehicleAge <= 1){
            this.vpf = 0.01;
        }
        else if (vehicleAge > 1 && vehicleAge <= 3){
            this.vpf = 0.008;
        }
        else if (vehicleAge > 3 && vehicleAge <= 5){
            this.vpf = 0.007;
        }
        else if (vehicleAge > 5 && vehicleAge <= 10){
            this.vpf = 0.006;
        }
        else if (vehicleAge > 10 && vehicleAge <= 15){
            this.vpf = 0.004;
        }
        else if (vehicleAge > 15 && vehicleAge <= 20){
            this.vpf = 0.002;
        }
        else{
            this.vpf = 0.001;
        }
    }

    public void getVehiclePurchasePrice(double price){
        this.vp = price;
    }

    public double calculatePremium(){
        if(dlx <= 0){
            return ((vp * vpf) + ((vp/100)/1));
        }
        else{
            return ((vp * vpf) + ((vp/100)/dlx));
        }
    }

    public void test(){
        System.out.println(vp+" "+vpf+" "+dlx);
    }
}
