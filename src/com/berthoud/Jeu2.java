package com.berthoud;

import java.util.Arrays;
import java.util.Scanner;

public abstract class Jeu2 {
    protected String mode;
    protected int nombreCases;
    protected int nombreEssaisMax;
    protected int nombreCouleurs;
    protected int nombreEssais;
    protected int bienPlacé;
    protected int couleurOk;
    protected boolean isCodeFound;
    protected int[] codeMysterieux = new int[nombreCases];
    protected int[] tentativeCode = new int[nombreCases];
    protected Scanner scan = new Scanner(System.in);


    public Jeu2(int pNombreCases, int pNombreEssaisMax, int pNombreCouleurs) {
        String name = "Mastermind";
        nombreCases = pNombreCases;
        nombreEssaisMax = pNombreEssaisMax;
        nombreCouleurs = pNombreCouleurs;
        nombreEssais = 0;
        isCodeFound = false;
        bienPlacé = 0;
        couleurOk = 0;
    }

    public abstract void play();


    protected int[] saisieCodeJoueur() {

        // Saisie dun code entré par le joueur (code input) de X chiffre
        String inputJoueur = scan.nextLine();

        // Vérifier que le code saisi contient le bon nombre de caractères ET
        // uniquement des chiffres de 0 à (x-1) (avec x = nombreCouleurs)
        while ((inputJoueur.length() != nombreCases) || (!isInt(inputJoueur)) || (!isColorValid(inputJoueur, nombreCouleurs))) {
            int k = nombreCouleurs - 1;
            System.out.println("Saisie non valide. Saisis une combinaison de " + nombreCases + " chiffres compris entre 0 à " + k);
            inputJoueur = scan.nextLine();
        }

        // Conversion du String codeInput en un tableau composé de int (codeChallenger)
        tentativeCode = new int[nombreCases];
        for (int j = 0; j < nombreCases; j++) {
            tentativeCode[j] = Character.getNumericValue(inputJoueur.charAt(j));
        }
        return tentativeCode;
    }

    protected int [] validation(int[] codeMysterieux, int[] tentativeCode) {

        // Comparaison des 2 codes et affichage du résultat de la comparaison
        int[] copieCodeMysterieux = Arrays.copyOf(codeMysterieux, nombreCases);

        bienPlacé = 0;
        couleurOk = 0;

        for (int i = 0; i < nombreCases; i++) {
            if (tentativeCode[i] == copieCodeMysterieux[i]) {
                bienPlacé++;
            } else {
                for (int k = 0; k < nombreCases; k++) {
                    if ((tentativeCode[i] == copieCodeMysterieux[k]) && (i != k ) && (tentativeCode[k] != copieCodeMysterieux[k])) {
                       couleurOk++;
                       copieCodeMysterieux[k] = -1;
                       break;
                    }
                }
            }
        }
        int [] validation = new int [2];
        validation [0] = bienPlacé;
        validation [1] = couleurOk;
        return validation;
    }

    protected int [] validation(byte[] codeMysterieux, int[] tentativeCode) {

        // Comparaison des 2 codes et affichage du résultat de la comparaison
        byte[] copieCodeMysterieux = Arrays.copyOf(codeMysterieux, nombreCases);

        bienPlacé = 0;
        couleurOk = 0;

        for (int i = 0; i < nombreCases; i++) {
            if (tentativeCode[i] == copieCodeMysterieux[i]) {
                bienPlacé++;
            } else {
                for (int k = 0; k < nombreCases; k++) {
                    if ((tentativeCode[i] == copieCodeMysterieux[k]) && (i != k ) && (tentativeCode[k] != copieCodeMysterieux[k])) {
                        couleurOk++;
                        copieCodeMysterieux[k] = -1;
                        break;
                    }
                }
            }
        }
        int [] validation = new int [2];
        validation [0] = bienPlacé;
        validation [1] = couleurOk;
        return validation;
    }







    protected void printValidation() {
        System.out.println(bienPlacé + " couleurs bien placées \n" + couleurOk + " couleurs présentes");
    }


    protected boolean isInt(String maChaine) {
        // Méthode permettant de tester si une entrée String est aussi un entier
        char[] tab = maChaine.toCharArray(); // conversion d'un String en tableau de chars
        int k = 0;
        for (char carac : tab) {
            if ((!Character.isDigit(carac)) || (carac < nombreCouleurs))
                k--;
        }
        if (k < 0) {
            return false;
        }
        return true;
    }

    protected boolean isColorValid(String inputJoueur, int nombreCouleurs) {
        int[] intArray = new int[nombreCases];
        int k = 0;
        for (int j = 0; j < nombreCases; j++) {
            intArray[j] = Character.getNumericValue(inputJoueur.charAt(j));
            if (intArray[j] > (nombreCouleurs-1)) {
                k--;
            }
        }
        if (k < 0) {
            return false;
        }
        return true;
    }

    protected int[] generateurCode() {
        // Méthode permettant de générer un code aléatoire de X chiffres compris entre 0 à x (avec x = nombreCouleurs et  3 <= x <= 9 )
        ////// Génération d'un tableau vide de X cases
        int[] codeAleatoire = new int[nombreCases];
        ////// Remplissage du tableau par des chiffres aléatoires
        for (int i = 0; i < nombreCases; i++) {
            codeAleatoire[i] = (int) (nombreCouleurs * Math.random());
        }

        return codeAleatoire;
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
    protected void isCodeFound() {
        if (bienPlacé == nombreCases)
            isCodeFound = true;
    }

    // Compteur du nombre de coups joués
    protected int increaseNombreEssais() {
        this.nombreEssais++;
        return this.nombreEssais;
    }


}
