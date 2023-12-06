package ch.heig.dai.smtp.config;

import ch.heig.dai.smtp.model.Message;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.regex.*;
import ch.heig.dai.smtp.model.*;

public class Configuration {
    List<Group> group;
    List<Message> messagesList;
    final String serverAddress;
    final int serverPort;

    public Configuration(File victims, File messages,int numberOfGroups, String serverAddress, int serverPort) throws IOException {
        if(numberOfGroups < 1){
            throw new IllegalArgumentException("Number of groups inferior to 1");
        }
        if(!verifyServerInfo(serverAddress, serverPort)){
            throw new IOException("Invalid server info");
        }
        this.messagesList = readMessages(messages);
        this.group = formGroup(victims, numberOfGroups);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        setEmaills();
    }

    /**
     * Verify ip adresse and port number given are correct
     * @param serverAddress
     * @param serverPort
     * @return true if information are correct
     */
    private boolean verifyServerInfo(String serverAddress, int serverPort){

        String[] ip = serverAddress.trim().split("\\s*.+\\s*.*\\s*");

        if(ip.length != 4){
            return false;
        }

        for (String s : ip) {
            if (Integer.parseInt(s) < 0 || Integer.parseInt(s) > 255) {
                return false;
            }
        }

        return serverPort >= 1024 && serverPort <= 65535;
    }

    /**
     * Fonction qui permet de lire le fichier des messages et de le convertir en String
     * @return Un tableau de message
     */
    private List<Message> readMessages(File messages){
        Charset charset = getEncoding(messages);
        String content;
        messagesList = new ArrayList<>();
        if(charset != null) {
            content = readFile(messages, charset);
            for (String s : content.split("---")) {
                messagesList.add(new Message(s.split("Subject: ")[1].split("Body:")[0], s.split("Body:")[1]));
            }
        }else {
            throw new IllegalArgumentException("Charset null");
        }
        return messagesList;
    }
    

    /**
     * Fonction qui permet de former les groupes de mail avec un nombre entre 2-5 mail pour chaque groupe
     * @param victims
     * @return Une liste de groupe de mail
     */
    private List<Group> formGroup(File victims, int nbGroupe) throws IOException {
        Charset charset = getEncoding(victims);
        String content;
        String [] str;
        if(charset != null) {
            content = readFile(victims, charset);
            str = content.trim().split("\\s*,+\\s*,*\\s*"); // pour éviter les espaces
        } else {
            return null;
        }

        List<Group> groups = new ArrayList<>();
        int peopleInserted = 0;

        for(int i = 0; i < nbGroupe; i++){
            //Génération d'un nombre entre 2 - 5 pour chaque groupe
            Random rand = new Random();
            int n = rand.nextInt(4);
            n += 2;
            String[] recipients = new String[n - 1];

            for(int j = 0; j < n; j++) {
                if(!EmailValidation.isValid(str[i+j])){
                    throw new IOException("Invalid email" + str[i+j]);
                }
                if (j == 0) {
                    peopleInserted++;
                } else {
                    recipients[j - 1] = str[peopleInserted++];
                }
            }

            if(recipients.length < 1){
                recipients[0] = str[0];
            }
            groups.add(new Group(str[n * i], recipients));
        }

        return groups;
    }

    private void setEmaills(){
        group.forEach(g -> {
            g.setMessage(messagesList.get(new Random().nextInt(messagesList.size())));
        });
    }

    /**
     * Fonction qui ermet de lire un fichier
     * @param file
     * @param encoding
     * @return le contenu du fichier
     */
    private String readFile(File file, Charset encoding) {
        try(var reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        encoding));){
            StringBuilder stringbuilder = new StringBuilder();
            String sCurrentLine;
            while ((sCurrentLine = reader.readLine()) != null) {
                stringbuilder.append(sCurrentLine).append("\n");
            }
            reader.close();
            return stringbuilder.toString();
        }catch(IOException e){
            return null;
        }
    }

    /**
     * Fonction qui permet d'obtenir le charset du fichier
     * @param file
     * @return le charset
     */
    private Charset getEncoding(File file) {

        String f = file.getName();
        String[] words = f.split("\\.");

        if(words[words.length - 1] != null) {
            switch (words[words.length - 1]) {
                case "utf8":
                    return StandardCharsets.UTF_8;
                case "txt":
                    return StandardCharsets.US_ASCII;
                case "utf16be":
                    return StandardCharsets.UTF_16BE;
                case "utf16le":
                    return StandardCharsets.UTF_16LE;
            }
        }
        return null;
    }
}
