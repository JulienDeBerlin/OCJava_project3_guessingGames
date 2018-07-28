package com.berthoud.oc_project3_gameclasses;

import com.berthoud.oc_project3_menu.Main;

import java.util.Arrays;


/**
 * Abstract class for Mastermind. It contains all the instance fields and methods required for all modes of Game2
 */
public abstract class Game2 extends Games {



// _____________________________________________________________________________________________________________________
    //INSTANCE FIELDS//


    /**
     * Number of digits found (right value and right position)
     */
    private int digitsFound;

    /**
     * Number of digits present (right value but wrong position)
     */
    private int digitsPresent;


// _____________________________________________________________________________________________________________________
    //CONSTRUCTORS//

    /**
     * Constructor
     *
     * @param nbDigits     number of digits of the code
     * @param maxGuesses   max number of guesses allowed
     * @param nbVariations number of value possible for each digit. Min = 4 , Max = 10
     */
    Game2(int nbDigits, int maxGuesses, int nbVariations) {
        super(nbDigits, maxGuesses, nbVariations);
        digitsFound = 0;
        digitsPresent = 0;
    }


// _____________________________________________________________________________________________________________________
    //GETTERS all package-private//


    int getDigitsFound() {
        return digitsFound;
    }

    int getDigitsPresent() {
        return digitsPresent;
    }


// _____________________________________________________________________________________________________________________
    //METHODS (SHARED BY ALL MODES)//


    /**
     * This methods compares codeTobeFound to codeProposal and return a validation indicating the number of digits found
     * (right digits on the right positions) and digits present (right digits on the wrong position)
     *
     * @param codeToBeFound the secret code
     * @param codeProposal  the code attempt
     * @return a two dimensional int array. 1st array for number of digits found and 2nd array for numbers of digit present
     */
    protected int[] validation(int[] codeToBeFound, int[] codeProposal) {

        int[] copyCodeToBeFound = Arrays.copyOf(codeToBeFound, getNbDigits());

        digitsFound = 0;
        digitsPresent = 0;

        for (int i = 0; i < getNbDigits(); i++) {
            if (codeProposal[i] == copyCodeToBeFound[i]) {
                digitsFound++;
            } else {
                for (int k = 0; k < getNbDigits(); k++) {
                    if ((codeProposal[i] == copyCodeToBeFound[k]) && (i != k) && (codeProposal[k] != copyCodeToBeFound[k])) {
                        digitsPresent++;
                        copyCodeToBeFound[k] = -1;
                        break;
                    }
                }
            }
        }
        int[] validation = new int[2];
        validation[0] = digitsFound;
        validation[1] = digitsPresent;
        return validation;
    }


    /**
     * This method overloads {@link #validation(int[], int[])} and takes as parameter codetobefound as a byte array instead of int array.
     *
     * @param codeToBeFound the secret code
     * @param codeProposal  the code attempt
     * @return a two dimensional int array. 1st array for number of digits found and 2nd array for numbers of digit present
     */
    protected int[] validation(byte[] codeToBeFound, int[] codeProposal) {

        byte[] copyCodeToBeFound = Arrays.copyOf(codeToBeFound, getNbDigits());

        digitsFound = 0;
        digitsPresent = 0;

        for (int i = 0; i < getNbDigits(); i++) {
            if (codeProposal[i] == copyCodeToBeFound[i]) {
                digitsFound++;
            } else {
                for (int k = 0; k < getNbDigits(); k++) {
                    if ((codeProposal[i] == copyCodeToBeFound[k]) && (i != k) && (codeProposal[k] != copyCodeToBeFound[k])) {
                        digitsPresent++;
                        copyCodeToBeFound[k] = -1;
                        break;
                    }
                }
            }
        }
        int[] validation = new int[2];
        validation[0] = digitsFound;
        validation[1] = digitsPresent;
        return validation;
    }


    /**
     * This methods generates a random code made of X digits, X being equal to value of {@link #nbDigits}. Each digit can take
     * {@link #nbVariations} different values.
     *
     * @return the random code
     */
    @Override
    protected int[] randomCodeGenerator() {
        int[] randomCode = new int[getNbDigits()];
        for (int i = 0; i < getNbDigits(); i++) {
            randomCode[i] = (int) (getNbVariations() * Math.random());
        }
        return randomCode;
    }


    /**
     * This method sets the instance field {@link #isCodeFound} to true if all the digits have been found at the right position
     *
     * @return true if the code has been found
     */
    boolean testIsCodeFound() {
        if (digitsFound == getNbDigits())
            setCodeFound(true);
        return isCodeFound();
    }


    /**
     * This method inputs the code entered by the player on the keyboard, tests that the code is only made of digits,
     * that the length of the input is identical to the value of instance field {@link #nbDigits}and additionally tests
     * that the nb of variations is valid {@link #nbVariations}
     *
     * @return a valid code entered by the player
     */
    @Override
    protected int[] codeInputUser() {

        String inputUser = scan.nextLine();
        Main.logger.debug("Input user= " + inputUser);

        while ((inputUser.length() != getNbDigits()) || (!MyTools.isMyStringAnInt(inputUser)) || (!inputInsideRange(inputUser, getNbVariations()))) {
            int k = getNbVariations() - 1;
            System.out.println("What do you mean? Please enter a combination of " + getNbDigits() + " digits from 0 to " + k);
            Main.logger.debug("Entry not valid.");
            inputUser = scan.nextLine();
            Main.logger.debug("New input use= " + inputUser);

        }

        // Conversion String into int array
        int[] codeInputUser = new int[getNbDigits()];
        for (int j = 0; j < getNbDigits(); j++) {
            codeInputUser[j] = Character.getNumericValue(inputUser.charAt(j));
        }
        return codeInputUser;
    }


    /**
     * This method is only required to test the validity of input player for game 2. The method is called within the method {@link #codeInputUser()}
     *
     * @param inputUser    Code entered by the player
     * @param nbVariations Number of values that each digit can possibly take. Only required for game 2
     * @return true or false
     */
    private boolean inputInsideRange(String inputUser, int nbVariations) {
        int[] intArray = new int[getNbDigits()];
        int k = 0;
        for (int j = 0; j < getNbDigits(); j++) {
            intArray[j] = Character.getNumericValue(inputUser.charAt(j));
            if (intArray[j] > (nbVariations - 1)) {
                k--;
            }
        }
        return k == 0;
    }

// _____________________________________________________________________________________________________________________

}
