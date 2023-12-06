package ch.heig.dai.smtp;
import ch.heig.dai.smtp.config.Configuration;
import ch.heig.dai.smtp.model.Message;
import ch.heig.dai.smtp.network.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.List;

public class App 
{
    public static void main( String[] args ) throws IOException {

        String serverAddress = "localhost";
        int serverPort = 1025;

        if(args.length == 5){
            serverAddress = args[3];
            serverPort = Integer.parseInt(args[4]);
        } else if(args.length != 3){
            throw new IOException("Pas le bom nombre d'argumentss, il en faut 3 :" +
                    " Liste des victims, liste des messages et nombre de groupe");
        }

        Configuration config = new Configuration(new File(args[0]), new File(args[1]), Integer.parseInt(args[2]), serverAddress, serverPort);

        SmtpClient client = new SmtpClient(config.getServerAddress(), config.getServerPort());
        client.sendEmail(config.getGroup());

    }
}
