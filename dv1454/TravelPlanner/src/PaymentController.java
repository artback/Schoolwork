import java.util.Calendar;

public class PaymentController{
    private final String user;
    private final String cardNr;
    private final int price;
    
    public PaymentController(String cardNr, int price, String user){
        this.cardNr = cardNr;
        this.price = price;
        this.user = user;
    }
    public Boolean makePayment() {
        boolean payed = false;
        
        if (!this.cardNr.equals("0")) {
        }
        
        if (payed) {
            logTranscation();
        }
        
        return payed;
    }
    
    private boolean logTranscation(){
            db.query(
                "INSERT INTO transactions VALUES (" + null + ", " +
                "'" + this.user + "', " +
                "'" + this.price + "', " +
                "'" + Calendar.getInstance().getTime() + "' )");
        return true;
    }
}


