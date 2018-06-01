package com.berthoud;

import java.util.Scanner;

public abstract class Jeu1 {
    private String name;
    protected String mode;
    protected int nombreCases;
    protected int nombreEssaisMax;
    protected int nombreEssais;
    protected boolean isCodeFound;
    protected int [] codeMysterieux = new int [nombreCases];
    protected int [] tentativeCode = new int [nombreCases];


    public Jeu1(int pNombreCases, int pNombreEssaisMax) {
        name = "Code +/-";
        nombreCases = pNombreCases;
        nombreEssaisMax = pNombreEssaisMax;
        nombreEssais = 0;
        isCodeFound = false;
    }

    public abstract void play();


    protected int[] saisieCodeJoueur() {

        // Saisie dun code entré par le joueur (code input) de X chiffre
        Scanner scan = new Scanner(System.in);
        String inputJoueur = scan.nextLine();

        // Vérifier que le code saisi contient le bon nombre de caractères ET
        // uniquement des chiffres de 0 à 9
        while ((inputJoueur.length() != nombreCases) || (!isInt(inputJoueur))) {
            System.out.println("Saisie non valide. Saisis une combinaison de " + nombreCases + " chiffres.");
            inputJoueur = scan.nextLine();
        }

        // Conversion du String codeInput en un tableau composé de int (codeChallenger)
        int[] codeJoueur = new int[nombreCases];
        for (int j = 0; j < nombreCases; j++) {
            codeJoueur[j] = Character.getNumericValue(inputJoueur.charAt(j));
        }
        return codeJoueur;
    }

    protected String[] validation(int[] codeMysterieux, int[] tentativeCode) {

        // Comparaison des 2 codes et affichage du résultat de la comparaison (création d'un tableau)
        String[] validation = new String[nombreCases];
        for (int i = 0; i < nombreCases; i++) {
            if (codeMysterieux[i] > tentativeCode[i]) {
                validation[i] = ">";
            } else if (codeMysterieux[i] < tentativeCode[i]) {
                validation[i] = "<";
            } else {
                validation[i] = "=";
            }
        }
        return validation;
    }


    protected boolean isInt(String maChaine) {
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

    protected int[] generateurCode() {
        // Méthode permettant de générer un code aléatoire de X chiffres
        ////// Génération d'un tableau vide de X cases
        int[] codeMysterieux = new int[nombreCases];
        ////// Remplissage du tableau par des chiffres aléatoires
        for (int i = 0; i < nombreCases; i++) {
            codeMysterieux[i] = (int) (10 * Math.random());
        }
//        for (int chiffreMystere : codeMysterieux) {
//            System.out.println(chiffreMystere);
//        }
        return codeMysterieux;
    }

    // Convertion String array to String
    protected String arrayToString(String myArray[]) {
        String myString = myArray[0];
        for (int i = 1; i < (nombreCases); i++) {
            myString += (' ' + myArray[i]);
        }
        return myString;
    }


    // Is code found?
    protected void isCodeFound(String [] validation) {
        int k = 0;
        for (String elementValidation : validation) {
            if ((elementValidation == ">") || (elementValidation == "<")) {
                k++;
            }
        }
        if (k == 0) {
           isCodeFound = true;
        }

    }

    // Compteur du nombre de coups joués
    protected int increaseNombreEssais (){
        this.nombreEssais++;
        return this.nombreEssais;
    }

}

