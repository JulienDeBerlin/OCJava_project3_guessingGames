package com.berthoud.oc_project3_gameclasses;

import java.util.Arrays;


/**
 *  Abstract class for Mastermind. It contains all the instance fields and methods required for all modes
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
    //GETTERS AND SETTERS all package-private//

    int getNbVariations() {
        return nbVariations;
    }

    int getDigitsFound() {
        return digitsFound;
    }

    int getDigitsPresent() {
        return digitsPresent;
    }

    void setNbVariations(int nbVariations) {
        this.nbVariations = nbVariations;
    }

    void setDigitsFound(int digitsFound) {
        this.digitsFound = digitsFound;
    }

    void setDigitsPresent(int digitsPresent) {
        this.digitsPresent = digitsPresent;
    }


    // _____________________________________________________________________________________________________________________
    // METHODS (SHARED BY ALL MODES)


    int[] codeInputUser() {

        String inputUser = scan.nextLine();

        // Vérifier que le code saisi contient le bon nombre de caractères ET
        // uniquement des chiffres de 0 à (x-1) (avec x = nbVariations)
        while ((inputUser.length() != getNbDigits()) || (!isInt(inputUser)) || (!inputInsideRange(inputUser, nbVariations))) {
            int k = nbVariations - 1;
            System.out.println("What do you mean? Please enter a combination of " + getNbDigits() + " digits from 0 to " + k);
            inputUser = scan.nextLine();
        }

        // Conversion du String codeInput en un tableau composé de int (codeChallenger)
        // ****************************************************************************************
        // POURQUOI ÇA MARCE PAS SANS CETTE LIGNE:
        setCodeProposal(new int[getNbDigits()]);
        // ****************************************************************************************

        for (int j = 0; j < getNbDigits(); j++) {
            setCodeProposal(j, Character.getNumericValue(inputUser.charAt(j)) );
        }

        return getCodeProposal();

    }

    protected int [] validation(int[] codeToBeFound, int[] codeProposal) {

        // Comparaison des 2 codes et affichage du résultat de la comparaison
        int[] copyCodeToBeFound = Arrays.copyOf(codeToBeFound, getNbDigits());

        digitsFound = 0;
        digitsPresent = 0;

        for (int i = 0; i < getNbDigits(); i++) {
            if (codeProposal[i] == copyCodeToBeFound[i]) {
                digitsFound++;
            } else {
                for (int k = 0; k < getNbDigits(); k++) {
                    if ((codeProposal[i] == copyCodeToBeFound[k]) && (i != k ) && (codeProposal[k] != copyCodeToBeFound[k])) {
                       digitsPresent++;
                       copyCodeToBeFound[k] = -1;
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

    protected int [] validation(byte[] codeToBeFound, int[] codeProposal) {

        // Comparaison des 2 codes et affichage du résultat de la comparaison
        byte[] copyCodeToBeFound = Arrays.copyOf(codeToBeFound, getNbDigits());

        digitsFound = 0;
        digitsPresent = 0;

        for (int i = 0; i < getNbDigits(); i++) {
            if (codeProposal[i] == copyCodeToBeFound[i]) {
                digitsFound++;
            } else {
                for (int k = 0; k < getNbDigits(); k++) {
                    if ((codeProposal[i] == copyCodeToBeFound[k]) && (i != k ) && (codeProposal[k] != copyCodeToBeFound[k])) {
                        digitsPresent++;
                        copyCodeToBeFound[k] = -1;
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



    protected boolean isInt(String myString) {
        // Méthode permettant de tester si une entrée String est aussi un entier
        char[] tab = myString.toCharArray(); // conversion d'un String en tableau de chars
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

    protected boolean inputInsideRange(String inputUser, int nbVariations) {
        int[] intArray = new int[getNbDigits()];
        int k = 0;
        for (int j = 0; j < getNbDigits(); j++) {
            intArray[j] = Character.getNumericValue(inputUser.charAt(j));
            if (intArray[j] > (nbVariations-1)) {
                k--;
            }
        }
        if (k < 0) {
            return false;
        }
        return true;
    }

    protected int[] randomCodeGenerator() {
        // Méthode permettant de générer un code aléatoire de X chiffres compris entre 0 à x (avec x = nbVariations et  3 <= x <= 9 )
        ////// Génération d'un tableau vide de X cases
        int[] randomCode = new int[getNbDigits()];
        ////// Remplissage du tableau par des chiffres aléatoires
        for (int i = 0; i < getNbDigits(); i++) {
            randomCode[i] = (int) (nbVariations * Math.random());
        }

        return randomCode;
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
    protected boolean testIsCodeFound() {
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
