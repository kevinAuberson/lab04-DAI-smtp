package ch.heig.dai.smtp.model;

import java.util.Random;

public class Group {
    /**
     * Fonction qui permet de former les groupes de mail avec un nombre entre 2-5 mail pour chaque groupe
     * @param group
     * @return Un tableau de tableau de groupe de mail
     */
    public String[][] formGroup(int numberOfGroups, String[] group){

        String[][] str = new String[numberOfGroups][5];//5 est le nombre max de personne dans un groupe
        int peopleInserted = 0;
        for(int i = 0; i < numberOfGroups; i++){
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
            if(str[i].length < 2){
                //Insertion du premier dans la liste dans un groupe avec un seul individu (dernier groupe)
                str[i][1] = group[0]; }
        }
        return str;
    }
}
