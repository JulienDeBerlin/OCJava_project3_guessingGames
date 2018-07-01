package com.berthoud.oc_project3_gameclasses;

import java.util.Arrays;


/**
 *  Abstract class for Mastermind. It contains all the instance fields and methods required for both modes challenger and defender
 */
public abstract class Game2 extends Games{

// _____________________________________________________________________________________________________________________
    //INSTANCE FIELDS//

    private int nbVariations;
    private int digitsFound;
    private int digitsPresent;


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Default constructor
     */
    public Game2() {
    }


    /**
     * Constructor
     * @param nbDigits
     * @param maxGuesses
     * @param nbVariations
     */
    public Game2(int nbDigits, int maxGuesses, int nbVariations) {
        super(nbDigits, maxGuesses);
        this.nbVariations = nbVariations;
        digitsFound = 0;
        digitsPresent = 0;
    }


// _____________________________________________________________________________________________________________________
    //GETTERS AND SETTERS//

    public int getNbVariations() {
        return nbVariations;
    }

    public int getDigitsFound() {
        return digitsFound;
    }

    public int getDigitsPresent() {
        return digitsPresent;
    }

    public void setNbVariations(int nbVariations) {
        this.nbVariations = nbVariations;
    }

    public void setDigitsFound(int digitsFound) {
        this.digitsFound = digitsFound;
    }

    public void setDigitsPresent(int digitsPresent) {
        this.digitsPresent = digitsPresent;
    }


    // _____________________________________________________________________________________________________________________
    // METHODS (SHARED BY ALL MODES)


    protected int[] saisieCodeJoueur() {

        String inputJoueur = scan.nextLine();

        // Vérifier que le code saisi contient le bon nombre de caractères ET
        // uniquement des chiffres de 0 à (x-1) (avec x = nbVariations)
        while ((inputJoueur.length() != getNbDigits()) || (!isInt(inputJoueur)) || (!isColorValid(inputJoueur, nbVariations))) {
            int k = nbVariations - 1;
            System.out.println("Saisie non valide. Saisis une combinaison de " + getNbDigits() + " chiffres compris entre 0 à " + k);
            inputJoueur = scan.nextLine();
        }

        // Conversion du String codeInput en un tableau composé de int (codeChallenger)
        // ****************************************************************************************
        // POURQUOI ÇA MARCE PAS SANS CETTE LIGNE:
        setCodeProposal(new int[getNbDigits()]);
        // ****************************************************************************************

        for (int j = 0; j < getNbDigits(); j++) {
            setCodeProposal(j, Character.getNumericValue(inputJoueur.charAt(j)) );
        }

        return getCodeProposal();

    }

    protected int [] validation(int[] codeMysterieux, int[] tentativeCode) {

        // Comparaison des 2 codes et affichage du résultat de la comparaison
        int[] copieCodeMysterieux = Arrays.copyOf(codeMysterieux, getNbDigits());

        digitsFound = 0;
        digitsPresent = 0;

        for (int i = 0; i < getNbDigits(); i++) {
            if (tentativeCode[i] == copieCodeMysterieux[i]) {
                digitsFound++;
            } else {
                for (int k = 0; k < getNbDigits(); k++) {
                    if ((tentativeCode[i] == copieCodeMysterieux[k]) && (i != k ) && (tentativeCode[k] != copieCodeMysterieux[k])) {
                       digitsPresent++;
                       copieCodeMysterieux[k] = -1;
                       break;
                    }
                }
            }
        }
        int [] validation = new int [2];
        validation [0] = digitsFound;
        validation [1] = digitsPresent;
        return validation;
    }

    protected int [] validation(byte[] codeMysterieux, int[] tentativeCode) {

        // Comparaison des 2 codes et affichage du résultat de la comparaison
        byte[] copieCodeMysterieux = Arrays.copyOf(codeMysterieux, getNbDigits());

        digitsFound = 0;
        digitsPresent = 0;

        for (int i = 0; i < getNbDigits(); i++) {
            if (tentativeCode[i] == copieCodeMysterieux[i]) {
                digitsFound++;
            } else {
                for (int k = 0; k < getNbDigits(); k++) {
                    if ((tentativeCode[i] == copieCodeMysterieux[k]) && (i != k ) && (tentativeCode[k] != copieCodeMysterieux[k])) {
                        digitsPresent++;
                        copieCodeMysterieux[k] = -1;
                        break;
                    }
                }
            }
        }
        int [] validation = new int [2];
        validation [0] = digitsFound;
        validation [1] = digitsPresent;
        return validation;
    }



    protected void printValidation() {
        System.out.println(digitsFound + " couleurs bien placées \n" + digitsPresent + " couleurs présentes");
    }


    protected boolean isInt(String maChaine) {
        // Méthode permettant de tester si une entrée String est aussi un entier
        char[] tab = maChaine.toCharArray(); // conversion d'un String en tableau de chars
        int k = 0;
        for (char carac : tab) {
            if ((!Character.isDigit(carac)) || (carac < nbVariations))
                k--;
        }
        if (k < 0) {
            return false;
        }
        return true;
    }

    protected boolean isColorValid(String inputJoueur, int nombreCouleurs) {
        int[] intArray = new int[getNbDigits()];
        int k = 0;
        for (int j = 0; j < getNbDigits(); j++) {
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
        // Méthode permettant de générer un code aléatoire de X chiffres compris entre 0 à x (avec x = nbVariations et  3 <= x <= 9 )
        ////// Génération d'un tableau vide de X cases
        int[] codeAleatoire = new int[getNbDigits()];
        ////// Remplissage du tableau par des chiffres aléatoires
        for (int i = 0; i < getNbDigits(); i++) {
            codeAleatoire[i] = (int) (nbVariations * Math.random());
        }

        return codeAleatoire;
    }

    // Convertion String array to String
    protected String arrayToString(String myArray[]) {
        String myString = myArray[0];
        for (int i = 1; i < (getNbDigits()); i++) {
            myString += (' ' + myArray[i]);
        }
        return myString;
    }


    // Is code found?
    protected boolean testAndGetCodeFound() {
        if (digitsFound == getNbDigits())
            setCodeFound(true);
        return isCodeFound();
    }

    // Compteur du nombre de coups joués
    protected int increaseNombreEssais() {
        setNbGuesses(getNbGuesses()+1);
        return this.getNbGuesses();
    }

// _____________________________________________________________________________________________________________________

}
