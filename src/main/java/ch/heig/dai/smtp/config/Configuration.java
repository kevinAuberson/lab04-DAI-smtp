package ch.heig.dai.smtp.config;

import ch.heig.dai.smtp.model.Message;

import java.io.*;
import java.nio.charset.*;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import ch.heig.dai.smtp.model.*;

public class Configuration {
    File victims;
    File messages;

    List<Message> messagesList;
    int numberOfGroups;
    String serverAddress;
    int serverPort;

    public Configuration(File victims, File messages, int numberOfGroups, String serverAddress, int serverPort){
        this.victims = victims;
        this.messages = messages;
        if(numberOfGroups < 1){
            throw new IllegalArgumentException("Number of groups inferior to 1");
        }
        this.numberOfGroups = numberOfGroups;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public boolean verifyServerInfo(){
        //Référence du regex utilisé pour vérifier l'adresse IP https://geeksforgeeks.org/how-to-validate-an-ip-address-using-regular-expressions-in-java/
        String zeroTo255 = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        if(!Pattern.compile(regex).matcher(this.serverAddress).matches()){
            return false;
        }

        return this.serverPort >= 1024 && this.serverPort <= 65535;
    }

    /**
     * Fonction qui permet de lire le fichier des mails et de le séparer dans un tableau de String
     * @return Un tableau de mail
     */
    public String[] readVictims(){
        Charset charset = getEncoding(this.victims);
        String content;
        if(charset != null) {
            content = readFile(this.victims, charset);
            return content.trim().split("\\s*,+\\s*,*\\s*"); // pour éviter les espaces
        }
        return null;
    }

    /**
     * Fonction qui permet de lire le fichier des messages et de le convertir en String
     * @return Un tableau de message
     */
    public List<Message> readMessages(){
        Charset charset = getEncoding(this.messages);
        String content;
        messagesList = new ArrayList<>();
        if(charset != null) {
            content = readFile(this.messages, charset);
            for (String s : content.split("---")) {
                messagesList.add(new Message(s.split("Subject:")[1].split("Body:")[0], s.split("Body:")[1]));
            }
        }else {
            throw new IllegalArgumentException("Charset null");
        }
        return messagesList;
    }
    

    /**
     * Fonction qui permet de former les groupes de mail avec un nombre entre 2-5 mail pour chaque groupe
     * @param group
     * @return Un tableau de tableau de groupe de mail
     */
    public String[][] formGroup(String[] group){

        String[][] str = new String[this.numberOfGroups][5];//5 est le nombre max de personne dans un groupe
        int peopleInserted = 0;
        for(int i = 0; i < this.numberOfGroups; i++){
            //Génération d'un nombre entre 2 - 5 pour chaque groupe
            Random rand = new Random();
            int n = rand.nextInt(4);
            n += 2;
            for(int j = 0; j < n; j++){
                if(group.length <= peopleInserted){
                    return str;
                }
                str[i][j] = group[peopleInserted++];
            }
        }
        return str;
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
