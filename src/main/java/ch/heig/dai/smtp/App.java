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
            throw new IOException("Pas le bom nombre d'argument, il en faut 3 :" +
                    " Liste des victims, liste des messages et nombre de groupe");
        }

        File victims = new File(args[0]);
        File messages = new File(args[1]);
        int nbrGroupe = Integer.parseInt(args[2]);
        Configuration config = new Configuration(victims, messages, nbrGroupe, serverAddress, serverPort);
        String[] strVictims = config.readVictims();
        List<Message> messageList = config.readMessages();
        

        String[][] groupeMail = config.formGroup(strVictims);

        //Test
        for (String strVictim : strVictims) {
            System.out.println(strVictim);
        }
        System.out.println();

        for(int i = 0; i < groupeMail.length; ++i){
            int j = 0;
            System.out.println("Groupe : " + (i + 1));
            while(j < 5){
                if(groupeMail[i][j] == null){
                    break;
                }
                System.out.println(groupeMail[i][j]);
                j++;
            }
        }
        System.out.println();

        //SmtpClient client = new SmtpClient();
        //client.sendEmail();

    }
}
