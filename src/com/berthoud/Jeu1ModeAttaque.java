package com.berthoud;

import java.util.Scanner;

public class Jeu1ModeAttaque extends Jeu1 {


    public void play(int nombreCases, int nombreEssaisMax) {


        // Génération d'un code aléatoire et initialisation de ce code sous forme d'un tableau
        int[] codeDefenseur = generateurCode(nombreCases);


        boolean isCodeFound = false;
        int nombreEssais = 0;
        while ((nombreEssais <= nombreEssaisMax) && (isCodeFound == false)) {

            nombreEssais++;

            // Saisie dun code entré par le joueur (code input) de X chiffre
            Scanner scan = new Scanner(System.in);
            System.out.println("Saisis ton code:");
            String codeInput = scan.nextLine();


            // Vérifier que le code saisi contient le bon nombre de caractères ET
            // uniquement des chiffres de 0 à 9
            while ((codeInput.length() != nombreCases) || (!isInt(codeInput))) {
                System.out.println("Saisie non valide. Saisis une combinaison de " + nombreCases + " chiffres.");
                codeInput = scan.nextLine();
            }


            // Conversion du String codeInput en un tableau composé de int (codeChallenger)
            int[] codeChallenger = new int[nombreCases];
            for (int j = 0; j < nombreCases; j++) {
                codeChallenger[j] = Character.getNumericValue(codeInput.charAt(j));
            }


            // Comparaison des 2 codes et affichage du résultat de la comparaison (création d'un tableau)
            String[] validation = new String[nombreCases];
            for (int i = 0; i < nombreCases; i++) {
                if (codeDefenseur[i] > codeChallenger[i]) {
                    validation[i] = ">";
                } else if (codeDefenseur[i] < codeChallenger[i]) {
                    validation[i] = "<";
                } else {
                    validation[i] = "=";
                }
            }

            // Affichage du résultat dans une ligne unique:
            String codeValidation = validation[0];
            for (int i = 1; i < (nombreCases); i++) {
                codeValidation += (' ' + validation[i]);
            }
            System.out.println(codeValidation);


            // Définition des conditions de sortie de boucle
            int k = 0;
            for (String elementValidation : validation) {
                if ((elementValidation == ">") || (elementValidation == "<")){
                    k++;}
            }
            if (k == 0) {
                isCodeFound = true;
            }
        }


        if (isCodeFound == true) {
            System.out.println("Bravo, tu as trouvé la combinaison secrète!");
        } else {
            System.out.println("Tu as perdu!");
        }
    }


    public int[] generateurCode(int nombreCases) {
        // Méthode permettant de générer un code aléatoire de X chiffres
        ////// Génération d'un tableau vide de X cases
        int[] codeDefenseur = new int[nombreCases];
        ////// Remplissage du tableau par des chiffres aléatoires
        for (int i = 0; i < nombreCases; i++) {
            codeDefenseur[i] = (int) (10 * Math.random());
        }
//        for (int chiffreMystere : codeDefenseur) {
//            System.out.println(chiffreMystere);
//        }
        return codeDefenseur;
    }


    public boolean isInt(String maChaine) {
        // Méthode permettant de tester si une entrée String est aussi un entier
        char[] tab = maChaine.toCharArray(); // conversion d'un String en tableau de chars
        int k = 0;
        for (char carac : tab) {
            if (!Character.isDigit(carac))
                k--;
        }
        if (k < 0) {
            return false;
        }
        return true;
    }


}
