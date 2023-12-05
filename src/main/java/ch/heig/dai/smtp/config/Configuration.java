package ch.heig.dai.smtp.config;

import java.io.*;
import java.nio.charset.*;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class Configuration {
    File victims;
    File messages;
    int numberOfGroups;

    public Configuration(File victims, File messages, int numberOfGroups){
        this.victims = victims;
        this.messages = messages;
        if(numberOfGroups < 1){
            throw new IllegalArgumentException("Number of groups inferior to 1");
        }
        this.numberOfGroups = numberOfGroups;
    }

    public String[] readVictims(){
        Charset charset = getEncoding(this.victims);
        String content;
        if(charset != null) {
            content = readFile(this.victims, charset);
            return content.trim().split("\\s*,+\\s*,*\\s*"); // pour éviter les espaces
        }
        return null;
    }

    public String[] readMessages(){
        Charset charset = getEncoding(this.messages);
        String content;
        if(charset != null) {
            content = readFile(this.messages, charset);
            return content.split("---");
        }
        return null;
    }

    public boolean validateEmail(String[] emails){
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        for(int i = 0; i < emails.length; i++) {
            if(!Pattern.compile(regexPattern).matcher(emails[i]).matches()){
                return false;
            }
        }
         return true;
    }

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
