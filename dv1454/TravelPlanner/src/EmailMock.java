package travelplanner;

public class EmailMock{
    
    //This function takes parameter email which is a string containing the message and
    //the recipent email adress. It return a bool representing if the message was succeded or not.
    public EmailMock() {
        
    }
    
    public boolean sendEmail(String email, String message){
        return true;
    }
}