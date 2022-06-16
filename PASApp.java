public class PASApp{
    public static void main(String[] args) {
        PASHelper main = new PASHelper();
        main.getDBConnection();
        main.clrScreen();
        main.menuScreen();
    }
}