package ch.heig.dai.smtp;
import ch.heig.dai.smtp.config.Configuration;
import ch.heig.dai.smtp.network.*;

import java.io.File;
import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException {
        if(args.length != 3){
            throw new IOException("Pas le bom nombre d'argument, il en faut 3 :" +
                    " Liste des victims, liste des messages et nombre de groupe");
        }

        File victims = new File(args[0]);
        File messages = new File(args[1]);
        int nbrGroupe = Integer.parseInt(args[2]);

        SmtpClient client = new SmtpClient();
        client.sendEmail();
    }
}
