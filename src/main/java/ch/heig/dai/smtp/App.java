package ch.heig.dai.smtp;
import ch.heig.dai.smtp.network.*;
import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException {
        SmtpClient client = new SmtpClient();
        client.sendEmail();
    }
}
