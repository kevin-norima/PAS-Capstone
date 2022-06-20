/**
 * Java Course 4, Module 3
 * 
 * Norima Java Developer Course Capstone Project
 *
 * @author Mc Kevin Aranda
 */
public class PASApp{
    public static void main(String[] args) {
        PASHelper main = new PASHelper();
        main.getDBConnection(); // Run db connection
        main.clrScreen();
        main.menuScreen();
    }
}