package com.berthoud;

import java.util.Scanner;

public class Jeu1ModeDefense extends Jeu1 {
    private static String mode = "mode défense";
    private int nombreEssais = 0;

    public Jeu1ModeDefense(int pNombreCases, int pNombreEssaisMax) {
        super(pNombreCases, pNombreEssaisMax);
    }

    public void play() {

        System.out.println("Choisis une combinaison de " + nombreCases + " chiffres.");
        int[] codeMysterieux = super.saisieCodeJoueur();


        int rangeCodeMysterieux[][] = new int [2][nombreCases]; // Array 1 = minValue, Array 2 = maxValue
        for (int x = 0; x < nombreCases; x++){
            rangeCodeMysterieux [0][x] = 0;
            rangeCodeMysterieux [1][x] = 9;
        }

        int [] tentativeCode   = super.generateurCode(); //1ere réponse de l'ordinateur = chiffre aléatoire

        while ((nombreEssais < super.nombreEssaisMax) && (super.isCodeFound == false)) {

                // affichage du code sous forme d'une ligne:
                String[] tentativeCodeStringArray = new String[nombreCases];
                for (int x = 0; x < nombreCases; x++) {
                    tentativeCodeStringArray[x] = String.valueOf(tentativeCode[x]);
                }
                System.out.println("Superbrain te répond:");
                System.out.println(super.arrayToString(tentativeCodeStringArray));


            // saisie validation (+/-) par le joueur:
            System.out.println("A toi de saisir le code de validation (+/-)");

            Scanner scan = new Scanner(System.in);

            String validationJoueur = scan.nextLine();

            // vérifie que la validation (+/-) entrée par le joueur est bien correcte
            String[] validationOrdinateurStringArray = super.validation(codeMysterieux, tentativeCode);
            String validationOrdinateur = validationOrdinateurStringArray[0];
            for (int j = 1; j < nombreCases; j++) {
                validationOrdinateur += validationOrdinateurStringArray[j];
            }

            while (!validationJoueur.equals(validationOrdinateur)) {
                System.out.println("Oups... on dirait que le code (+/-) est erroné. Saisi le à nouveau.");
                validationJoueur = scan.nextLine();
            }

            rangeCodeMysterieux = updateRangeCodeMysterieux(rangeCodeMysterieux, tentativeCode, validationOrdinateurStringArray);
            tentativeCode = newTentativeCode(rangeCodeMysterieux, tentativeCode, validationOrdinateurStringArray);

            //   Définition des conditions de sortie de boucle
            super.isCodeFound(validationOrdinateurStringArray);
            nombreEssais ++;
        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.isCodeFound == true) {
            System.out.println("Superbrain a gagné!");
        } else {
            System.out.println("Tu as vaincu Superbrain!");
        }

    }

    // restriction de rangeCodeMysterieux apres validation d'une tentative:
    private int [] [] updateRangeCodeMysterieux (int [][]range, int [] tentative, String [] validation ) {
        for (int x = 0; x < nombreCases; x++) {
            if (validation[x] == ">") {
                range[0][x] = tentative[x];
            }
            if (validation[x] == "<") {
                range[1][x] = tentative[x];
            }
        } return range;
    }

    // Proposition d'un nouveau code en réponse à la validation précédente:
    private int [] newTentativeCode (int [][] newRange, int [] tentative, String [] validation){
        for (int x = 0; x < nombreCases; x++){
            if (validation[x] != "="){
                tentative [x] = (newRange [0][x] + newRange [1][x])/2;
            }
        }
        return tentative;
    }


//
//
//    // Méthode pour vérifier si un entier est pair
//    private boolean isPair (int x){
//        if (x % 2 == 0){
//            return true;
//        } return false;
//    }

}